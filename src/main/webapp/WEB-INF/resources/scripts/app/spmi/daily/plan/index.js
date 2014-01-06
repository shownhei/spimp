define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 启用日期控件
	Utils.input.date('#query-startDate');
	Utils.input.date('#query-endDate');
	$('#create-cutoffDate,#edit-cutoffDate').datepicker({
		autoclose : true,
		format : 'yyyy-mm-dd',
		startDate : '0d'
	});

	// 初始化富文本编辑器
	var designateContent = $('#designate-content').xheditor({
		skin : 'nostyle',
		tools : 'simple'
	});
	// 初始化富文本编辑器
	var editContent = $('#edit-content').xheditor({
		skin : 'nostyle',
		tools : 'simple'
	});

	// 流程图示
	var infoHtml = '整改安排<br><span class="label label-important arrowed-right">未指派</span>';
	infoHtml += '<span class="label label-warning arrowed-in arrowed-right">已指派</span>';
	infoHtml += '<span class="label label-success arrowed-in arrowed-right">已执行</span>';
	infoHtml += '<span class="label label-info arrowed-in">已完成</span>';
	infoHtml += '<br>日常工作/其他工作<br><span class="label label-important arrowed-right">未指派</span>';
	infoHtml += '<span class="label label-warning arrowed-in arrowed-right">已指派</span>';
	infoHtml += '<span class="label label-success arrowed-in">已执行</span>';
	$('#info').popover({
		placement : 'bottom',
		trigger : 'hover',
		html : true,
		title : '流程图示',
		content : infoHtml
	});

	// 配置表格列
	var fields = [ {
		header : '主题',
		name : 'title',
		render : function(value) {
			return '<span title="' + value + '">' + value + '</span>';
		}
	}, {
		header : '分类',
		name : 'category',
		width : 90
	}, {
		header : '创建人',
		name : 'creater',
		width : 100
	}, {
		header : '执行人',
		name : 'executor',
		width : 100
	}, {
		header : '截止日期',
		name : 'cutoffDate',
		width : 90
	}, {
		header : '状态',
		name : 'status',
		width : 60,
		align : 'center',
		render : function(value) {
			return getLabelByStatus(value);
		}
	}, {
		header : '查看',
		name : 'id',
		width : 50,
		align : 'center',
		render : function(value) {
			return '<i data-role="view" class="icon-list" style="cursor:pointer;"></i>';
		}
	} ];

	function getLabelByStatus(status) {
		var labelClass;
		switch (status) {
			case '未指派':
				labelClass = 'label-important';
				break;
			case '已下发':
				labelClass = 'label-important';
				break;
			case '已指派':
				labelClass = 'label-warning';
				break;
			case '已执行':
				labelClass = 'label-success';
				break;
			case '已完成':
				labelClass = 'label-info';
				break;
			case '已审核':
				labelClass = 'label-info';
				break;
			default:
				labelClass = '';
				break;
		}

		return '<span class="label ' + labelClass + ' label-small">' + status + '</span>';
	}

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 84);
	var pageSize = Math.floor(gridHeight / GRID_ROW_HEIGHT);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected) {
			if (data.category === '整改安排') {
				switch (data.status) {
					case '未指派':
						Utils.button.enable([ 'designate' ]);
						Utils.button.disable([ 'edit', 'execute', 'remove' ]);
						break;
					case '已指派':
						Utils.button.enable([ 'designate', 'execute' ]);
						Utils.button.disable([ 'edit', 'remove' ]);
						break;
					case '已执行':
						Utils.button.disable([ 'edit', 'designate', 'execute', 'remove' ]);
						break;
					case '已完成':
						Utils.button.disable([ 'edit', 'designate', 'execute', 'remove' ]);
						break;
					default:
						Utils.button.disable([ 'edit', 'designate', 'execute', 'remove' ]);
						break;
				}
			} else { // 其他分类的工作安排
				switch (data.status) {
					case '未指派':
						Utils.button.enable([ 'edit', 'designate', 'remove' ]);
						Utils.button.disable([ 'execute' ]);
						break;
					case '已指派':
						Utils.button.enable([ 'edit', 'designate', 'execute', 'remove' ]);
						break;
					case '已执行':
						Utils.button.disable([ 'edit', 'designate', 'execute', 'remove' ]);
						break;
					case '已完成':
						Utils.button.disable([ 'edit', 'designate', 'execute', 'remove' ]);
						break;
					default:
						Utils.button.disable([ 'edit', 'designate', 'execute', 'remove' ]);
						break;
				}
			}
		} else {
			Utils.button.disable([ 'edit', 'designate', 'execute', 'remove' ]);
		}
	}

	// 配置表格
	var defaultUrl = contextPath + '/spmi/daily/plans?orderBy=id&order=desc&pageSize=' + pageSize;
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

			// 查看
			if (target.data('role') === 'view') {
				$.each(data, function(k, v) {
					if (k === 'status') {
						$('#view-plan-' + k).html(getLabelByStatus(v));
					} else {
						$('#view-plan-' + k).html(v);
					}
				});

				Utils.modal.show('view');
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

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');

		// 验证
		if (object.executor === '') {
			Utils.modal.message('create', [ '请输入执行人' ]);
			return;
		}

		$.post(contextPath + '/spmi/daily/plans', JSON.stringify(object), function(data) {
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
		$.get(contextPath + '/spmi/daily/plans/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			editContent.setSource(object.content);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		// 验证
		if (object.executor === '') {
			Utils.modal.message('edit', [ '请输入执行人' ]);
			return;
		}

		// 处理属性
		var selectId = grid.selectedData('id');
		object.id = selectId;

		$.put(contextPath + '/spmi/daily/plans/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.hide('edit');
			} else {
				Utils.modal.message('edit', data.errors);
			}
		});
	});

	// 指派
	$('#designate').click(function() {
		if (Utils.button.isDisable('designate')) {
			return;
		}

		Utils.modal.reset('designate');

		var selectId = grid.selectedData('id');
		$.get(contextPath + '/spmi/daily/plans/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('designate', object);
			designateContent.setSource(object.content);
			Utils.modal.show('designate');
		});
	});

	// 保存指派
	$('#designate-save').click(function() {
		var object = Utils.form.serialize('designate');

		// 验证
		if (object.executor === '') {
			Utils.modal.message('designate', [ '请输入执行人' ]);
			return;
		}

		// 处理属性
		var selectId = grid.selectedData('id');
		object.id = selectId;

		$.put(contextPath + '/spmi/daily/plans/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.hide('designate');
			} else {
				Utils.modal.message('designate', data.errors);
			}
		});
	});

	// 已执行
	$('#execute').click(function() {
		if (Utils.button.isDisable('execute')) {
			return;
		}

		Utils.modal.reset('execute');

		var selectId = grid.selectedData('id');
		$.get(contextPath + '/spmi/daily/plans/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('execute', object);
			Utils.modal.show('execute');
		});
	});

	// 保存已执行
	$('#execute-save').click(function() {
		var object = Utils.form.serialize('execute');

		// 处理属性
		var selectId = grid.selectedData('id');
		object.id = selectId;

		$.put(contextPath + '/spmi/daily/plans/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.hide('execute');
			} else {
				Utils.modal.message('execute', data.errors);
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
		$.del(contextPath + '/spmi/daily/plans/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	// 搜索
	$('#query-button').click(function() {
		grid.set('url', defaultUrl + Utils.form.buildParams('query-form'));
	});

	// 重置
	$('#reset-button').click(function() {
		grid.set('url', defaultUrl);
	});
});
