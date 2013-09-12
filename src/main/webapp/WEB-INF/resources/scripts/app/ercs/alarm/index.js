define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
     Utils.select.remote([ 'create-accidentType','edit-accidentType','accidentTypeSelect' ], '/ercs/dictionaries?typeCode=plan_type&list=true', 'id', 'itemName');	

	// 配置表格列
	var fields = [{
	            header : '事故地点',
	            name : 'accidentLocation'
	        },{
	            header : '事故类型',
	            name : 'accidentType'
	        },{
	            header : '严重程度',
	            name : 'severity'
	        },{
	            header : '报警人',
	            name : 'alarmPeople'
	        },{
	            header : '报警时间',
	            name : 'alarmTime'
	        },{
	            header : '接警流水号',
	            name : 'serialNumber'
	        }];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 20);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
	    if (selected ) {
		   Utils.button.enable([ 'edit', 'remove' ]);
		}else{
		   Utils.button.disable([ 'edit', 'remove' ]);
		}
		
	}

	// 配置表格
	var defaultUrl = contextPath + '/ercs/alarms?orderBy=id&order=desc&pageSize='+pageSize;
	var grid = new Grid({
		parentNode : '#alarm-table',
		url : defaultUrl,
		model : {
			needOrder:true,
			fields : fields,
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
		$('#planName')[0].focus();
	});

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');

		// 验证
		if (object.credential === '') {
			Utils.modal.message('create', [ '请输入密码' ]);
			return;
		}
		if (object.checkCredential === '') {
			Utils.modal.message('create', [ '请输入确认密码' ]);
			return;
		}
		if (object.credential !== object.checkCredential) {
			Utils.modal.message('create', [ '两次输入的密码不一致，请重新输入' ]);
			return;
		}

		// 处理属性
		if (object.locked === 'on') {
			object.locked = true;
		} else {
			object.locked = false;
		}
		delete object.checkCredential;

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
			$('#edit-form input[name="planName"]')[0].focus();
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		// 处理属性
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


	/**
	 * 更新部分属性
	 */
	function update(locked, credential) {
		var selectId = grid.selectedData('id');
		$.get('/ercs/alarms' + selectId, function(data) {
			var object = data.data;
			object.credential = credential;
			if (locked !== null) {
				object.locked = locked;
			}
			object.groupEntity = {
				id : object.groupEntity.id
			};
			object.roleEntity = {
				id : object.roleEntity.id
			};

			$.put('/ercs/alarms/' + selectId, JSON.stringify(object), function(data) {
				if (data.success === true) {
					grid.refresh();
				}
			});
		});
	}

	// 搜索
	$('#nav-search-button').click(function() {
		grid.set({
			url : defaultUrl + '&' + $('#search-form').serialize()
		});
	});
	
});