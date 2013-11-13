define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	window.$ = $;
	window.Utils = Utils;
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	
	Utils.select.remote([ 'create_projectType', 'edit_projectType', 'query_projectType' ], '/system/dictionaries?typeCode=document_project_type&list=true', 'id', 'itemName', true, '选择工程分类');

	// 启用日期控件
	Utils.input.date('input[type=datetime]');
	
	// 配置表格列
	var fields = [ {
		header : '文档名称',
		name : 'documentName'
	}, {
		header : '科室',
		name : 'office',
		width : 60
	}, {
		header : '工程分类',
		name : 'projectType',
		width : 100,
		render : function(value) {
			return value.itemName;
		}
	}, {
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
	}, {
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
		onClick : function(target, data) {
			
		},
		onLoaded : function() {
			
		}
	}).render();

	// 搜索
	$('#submit').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('query-form')
		});
	});
	
	// 重置
	$('#reset').click(function() {
		grid.set('url', defaultUrl);
        grid.refresh();
	});
	
	$(document).click(function(event) {
		var docId = $(event.target).attr('doc_id');
		if (docId) {
			$('#showDocument').attr('src', '/ercs/view-pdf/' + docId + "?t=" + new Date().getTime());
			Utils.modal.show('view');
		}
	});
});