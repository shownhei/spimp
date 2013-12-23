define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/electr/maintenance/problems';

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 启用日期控件
	Utils.input.date('input[type=datetime]');
	// 下拉列表初始化
	Utils.select.remote([ 'search_car', 'create_car', 'edit_car' ], '/electr/car/carslist', 'id', 'carNo', true, '车号');
	// 配置表格列
	var fields = [ {
		header : '时间',
		width : 90,
		name : 'reportDate'
	}, {
		header : '故障车辆',
		name : 'car',
		render:function(val){
			return val?val.carNo:'';
		}
	},{
		header : '班次',
		width : 90,
		render:function(v){
			switch(v){
				case '0':return '零点班';
				case '4':return '四点班';
				case '8':return '八点班';
				default :return '未知班次';
			}
		},
		name : 'classType'
	}, {
		header : '故障说明',
		name : 'problem'
	}, {
		header : '上报人',
		width : 80,
		name : 'reporter'
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
			Utils.button.enable([ 'edit', 'remove' ]);
		} else {
			Utils.button.disable([ 'edit', 'remove' ]);
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
				Utils.button.enable([ 'export' ]);
			} else {
				Utils.button.disable([ 'export' ]);
			}
		}
	}).render();

	// 新建
	$('#create').click(function() {
		Utils.modal.reset('create');
		Utils.modal.show('create');
	});

	// 验证
	function validate(showType, model) {
		var errorMsg = [];

		if (model.reportDate === '') {
			errorMsg.push('请输入上报日期');
		}

		if (model.car === '') {
			errorMsg.push('请输入故障车辆');
		}

		if (model.problem === '') {
			errorMsg.push('请输入故障说明');
		}

		if (model.reporter === '') {
			errorMsg.push('请输入上报人');
		}

		if (errorMsg.length > 0) {
			Utils.modal.message(showType, [ errorMsg.join(',') ]);
			return false;
		}

		return true;
	}

	// 查看
	function showDetail(data) {
		Utils.modal.reset('detail');
		var object = $.extend({}, data);
		object.car=object.car.carNo;
		Utils.form.fill('detail', object);
		Utils.modal.show('detail');
	}

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');

		// 验证
		if (!validate('create', object)) {
			return false;
		}

		$.post(operateUri, JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.hide('create');
			} else {
				Utils.modal.message('create', data.errors);
			}
		});
	});

	// 编辑
	$('#edit').click(function() {
		if (Utils.button.isDisable('edit')) {
			return;
		}

		Utils.modal.reset('edit');

		var selectId = grid.selectedData('id');
		$.get(operateUri + '/' + selectId, function(data) {
			var object = data.data;
			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
			$('#edit_car').attr('data-id',object.car.id);
			$('#edit_car').val(object.car.carNo);
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		// 验证
		if (!validate('edit', object)) {
			return false;
		}
		var car = {id:$('#edit_car').attr('data-id')};
		delete object.car;
		object.car=car;
		// 处理属性
		var selectId = grid.selectedData('id');
		object.id = selectId;
		$.put(operateUri + '/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.hide('edit');
			} else {
				Utils.modal.message('edit', data.errors);
			}
		});
	});

	// 删除
	$('#remove').click(function() {
		if (Utils.button.isDisable('remove')) {
			return;
		}
		Utils.modal.show('remove');
	});

	// 删除确认
	$('#remove-save').click(function() {
		var selectId = grid.selectedData('id');
		$.del(operateUri + '/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	$("#create-car").autocomplete('/electr/cars', {
		dataType : "json",
		mustMatch : true,
		cacheLength : 20,
		parse : function(data) {
			return $.map(data.data.result, function(row) {
				return {
					data : row,
					value : row.carNo,
					result : row.carNo
				};
			});
		},
		formatItem : function(item) {
			return item.carNo;
		}
	}).result(function(e, item) {
		$('#create-car').attr('data-id', item.id);
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