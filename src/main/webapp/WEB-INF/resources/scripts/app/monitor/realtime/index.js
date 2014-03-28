define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 控制显示左侧机构树
	if (showGroup) {
		$('.page-content .row-fluid div').removeClass('hide');
	} else {
		$('.page-content .row-fluid .span3').remove();
		$('.page-content .row-fluid .span9').removeClass('span9 hide').addClass('span12');
	}
	var mineField = [ {
		header : '煤矿名称',
		name : 'mineName'
	} ];

	// 获取测点类型
	Utils.select.remote([ 'monitorSensorType1' ], contextPath + '/monitor/monitor-sensor-types', 'sensorTypeId', 'sensorTypeName', true, '选择测点类型');
	Utils.select.remote([ 'monitorSensorType2' ], contextPath + '/monitor/monitor-sensor-types?type=1', 'sensorTypeId', 'sensorTypeName', true, '选择测点类型');
	Utils.select.remote([ 'monitorSensorType3' ], contextPath + '/monitor/monitor-sensor-types?type=2', 'sensorTypeId', 'sensorTypeName', true, '选择测点类型');
	Utils.select.remote([ 'monitorSensorType4' ], contextPath + '/monitor/monitor-sensor-types?type=3', 'sensorTypeId', 'sensorTypeName', true, '选择测点类型');

	// 获取测点状态
	Utils.select.remote([ 'monitorState1', 'monitorState2', 'monitorState3', 'monitorState4' ], contextPath + '/monitor/monitor-states', 'stateId',
			'stateName', true, '选择测点状态');

	// 获取瓦斯报警和一氧化碳超限
	$.get(contextPath + '/monitor/statistic-ch4-co', function(data) {
		$('span[class="CH4"]').html(data.data.split("/")[0]);
		$('span[class="CO"]').html(data.data.split("/")[1]);
	});

	// 配置表格列
	var fields1 = [ {
		header : '测点编号',
		name : 'nodeId'
	}, {
		header : '类型',
		name : 'sensorName'
	}, {
		header : '测点值',
		render : function(value, record) {
			if (record.sensorUnit !== null) {
				return record.currentData + '/' + record.sensorUnit;
			} else {
				return record.currentData;
			}
		}
	}, {
		header : '状态',
		name : 'stateName'
	}, {
		header : '安装位置',
		name : 'nodePlace',
		width : 150
	}, {
		header : '时间',
		name : 'dataTime',
		width : 150
	} ];

	var fields2 = [ {
		header : '分站编号',
		name : 'stationId'
	}, {
		header : '安装位置',
		name : 'stationPlace',
		width : 150
	}, {
		header : '当前状态',
		name : 'stationData',
		render : function(value) {
			return value === '1' ? '正常' : '故障';
		}
	}, {
		header : '更新时间',
		name : 'dataTime',
		width : 150
	} ];

	// 控制显示表格煤矿名称列
	if (showGroup) {
		fields1 = mineField.concat(fields1);
		fields2 = mineField.concat(fields2);
	}

	// 配置表格
	function configGrid(parentNode, fields) {
		return new Grid({
			parentNode : parentNode,
			model : {
				fields : fields,
				needOrder : true,
				orderWidth : 40,
				height : gridHeight
			}
		}).render();
	}

	// 下拉框选择查询
	function selectChange(formId) {
		$('#' + formId + ' select').change(function() {
			loadTab('#' + $('.tab-content .active').attr('id'));
		});
	}

	// 配置grid、查询、统计
	var gridHeight, pageSize;
	var gridUrl1, gridUrl2, gridUrl3, gridUrl4, gridUrl5;
	var grid1 = configGrid('#grid1', fields1);
	var grid2 = configGrid('#grid2', fields1);
	var grid3 = configGrid('#grid3', fields1);
	var grid4 = configGrid('#grid4', fields1);
	var grid5 = configGrid('#grid5', fields2);
	selectChange('query-form1');
	selectChange('query-form2');
	selectChange('query-form3');
	selectChange('query-form4');
	selectChange('query-form5');

	// 机构树
	var groupTree = Utils.tree.group('groupTree', contextPath + '/system/groups/2?label=mine', function(event, treeId, treeNode, clickFlag) {
		switch ($('.tab-content .active').attr('id')) {
			case 'tab1':
				loadGrid(grid1, 0, 'query-form1', gridUrl1, treeNode.number);
				statistic(0, treeNode.number);
				break;
			case 'tab2':
				loadGrid(grid2, 1, 'query-form2', gridUrl2, treeNode.number);
				statistic(1, treeNode.number);
				break;
			case 'tab3':
				loadGrid(grid3, 2, 'query-form3', gridUrl3, treeNode.number);
				statistic(2, treeNode.number);
				break;
			case 'tab4':
				loadGrid(grid4, 3, 'query-form4', gridUrl4, treeNode.number);
				statistic(3, treeNode.number);
				break;
			case 'tab5':
				gridUrl5 = contextPath + '/monitor/monitor-stations?orderBy=id&order=desc&mineId=' + treeNode.number + '&pageSize=' + pageSize;
				grid5.set('url', gridUrl5 + Utils.form.buildParams('query-form5'));
				$.get(contextPath + '/monitor/statistic-by-data?mineId=' + treeNode.number + Utils.form.buildParams('query-form5'), function(data) {
					$('#statistic5').html(data.data);
				});
				break;
			default:
				break;
		}
	});

	// 计算树和表格高度
	function resize() {
		// 计算高度
		var baseHeight = $(window).height() - ($('.navbar').height() + 87);
		gridHeight = $(window).height() - ($('.navbar').height() + 200);

		// 重设tree、tab、grid的高度
		$('.tree-widget-main .ztree').height(baseHeight + 39);
		$('.tab-content').height(baseHeight + 12);
		$('.grid-bd').height(gridHeight);

		// 重新计算表格行数
		pageSize = Math.floor(gridHeight / GRID_ROW_HEIGHT);

		// 初始化grid的url，type不同
		gridUrl1 = contextPath + '/monitor/real-time-datas?orderBy=id&order=desc&type=0&pageSize=' + pageSize;
		gridUrl2 = contextPath + '/monitor/real-time-datas?orderBy=id&order=desc&type=1&pageSize=' + pageSize;
		gridUrl3 = contextPath + '/monitor/real-time-datas?orderBy=id&order=desc&type=2&pageSize=' + pageSize;
		gridUrl4 = contextPath + '/monitor/real-time-datas?orderBy=id&order=desc&type=3&pageSize=' + pageSize;
		gridUrl5 = contextPath + '/monitor/monitor-stations?orderBy=id&order=desc&pageSize=' + pageSize;

		// 根据激活的tab，重新加载tab中的grid数据
		loadTab('#' + $('.tab-content .active').attr('id'));
	}
	resize();
	window.onresize = resize;

	// tab切换
	$('.nav-tabs li a').click(function() {
		loadTab($(this).attr('href'));
	});

	// 加载grid
	function loadGrid(grid, type, formId, gridUrl, number) {
		if (number !== undefined) { // 根据number来判断是点击机构树还是点击tab
			gridUrl = contextPath + '/monitor/real-time-datas?orderBy=id&order=desc&type=' + type + '&mineId=' + number + '&pageSize=' + pageSize;
		} else {
			if (groupTree.getSelectedNodes()[0] !== undefined) { // 有可能未选择任何机构树节点
				gridUrl = contextPath + '/monitor/real-time-datas?orderBy=id&order=desc&type=' + type + '&mineId=' + groupTree.getSelectedNodes()[0].number
						+ '&pageSize=' + pageSize;
			}
		}

		grid.set('url', gridUrl + Utils.form.buildParams(formId));
	}

	// 测点统计
	function statistic(type, number) {
		var statisticUrl = contextPath + '/monitor/statistic-by-state?type=' + type + Utils.form.buildParams('query-form' + (type + 1));
		if (number !== undefined) {
			statisticUrl = contextPath + '/monitor/statistic-by-state?type=' + type + '&mineId=' + number + Utils.form.buildParams('query-form' + (type + 1));
		} else {
			if (groupTree !== undefined && groupTree.getSelectedNodes()[0] !== undefined) {
				statisticUrl = contextPath + '/monitor/statistic-by-state?type=' + type + '&mineId=' + groupTree.getSelectedNodes()[0].number
						+ Utils.form.buildParams('query-form' + (type + 1));
			}
		}

		$.get(statisticUrl, function(data) {
			$('#statistic' + (type + 1)).html(data.data);
		});
	}

	// 加载tab
	function loadTab(tab) {
		switch (tab) {
			case '#tab1':
				loadGrid(grid1, 0, 'query-form1', gridUrl1);
				statistic(0);
				break;
			case '#tab2':
				loadGrid(grid2, 1, 'query-form2', gridUrl2);
				statistic(1);
				break;
			case '#tab3':
				loadGrid(grid3, 2, 'query-form3', gridUrl3);
				statistic(2);
				break;
			case '#tab4':
				loadGrid(grid4, 3, 'query-form4', gridUrl4);
				statistic(3);
				break;
			case '#tab5':
				var statisticUrl5 = contextPath + '/monitor/statistic-by-data?';
				if (groupTree.getSelectedNodes()[0] !== undefined) {
					gridUrl5 = contextPath + '/monitor/monitor-stations?orderBy=id&order=desc&mineId=' + groupTree.getSelectedNodes()[0].number + '&pageSize='
							+ pageSize;
					statisticUrl5 = contextPath + '/monitor/statistic-by-data?mineId=' + groupTree.getSelectedNodes()[0].number;
				}

				grid5.set('url', gridUrl5 + Utils.form.buildParams('query-form5'));

				$.get(statisticUrl5 + Utils.form.buildParams('query-form5'), function(data) {
					$('#statistic5').html(data.data);
				});
				break;
			default:
				break;
		}
	}
});
