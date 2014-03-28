define(function(require, exports, module) {
	var $ = require('$'), Handlebars = require('handlebars');

	// 自适应高度
	function resize() {
		var mainHeight = $(window).height() - ($('.navbar').height() + $('#breadcrumbs').height() + 20);
		var autoHeight = mainHeight / 2 - 65;
		$('#infobox,#chart1,#chart2,#chart3').height(autoHeight);
		$('#infobox').parent().css({
			'min-height' : autoHeight,
			'overflow-y' : 'auto'
		});
		loadChart1();
		loadChart2();
		loadChart3();
	}
	resize();
	window.onresize = resize;

	// 信息盒
	$.get(contextPath + '/infobox/pull', function(data) {
		var template = Handlebars.compile($('#infobox-template').html());
		var html = template(data);
		$('#infobox').html(html);
	});

	// 图表样式
	var chart1, chart2, chart3;
	var textStyle = {
		fontFamily : '"Microsoft YaHei", "Helvetica Neue", Helvetica, "Lucida Grande", "Luxi Sans", Arial, sans-serif'
	};
	var colors = [ '#82af6f', '#3a87ad', '#f89406', '#b94a48' ];
	var legends = [ '0点班', '8点班', '4点班' ];
	var today = new Date().format('yyyy-MM-dd');

	// 图表1
	function loadChart1() {
		if (chart1 !== undefined) {
			chart1.dispose();
		}

		$.get(contextPath + '/system/roles/has-permission/spmi:zcd:daily:output', function(data) {
			if (data.success === true) {
				chart1 = echarts.init(document.getElementById('chart1'));
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
			}
		});
	}

	// 图表2
	function loadChart2() {
		if (chart2 !== undefined) {
			chart2.dispose();
		}

		$.get(contextPath + '/system/roles/has-permission/spmi:zcd:car:annual-oil', function(data) {
			if (data.success === true) {
				chart2 = echarts.init(document.getElementById('chart2'));
				$('#chart2-title').html(new Date().format('yyyy年') + '运行统计');
				$.get(contextPath + '/electr/car/annual-oil/resultchart?year=' + new Date().format('yyyy'), function(data) {
					var carTypes = [];
					var runs = [];
					var kilomiters = [];
					var oils = [];
					var container = {};

					$.each(data.data.category, function(k, v) {
						if (v) {
							var len = v.length;
							var currentType = null;
							for (var i = 0; i < len; i++) {
								if (carTypes.indexOf(v[i].carCategory) === -1) {
									currentType = {
										run : 0,
										kilomiter : 0,
										oil : 0
									};
									container[v[i].carCategory] = currentType;
									carTypes.push(v[i].carCategory);
								} else {
									currentType = container[v[i].carCategory];
								}
								currentType.run += v[i].trainNumber;
								currentType.kilomiter += v[i].distance;
								currentType.oil += v[i].refuelNumber;
							}
						}
					});

					$.each(carTypes, function(k, v) {
						runs.push(container[v].run);
						kilomiters.push(container[v].kilomiter);
						oils.push(container[v].oil);
					});

					chart2.setOption({
						color : colors,
						tooltip : {
							trigger : 'axis'
						},
						legend : {
							orient : 'horizontal',
							data : [ '运行次数', '行驶公里数', '加油数' ],
							x : 'center',
							y : 'top'
						},
						calculable : false,
						xAxis : [ {
							type : 'category',
							data : carTypes,
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
					});
				});
			}
		});
	}

	// 图表3
	function loadChart3() {
		if (chart3 !== undefined) {
			chart3.dispose();
		}

		$.get(contextPath + '/system/roles/has-permission/spmi:zcd:car:monthly-oil', function(data) {
			if (data.success === true) {
				chart3 = echarts.init(document.getElementById('chart3'));
				$('#chart3-title').html(new Date().format('yyyy年') + '运行公里数统计');
				$.get(contextPath + '/electr/car/annual-kilometer/chart?year=' + new Date().format('yyyy'), function(data) {
					var kilos = 0;
					var datas = [];

					$.each(data.data.result, function(k, v) {
						kilos = 0;
						var _tempLen = v.length;
						$.each(v, function(key, val) {
							if (key < _tempLen - 1) {
								kilos += val;
							}
						});
						datas.push(kilos);
					});

					chart3.setOption({
						color : colors,
						tooltip : {
							trigger : 'item',
							formatter : '{a} <br/>{b} : {c}公里 '
						},
						legend : {
							orient : 'horizontal',
							data : [ '运行公里数' ],
							x : 'center',
							y : 'top'
						},
						calculable : false,
						xAxis : [ {
							type : 'category',
							data : [ "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月" ],
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
							name : '运行公里数',
							type : 'bar',
							data : datas
						} ]
					});
				});
			}
		});
	}
});
