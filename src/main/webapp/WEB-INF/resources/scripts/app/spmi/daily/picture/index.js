define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	/**
	 * 重置页面状态
	 */
	function reset() {
		Utils.button.disable([ 'create', 'remove' ]);
	}

	// 机构树配置
	var groupTreeSetting = {
		view : {
			selectedMulti : false,
			showTitle : false
		},
		async : {
			enable : true,
			url : contextPath + '/system/groups/1',
			type : "get",
			dataFilter : function(treeId, parentNode, responseData) {
				return responseData.data;
			}
		},
		data : {
			key : {
				children : 'groupEntities'
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode, clickFlag) {
				var groupTree = $.fn.zTree.getZTreeObj(treeId);

				// 控制按钮状态/详细信息显示
				if (treeNode.level > 0) {
					Utils.button.enable([ 'create' ]);
				} else {
					reset();
				}
			},
			onAsyncSuccess : function(event, treeId, treeNode, msg) {
				var groupTree = $.fn.zTree.getZTreeObj(treeId);
				groupTree.expandAll(true);
			}
		}
	};

	var groupTree = $.fn.zTree.init($('#groups-tree'), groupTreeSetting);

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

	// 新建
	$('#create').click(function() {
		if (Utils.button.isDisable('create')) {
			return;
		}

		Utils.modal.reset('create');
		Utils.modal.show('create');
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
});
