define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	// 下拉列表初始化
	Utils.select.remote([ 'search_car' ], '/electr/car/carslist', 'id', 'carNo', true, '车号');
	// 下拉列表change事件
	$('#query_year,#query_month,#search_car').bind('change', function() {
		$('#submit').trigger('click');
	});
	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 50);
	$('#tablePanel').css({
		height : gridHeight + 'px'
	});
	$(document).ready(function() {
		var currentDate = new Date();
		$('#query_year').val(currentDate.getFullYear());
		$('#query_month').val(currentDate.getMonth() + 1);
		$('#submit').trigger('click');
	});
	var loadMaintenance = function() {
		$('#tablePanel').html('');
		Utils.button.disable([ 'export_result' ]);
		var data = 'year=' + $('#query_year').val();
		data += "&month=" + $('#query_month').val();
		var carId = $('#search_car').val();
		if (carId) {
			data += "&carId=" + carId;
		}
		$.ajax({
			type : 'get',
			data : data,
			dataType : 'text',
			url : '/electr/car/monthly-run/result',
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
		window.location.href = '/electr/car/monthly-run/export?' + Utils.form.buildParams('query-form');
	});
	// 搜索
	$('#submit').click(function() {
		loadMaintenance(new Date().getFullYear());
	});
});