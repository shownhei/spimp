define(function (require, exports, module) {
	var $ = require('kjquery'),
	Grid = require('grid'),
	Utils = require('../../common/utils');

	// 启用日期控件
	Utils.input.date('input[type=datetime]');

	// 获取测点类型
	Utils.select.remote(['monitorSensorType'], contextPath + '/monitor/monitor-sensor-types', 'sensorTypeId','sensorTypeName', true, '选择测点类型');
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
	$('#historyChart').css({
		height : gridHeight
	});
	// 级联下拉列表
	function change(selectId, cascadeId, url, value, display, blank, blankText) {
		$('#' + selectId).change(function () {
			if ($(this).val() === '') {
				$('#' + cascadeId).attr('disabled', 'disabled');
			} else {
				Utils.select.remote([cascadeId],
					encodeURI(url + $(this).val()), value, display, blank,
					blankText);
				$('#' + cascadeId).removeAttr('disabled');
			}
		});
	}

	change('monitorSensorType', 'nodePlace', contextPath + '/monitor/monitor-node-places?sensorTypeId=','id.nodeId', 'nodePlace', true, '选择位置');

	// 绑定查询按钮
	$('#query').bind('click', function () {
		loadChart();
	});

	// 显示图形
	var chart;
	function loadChart() {
		if (chart !== undefined) {
			chart.dispose();
		}
		var param = Utils.form.serialize('query');

		$.get('history-curve?nodeIds=' + param.nodePlace + '&date='
			 + param.date, function (data) {
			// 如果数据为空，给出提示，并返回
			var result = data.data;
			if (result.length < 1) {
				alert("所选测点当前日期内在无数据");
				return false;
			}

			for (var j = 0; j < result.length; j++) {
				var node = result[j];
				var currentNodeID = node.nodeID;
				var currentNodeData = node.data;
				var dataLength = currentNodeData.length;

				var dateTimeArray = new Array(dataLength);
				var avgArray = new Array(dataLength);
				var maxArray = new Array(dataLength);
				var minArray = new Array(dataLength);

				
				for (var i = 0; i < dataLength; i++) {
					dateTimeArray[i] = convertDate(currentNodeData[i].dataTime);
					avgArray[i] = currentNodeData[i].avgData;
					maxArray[i] = currentNodeData[i].maxData;
					minArray[i] = currentNodeData[i].minData;
				}
				
//				var scale = new Array(6);
//				scale[0]= Math.max.apply(null, avgArray);
//				scale[1]= Math.min.apply(null, avgArray);
//				scale[2]= Math.max.apply(null, maxArray);
//				scale[3]= Math.min.apply(null, maxArray);
//				scale[4]= Math.max.apply(null, minArray);
//				scale[5]= Math.min.apply(null, minArray);
//				
//				var a = Math.max.apply(null, scale);
//				var b = Math.min.apply(null, scale);
//				console.log(a);
//				console.log(b);
	
			}

			chart = echarts.init(document
					.getElementById('historyChart'));

			option = {
				title : {
					text : '          测点历史曲线'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : ['最大值', '最小值', '平均值']
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : true
						},
						dataView : {
							show : true,
							readOnly : false
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [{
						type : 'category',
						boundaryGap : false,
						data : dateTimeArray,
						axisLabel:{
							show: true
							//横坐标标记倾斜45度
//							rotate:45
						}
					}
				],
				yAxis : [{
						type : 'value',
						//控制显示效果，设置曲线显示的上下限
//						min: b,
//						max: a,
//						scale: true,
						splitArea : {
							show : true
						}
					}
				],
				series : [{
						name : '最大值',
						type : 'line',
						data : maxArray
					}, {
						name : '最小值',
						type : 'line',
						data : minArray
					}, {
						name : '平均值',
						type : 'line',
						data : avgArray
					}
				]
			};
			chart.setOption(option);

		});
	}
	function convertDate(dateStr) {
		var hours= new Date(dateStr).getHours();
		var minutes= new Date(dateStr).getMinutes();
		return hours+"点"+minutes+"分";
	}

});
