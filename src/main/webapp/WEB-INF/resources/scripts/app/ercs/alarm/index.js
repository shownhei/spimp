define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	Utils.select.remote([ 'accidentTypeSelect', 'edit-accidentType', 'view-accidentType' ], '/system/dictionaries?typeCode=accident_category&list=true', 'id',
			'itemName', true, "请选择事故类型...");
	Utils.select.remote([ 'accidentLevelSelect' ], '/system/dictionaries?typeCode=accident_level&list=true', 'id', 'itemName', true, "请选择严重程度...");
	Utils.select.remote([ 'edit-accidentLevel', 'view-accidentLevel' ], '/system/dictionaries?typeCode=accident_level&list=true', 'id', 'itemName');

	$('#accidentTypeSelect').bind('change', function() {
		var val = $(this).children('option:selected').val();
		$('#nav-search-button').trigger('click');
	});
	$('#accidentLevelSelect').bind('change', function() {
		var val = $(this).children('option:selected').val();
		$('#nav-search-button').trigger('click');
	});
	$('#dealFlagSelect').bind('change', function() {
		var val = $(this).children('option:selected').val();
		$('#nav-search-button').trigger('click');
	});
	// 配置表格列
	var fields = [ {
		header : '事故地点',
		name : 'accidentLocation'
	}, {
		header : '事故类型',
		name : 'accidentType',
		width : 100,
		render : function(val) {
			return val ? val.itemName : '';
		}
	}, {
		header : '严重程度',
		name : 'accidentLevel',
		render : function(val) {
			return val ? '<div style="color:red;">' + val.itemName + '</div>' : '';
		}
	}, {
		header : '处理状态',
		name : 'dealFlag',
		width : 120,
		render : function(val) {
			switch (val) {
				case undefined:
					return '';
				case 0:
					return '未处理';
				case 2:
					return '误报';
				default:
					return '已处理';
			}
		}
	}, {
		header : '报警人',
		width : 120,
		name : 'alarmPeople'
	}, {
		header : '报警时间',
		width : 150,
		name : 'alarmTime'
	}, {
		header : '处理时间',
		width : 150,
		name : 'processingTime'
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 21);

	/**
	 * 修改/重置按钮状态
	 */

	function changeButtonsStatus(selected, data) {
		Utils.button.disable([ 'edit' ]);
		if (data) {
			if (data.dealFlag === 0) {
				Utils.button.enable([ 'edit' ]);
			}
		}
		if (selected) {
			Utils.button.enable([ 'remove', 'view' ]);
		} else {
			Utils.button.disable([ 'remove', 'view' ]);
		}

	}
	$('#create').click(function() {
		Utils.button.disable([ 'create' ]);
		$.ajax({
			url : '/ercs/alarm/putalarm',
			success : function(data) {
				Utils.button.enable([ 'create' ]);
			}
		});
	});
	// 配置表格
	var defaultUrl = contextPath + '/ercs/alarms?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#alarm-table',
		url : defaultUrl,
		model : {
			fields : fields,
			needOrder : true,
			orderWidth : 50,
			height : gridHeight
		},
		onClick : function(target, data) {
			changeButtonsStatus(this.selected, data);
		},
		onLoaded : function() {
			changeButtonsStatus();
		}
	}).render();

	// 根据严重程度渲染行颜色

	function renderRowColor() {
		var records = grid.data.result;
		var rows = grid.$('.grid-row');
		for (var i = 0; i < rows.length; i++) {
			var raw = records[i];
			$(rows[i]).removeClass('grid-row-alt');
			if (raw.accidentLevel.itemValue === '1') {
				rows[i].bgColor = "red";
			} else if (raw.accidentLevel.itemValue === '2') {
				rows[i].bgColor = "yellow";
			} else if (raw.accidentLevel.itemValue === '3') {
				rows[i].bgColor = "green";
			}
		}
	}
	// 编辑
	$('#edit').click(function() {
		if (Utils.button.isDisable('edit')) {
			return;
		}

		Utils.modal.reset('edit');
		var selectId = grid.selectedData('id');
		$.get('/ercs/alarms/' + selectId, function(data) {
			var object = data.data;
			var raw = object;
			if (!raw.accidentType) {
				raw.accidentType = {};
			}
			if (!raw.accidentLevel) {
				raw.accidentLevel = {};
			}
			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});
	$('#view').click(

	function() {
		if (Utils.button.isDisable('view')) {
			return;
		}
		var selectId = grid.selectedData('id');

		$.get('/ercs/alarms/' + selectId, function(data) {
			var object = data.data;
			var template = Handlebars.compile($('#viewwindow-template').html());
			var html = template(object);
			$(html).appendTo($('body'));
			Utils.form.fill('view', object);
			Utils.modal.show('view');
		});
	});
	// 更新
	$('#edit-save').click(

	function() {
		var object = Utils.form.serialize('edit');
		var selectId = grid.selectedData('id');
		if (object.accidentLevel === '') {
			Utils.modal.message('edit', [ '请输入事故等级' ]);
			return;
		}
		$.put('/ercs/alarms/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.hide('edit');
			} else {
				Utils.modal.message('edit', data.errors);
			}
		});
	});

	// 删除
	$('#remove').click(function() {
		if (Utils.button.isDisable('remove')) {
			return;
		}
		// 未处理的记录不能删除
		var row = grid.selectedData();
		if (row.dealFlag === 0) {
			Utils.modal.show('warning');
			return false;
		}
		Utils.modal.show('remove');
	});

	// 删除确认
	$('#remove-save').click(function() {
		var selectId = grid.selectedData('id');
		$.del('/ercs/alarms/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	// 搜索
	$('#nav-search-button').click(function() {
		grid.set({
			url : defaultUrl + '&' + $('#search-form').serialize()
		});
	});
	$('#nav-reset-button').click(function() {
		$('#accidentTypeSelect').val('');
		$('#accidentLevelSelect').val('');
		$('#dealFlagSelect').val('');
		grid.set({
			url : defaultUrl
		});
	});
	var idarray = [];

	function openDialog(alarmId) {
		$.ajax({
			url : '/ercs/alarms/' + alarmId,
			success : function(data) {
				grid.refresh();
				var raw = data.data;
				var template = Handlebars.compile($('#alarmwindow-template').html());
				var html = template(raw);
				$(html).appendTo($('body'));
				if (!raw.accidentType) {
					raw.accidentType = {};
				}
				if (!raw.accidentLevel) {
					raw.accidentLevel = {};
				}
				Utils.modal.show('edit' + raw.id);
				Utils.select.remote([ 'edit' + raw.id + '-accidentType' ], '/system/dictionaries?typeCode=accident_category&list=true', 'id', 'itemName');
				Utils.select.remote([ 'edit' + raw.id + '-accidentLevel' ], '/system/dictionaries?typeCode=accident_level&list=true', 'id', 'itemName');
				// 事件绑定
				$('#edit' + raw.id + '-save').bind('click', {
					alarmId : raw.id
				}, function(event) {
					var object = Utils.form.serialize('edit' + event.data.alarmId);
					if (object.accidentLocation === '') {
						Utils.modal.message('edit' + event.data.alarmId, [ "事故地点不能为空" ]);
						return false;
					}
					var selectId = event.data.alarmId;
					$.put('/ercs/alarms/' + selectId, JSON.stringify(object), function(data) {
						if (data.success) {
							grid.refresh();
							Utils.modal.hide('edit' + event.data.alarmId);
						} else {
							Utils.modal.message('edit' + event.data.alarmId, data.errors);
						}
					});
				});// edit-save
				$('#edit' + raw.id + '-miss').bind('click', {
					alarmId : raw.id
				}, function(event) {
					var object = Utils.form.serialize('edit' + event.data.alarmId);
					if (object.accidentLocation === '') {
						Utils.modal.message('edit' + event.data.alarmId, [ "事故地点不能为空" ]);
						return false;
					}
					object.dealFlag = 2;
					delete object.accidentLevel;
					delete object.accidentType;
					var selectId = event.data.alarmId;
					$.put('/ercs/alarms/' + selectId, JSON.stringify(object), function(data) {
						if (data.success) {
							grid.refresh();
							Utils.modal.hide('edit' + event.data.alarmId);
						} else {
							Utils.modal.message('edit' + event.data.alarmId, data.errors);
						}
					});
				});// edit-save
				Utils.form.fill('edit' + raw.id, raw);
			}
		});
	}

	function asynGet() {
		window.atAlarmPage=true;
		$.ajax({
			type : 'GET',
			url : '/ercs/alarm/waitalarm',
			cache : false,
			data : 'idArray=' + idarray.join(','),
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				var newIdArray = data.alarmList;
				var len = idarray.length;
				for (var i = 0; i < newIdArray.length; i++) {
					if(idarray.indexOf(newIdArray[i])===-1){
						idarray.push(newIdArray[i]);
						openDialog(newIdArray[i]);
					}
				}
				asynGet();
			},
			error : function(data, textStatus) {
				//alert(textStatus);
				if (textStatus !== 'error') {
					asynGet();
					//setTimeout(asynGet,50000);
				}
			}
		});
	}
	var asynClose = function() {
		$.ajax({
			type : 'GET',
			url : '/ercs/alarm/waitclose',
			cache : false,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				var alarmId = data.alarmList[0];
				Utils.modal.hide('edit' + alarmId);
				asynClose();
			},
			error : function(data, textStatus) {
				if (textStatus !== 'error') {
					asynClose();
				}
			}
		});
	};
	asynGet();
	asynClose();
});