define(function(require, exports, module) {
	var $ = require('$'), Handlebars = require('handlebars');

	// 自适应高度
	var mainHeight = $(window).height() - ($('.navbar').height() + $('#breadcrumbs').height() + 20);
	var autoHeight = mainHeight / 2 - 65;
	$('#infobox,#chart1,#chart2,#chart3').height(autoHeight);

	// 信息盒
	$.get(contextPath + '/infobox/pull', function(data) {
		var template = Handlebars.compile($('#infobox-template').html());
		var html = template(data);
		$('#infobox').html(html);
	});

	// 图表样式
	var textStyle = {
		fontFamily : '"Microsoft YaHei", "Helvetica Neue", Helvetica, "Lucida Grande", "Luxi Sans", Arial, sans-serif'
	};
	var colors = [ '#82af6f', '#3a87ad', '#f89406', '#b94a48' ];
	var legends = [ '0点班', '8点班', '4点班' ];
	var today = new Date().format('yyyy-MM-dd');

	// 图表1
	var chart1 = echarts.init(document.getElementById('chart1'));
	$('#chart1-title').html(new Date().format('yyyy年M月d日') + '产量统计图');
	$.get(contextPath + '/spmi/daily/daily-reports/statistics?startDate=' + today + '&endDate=' + today, function(data) {
		var datas = [];

		$.each(data.data, function(k, v) {
			datas.push({
				value : v[2] * 10,
				name : v[1]
			});
		});

		chart1.setOption({
			color : colors,
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c}吨 ({d}%)"
			},
			series : [ {
				name : '产量',
				type : 'pie',
				data : datas
			} ]
		});
	});

	// chart2--年度油耗
	var chart2 = echarts.init(document.getElementById('chart2'));
	$('#chart2-title').html(new Date().format('yyyy年') + '年度油耗统计');
	var cars = [];
	var runs = [];
	var kilomiters = [];
	var oils = [];
	var oilDistanceDisplays = [];
	$.get(contextPath + '/electr/car/annual-oil/resultchart?year=' + new Date().format('yyyy'), function(data) {
		var result = data.data.category;
		$.each(result, function(k, v) {
			if (v) {
				var len = v.length;
				for (var i = 0; i < len; i++) {
					cars.push(v[i]['carNo']);
					runs.push(v[i]['trainNumber']);
					kilomiters.push(v[i]['distance']);
					oils.push(v[i]['refuelNumber']);
					oilDistanceDisplays.push(v[i]['oilDistanceDisplay']);
				}
			}
		});
		chart2.setOption(yearOidOption);
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
			data : [ '运行次数', '行驶公里数', '加油数', '百公里油耗' ],
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
			zoomLock : true,
			start : 10,
			end : 60
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
		}, {
			name : '百公里油耗',
			type : 'bar',
			data : oils
		} ]
	};

});
