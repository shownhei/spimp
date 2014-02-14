define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 启用日期控件
	Utils.input.date('input[type=datetime]');

	// 配置表格列
	var fields = [ {
		header : '班次',
		name : 'shift'
	}, {
		header : '割煤(架)',
		name : 'output',
		render : function(v) {
			return v + '架';
		}
	}, {
		header : '存在问题',
		name : 'issue'
	}, {
		header : '报表日期',
		name : 'reportDate'
	}, {
		header : '跟班队长',
		name : 'leader'
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
	var defaultUrl = contextPath + '/spmi/daily/daily-reports?orderBy=id&order=desc&pageSize=' + pageSize;
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

			if (target.attr('data-role') === 'detail') {
				showDetail(data);
			}
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
	// 查看
	function showDetail(data) {
		Utils.modal.reset('detail');

		var object = $.extend({}, data);
		console.log("object:", object);

		Utils.form.fill('detail', object);
		Utils.modal.show('detail');
	}
	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');

		// 验证
		if (object.leader === '') {
			Utils.modal.message('create', [ '跟班队长不能为空' ]);
			return;
		}
		if (object.reportDate === '') {
			Utils.modal.message('create', [ '日期不能为空' ]);
			return;
		}
		if (object.output === '') {
			Utils.modal.message('create', [ '割煤不能为空且为数字类型' ]);
			return;
		}

		// 处理属性

		$.post(contextPath + '/spmi/daily/daily-reports', JSON.stringify(object), function(data) {
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
		$.get(contextPath + '/spmi/daily/daily-reports/' + selectId, function(data) {
			var object = data.data;
			$("#edit-issue").val(object.issue);
			$("#edit-leaveIssue").val(object.leaveIssue);
			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		// 验证
		if (object.leader === '') {
			Utils.modal.message('edit', [ '跟班队长不能为空' ]);
			return;
		}
		if (object.reportDate === '') {
			Utils.modal.message('edit', [ '日期不能为空' ]);
			return;
		}
		if (object.output === '') {
			Utils.modal.message('edit', [ '割煤不能为空且为数字类型' ]);
			return;
		}

		// 处理属性
		var selectId = grid.selectedData('id');
		object.id = selectId;

		$.put(contextPath + '/spmi/daily/daily-reports/' + selectId, JSON.stringify(object), function(data) {
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
		$.del(contextPath + '/spmi/daily/daily-reports/' + selectId, function(data) {
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
