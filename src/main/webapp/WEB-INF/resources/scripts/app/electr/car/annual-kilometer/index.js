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
		var data = 'year=' + $('#query_year').val();
		$.ajax({
			type : 'get',
			data : data,
			dataType : 'text',
			url : '/electr/car/annual-kilometer/result',
			success : function(data) {
				$('#tablePanel').html(data);
			}
		});
	};
	// 搜索
	$('#submit').click(function() {
		loadMaintenance(new Date().getFullYear());
	});
});