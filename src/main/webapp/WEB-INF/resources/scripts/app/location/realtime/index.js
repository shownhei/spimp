define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('select[title]').tooltip({
		placement : 'right'
	});

	// 配置表格列
	var fields1 = [ {
		header : '区域编号',
		name : 'id',
		render : function(value) {
			return value.areaId;
		}
	}, {
		header : '区域位置',
		name : 'pos'
	}, {
		header : '区域类型',
		name : 'typeString'
	}, {
		header : '区域人数',
		name : 'curPersonNum',
		render : function(value) {
			return '<a href="#" data-name=' + value + '>' + value + '</a>';
		}
	}, {
		header : '时间',
		name : 'createTime'
	} ];

	var fields2 = [ {
		header : '分站编号',
		name : 'id',
		render : function(value) {
			return value.stationId;
		}
	}, {
		header : '分站位置',
		name : 'pos'
	}, {
		header : '分站人数',
		name : 'curPersonNum',
		render : function(value) {
			return '<a href="#" data-name=' + value + '>' + value + '</a>';
		}
	}, {
		header : '分站类型',
		name : 'typeString'
	}, {
		header : '时间',
		name : 'dataTime'
	} ];

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
	}, {
		header : '轨迹',
		name : 'id',
		render : function(value) {
			return '<a href="#" link-name=' + value.staffId + '>' + '轨迹' + '</a>';
		}
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
				default:
					break;
			}
		});

		// 控制显示表格煤矿名称列
		fields1 = mineField.concat(fields1);
		fields2 = mineField.concat(fields2);
		fields3 = mineField.concat(fields3);
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

	// 配置grid、查询、统计
	var gridHeight, pageSize;
	var gridUrl1, gridUrl2, gridUrl3;
	var grid1 = configGrid('#grid1', fields1);
	var grid2 = configGrid('#grid2', fields2);
	var grid3 = configGrid('#grid3', fields3);

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
		gridUrl1 = contextPath + '/location/location-areas?orderBy=id&order=desc&pageSize=' + pageSize;
		gridUrl2 = contextPath + '/location/location-stations?orderBy=id&order=desc&pageSize=' + pageSize;
		gridUrl3 = contextPath + '/location/location-staffs?orderBy=id&order=desc&pageSize=' + pageSize;

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

	query('query3', grid3, 'query-form3', gridUrl3);

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

	reset('reset3', grid3, 'query-form3', gridUrl3);

	// 自动刷新
	var refreshFunction;
	function refresh(selectId) {
		window.clearInterval(refreshFunction);
		refreshFunction = window.setInterval(function() {
			loadTab('#' + $('.tab-content .active').attr('id'));
		}, $('#' + selectId).val());
	}
	function change(selectId) {
		$('#' + selectId).change(function() {
			refresh(selectId);

			// 同步更新其他自动刷新下拉列表选项
			Utils.select.setOption('refresh1', $(this).val());
			Utils.select.setOption('refresh2', $(this).val());
			Utils.select.setOption('refresh3', $(this).val());
		});
	}

	change('refresh1');
	change('refresh2');
	change('refresh3');
	refresh('refresh1');
});
