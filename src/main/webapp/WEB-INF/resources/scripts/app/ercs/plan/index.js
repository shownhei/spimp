define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
     Utils.select.remote([ 'create-planType','edit-planType','planTypeSelect' ], '/ercs/dictionaries?typeCode=plan_type&list=true', 'id', 'itemName');	

	// 配置表格列
	var fields = [ {
		header : '编号',
		name : 'id',
		width : 50,
		align : 'center'
	}, {
		header : '预案名称',
		name : 'planName'
	}, {
		header : '预案种类',
		name : 'planType',
		render : function(value) {
			return value.itemName;
		}
	}, {
		header : '附件',
		name : 'attachment'
	},{
		header : '创建时间',
		name : 'addTime',
		width : 150
	}  ];

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
	var defaultUrl = contextPath + '/ercs/plans?orderBy=id&order=desc&pageSize='+pageSize;
	var grid = new Grid({
		parentNode : '#plan-table',
		url : defaultUrl,
		model : {
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
		if (object.planName === '') {
			Utils.modal.message('create', [ '请输入预案名称' ]);
			return;
		}
		$.post('plans', JSON.stringify(object), function(data) {
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
		$.get('plans/' + selectId, function(data) {
			var object = data.data;
			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		// 验证
		if (object.planName === '') {
			Utils.modal.message('edit', [ '请输入预案名称' ]);
			return;
		}
		// 处理属性
		var selectId = grid.selectedData('id');
		$.put('plans/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('plans/' + selectId, function(data) {
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
