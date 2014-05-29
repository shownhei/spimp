define(function(require, exports, module) {
	var $ = require('kjquery');
	var loop=function(){

		//1. 实时下井总人数
		$.get(contextPath + '/location/location-staffs/count', function(data) {
			WebMineSystem._Rydw_SetWellPersonNum('{"wellpersonnum":' + data.data + '}');
		});
		//2. 实时下井领导（名字，所在位置）
		$.get(contextPath + '/location/location-staffs/leader', function(data) {
			var buff = [];
			var header = '{"wellleader":[';
			$.each(data.data, function(key, val) {
				buff.push('"' + val.name + '"');
			});
			var footer = ']}';
			WebMineSystem._Rydw_SetWellLeader(header + buff.join(',') + footer);
		});
		//3 实时最大瓦斯浓度数值及其传感器位置
		$.get(contextPath + '/monitor/monitor-nodes/max?sensorTypeId=1', function(data) {
			//{"success":true,"data":[[0.3,"E302工作面上隅角瓦斯"]],"errors":null}
			//{"CH4":"0.5" }
			WebMineSystem.SF_SetRealMaxCH4('{"CH4":"' + data.data[0][0] + '"}');
		});
		//4 实时最大一氧化碳浓度数值及其传感器位置
		$.get(contextPath + '/monitor/monitor-nodes/max?sensorTypeId=4', function(data) {
			//{"success":true,"data":[[1.5,"E302进风顺槽3部机头一氧化碳"],[1.5,"E302进风顺槽1部机头一氧化碳"]],"errors":null}
			//{"CO":"0.5" }
			WebMineSystem.SF_SetRealMaxCO('{"CO":"' + data.data[0][0] + '"}');
		});
		//5 当天最大瓦斯浓度数值及其传感器位置
		var currentDate = new Date();
		var dateStr = currentDate.getFullYear() + '-' + (currentDate.getMonth() + 1) + '-' + currentDate.getDate();
		$.get(contextPath + '/monitor/monitor-real-datas/max?sensorTypeId=1&date=' + dateStr, function(data) {
			//{"success":true,"data":[[0.22,"9#煤水泵房工作面回风甲烷"]],"errors":null}
		});
		//6 当天最大一氧化碳浓度数值及其传感器位置
		$.get(contextPath + '/monitor/monitor-real-datas/max?sensorTypeId=4&date=' + dateStr, function(data) {
			//{"success":true,"data":[[2.75,"采区回风巷一氧化碳"]],"errors":null}

		});
		//7 所有读卡器 下 所有人
		$.get(contextPath + '/location/read_cards_staff', function(data) {
			var array = [];
			$.each(data.data, function(key, value) {
				key+=';';
				array.push({
					"DBID" : key,
					"PERSON" : value
				});
			});
			var tansferJson = {
				"ID" : array
			};
			WebMineSystem._Rydw_SetReadCardName(JSON.stringify(tansferJson));
		});
		//8 安全监控传感器实时值
		$.get(contextPath + '/monitor/monitor-nodes-value', function(data) {
			for(var i=0;i<data.data.length;i++){
				data.data[i].DBID+=';';
			}
			var tansferJson = {
				"ID" : data.data
			};
			WebMineSystem._SF_SetSensorPointValue(JSON.stringify(tansferJson));
		});
		//9 设置人员轨迹
		//10 重复人员轨迹
	};
	loop();
	window.$=$;
	setInterval(loop, 60000);
});
