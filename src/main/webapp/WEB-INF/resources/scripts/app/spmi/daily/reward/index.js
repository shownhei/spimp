define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 启用日期控件
	Utils.input.date('input[type=datetime]');
	// 获取机构
	Utils.select.remote([ 'create-groupName','edit-groupName'], contextPath + '/system/groups', 'id', 'name');

	// 配置表格列
	var fields = [ {
		header : '奖惩人姓名',
		name : 'name'
	}, {
		header : '奖惩人所属部门',
		name : 'groupName'
	}, {
		header : '分类',
		name : 'category'
	}, {
		header : '奖惩内容',
		name : 'content'
	}, {
		header : '授奖人',
		name : 'executor'
	}, {
		header : '奖惩日期',
		name : 'rewardDate'
	}, {
		header : '查看',
		name : 'id',
		width : 50,
		align : 'center',
		render : function(value) {
			return '<i data-role="view" class="icon-list" style="cursor:pointer;"></i>';
		}
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 84);
	var pageSize = Math.floor(gridHeight / GRID_ROW_HEIGHT);

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
	var defaultUrl = contextPath + '/spmi/daily/rewards?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#records',
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
		if(object.name===null||object.name===''){
			Utils.modal.message('create', [ '奖惩人姓名不能为空' ]);
			return;
		}
		if(object.category===''){
			Utils.modal.message('create', [ '奖惩分类不能为空' ]);
			return;
		}
		if(object.content===''){
			Utils.modal.message('create', [ '奖惩内容不能为空' ]);
			return;
		}
		// 处理属性

		$.post(contextPath + '/spmi/daily/rewards', JSON.stringify(object), function(data) {
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
		$.get(contextPath + '/spmi/daily/rewards/' + selectId, function(data) {
			var object = data.data;
			$("#edit-reason").val(object.reason);
			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		// 验证
		if(object.name===null||object.name===''){
			Utils.modal.message('edit', [ '奖惩人姓名不能为空' ]);
			return;
		}
		if(object.category===''){
			Utils.modal.message('edit', [ '奖惩分类不能为空' ]);
			return;
		}
		if(object.content===''){
			Utils.modal.message('edit', [ '奖惩内容不能为空' ]);
			return;
		}
		// 处理属性
		var selectId = grid.selectedData('id');
		object.id = selectId;
		
		$.put(contextPath + '/spmi/daily/rewards/' + selectId, JSON.stringify(object), function(data) {
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
		$.del(contextPath + '/spmi/daily/rewards/' + selectId, function(data) {
			grid.set('url', defaultUrl);
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	// 搜索
	$('#nav-search-button').click(function() {
		grid.set('url', defaultUrl + Utils.form.buildParams('search-form'));
	});
});
