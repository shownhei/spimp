define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	/**
	 * 填充机构详细信息
	 */
	function fillDetails(name, number, category, accountsCount, childrensCount) {
		$('#group-name').html(name);
		$('#group-number').html(number);
		$('#group-category').html(category);
		$('#group-accounts-count').html(accountsCount);
		$('#group-childrens-count').html(childrensCount);
	}

	/**
	 * 重置页面状态
	 */
	function reset() {
		Utils.button.disable([ 'create', 'edit', 'remove' ]);
		fillDetails('&nbsp;', '&nbsp;', '&nbsp;', '&nbsp;', '&nbsp;');
	}

	// 机构树
	var groupTree = Utils.tree.group('groups-tree', contextPath + '/system/groups/1', function(event, treeId, treeNode, clickFlag) {
		// 控制按钮状态/详细信息显示
		if (treeNode.level > 0) {
			Utils.button.enable([ 'create', 'edit' ]);

			$.get(contextPath + '/system/groups/' + treeNode.id + '/accounts?type=count', function(count) {
				if (treeNode.groupEntities.length === 0 && count.data === 0) {
					Utils.button.enable([ 'remove' ]);
				} else {
					Utils.button.disable([ 'remove' ]);
				}

				fillDetails(treeNode.name, treeNode.number, treeNode.category, count.data, treeNode.groupEntities.length);
			});
		} else {
			reset();
		}
	});

	// 计算树和表格高度
	var treeHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 87);
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('#group-detail').height() + 162);
	if ($(window).width() >= 768) {
		$('#groups-tree').height(treeHeight + 39);
		$('#tab-content').height(treeHeight);
	} else {
		$('#groups-tree').height(150);
		$('#tab-content').height(treeHeight - 170);
	}

	// 配置表格列
	var fields = [ {
		header : '姓名',
		name : 'name'
	}, {
		header : '性别',
		name : 'gender'
	}, {
		header : '工种',
		name : 'workType'
	} ];

	// 配置表格
	var pageSize = Math.floor(gridHeight / GRID_ROW_HEIGHT);
	var grid = new Grid({
		parentNode : '#staff-table',
		model : {
			fields : fields,
			needOrder : true,
			orderWidth : 50,
			height : gridHeight
		}
	}).render();

	// 新建
	$('#create').click(function() {
		if (Utils.button.isDisable('create')) {
			return;
		}

		Utils.modal.reset('create');
		Utils.modal.show('create');
	});

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');

		// 验证
		if (object.name === '') {
			Utils.modal.message('create', [ '请输入机构名称' ]);
			return;
		}
		if (object.number === '') {
			Utils.modal.message('create', [ '请输入机构编号' ]);
			return;
		}

		// 处理属性
		object.topLevel = false;
		object.queryLabel = object.category.split('-')[1];
		object.category = object.category.split('-')[0];
		object.parentId = groupTree.getSelectedNodes()[0].id;

		$.post(contextPath + '/system/groups', JSON.stringify(object), function(data) {
			if (data.success) {
				groupTree.reAsyncChildNodes(null, "refresh");
				Utils.modal.hide('create');
				reset();
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

		var selectId = groupTree.getSelectedNodes()[0].id;
		$.get(contextPath + '/system/groups/' + selectId, function(data) {
			var object = data.data;

			object.category = object.category + '-' + object.queryLabel;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		// 验证
		if (object.name === '') {
			Utils.modal.message('edit', [ '请输入机构名称' ]);
			return;
		}
		if (object.number === '') {
			Utils.modal.message('edit', [ '请输入机构编号' ]);
			return;
		}

		// 处理属性
		var selectId = groupTree.getSelectedNodes()[0].id;
		object.id = selectId;
		object.topLevel = false;
		object.queryLabel = object.category.split('-')[1];
		object.category = object.category.split('-')[0];

		$.put(contextPath + '/system/groups/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				groupTree.reAsyncChildNodes(null, "refresh");
				Utils.modal.hide('edit');
				reset();
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
		var selectId = groupTree.getSelectedNodes()[0].id;
		$.del(contextPath + '/system/groups/' + selectId, function(data) {
			groupTree.reAsyncChildNodes(null, "refresh");
			Utils.modal.hide('remove');
			reset();
		});
	});

	// 搜索
	$('#nav-search-button').click(function() {
		var input = $('#nav-search-input').val();

		if (input === '') {
			return;
		}

		var searchResult = groupTree.getNodesByFilter(function(node) {
			if (node.level > 0 && (node.name.indexOf(input) > -1 || node.number.indexOf(input) > -1)) {
				return true;
			}
		}, true);
		if (searchResult !== null) {
			groupTree.selectNode(searchResult);
			groupTreeSetting.callback.onClick(null, 'groups-tree', searchResult, null);
		}
	});
});
