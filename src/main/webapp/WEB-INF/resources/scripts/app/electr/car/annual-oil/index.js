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
		Utils.button.enable([ 'disable']);
		var data = 'year=' + $('#query_year').val();
		$.ajax({
			type : 'get',
			data : data,
			dataType : 'text',
			url : '/electr/car/annual-oil/result',
			success : function(data) {
				showChart();
				$('#tablePanel').html(data);
				Utils.button.enable([ 'export_result']);
			}
		});
	};
	// 导出
	$('#export_result').click(function() {
		if (Utils.button.isDisable('export_result')) {
			return;
		}
		window.location.href = '/electr/car/annual-oil/export?' + Utils.form.buildParams('query-form');
	});
	// 搜索 
	$('#submit').click(function() {
		loadMaintenance(new Date().getFullYear());
	});
	var showChart=function(){
		// chartPanel--年度油耗
		var colors = [ '#82af6f', '#3a87ad', '#f89406', '#b94a48' ];
		var chartPanel = echarts.init(document.getElementById('chartPanel'));
		$('#chartPanel-title').html(new Date().format('yyyy年') + '年度油耗统计');
		var cars = [];
		var runs = [];
		var kilomiters = [];
		var oils = [];
		$.get(contextPath + '/electr/car/annual-oil/resultchart?year=' + $('#query_year').val(), function(data) {
			var result = data.data.category;
			$.each(result, function(k, v) {
				if (v) {
					var len = v.length;
					for (var i = 0; i < len; i++) {
						cars.push(v[i].carNo);
						runs.push(v[i].trainNumber);
						kilomiters.push(v[i].distance);
						oils.push(v[i].refuelNumber);
					}
				}
			});
			chartPanel.setOption(yearOidOption);
		});
		var yearOidOption = {
			color : colors,
			title : {
				text : '年度油耗'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				orient:'vertical',
				data : [ '运行次数', '行驶公里数', '加油数'],
				x : 'right',
				y : 'center'
			},

			toolbox : {
				show : true,
				feature : {
					mark : true,
					magicType : [ 'line', 'bar' ],
					restore : true,
					dataZoom : true,
					dataView : true,
					saveAsImage : false
				}
			},
			calculable : false,
			dataZoom : {
				show : true,
				realtime : true,
				orient : 'horizontal',
				y : 36,
				height : 20,
				backgroundColor : 'rgba(221,160,221,0.5)',
				dataBackgroundColor : 'rgba(138,43,226,0.5)',
				fillerColor : 'rgba(38,143,26,0.6)',
				handleColor : 'rgba(128,43,16,0.8)',
				zoomLock : false,
				start : 10,
				end : 100
			},
			xAxis : [ {
				type : 'category',
				data : cars,
				axisLabel : {
					rotate : '30'
				}
			} ],
			yAxis : [ {
				type : 'value',
				splitArea : {
					show : true
				}
			} ],
			series : [ {
				name : '运行次数',
				type : 'bar',
				data : runs
			}, {
				name : '行驶公里数',
				type : 'bar',
				data : kilomiters
			}, {
				name : '加油数',
				type : 'bar',
				data : oils
			} ]
		};
	};
	
});