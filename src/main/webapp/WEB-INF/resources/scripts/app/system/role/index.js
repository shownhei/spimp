define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 计算表格高度和行数
	var treeHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 100);

	// 角色树配置
	var setting = {
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

				} else {
					Utils.button.disable([ 'edit', 'remove' ]);
				}
			},
			onAsyncSuccess : function(event, treeId, treeNode, msg) {
				var roleTree = $.fn.zTree.getZTreeObj(treeId);
				roleTree.expandAll(true);
			}
		}
	};

	var roleTree = $.fn.zTree.init($('#role-tree'), setting);
	$('#role-tree').height(treeHeight + 39);
	$('#tab-content').height(treeHeight);

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
			Utils.modal.message('create', [ '请输入角色名' ]);
			return;
		}

		$.post(contextPath + '/system/roles', JSON.stringify(object), function(data) {
			if (data.success) {
				roleTree.reAsyncChildNodes(null, "refresh");
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
				roleTree.reAsyncChildNodes(null, "refresh");
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
			roleTree.reAsyncChildNodes(null, "refresh");
			Utils.modal.hide('remove');
		});
	});
});
