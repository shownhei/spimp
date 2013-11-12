define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	Utils.select.remote([ 'create-organizationType', 'edit-organizationType' ], '/system/dictionaries?typeCode=organization_type&list=true', 'id', 'itemName');

	// 配置表格列
	var fields = [ {
		header : '资源名称',
		name : 'organizationName'
	}, {
		header : '资源类型',
		name : 'organizationType',
		render : function(v) {
			return v ? v.itemName : '';
		}
	}, {
		header : '行政区划分',
		name : 'administrativeDivision'
	}, {
		header : '救援资质',
		name : 'qualification'
	}, {
		header : '负责人',
		name : 'personInCharge'
	}, {
		header : '固话',
		name : 'telephone'
	}, {
		header : '手机',
		name : 'mobilePhone'
	}, {
		header : '备注',
		name : 'remark'
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 20);

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
	var defaultUrl = contextPath + '/ercs/safegard-organizations?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#safegardOrganization-table',
		url : defaultUrl,
		model : {
			needOrder : true,
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
		if (object.organizationName === '') {
			Utils.modal.message('create', [ '请输入资源名称' ]);
			return;
		}
		if ($('#create-organizationType').val() === null) {
			Utils.modal.message('create', [ '请输入资源类型' ]);
			return;
		}
		if (object.administrativeDivision === '') {
			Utils.modal.message('create', [ '请输入行政区划分' ]);
			return;
		}
		if (object.personInCharge === '') {
			Utils.modal.message('create', [ '请输入负责人' ]);
			return;
		}
		if (object.mobilePhone === '') {
			Utils.modal.message('create', [ '请输入手机' ]);
			return;
		}
		$.post('/ercs/safegard-organizations', JSON.stringify(object), function(data) {
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
		$.get('/ercs/safegard-organizations/' + selectId, function(data) {
			Utils.form.fill('edit', data.data);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		// 处理属性
		var selectId = grid.selectedData('id');
		if (object.organizationName === '') {
			Utils.modal.message('edit', [ '请输入资源名称' ]);
			return;
		}
		if ($('#edit-organizationType').val() === null) {
			Utils.modal.message('edit', [ '请输入资源类型' ]);
			return;
		}
		if (object.administrativeDivision === '') {
			Utils.modal.message('edit', [ '请输入行政区划分' ]);
			return;
		}
		if (object.personInCharge === '') {
			Utils.modal.message('edit', [ '请输入负责人' ]);
			return;
		}
		if (object.mobilePhone === '') {
			Utils.modal.message('edit', [ '请输入手机' ]);
			return;
		}
		$.put('/ercs/safegard-organizations/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('/ercs/safegard-organizations/' + selectId, function(data) {
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

});