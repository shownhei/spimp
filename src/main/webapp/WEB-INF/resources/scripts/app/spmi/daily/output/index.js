define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

	// 启用日期控件
	Utils.input.date('input[type=datetime]');

	// 计算表格高度
	var chartHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 20);
	$('#chart1').height(chartHeight);

	// 图表样式
	var title = '每日班次产量统计图';
	var textStyle = {
		fontFamily : '"Microsoft YaHei", "Helvetica Neue", Helvetica, "Lucida Grande", "Luxi Sans", Arial, sans-serif'
	};
	var colors = [ '#82af6f', '#3a87ad', '#f89406', '#b94a48' ];
	var legends = [ '0点班', '8点班', '4点班' ];

	// 图表初始化
	var chart1 = echarts.init(document.getElementById('chart1'));

	// 获取数据
	$('#query').click(function() {
		var startDate = $('#query-form').serializeObject().startDate;
		var endDate = $('#query-form').serializeObject().endDate;

		// 验证
		if (startDate === '' || endDate === '') {
			$('#message').css('display', 'inline').fadeOut(3000);
			return;
		}

		var start = new Date(startDate.replace(/-/g, '/'));
		var end = new Date(endDate.replace(/-/g, '/')).addDays(1);

		// 副标题
		var subtext = new Date(start).format('yyyy年M月d日') + ' - ' + new Date(end).addDays(-1).format('yyyy年M月d日');

		// x轴
		var xAxisData = [];
		var days = []; // 日期范围
		var classes = [ [], [], [] ]; // 3个班次默认值都为0
		for (; start < end; start.addDays(1)) {
			xAxisData.push(new Date(start).format('yyyy年M月d日'));
			days.push(new Date(start).format('yyyy-MM-dd'));
			classes[0].push(0);
			classes[1].push(0);
			classes[2].push(0);
		}

		// 获取数据
		$.get(contextPath + '/spmi/daily/daily-reports/statistics?' + Utils.form.buildParams('query-form'), function(data) {
			$.each(days, function(index, day) {
				$.each(data.data, function(k, v) {
					if (v[0] === day) {
						switch (v[1]) {
							case '0点班':
								classes[0][index] = v[2] * 10;
								break;
							case '8点班':
								classes[1][index] = v[2] * 10;
								break;
							case '4点班':
								classes[2][index] = v[2] * 10;
								break;
							default:
								break;
						}
					}
				});
			});

			// 图标配置
			chart1.clear();
			chart1.setOption({
				title : {
					text : title,
					textStyle : textStyle,
					subtext : subtext
				},
				color : colors,
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : legends,
					textStyle : textStyle
				},
				toolbox : {
					show : true,
					color : colors,
					feature : {
						magicType : [ 'line', 'bar' ],
						saveAsImage : true
					}
				},
				xAxis : [ {
					type : 'category',
					data : xAxisData
				} ],
				yAxis : [ {
					type : 'value',
					axisLabel : {
						formatter : '{value} 吨'
					},
					splitArea : {
						show : true
					}
				} ],
				series : [ {
					name : legends[0],
					type : 'bar',
					data : classes[0]
				}, {
					name : legends[1],
					type : 'bar',
					data : classes[1]
				}, {
					name : legends[2],
					type : 'bar',
					data : classes[2]
				} ]
			}, true);
		});
	});

	// 默认统计近一个月的数据
	$('#startDate').val(new Date().addMonths(-1).format('yyyy-MM-dd'));
	$('#endDate').val(new Date().format('yyyy-MM-dd'));
	$('#query').trigger('click');
});
