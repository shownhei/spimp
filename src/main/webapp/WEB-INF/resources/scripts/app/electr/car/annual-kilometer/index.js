define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	// 下拉列表change事件
	$('#query_year').bind('change', function() {
		$('#submit').trigger('click');
	});
	$(document).ready(function() {
		var currentDate = new Date();
		$('#query_year').val(currentDate.getFullYear());
		$('#submit').trigger('click');
	});
	var loadMaintenance = function() {
		$('#tablePanel').html('');
		Utils.button.enable([ 'disable' ]);
		var data = 'year=' + $('#query_year').val();
		$.ajax({
			type : 'get',
			data : data,
			dataType : 'text',
			url : '/electr/car/annual-kilometer/result',
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
		window.location.href = '/electr/car/annual-kilometer/export?' + Utils.form.buildParams('query-form');
	});
	// 搜索
	$('#submit').click(function() {
		loadMaintenance(new Date().getFullYear());
	});
});