define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	window.$ = $;
	$.ajaxSetup({ cache: false }); //解决 ztree ie11下面的缓存问题
	window.Utils = Utils;
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	
	var securityLevel={'1':'私有(仅自己可见) ','2':'保护(同组织内可见)','3':'公开(所有人可见)'};
	// 配置表格列
	var fields = [ {
		header : '附件',
		name : 'attachment',
		width : 300,
		render : function(v) {
			var name = v.simpleName;
			var html = '<a href="javascript:void(0);" doc_id=' + v.id + ' title=' + name + '>' + name.substring(0, 20) + '</a>&nbsp;&nbsp;';
			html += '<a href="' + v.filePath + '" target="_blank" class="pull-right">下载</a>';
			return v ? html : '';
		}
	},{
		header : '开放程度',
		name : 'securityLevel',
		render:function(v){
			var key=''+v;
			return securityLevel[key];
		}
	},  {
		header : '创建人',
		name : 'createBy',
		width : 80
	}, {
		header : '创建时间',
		name : 'createTime',
		width : 145
	}, {
		header : '更新人',
		name : 'updateBy'
	}];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 21);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected) {
			var accountId=$('#current_user_account_id').attr('data-id');
			if(data.account.id===parseInt(accountId)){
				Utils.button.enable([ 'edit', 'remove' ]);
			}else{
				Utils.button.disable([ 'edit', 'remove' ]);
			}
		} else {
			Utils.button.disable([ 'edit', 'remove' ]);
		}
	}

	// 配置表格
	var defaultUrl = contextPath + '/spmi/document/documents?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#plan-table',
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

	$('#folders-tree').css({height:(gridHeight+30)});
	// 新建
	$('#create').click(function() {
		Utils.modal.reset('create');
		Utils.modal.show('create');
		$('#create-file-delete').trigger('click');
	});

	// 保存
	$('#create-save').click(function() {
		if ($('#create-save').hasClass('disabled')) {
			return;
		}
		
		var object = Utils.form.serialize('create');
		// 验证
		if (object.documentName === '') {
			Utils.modal.message('create', [ '请输入文档名称' ]);
			return;
		}
		if (object.attachment === '') {
			Utils.modal.message('create', [ '请添加附件' ]);
			return;
		}
		var attachment = {
			id : $('#create_attachment').attr('data-id'),
			name : object.simpleName
		};
		
		delete object.filePath;
		object.attachment = attachment;
		
		$.post('/spmi/document/documents', JSON.stringify(object), function(data) {
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
		$.get('/spmi/document/documents/' + selectId, function(data) {
			var object = data.data;
			Utils.form.fill('edit', object);
			$('#edit_attachment').val(object.attachment.simpleName);
			$('#edit_attachment').attr('data-id', object.attachment.id);
			$('#edit_account').val( object.account.id);
			$('#edit_uploadGroup').val(object.uploadGroup.id);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		// 验证
		if (object.documentName === '') {
			Utils.modal.message('edit', [ '请输入文档名称' ]);
			return;
		}
		
		var attachment = {
			id : $('#edit_attachment').attr('data-id')
		};
		delete object.attachment;
		object.attachment = attachment;
		
		
		var account={id:$('#edit_account').val()};
		delete object.account;
		object.account = account;
		
		var uploadGroup={id:$('#edit_uploadGroup').val()};
		delete object.uploadGroup;
		object.uploadGroup = uploadGroup;
		// 处理属性
		var selectId = grid.selectedData('id');
		$.put('/spmi/document/documents/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('/spmi/document/documents/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	// 搜索
	$('#submit').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});
	
	// 重置
	$('#reset').click(function() {
		$('#search_folder_documentname').val('');
		$('#submit').trigger('click');
	});
	
	// 文件上传
	$('#file').bind('change', function() {
		var val=$('#file').val();
		var postfix = val.substring(val.lastIndexOf(".")+1).toLowerCase();
		var types=['doc','docx','ppt','pptx','xls','xlsx','txt','pdf'];
		if(types.indexOf(postfix)===-1){
			Utils.modal.showAlert('不支持当前格式文件上传','提示','typeAlert');
			return ;
		}
		if ($('#file').val() !== '') {
			$('#create-file-form').submit();
			var process = new Utils.modal.showProcess('process');
			window.process = process;
		}
	});
	
	$('#create-file-delete').bind('click', function() {
		$('#create_attachment').parent().parent().hide();
		$('#create-file-form')[0].reset();
		if ($('#create_attachment').val()) {
			var process = new Utils.modal.showProcess('process');
			window.process = process;
			$.del('/ercs/uploaded-files/' + $('#create_attachment').attr('data-id'), function(data) {
				grid.refresh();
				window.process.stop();
				window.process = null;
				Utils.modal.hide('remove');
			});
		}
		$('#create-file-form').show();
	});
	
	$(document).click(function(event) {
		var docId = $(event.target).attr('doc_id');
		if (docId) {
			$('#showDocument').attr('src', '/ercs/view-pdf/' + docId + "?t=" + new Date().getTime());
			Utils.modal.show('view');
		}
	});
	
	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		var result=childNodes.data.result;
		for (var i=0, l=result.length; i<l; i++) {
			result[i].isParent=true;
		}
		return result;
	}
	var nodeOperation={};
	nodeOperation.onClick=function(event, treeId, treeNode){
		$('#create_parentId').val(treeNode.id);
		if(treeNode.parentId!==-1){
			Utils.button.enable([ 'create-folder','remove-folder','create' ]);
		}else{
			Utils.button.disable([ 'remove-folder','create' ]);
		}
		Utils.button.enable([ 'create-folder']);
		$('#search_folder_id').val(treeNode.id);
		$('#create_folderId').val(treeNode.id);
		$.fn.zTree.getZTreeObj("folders-tree").expandNode(treeNode,true);
		$('#submit').trigger('click');
	};
	var setting = {
			view: {
				dblClickExpand: true,
				showLine: true,
				selectedMulti: false
			},
			async: {
				enable: true,
				url:"/spimp/document/document-folders",
				autoParam:["id=parentId", "name=folderName", "level=lv"],
				type: "get",
				dataFilter: filter
			},
			callback: {
				onExpand:function(event, treeId, treeNode){
					$('#create_parentId').val(treeNode.id);
				},
				onClick:function(event, treeId, treeNode){
					nodeOperation.onClick(event, treeId, treeNode);
				},
				onAsyncSuccess:function(event, treeId){
					//自动展开第一个节点
					var ztree=$.fn.zTree.getZTreeObj("folders-tree");
					var nodes = ztree.getNodes();
					ztree.expandNode(nodes[0], true);
				}
			},
			data : {
				key : {
					name : 'folderName'
				}
			}
		};
	var folderTree=$.fn.zTree.init($("#folders-tree"), setting);
	
	//create-folder
	// 新建
	$('#create-folder').click(function() {
		if (Utils.button.isDisable('create-folder')) {
			return;
		}
		Utils.modal.reset('create-folder');
		Utils.modal.show('create-folder');
		$('#create_folderName')[0].focus();
	});
	$('#refresh-folder').click(function(){
		var tree=$.fn.zTree.getZTreeObj("folders-tree");
		var nodes = tree.getNodes();
		tree.reAsyncChildNodes(nodes[0], "refresh");
	});
	$('#remove-folder').click(function() {
		if (Utils.button.isDisable('remove-folder')) {
			return;
		}
		var tree = $.fn.zTree.getZTreeObj("folders-tree");
		var nodes = tree.getSelectedNodes();
		if(nodes.length===0){
			Utils.modal.showAlert("<span style='color:red;'>先选择要删除的文件夹</span>","警告",'warning');
			return ;
		}
		Utils.modal.showAlert("确实要删除选中文件夹吗?",'提示','remove',function(){
			nodes = tree.getSelectedNodes();
			var parentNode=nodes[0].getParentNode();
			$.del(contextPath + '/spimp/document/document-folders/' + nodes[0].id, function(data) {
				if (data.success) {
					tree.reAsyncChildNodes(parentNode, "refresh");
					tree.expandNode(parentNode,true);
					Utils.button.disable([ 'remove-folder','create' ]);
				}else{
					Utils.modal.showAlert("删除失败，文件夹内不为空!",'提示','afterdelete');
				}
			});
		});
	});
	
	$('#create-folder-save').click(function(){
		var object = Utils.form.serialize('create-folder');
		// 验证
		if (object.folderName === '') {
			Utils.modal.message('create-folder', [ '请输入文件夹名称' ]);
			return;
		}
		delete object.account;
		$.post('/spimp/document/document-folders', JSON.stringify(object), function(data) {
			if (data.success) {
				Utils.modal.hide('create-folder');
				var tree=$.fn.zTree.getZTreeObj("folders-tree");
				var currentFolderNode = tree.getSelectedNodes()[0];
				tree.reAsyncChildNodes(currentFolderNode, "refresh");
				tree.expandNode(currentFolderNode,true);
			} else {
				Utils.modal.message('create-folder', data.errors);
			}
		});
	});
// 文件上传回调
	var callBack=function(data) {
		if(data.success){
			var attachment = $('#create_attachment');
			var docName=$('#create_documentName');
			if(docName.val()===''){
				docName.val(data.data.simpleName);
			}
			attachment.val(data.data.simpleName);
			attachment.attr('data-id', data.data.id);
			$('#create-file-form').hide();
			attachment.parent().parent().show();
			$('#create-save').removeClass('disabled');
		}else{
			alert(data.data);
		}
		window.process.stop();
		window.process = null;
	};
	window.callBack=callBack;
	
});