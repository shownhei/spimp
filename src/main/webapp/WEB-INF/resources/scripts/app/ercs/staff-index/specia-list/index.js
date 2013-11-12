define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	Utils.input.date('input[type=datetime]');
	Utils.select
			.remote([ 'create-specialty', 'edit-specialty', 'specialtySelect' ], '/system/dictionaries?typeCode=expertise_area&list=true', 'id', 'itemName');

	// 配置表格列
	var fields = [ {
		header : '人员名称',
		name : 'name'
	}, {
		header : '性别',
		render : function(v) {
			return v !== null ? (v === 1 ? '男' : '女') : '--';
		},
		name : 'gender'
	}, {
		header : '出生日期',
		name : 'birthDay'
	}, {
		header : '专业类型',
		render : function(v) {
			return v ? v.itemName : '';
		},
		name : 'specialty'
	}, {
		header : '健康状况',
		name : 'physicalCondition'
	}, {
		header : '手机',
		name : 'mobile'
	}, {
		header : '住址',
		name : 'address'
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
	var defaultUrl = contextPath + '/ercs/specia-lists?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#refuge-table',
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
		if (object.name === '') {
			Utils.modal.message('create', [ '请输入专家名称' ]);
			return;
		}
		if (object.specialty === '') {
			Utils.modal.message('create', [ '请输入专业类型' ]);
			return;
		}
		if (object.mobile === '') {
			Utils.modal.message('create', [ '请输入联系方式' ]);
			return;
		}
		$.post('/ercs/specia-lists', JSON.stringify(object), function(data) {
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
		$.get('/ercs/specia-lists/' + selectId, function(data) {
			var object = data.data;
			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		if (object.name === '') {
			Utils.modal.message('edit', [ '请输入专家名称' ]);
			return;
		}
		if (object.specialty === '') {
			Utils.modal.message('edit', [ '请输入专业类型' ]);
			return;
		}
		if (object.mobile === '') {
			Utils.modal.message('edit', [ '请输入联系方式' ]);
			return;
		}
		// 处理属性
		var selectId = grid.selectedData('id');
		$.put('/ercs/specia-lists/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('/ercs/specia-lists/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});
	$('#specialtySelect').bind('change', function() {
		$('#nav-search-button').trigger('click');
	});
	// 搜索
	$('#nav-search-button').click(function() {
		grid.set('url', defaultUrl + Utils.form.buildParams('search-form'));
	});
});
