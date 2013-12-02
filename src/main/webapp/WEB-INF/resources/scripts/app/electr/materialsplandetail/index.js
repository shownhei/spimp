define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/electr/materials-plan-details';

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 下拉列表初始化
	Utils.select.remote([ 'search_plan', 'create_plan', 'edit_plan' ], '/electr/materials-planslist', 'id', 'planTitle', true, '材料计划');
	Utils.select.remote([ 'create-groupEntity'], '/system/groups', 'id', 'name');
	Utils.select.remote([ 'plan'], '/electr/materials-planslist', 'id', 'planTitle'); 
	
	// 下拉列表change事件
	$('#search_plan').bind('change', function() {
		$('#submit').trigger('click');
	});

	// 启用日期控件
	Utils.input.date('input[type=datetime]');
	$('#planDate').datepicker('update', new Date());
	// 配置表格列
	var fields = [ {
		header : '单位',
		name : 'plan',
		render : function(value) {
			return value === null ? '' : value.planTitle;
		}
	}, {
		header : '材料名称',
		name : 'materialName'
	}, {
		header : '规格型号、设备号',
		name : 'model'
	}, {
		header : '度量单位',
		name : 'measureUnit'
	}, {
		header : '单价（元）',
		name : 'price'
	}, {
		header : '数量',
		name : 'quantity'
	},{
		header : '备注',
		name : 'remark'
	}, {
		header : '查看',
		name : 'id',
		width : 50,
		align : 'center',
		render : function(value) {
			return '<i data-role="detail" class="icon-list" style="cursor:pointer;"></i>';
		}
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
	var defaultUrl = contextPath + operateUri + '?orderBy=id&order=desc&pageSize=' + pageSize;
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

			if (target.attr('data-role') === 'detail') {
				showDetail(data);
			}
		},
		onLoaded : function() {
			changeButtonsStatus();

			// 改变导出按钮状态
			if (this.data.totalCount > 0) {
				Utils.button.enable([ 'export' ]);
			} else {
				Utils.button.disable([ 'export' ]);
			}
		}
	}).render();

	// 新建
	$('#create').click(function() {
		Utils.modal.reset('create');
		Utils.modal.show('create');
	});

	// 验证
	function validate(showType, model) {
		var errorMsg = [];

		if (model.plan=== '') {
			errorMsg.push('请输入单位');
		}

		if (model.materialName === '') {
			errorMsg.push('请输入材料名称');
		}

		if (model.model === '') {
			errorMsg.push('请输入规格型号、设备号');
		}

		if (model.measureUnit === '') {
			errorMsg.push('请输入度量单位');
		}

		if (model.price === '') {
			errorMsg.push('请输入单价（元）');
		}

		if (errorMsg.length > 0) {
			Utils.modal.message(showType, [ errorMsg.join(',') ]);
			return false;
		}
		delete model.plan;
		model.plan={id:'1'};
		return true;
	}

	// 查看
	function showDetail(data) {
		Utils.modal.reset('detail');

		var object = $.extend({}, data);
		object.plan = object.plan.planTitle;

		Utils.form.fill('detail', object);
		Utils.modal.show('detail');
	}

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');

		// 验证
		if (!validate('create', object)) {
			return false;
		}

		$.post(operateUri, JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.reset('create');
			} else {
				Utils.modal.message('create', data.errors);
			}
		});
	});
	// 保存
	$('#create-saveandclose').click(function() {
		var object = Utils.form.serialize('create');

		// 验证
		if (!validate('create', object)) {
			return false;
		}

		$.post(operateUri, JSON.stringify(object), function(data) {
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
		$.get(operateUri + '/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		// 验证
		if (!validate('edit', object)) {
			return false;
		}

		// 处理属性
		var selectId = grid.selectedData('id');
		object.id = selectId;
		$.put(operateUri + '/' + selectId, JSON.stringify(object), function(data) {
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
		$.del(operateUri + '/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	// 导出
	$('#export').click(function() {
		if (Utils.button.isDisable('export')) {
			return;
		}

		window.location.href = operateUri + '/export-excel?' + Utils.form.buildParams('search-form');
	});

	// 搜索
	$('#submit').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});

	// 查询条件重置
	$('#reset').click(function() {
		grid.set('url', defaultUrl);
		grid.refresh();
	});
});