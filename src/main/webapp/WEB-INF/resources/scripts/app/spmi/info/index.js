define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 启用日期控件
	Utils.input.date('#query-startDate');
	Utils.input.date('#query-endDate');

	// 配置表格列
	var fields = [ {
		header : '安全问题或隐患',
		name : 'content',
		render : function(value) {
			return '<span title="' + value + '">' + value + '</span>';
		}
	}, {
		header : '创建人',
		name : 'creater',
		width : 100
	}, {
		header : '执行人',
		name : 'executor',
		width : 100,
		render : function(value) {
			return '<span title="' + value + '">' + value + '</span>';
		}
	}, {
		header : '反馈时间',
		name : 'feedbackTime',
		width : 150
	}, {
		header : '查看',
		name : 'id',
		width : 50,
		align : 'center',
		render : function(value) {
			return '<i data-role="view" class="icon-list" style="cursor:pointer;"></i>';
		}
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 84);
	var pageSize = Math.floor((gridHeight - 1) / GRID_ROW_HEIGHT);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
	}

	// 配置表格
	var defaultUrl = contextPath + '/spmi/daily/plans?orderBy=id&order=desc&status=已完成&category=整改安排&filter=false&pageSize=' + pageSize;
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
					$('#view-plan-' + k).html(v);
				});

				Utils.modal.show('view');
			}
		},
		onLoaded : function() {
			changeButtonsStatus();
		}
	}).render();

	// 搜索
	$('#query-button').click(function() {
		grid.set('url', defaultUrl + Utils.form.buildParams('query-form'));
	});

	// 重置
	$('#reset-button').click(function() {
		grid.set('url', defaultUrl);
	});
});
