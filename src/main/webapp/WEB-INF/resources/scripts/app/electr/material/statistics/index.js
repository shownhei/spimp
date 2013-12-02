define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/electr/stock-details';

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	$(document).ready(function() {
		$('#query_year,#query_month').bind('change', function() {
			$('#submit').trigger('click');
		});
		$.ajax({
			type : 'get',
			data : '',
			dataType : 'text',
			url : '/electr/material/statistics_query',
			success : function(data) {
				$('#table_panel').html(data);
			}
		});
	});
	// 启用日期控件
	Utils.input.date('input[type=datetime]');
	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected) {
			Utils.button.enable([ 'edit', 'remove' ]);
		} else {
			Utils.button.disable([ 'edit', 'remove' ]);
		}
	}
	// 导出
	$('#export').click(function() {
		if (Utils.button.isDisable('export')) {
			return;
		}

		window.location.href = operateUri + '/export-excel?' + Utils.form.buildParams('search-form');
	});

	// 搜索
	$('#submit').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});

	// 查询条件重置
	$('#reset').click(function() {
		grid.set('url', defaultUrl);
		grid.refresh();
	});
});