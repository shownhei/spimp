define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	// 下拉列表change事件
	$('#query_year,#query_month').bind('change', function() {
		$('#submit').trigger('click');
	});
	$(document).ready(function() {
		var currentDate = new Date();
		$('#query_year').val(currentDate.getFullYear());
		$('#query_month').val(currentDate.getMonth()+1);
		$('#submit').trigger('click');
	});
	var loadMaintenance = function() {
		$('#tablePanel').html('');
		Utils.button.disable([ 'export_result' ]);
		var data = 'year=' + $('#query_year').val();
		data+="&month="+$('#query_month').val();
		$.ajax({
			type : 'get',
			data : data,
			dataType : 'text',
			url : '/electr/car/monthly-oil/result',
			success : function(data) {
				$('#tablePanel').html(data);
				Utils.button.enable([ 'export_result' ]);
			}
		});
	};
	// 导出
	$('#export_result').click(function() {
		if (Utils.button.isDisable('export_result')) {
			return;
		}
		window.location.href = '/electr/car/monthly-oil/export?' + Utils.form.buildParams('query-form');
	});
	// 搜索
	$('#submit').click(function() {
		loadMaintenance(new Date().getFullYear());
	});
});