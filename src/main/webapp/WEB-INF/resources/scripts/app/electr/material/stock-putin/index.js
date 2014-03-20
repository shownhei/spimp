define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/electr/material/blotters';

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 日期时间选择控件
	$('#create_recordTime').datetimepicker();

	$("#create_materialName").autocomplete('/electr/material/stocks/listname', {
		dataType : "json",
		mustMatch : false,
		cacheLength : 0,
		parse : function(data) {
			return $.map(data.data.result, function(row) {
				return {
					data : row,
					value : row.materialName,
					result : row.materialName
				};
			});
		},
		formatItem : function(item) {
			return item.materialName;
		}
	}).result(function(e, item) {
		var object = $.extend({}, item);
		Utils.form.fill('create', object);
		$('#create_materialName').attr('data-id', item.id);
		$('#create_originalId').val(item.id);
	});
	// 启用日期控件
	Utils.input.date('input[type=datetime]');

	// 配置表格列
	var fields = [ {
		header : '材料名称',
		name : 'materialName'
	}, {
		header : '规格型号/设备号',
		name : 'model'
	}, {
		header : '度量单位',
		name : 'measureUnit'
	}, {
		header : '数量',
		align : 'right',
		width : 80,
		name : 'amount'
	}, {
		header : '单价（元）',
		name : 'price'
	}, {
		header : '经办人',
		width : 80,
		name : 'operator'
	}, {
		header : '备注',
		name : 'remark'
	}, {
		header : '记录时间',
		width : 145,
		name : 'recordTime'
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
		url : contextPath + '/electr/material/putin' + '?orderBy=id&order=desc&pageSize=' + pageSize,
		model : {
			fields : fields,
			needOrder : true,
			orderWidth : 50,
			height : gridHeight
		},
		onClick : function(target, data) {
			changeButtonsStatus(this.selected, data);
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

		if (model.materialName === '') {
			errorMsg.push('请输入材料名称');
		}

		if (model.model === '') {
			errorMsg.push('请输入规格型号/设备号');
		}

		if (model.measureUnit === '') {
			errorMsg.push('请输入度量单位');
		}

		if (model.amount === '') {
			errorMsg.push('请输入数量');
		}

		if (model.amount !== '' && !$.isNumeric(model.amount)) {
			errorMsg.push('数量为数字格式');
		}

		if (model.price === '') {
			errorMsg.push('请输入单价（元）');
		}

		if (model.opertionType === '') {
			errorMsg.push('请输入操作类型');
		}

		if (model.opertionType !== '' && !$.isNumeric(model.opertionType)) {
			errorMsg.push('操作类型为数字格式');
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
	$('#create_operator').val();
});
