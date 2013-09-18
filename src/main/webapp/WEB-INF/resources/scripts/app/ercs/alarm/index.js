define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	Utils.select.remote([ 'accidentTypeSelect' ],
			'/ercs/dictionaries?typeCode=accident_category&list=true', 'id',
			'itemName');

	// 配置表格列
	var fields = [ {
		header : '事故地点',
		name : 'accidentLocation'
	}, {
		header : '事故类型',
		name : 'accidentType',
		render : function(val) {
			if (val===undefined||val===''||val==null) {
				return '';
			} else {
				return val.itemName;
			}
		}
	}, {
		header : '严重程度',
		name : 'severity'
	}, {
		header : '处理状态',
		name : 'dealFlag',
		render : function(val) {
			return val === undefined ? '' : (val === 0 ? '未处理' : '已处理')
		}
	}, {
		header : '报警人',
		name : 'alarmPeople'
	}, {
		header : '报警时间',
		name : 'alarmTime'
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height()
			- ($('.navbar').height() + $('.page-toolbar').height()
					+ $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 21);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected) {
			Utils.button.enable([ 'edit', 'remove' ]);
		} else {
			Utils.button.disable([ 'edit', 'remove' ]);
		}

	}

	// 配置表格
	var defaultUrl = contextPath
			+ '/ercs/alarms?orderBy=id&order=desc&pageSize=' + pageSize;
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

	// 编辑
	$('#edit').click(function() {
		if (Utils.button.isDisable('edit')) {
			return;
		}

		Utils.modal.reset('edit');

		var selectId = grid.selectedData('id');
		$.get('/ercs/alarms' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(
			function() {
				var object = Utils.form.serialize('edit');
				var selectId = grid.selectedData('id');
				$.put('/ercs/alarms' + selectId, JSON.stringify(object),
						function(data) {
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

	var idarray = [];
	function openDialog(alarmId) {
		$
				.ajax({
					url : '/ercs/alarms/' + alarmId,
					success : function(data) {
						var raw = data.data;
						var template = Handlebars.compile($(
								'#alarmwindow-template').html());
						var html = template(raw);
						$(html).appendTo($('body'));
						delete raw.accidentType;
						Utils.modal.show('edit' + raw.id);
						Utils.select
								.remote(
										[ 'edit' + raw.id + '-accidentType' ],
										'/ercs/dictionaries?typeCode=accident_category&list=true',
										'id', 'itemName');
						// 事件绑定
						$('#edit' + raw.id + '-save').bind(
								'click',
								{
									alarmId : raw.id
								},
								function(event) {
									var object = Utils.form.serialize('edit'
											+ event.data.alarmId);
									var selectId = event.data.alarmId;
									$.put('/ercs/alarms/' + selectId, JSON
											.stringify(object), function(data) {
										if (data.success) {
											Utils.modal.hide('edit'
													+ event.data.alarmId);
										} else {
											Utils.modal.message('edit'
													+ event.data.alarmId,
													data.errors);
										}
									});
								});// bind end
						Utils.form.fill('edit' + raw.id, raw);
					}
				});
	}
	function asynGet() {
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
				for ( var i = 0; i < newIdArray.length; i++) {
					idarray.push(newIdArray[i]);
					openDialog(newIdArray[i]);
				}
				asynGet();
			},
			error : function(data) {
				asynGet();
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
			error : function(data) {
				asynClose();
			}
		});
	}
	asynGet();
	asynClose();

});