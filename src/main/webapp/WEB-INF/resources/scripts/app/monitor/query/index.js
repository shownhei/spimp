define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 启用日期控件
	Utils.input.date('input[type=datetime]');

	// 获取测点类型
	Utils.select.remote([ 'monitorSensorType1', 'monitorSensorType2', 'monitorSensorType3' ], contextPath + '/monitor/monitor-sensor-types', 'sensorTypeId',
			'sensorTypeName', true, '选择测点类型');
	Utils.select.remote([ 'monitorSensorType4' ], contextPath + '/monitor/monitor-sensor-types?type=1', 'sensorTypeId', 'sensorTypeName', true, '选择测点类型');
	Utils.select.remote([ 'monitorSensorType5' ], contextPath + '/monitor/monitor-sensor-types?type=2', 'sensorTypeId', 'sensorTypeName', true, '选择测点类型');

	// 获取报警类型
	Utils.select.remote([ 'monitorState1', 'monitorState2' ], contextPath + '/monitor/curuser-selected-monitor-states', 'stateId', 'stateName', true, '选择报警类型');

	// 配置表格列
	var fields1 = [ {
		header : '测点编号',
		name : 'nodeId'
	}, {
		header : '测点类型',
		name : 'sensorName',
		width : 80
	}, {
		header : '报警类型',
		name : 'stateName',
		width : 80
	}, {
		header : '安装位置',
		name : 'nodePlace'
	}, {
		header : '开始时间',
		name : 'startTime',
		width : 150
	}, {
		header : '结束时间',
		name : 'endTime',
		width : 150
	}, {
		header : '持续时间',
		name : 'timeDiff'
	}, {
		header : '最大值',
		name : 'maxData'
	}, {
		header : '报警/断电值',
		render : function(value, record) {
			return record.alarmUpperValue + '/' + record.hCutValue;
		},
		width : 80
	} ];

	var fields2 = [ {
		header : '测点类型',
		name : 'sensorName'
	}, {
		header : '安装位置',
		name : 'nodePlace'
	}, {
		header : '累计报警次数',
		name : 'totalAlarmCount'
	}, {
		header : '累计报警时间',
		name : 'timeDiff'
	} ];

	var fields3 = [ {
		header : '测点编号',
		name : 'id',
		render : function(v) {
			return v.nodeId;
		}
	}, {
		header : '测点类型',
		name : 'sensorName'
	}, {
		header : '安装位置',
		name : 'nodePlace'
	}, {
		header : '当前值',
		name : 'realData'
	}, {
		header : '状态',
		name : 'stateName'
	}, {
		header : '数据时间',
		name : 'id',
		render : function(v) {
			return v.dataTime;
		}
	} ];

	var fields4 = [ {
		header : '测点编号',
		name : 'nodeId',
		width : 120
	}, {
		header : '测点类型',
		name : 'sensorName',
		width : 80
	}, {
		header : '安装位置',
		name : 'nodePlace'
	}, {
		header : '报警最大值',
		name : 'maxData',
		width : 80
	}, {
		header : '报警最小值',
		name : 'minData',
		width : 80
	}, {
		header : '平均值',
		name : 'avgData',
		width : 80
	}, {
		header : '数据时间',
		name : 'dataTime',
		width : 150
	} ];

	var fields5 = [ {
		header : '测点编号',
		name : 'nodeId'
	}, {
		header : '测点类型',
		name : 'sensorName'
	}, {
		header : '安装位置',
		name : 'nodePlace'
	
	}, {
		header : '实时数据',
		name : 'realData',
		render : function(value){
				return value==0?"关":"开";
		}
	}, {
		header : '数据时间',
		name : 'createtime'
	} ];

	// 根据是否显示机构，控制页面显示、机构树加载、表格列等
	var groupTree;
	var mineField = [ {
		header : '煤矿名称',
		name : 'mineName'
	} ];
	if (showGroup) {
		$('.page-content .row-fluid div').removeClass('hide');

		// 控制显示机构树
		groupTree = Utils.tree.group('groupTree', contextPath + '/system/groups/2?label=mine', function(event, treeId, treeNode, clickFlag) {
			switch ($('.tab-content .active').attr('id')) {
				case 'tab1':
					loadGrid(grid1, 'query-form1', gridUrl1, treeNode.number);
					break;
				case 'tab2':
					loadGrid(grid2, 'query-form2', gridUrl2, treeNode.number);
					break;
				case 'tab3':
					loadGrid(grid3, 'query-form3', gridUrl3, treeNode.number);
					break;
				case 'tab4':
					loadGrid(grid4, 'query-form4', gridUrl4, treeNode.number);
					break;
				case 'tab5':
					loadGrid(grid5, 'query-form5', gridUrl5, treeNode.number);
					break;
				default:
					break;
			}
		});

		// 控制显示表格煤矿名称列
		fields1 = mineField.concat(fields1);
		fields2 = mineField.concat(fields2);
		fields3 = mineField.concat(fields3);
		fields4 = mineField.concat(fields4);
		fields5 = mineField.concat(fields5);
	} else {
		$('.page-content .row-fluid .span3').remove();
		$('.page-content .row-fluid .span9').removeClass('span9 hide').addClass('span12');
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

	// 配置grid
	var gridHeight, pageSize;
	var gridUrl1, gridUrl2, gridUrl3, gridUrl4, gridUrl5;
	var grid1 = configGrid('#grid1', fields1);
	var grid2 = configGrid('#grid2', fields2);
	var grid3 = configGrid('#grid3', fields3);
	var grid4 = configGrid('#grid4', fields4);
	var grid5 = configGrid('#grid5', fields5);

	// 计算树和表格高度
	function resize() {
		// 计算高度
		var baseHeight = $(window).height() - ($('.navbar').height() + 87);
		gridHeight = $(window).height() - ($('.navbar').height() + 176);

		// 重设tree、tab、grid的高度
		$('.tree-widget-main .ztree').height(baseHeight + 39);
		$('.tab-content').height(baseHeight + 12);
		$('.grid-bd').height(gridHeight);

		// 重新计算表格行数
		pageSize = Math.floor(gridHeight / GRID_ROW_HEIGHT);

		// 初始化grid的url
		gridUrl1 = contextPath + '/monitor/alarm-datas?orderBy=id&order=desc&pageSize=' + pageSize;
		gridUrl2 = contextPath + '/monitor/alarm-datas-statistic?orderBy=id&order=desc&pageSize=' + pageSize;
		gridUrl3 = contextPath + '/monitor/real-datas?orderBy=id&order=desc&pageSize=' + pageSize;
		gridUrl4 = contextPath + '/monitor/five-minute-datas?orderBy=id&order=desc&pageSize=' + pageSize;
		gridUrl5 = contextPath + '/monitor/value-change?orderBy=id&order=desc&pageSize=' + pageSize;

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
	function loadGrid(grid, formId, gridUrl, number) {
		if (number !== undefined) { // 根据number来判断是点击机构树还是点击tab
			gridUrl = gridUrl + '&mineId=' + number;
		} else {
			if (groupTree !== undefined && groupTree.getSelectedNodes()[0] !== undefined) {
				gridUrl = gridUrl + '&mineId=' + groupTree.getSelectedNodes()[0].number;
			}
		}

		grid.set('url', encodeURI(gridUrl + Utils.form.buildParams(formId)));
	}

	// 加载tab
	function loadTab(tab) {
		switch (tab) {
			case '#tab1':
				loadGrid(grid1, 'query-form1', gridUrl1);
				break;
			case '#tab2':
				loadGrid(grid2, 'query-form2', gridUrl2);
				break;
			case '#tab3':
				loadGrid(grid3, 'query-form3', gridUrl3);
				break;
			case '#tab4':
				loadGrid(grid4, 'query-form4', gridUrl4);
				break;
			case '#tab5':
				loadGrid(grid5, 'query-form5', gridUrl5);
				break;
			default:
				break;
		}
	}

	// 查询
	function query(buttonId, grid, queryForm, gridUrl) {
		$('#' + buttonId).click(function() {
			loadGrid(grid, queryForm, gridUrl);
		});
	}

	query('query1', grid1, 'query-form1', gridUrl1);
	query('query2', grid2, 'query-form2', gridUrl2);
	query('query3', grid3, 'query-form3', gridUrl3);
	query('query4', grid4, 'query-form4', gridUrl4);
	query('query5', grid5, 'query-form5', gridUrl5);

	// 重置
	function reset(buttonId, grid, queryForm, gridUrl, disabledId) {
		$('#' + buttonId).click(function() {
			document.getElementById(queryForm).reset();
			loadGrid(grid, queryForm, gridUrl);

			if (disabledId !== undefined) {
				$('#' + disabledId).attr('disabled', 'disabled');
			}
		});
	}

	reset('reset1', grid1, 'query-form1', gridUrl1);
	reset('reset2', grid2, 'query-form2', gridUrl2);
	reset('reset3', grid3, 'query-form3', gridUrl3, 'nodePlace3');
	reset('reset4', grid4, 'query-form4', gridUrl4, 'nodePlace4');
	reset('reset5', grid5, 'query-form5', gridUrl5, 'nodePlace5');

	// 级联下拉列表
	function change(selectId, cascadeId, url, value, display, blank, blankText) {
		$('#' + selectId).change(function() {
			if ($(this).val() === '') {
				$('#' + cascadeId).attr('disabled', 'disabled');
			} else {
				Utils.select.remote([ cascadeId ], encodeURI(url + $(this).val()), value, display, blank, blankText);
				$('#' + cascadeId).removeAttr('disabled');
			}
		});
	}

	change('monitorSensorType3', 'nodePlace3', contextPath + '/monitor/monitor-node-places?sensorTypeId=', 'id.nodeId', 'nodePlace', true, '选择位置');
	change('monitorSensorType4', 'nodePlace4', contextPath + '/monitor/monitor-node-places?sensorTypeId=', 'id.nodeId', 'nodePlace', true, '选择位置');
	change('monitorSensorType5', 'nodePlace5', contextPath + '/monitor/monitor-node-places?sensorTypeId=', 'id.nodeId', 'nodePlace', true, '选择位置');

	// 导出
	var EXPORT_PAGE_SIZE = 10000;
	var exportUrl1 = contextPath + '/monitor/alarm-datas-export?orderBy=id&order=desc&pageSize=' + EXPORT_PAGE_SIZE;
	var exportUrl2 = contextPath + '/monitor/alarm-datas-statistic-export?orderBy=id&order=desc&pageSize=' + EXPORT_PAGE_SIZE;
	var exportUrl3 = contextPath + '/monitor/real-datas-export?orderBy=id&order=desc&pageSize=' + EXPORT_PAGE_SIZE;
	var exportUrl4 = contextPath + '/monitor/five-minute-datas-export?orderBy=id&order=desc&pageSize=' + EXPORT_PAGE_SIZE;
	var exportUrl5 = contextPath + '/monitor/value-change-export?orderBy=id&order=desc&pageSize=' + EXPORT_PAGE_SIZE;

	function exportExcel(buttonId, exportUrl, formId) {
		$('#' + buttonId).click(function() {
			window.open(encodeURI(exportUrl + Utils.form.buildParams(formId)));
		});
	}

	exportExcel('export1', exportUrl1, 'query-form1');
	exportExcel('export2', exportUrl2, 'query-form2');
	exportExcel('export3', exportUrl3, 'query-form3');
	exportExcel('export4', exportUrl4, 'query-form4');
	exportExcel('export5', exportUrl5, 'query-form5');
});
