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
		$.get('history-curve?nodeIds=' + param.nodePlace + '&date=' + param.date, function (data) {
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
					dateTimeArray[i] = getTimes(currentNodeData[i].dataTime);
					avgArray[i] = currentNodeData[i].avgData;
					maxArray[i] = currentNodeData[i].maxData;
					minArray[i] = currentNodeData[i].minData;
				}	
			}

			chart = echarts.init(document.getElementById('historyChart'));

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
						restore : {
							show: true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : false,
				dataZoom : {
					//控制数据横轴缩放
			        show : true,
			        realtime : true,
			        height: 30,
			        backgroundColor: 'rgba(221,160,221,0.5)',
			        dataBackgroundColor: 'rgba(138,43,226,0.5)',
			        fillerColor: 'rgba(38,143,26,0.6)',
			        handleColor: 'rgba(128,43,16,0.8)',
			        start : 20,
			        end : 50
			    },
				xAxis : [{
						type : 'category',
						boundaryGap : false,
						data : dateTimeArray,
						axisLabel:{
							show: true
							//横坐标标记倾斜45度
							//rotate:45
						}
					}
				],
				yAxis : [{
						type : 'value',
						//控制显示效果，设置曲线显示的上下限
						//min: b,
						//max: a,
						scale: true,
						splitArea : {
							show : true
						}
					}
				],
				series : [{
						name : '最大值',
						type : 'line',
						symbol: 'none',
						data : maxArray
					}, {
						name : '最小值',
						type : 'line',
						symbol: 'none',
						data : minArray
					}, {
						name : '平均值',
						type : 'line',
						symbol: 'none',
						data : avgArray
					}
				]
			};
			
			chart.setOption(option);

		});
	}
	
	//String类型日期时间转换成格林威治类型时间
	var convertToDate=function(str){
		var timetmp = str.replace(/-/g,"/");
		var date = new Date(timetmp);
		return date;
	};
	//获取时间作为x轴坐标
	var getTimes = function(dateStr) {
		var hours = (convertToDate(dateStr)).getHours();
		var minutes = (convertToDate(dateStr)).getMinutes();
		return hours+":"+minutes;
	}

});
