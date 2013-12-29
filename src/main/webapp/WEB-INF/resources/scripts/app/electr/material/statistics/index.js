define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/electr/stock-details';

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	var currentDate = new Date();
	$('#query_year').val(currentDate.getFullYear());
	$('#query_month').val(currentDate.getMonth() + 1);
	var loadInfo=function(){
		Utils.button.disable(['export' ]);
		$('#table_panel').html('');
		$.ajax({
			type : 'get',
			data : Utils.form.buildParams('search-form'),
			dataType : 'text',
			url : '/electr/material/statistics_query',
			success : function(data) {
				$('#table_panel').html(data);
				Utils.button.enable(['export' ]);
			}
		});
	};
	$(document).ready(function() {
		$('#query_year,#query_month').bind('change', function() {
			$('#submit').trigger('click');
		});
		loadInfo();
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
		window.location.href = '/electr/material/blotters/export-excel?' + Utils.form.buildParams('search-form');
	});

	// 搜索
	$('#submit').click(function() {
		loadInfo();
	});

	// 查询条件重置
	$('#reset').click(function() {
		grid.set('url', defaultUrl);
		grid.refresh();
	});
});