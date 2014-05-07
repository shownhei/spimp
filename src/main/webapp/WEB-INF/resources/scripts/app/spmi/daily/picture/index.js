define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var fileID, pictureID;
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected) {
			Utils.button.enable([  'remove' ]);
		} else {
			Utils.button.disable([  'remove' ]);
		}
	}
	// 配置表格列
	var fields = [ {
		header : '图片',
		name : 'attachment',
		render : function(value) {
			return '<a href="javascript:void(0);" data-type="show_picture"><image src="' + value + '" style="height:30px;"/></a>' ;
		}
	},{
		header : '上传人',
		width:80,
		name : 'uploader'
	},{
		header : '上传时间',
		width:200,
		name : 'uploadTime'
	},{
		header : '操作',
		width:80,
		name : 'id',
		render:function(id){
			return '<a href="javascript:void(0);" data-id="'+id+'" data-type="delete_picture" class="icon-trash" title="删除图片">&nbsp;</a>' ;
		}
	} ];
	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 84);
	var pageSize = Math.floor((gridHeight - 1) / GRID_ROW_HEIGHT);
	// 配置表格
	var defaultUrl = contextPath + '/spmi/daily/pictures?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#pictureTable',
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
	
	$(document).click(function(event) {
		var el = $(event.target);
		if(el.is('a')&& el.attr('data-type')==='delete_picture'){
			Utils.modal.showAlert('确实要删除该图片吗','确认删除','delete_pic',function(){
				var selectId = grid.selectedData('id');
				$.del('/spmi/daily/pictures/' + selectId, function(data) {
					if (data.success === true) {
						grid.refresh();
					}
				});
			});
			return;
		}
		var parentEl=el.parent();
		var elType = parentEl.attr('data-type');
		if (parentEl.is('a') && elType === 'show_picture') {
			show(el.attr("src"));
			return;
		}
	});
	function show(src) {
		$("#imgfile").attr("src", src);
		var tmpObj = new Image();
		tmpObj.src = src;
		if (tmpObj.width > 500 && tmpObj.width > tmpObj.height) {
			tmpObj.height = parseInt(500 * tmpObj.height / tmpObj.width, 10);
			tmpObj.width = 500;
		} else if (tmpObj.height > 500 && tmpObj.width < tmpObj.height) {
			tmpObj.width = parseInt(500 * tmpObj.width / tmpObj.height, 10);
			tmpObj.height = 500;
		}
		tmpObj.onload = function() {
			var modalWidth = $("#detail-modal").width();
			$("#detail-modal").width(tmpObj.width);
			$("#imgfile").attr("style", "width:" + tmpObj.width + ";height:" + tmpObj.height + ";");
			Utils.modal.show('detail');
		};
	}
	// 删除确认
	$('#remove-save').click(function() {
		$.del('/spmi/daily/pictures/' + pictureID, function(data) {
			if (data.success === true) {
				groupId = groupTree.getSelectedNodes()[0].id;
				Utils.modal.hide('remove');
			}
		});
	});
	/**
	 * 处理图标路径
	 */
	function handleIcon(childrens) {
		if (childrens) {
			$.each(childrens, function(index, item) {
				if (item.folders.length > 0) {
					handleIcon(item.folders);
				} else {
					item.icon = resources + '/images/icons/folder.png';
				}
			});
		}
	}
	// 机构树配置
	var groupTreeSetting = {
		view : {
			selectedMulti : false,
			showTitle : false
		},
		async : {
			enable : true,
			url : contextPath + '/spmi/daily/folders/1',
			type : "get",
			dataFilter : function(treeId, parentNode, responseData) {
				handleIcon(responseData.data.folders);
				return responseData.data;
			}
		},
		data : {
			key : {
				children : 'folders'
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode, clickFlag) {
				var groupTree = $.fn.zTree.getZTreeObj(treeId);
				var selectNode=groupTree.getSelectedNodes()[0];
				var groupId = selectNode.id;
				if(selectNode.name==='根相册'){
					Utils.button.disable([ 'edit','upload','delete' ]);
				}else{
					Utils.button.enable([ 'edit','upload','delete' ]);
				}
				Utils.button.enable([ 'new' ]);
				grid.set('url', defaultUrl+'&groupId='+ treeNode.id );
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
	if ($(window).width() >= 768) {
		$('#groups-tree').height(treeHeight + 39);
		$('#tab-content').height(treeHeight);
	} else {
		$('#groups-tree').height(150);
		$('#tab-content').height(treeHeight - 170);
	}
	// 保存
	$('#create-save').click(function() {
		$('#create-name').val($('#attachment').val());
		var object = Utils.form.serialize('create');
		
		object.groupId = groupTree.getSelectedNodes()[0].id;
		// 验证
		if (object.name === '') {
			Utils.modal.message('create', [ '图片不能为空' ]);
			return;
		}
		if ($('#attachment').val() === '') {
			Utils.modal.message('create', [ '文件不能为空' ]);
			return;
		}
		// 处理属性
		$.post(contextPath + '/spmi/daily/pictures', JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
			} else {
				Utils.modal.message('create', data.errors);
			}
		});
	});
	
	$('#upload').click(function(){
		Utils.modal.showUpload('/simpleupload',function(data){
			var attachment = $('#attachment');
			attachment.val(data.data);
			$('#create-save').trigger('click');
		},'图片上传');
	});
	
	$('#new').click(function() {
		if (Utils.button.isDisable('new')) {
			return;
		}
		Utils.modal.reset('new');
		Utils.modal.show('new');
	});

	// 保存
	$('#new-save').click(function() {
		var object = Utils.form.serialize('new');

		// 验证
		if (object.name === '') {
			Utils.modal.message('new', [ '相册名称' ]);
			return;
		}

		// 处理属性
		object.parentId = groupTree.getSelectedNodes()[0].id;
		$.post(contextPath + '/spmi/daily/folders', JSON.stringify(object), function(data) {
			if (data.success) {
				groupTree.reAsyncChildNodes(null, "refresh");
				Utils.modal.hide('new');
			} else {
				Utils.modal.message('new', data.errors);
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
		$.get(contextPath + '/spmi/daily/folders/' + selectId, function(data) {
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
			Utils.modal.message('edit', [ '请输入相册名称' ]);
			return;
		}

		// 处理属性
		var selectId = groupTree.getSelectedNodes()[0].id;
		object.id = selectId;

		$.put(contextPath + '/spmi/daily/folders/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				groupTree.reAsyncChildNodes(null, "refresh");
				Utils.modal.hide('edit');
			} else {
				Utils.modal.message('edit', data.errors);
			}
		});
	});

	// 删除
	$('#delete').click(function() {
		if (Utils.button.isDisable('delete')) {
			return;
		}

		Utils.modal.show('delete');
	});

	// 删除确认
	$('#delete-save').click(function() {
		var selectId = groupTree.getSelectedNodes()[0].id;
		$.del(contextPath + '/spmi/daily/folders/' + selectId, function(data) {
			groupTree.reAsyncChildNodes(null, "refresh");
			Utils.modal.hide('delete');
		});
	});
	// 搜索
	$('#nav-search-button').click(function() {
		var selectId = groupTree.getSelectedNodes()[0].id;
		grid.set('url', defaultUrl + Utils.form.buildParams('search-form'));
	});
});
