define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	$.ajaxSetup({ cache: false }); //解决 ztree ie11下面的缓存问题
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	
	Utils.select.remote([ 'create_projectType', 'edit_projectType', 'query_projectType' ], '/system/dictionaries?typeCode=document_project_type&list=true', 'id', 'itemName', true, '选择工程分类');

	// 启用日期控件
	Utils.input.date('input[type=datetime]');
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
	}, {
		header : '关键字',
		name : 'keyword'
	},  {
		header : '创建人',
		name : 'createBy',
		width : 80
	}, {
		header : '创建时间',
		name : 'createTime',
		width : 145
	}];
	
	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('#search_content').height() + $('#search_title').height() + $('.page-header').height() + 170);
	var pageSize = Math.floor(gridHeight / 21);
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
		onClick : function(target, data) {},
		onLoaded : function() {}
	}).render();
	$('#folders-tree').css({height:(gridHeight+80)});
	// 搜索
	$('#submit').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('query-form')
		});
	});
	
	// 重置
	$('#reset').click(function() {
		$('#search_documentName').val('');
	});
	
	$(document).click(function(event) {
		var docId = $(event.target).attr('doc_id');
		if (docId) {
			$('#showDocument').attr('src', '/ercs/view-pdf/' + docId + "?t=" + new Date().getTime());
			Utils.modal.show('view');
		}
	});
	
	
	function filter(treeId, parentNode, childNodes) {
		if (!childNodes){
			return null;
		}
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
});