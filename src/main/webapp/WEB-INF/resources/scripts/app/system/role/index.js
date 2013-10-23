define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 角色树配置
	var roleTreeSetting = {
		view : {
			selectedMulti : false,
			showTitle : false
		},
		async : {
			enable : true,
			url : contextPath + '/system/roles',
			type : "get",
			dataFilter : function(treeId, parentNode, responseData) {
				$.each(responseData.data, function(index, item) {
					item.icon = resources + "/images/icons/user.png";
				});

				var roleTreeObject = {
					id : 0,
					name : '角色列表',
					children : responseData.data,
					iconOpen : resources + "/images/icons/folder_user.png",
					iconClose : resources + "/images/icons/folder_user.png"
				};

				return roleTreeObject;
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode, clickFlag) {
				var roleTree = $.fn.zTree.getZTreeObj(treeId);
				var resourceTree = $.fn.zTree.getZTreeObj('resources-tree');

				resourceTree.checkAllNodes(false);

				if (treeNode.level > 0) {
					Utils.button.enable([ 'edit' ]);

					// 如果角色包含用户则不能删除
					$.get(contextPath + '/system/roles/' + treeNode.id + '/accounts', function(data) {
						if (data.data.totalCount === 0) {
							Utils.button.enable([ 'remove' ]);
						} else {
							Utils.button.disable([ 'remove' ]);
						}
					});

					// 根据角色加载菜单
					Utils.button.disable([ 'save-menu' ]);
					$.get(contextPath + '/system/roles/' + treeNode.id + '/resources?type=list', function(data) {
						$.each(data.data, function(entryIndex, entry) {
							var node = resourceTree.getNodesByParam('id', entry.id);
							if (node[0] !== undefined) {
								resourceTree.checkNode(node[0], true, false);
							}
						});

						// 启用保存按钮
						Utils.button.enable([ 'save-menu' ]);
					});

				} else {
					Utils.button.disable([ 'edit', 'remove', 'save-menu' ]);
				}

				// 根据选择角色加载用户数据
				grid.set('url', contextPath + '/system/roles/' + treeNode.id + '/accounts?orderBy=id&order=desc&pageSize=' + pageSize);
			},
			onAsyncSuccess : function(event, treeId, treeNode, msg) {
				var roleTree = $.fn.zTree.getZTreeObj(treeId);
				roleTree.expandAll(true);
			}
		}
	};

	var roleTree = $.fn.zTree.init($('#roles-tree'), roleTreeSetting);

	// 菜单树配置
	var resourceTreeSetting = {
		view : {
			selectedMulti : false,
			showTitle : false,
			showIcon : false
		},
		async : {
			enable : true,
			url : contextPath + '/system/resources/2?order=true',
			type : "get",
			dataFilter : function(treeId, parentNode, responseData) {
				return responseData.data;
			}
		},
		check : {
			enable : true
		},
		data : {
			key : {
				children : 'resourceEntities'
			}
		},
		callback : {
			onAsyncSuccess : function(event, treeId, treeNode, msg) {
				var resourceTree = $.fn.zTree.getZTreeObj(treeId);
				resourceTree.expandAll(true);
			}
		}
	};

	var resourceTree = $.fn.zTree.init($('#resources-tree'), resourceTreeSetting);

	// 计算树和表格高度
	var treeHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 100);
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 175);
	if ($(window).width() >= 768) {
		$('#roles-tree').height(treeHeight + 39);
		$('#tab-content').height(treeHeight);
	} else {
		$('#roles-tree').height(150);
		$('#tab-content').height(treeHeight - 170);
		gridHeight = treeHeight - 245;
	}

	// 配置表格列
	var fields = [ {
		header : '',
		name : 'locked',
		align : 'center',
		width : 20,
		render : function(value) {
			switch (value) {
				case false:
					return '';
				case true:
					return '<i class="icon-lock"></i>';
			}
		}
	}, {
		header : '用户名',
		name : 'principal'
	}, {
		header : '所属机构',
		name : 'groupEntity',
		render : function(value) {
			return value.name;
		}
	}, {
		header : '姓名',
		name : 'realName'
	}, {
		header : '电话',
		name : 'telephone'
	} ];

	// 配置表格
	var pageSize = Math.floor(gridHeight / GRID_ROW_HEIGHT);
	var grid = new Grid({
		parentNode : '#account-table',
		model : {
			fields : fields,
			needOrder : true,
			orderWidth : 50,
			height : gridHeight
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
		if (object.name === '') {
			Utils.modal.message('create', [ '请输入角色名称' ]);
			return;
		}

		$.post(contextPath + '/system/roles', JSON.stringify(object), function(data) {
			if (data.success) {
				// 重置页面
				roleTree.reAsyncChildNodes(null, "refresh");
				resourceTree.checkAllNodes(false);
				Utils.button.disable([ 'edit', 'remove', 'save-menu' ]);
				grid.set('url', contextPath + '/system/roles/' + data.data.id + '/accounts?orderBy=id&order=desc&pageSize=' + pageSize);
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

		var selectId = roleTree.getSelectedNodes()[0].id;
		$.get(contextPath + '/system/roles/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		var selectId = roleTree.getSelectedNodes()[0].id;
		object.id = selectId;

		$.put(contextPath + '/system/roles/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				// 只更新节点名称
				var node = roleTree.getNodesByParam('id', data.data.id);
				node[0].name = data.data.name;
				roleTree.updateNode(node[0]);
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
		var selectId = roleTree.getSelectedNodes()[0].id;
		$.del(contextPath + '/system/roles/' + selectId, function(data) {
			// 重置页面
			roleTree.reAsyncChildNodes(null, "refresh");
			resourceTree.checkAllNodes(false);
			Utils.button.disable([ 'edit', 'remove', 'save-menu' ]);
			Utils.modal.hide('remove');
		});
	});

	// 保存菜单权限
	$('#save-menu').click(function() {
		if (Utils.button.isDisable('save-menu')) {
			return;
		}

		// 获取所有选中的菜单id
		var menus = [];
		$.each(resourceTree.getCheckedNodes(true), function(entryIndex, entry) {
			menus.push(entry.id);
		});

		// 更新角色菜单权限
		var selectId = roleTree.getSelectedNodes()[0].id;
		$.put(contextPath + '/system/roles/' + selectId + '/resources?resourceIds=' + menus.join(','), function(data) {
			if (data.success) {
				$('#alert-message').html('保存成功。').show().fadeOut(2000);
			} else {
				$('#alert-message').html('保存失败，请重试。').show().fadeOut(2000);
			}
		});
	});

	// 全选
	$('#check-all-menu').click(function() {
		if (Utils.button.isDisable('check-all-menu')) {
			return;
		}

		resourceTree.checkAllNodes(true);
	});

	// 反选
	$('#uncheck-all-menu').click(function() {
		if (Utils.button.isDisable('uncheck-all-menu')) {
			return;
		}

		resourceTree.checkAllNodes(false);
	});
});
