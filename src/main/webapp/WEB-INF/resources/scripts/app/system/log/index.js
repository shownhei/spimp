define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 配置表格列
	var fields = [ {
		header : '编号',
		name : 'id',
		width : 50,
		align : 'center'
	}, {
		header : '级别',
		name : 'level',
		align : 'center',
		render : function(value) {
			switch (value) {
				case 'INFO':
					return '<span class="label label-success label-small">普通</span>';
				case 'WARN':
					return '<span class="label label-warning label-small">警告</span>';
				case 'SEVERE':
					return '<span class="label label-important label-small">严重</span>';
			}
		}
	}, {
		header : '用户',
		name : 'currentUserName',
		render : function(value) {
			if (value === 'System') {
				return '系统';
			} else {
				return value;
			}
		}
	}, {
		header : '记录IP',
		name : 'ip',
		width : 120
	}, {
		header : '内容',
		name : 'content',
		width : 600
	}, {
		header : '记录时间',
		name : 'recordTime',
		width : 150
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 21);

	// 配置表格
	var defaultUrl = contextPath + '/system/logs?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#log-table',
		url : defaultUrl,
		model : {
			fields : fields,
			height : gridHeight
		}
	}).render();

	// 搜索
	$('#query').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('query-form')
		});
	});
});
