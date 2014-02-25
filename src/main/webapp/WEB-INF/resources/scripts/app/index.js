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
});
