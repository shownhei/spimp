define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');
	var baseHeight = $(window).height() - 87;
	gridHeight = $(window).height()-87;
	pageSize = Math.floor(gridHeight / GRID_ROW_HEIGHT);
	gridUrl3 = contextPath + '/location/station-staffs?orderBy=id&order=desc&pageSize=' + pageSize;

	// 提示信息	
	$('select[title]').tooltip({
		placement : 'right'
	});


	var fields3 = [ {
		header : '人员编号',
		name : 'id',
		render : function(value) {
			return '<b>' + value.staffId + '</b>';
		}
	}, {
		header : '姓名',
		name : 'name'
	}, {
		header : '部门',
		name : 'department'
	}, {
		header : '职务',
		name : 'jobName'
	}, {
		header : '所在位置',
		name : 'curStationId'
	}, {
		header : '所在位置时间',
		name : 'indataTime',
		width : 150
	}, {
		header : '状态',
		name : 'stateString'
	} ];
	

	// 计算树和表格高度
	function resize() {
		// 计算高度
		var baseHeight = $(window).height() - 87;
		gridHeight = $(window).height() -176;

		// 重设tree、tab、grid的高度
		$('.tree-widget-main .ztree').height(baseHeight + 39);
		$('.tab-content').height(baseHeight + 12);
		$('#grid3').height(gridHeight);

		// 重新计算表格行数
		pageSize = Math.floor(gridHeight / GRID_ROW_HEIGHT);

		// 初始化grid的url

		// 根据激活的tab，重新加载tab中的grid数据
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

	// 配置grid、查询、统计
	var gridHeight, pageSize;
	var  gridUrl3;
	var nodeId = $("#nodeId").val();
	var grid3 = configGrid('#grid3', fields3);
	loadGrid(grid3, 'query-form3', gridUrl3, nodeId);
	resize();
	window.onresize = resize;


	// 加载grid
	function loadGrid(grid, formId, gridUrl, nodeId) {
		if (nodeId !== undefined) { // 根据number来判断是点击机构树还是点击tab
			gridUrl = gridUrl + '&nodeId=' + nodeId;
		} 

		grid.set('url', encodeURI(gridUrl));
	}

});
