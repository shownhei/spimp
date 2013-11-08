define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	Utils.input.date('input[type=datetime]');
	window.$=$;
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 配置表格列
	var fields = [ {
		header : '资源名称',
		name : 'resourceName'
	}, {
		header : '编号',
		name : 'resourceNo'
	}, {
		header : '数量',
		width : 50,
		name : 'amount'
	}, {
		header : '用途',
		name : 'function'
	}, {
		header : '型号',
		name : 'model'
	}, {
		header : '产地',
		name : 'origin'
	}, {
		header : '购买时间',
		width : 70,
		name : 'butTime'
	}, {
		header : '有效使用时间',
		name : 'expiration'
	}, {
		header : '存放位置',
		width : 70,
		name : 'location'
	}, {
		header : '管理人员',
		width : 70,
		name : 'manager'
	}, {
		header : '手机',
		name : 'telephone'
	}, {
		header : '备注',
		name : 'remark'
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
	var defaultUrl = contextPath + '/ercs/emergency-resources?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#material-table',
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
		if (object.resourceName === '') {
			Utils.modal.message('create', [ '请输入资源名称' ]);
			
			return;
		}
		if (object.resourceNo === '') {
			Utils.modal.message('create', [ '请输入资源编号' ]);
			return;
		}
		$.post('/ercs/emergency-resources', JSON.stringify(object), function(data) {
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
		$.get('/ercs/emergency-resources/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
			if (object.department.name) {
				$('#edit_department').val(object.department.name);
				$('#edit_department').attr('data-id', object.department.id);
			}
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		if (object.resourceName === '') {
			Utils.modal.message('edit', [ '请输入资源名称' ]);
			return;
		}
		if (object.resourceNo === '') {
			Utils.modal.message('edit', [ '请输入资源编号' ]);
			return;
		}
		// 处理属性
		var selectId = grid.selectedData('id');
		$.put('/ercs/emergency-resources/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('/ercs/emergency-resources/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	// 搜索
	$('#nav-search-button').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});
});
