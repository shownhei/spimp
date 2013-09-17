define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	Utils.select.remote([ 'create-accidentType', 'edit-accidentType', 'accidentTypeSelect' ], '/ercs/dictionaries?typeCode=plan_type&list=true', 'id',
			'itemName');

	// 配置表格列
	var fields = [ {
		header : '事故地点',
		name : 'accidentLocation'
	}, {
		header : '事故类型',
		name : 'accidentType'
	}, {
		header : '严重程度',
		name : 'severity'
	}, {
		header : '报警人',
		name : 'alarmPeople'
	}, {
		header : '报警时间',
		name : 'alarmTime'
	}, {
		header : '接警流水号',
		name : 'serialNumber'
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
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

	// 新建
	$('#create').click(function() {
		Utils.modal.reset('create');
		Utils.modal.show('create');
	});

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');

		// 验证
		if (object.credential === '') {
			Utils.modal.message('create', [ '请输入密码' ]);
			return;
		}

		// 处理属性

		$.post('/ercs/alarms', JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.hide('create');
			} else {
				Utils.modal.message('create', data.errors);
			}
		});
	});

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
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		var selectId = grid.selectedData('id');

		$.put('/ercs/alarms' + selectId, JSON.stringify(object), function(data) {
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
		$.del('/ercs/alarms' + selectId, function(data) {
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
	var idarray=[];
	function openDialog(alarmId){
		var idStr=alarmId+'';
		var len=idarray.length;
		var html='<div id="d'+idStr+'" style=" z-index:9001;border-color: black;border-width:1x;border-style: solid;position:absolute;top:'+(100+len*2)+'px;left:'+(100+len*4)+'px;width:400px;height:200px;background-color:red;" >'+idStr+'</div>';
		onclick="$(\'#d'+idStr+'\').remove();";
		$(html).appendTo($('#test'));
		//触发关闭操作
		$("#d"+idStr).bind('click',{id:idStr},function(event){
			$.ajax({
				url:'/ercs/alarm/closealarm',
				data:'alarmId='+event.data.id,
				success:function(){
					
				}
			});
		});
	}
	function asynGet() {
		$.ajax({
			type : 'GET',
			url : '/ercs/alarm/waitalarm',
			cache : false,
			data:'idArray='+idarray.join(','),
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				var newIdArray=data.newAlarmList;
				var len = idarray.length;
				for(var i=0;i<newIdArray.length;i++){
					idarray.push(newIdArray[i]);
					openDialog(newIdArray[i]);
				}
				Utils.modal.show('create');
				asynGet();
			},
			error : function(data) {
				asynGet();
			}
		});
	}
	var asynClose=function() {
		$.ajax({
			type : 'GET',
			url : '/ercs/alarm/waitclose',
			cache : false,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				var alarmId=data.newAlarmList[0];
				$('#d'+alarmId).hide();
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