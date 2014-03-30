define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 获取测点类型
	Utils.select.remote([ 'monitorSensorType2' ], contextPath + '/monitor/monitor-sensor-types', 'sensorTypeId', 'sensorTypeName', true, '选择测点类型');

	// 配置表格列
	var fields1 = [ {
		header : '分站编号',
		name : 'id'
	}, {
		header : '分站位置',
		name : 'nodeId'
	}, {
		header : '备注',
		name : 'sensorName'
	} ];

	var fields2 = [ {
		header : '测点编号',
		name : 'nodeId'
	}, {
		header : '测点类型',
		name : 'sensorName'
	}, {
		header : '安装位置',
		name : 'nodePlace'
	}, {
		header : '所属分站编号',
		name : 'stationId'
	}, {
		header : '报警上限',
		name : 'alarmUpperValue'
	}, {
		header : '报警下限',
		name : 'alarmLowerValue'
	}, {
		header : '高断电值',
		name : 'hCutValue'
	}, {
		header : '低断电值',
		name : 'lCutValue'
	}, {
		header : '高复电值',
		name : 'hResetValue'
	}, {
		header : '低复电值',
		name : 'lResetValue'
	}, {
		header : '测点控制关系',
		name : 'corRelativeNodes'
	} ];

	// 控制显示左侧机构树
	var groupTree;
	var mineField = [ {
		header : '煤矿名称',
		name : 'mineName'
	} ];
	// 根据是否显示机构，控制页面显示、机构树加载、表格列等
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
				default:
					break;
			}
		});

		// 控制显示表格煤矿名称列
		fields1 = mineField.concat(fields1);
		fields2 = mineField.concat(fields2);
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

	// 下拉框选择查询
	function selectChange(formId) {
		$('#' + formId + ' select').change(function() {
			loadTab('#' + $('.tab-content .active').attr('id'));
		});
	}

	// 配置grid
	var gridHeight, pageSize;
	var gridUrl1, gridUrl2;
	var grid1 = configGrid('#grid1', fields1);
	var grid2 = configGrid('#grid2', fields2);
	selectChange('query-form2');

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

		// 初始化grid的url，type不同
		gridUrl1 = contextPath + '/monitor/information-stations?orderBy=id&order=desc&pageSize=' + pageSize;
		gridUrl2 = contextPath + '/monitor/information-nodes?orderBy=id&order=desc&pageSize=' + pageSize;

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

		grid.set('url', gridUrl + Utils.form.buildParams(formId));
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
			default:
				break;
		}
	}
});
