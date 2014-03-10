define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/electr/maintenance/regular-reminds';
	/**
	 * 加载用来渲染保养级别的数据
	 */
	var levelMap = {};
	window.levelMap = levelMap;
	$.get('/electr/maintenance/regular-configs-list', function(data) {
		$.each(data.data, function(entryIndex, entry) {
			levelMap[entry.maintenanceLevel] = entry.kilometres + "km 维修一次";
		});
	});

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 下拉列表初始化
	Utils.select.remote([ 'search_car', 'create_car', 'edit_car' ], '/electr/car/carslist', 'id', 'carNo', true, '车号');

	// 下拉列表change事件
	$('#search_car').bind('change', function() {
		$('#submit').trigger('click');
	});

	// 配置表格列
	var fields = [ {
		header : '车号',
		name : 'car',
		width : 80,
		render : function(value) {
			return value === null ? '' : value.carNo;
		}
	}, {
		header : '保养类别',
		align : 'right',
		width : 120,
		render : function(v) {
			return levelMap[v];
		},
		name : 'maintenanceLevel'
	}, {
		header : '已行驶公里数',
		align : 'right',
		name : 'kilometres'
	}, {
		header : '查看',
		name : 'id',
		width : 50,
		align : 'center',
		render : function(value) {
			return '<i data-role="detail" class="icon-list" style="cursor:pointer;"></i>';
		}
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 21);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected) {
			Utils.button.enable([ 'close-remind', 'export' ]);
		} else {
			Utils.button.disable([ 'close-remind', 'export' ]);
		}
	}

	// 配置表格
	var defaultUrl = contextPath + operateUri + '?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#material-table',
		url : defaultUrl,
		model : {
			fields : fields,
			needOrder : true,
			orderWidth : 50,
			height : gridHeight
		},
		onClick : function(target, data) {
			changeButtonsStatus(this.selected, data);

			if (target.attr('data-role') === 'detail') {
				showDetail(data);
			}
		},
		onLoaded : function() {
			changeButtonsStatus();

			// 改变导出按钮状态
			if (this.data.totalCount > 0) {
				Utils.button.enable([ 'close-remind', 'export' ]);
			} else {
				Utils.button.disable([ 'close-remind', 'export' ]);
			}
		}
	}).render();

	// 查看
	function showDetail(data) {
		Utils.modal.reset('detail');

		var object = $.extend({}, data);
		object.car = object.car.carNo;

		Utils.form.fill('detail', object);
		Utils.modal.show('detail');
	}

	// 关闭
	$('#close-remind').click(function() {
		if (Utils.button.isDisable('close-remind')) {
			return;
		}
		var selectId = grid.selectedData('id');
		$.get('/electr/maintenance/regular-reminds/close?id=' + selectId, function(data) {
			grid.refresh();
		});
	});

	// 导出
	$('#export').click(function() {
		if (Utils.button.isDisable('export')) {
			return;
		}

		window.location.href = operateUri + '/export-excel?' + Utils.form.buildParams('search-form');
	});

	// 搜索
	$('#submit').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});

	// 查询条件重置
	$('#reset').click(function() {
		grid.set('url', defaultUrl);
		grid.refresh();
	});
});