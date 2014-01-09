define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/electr/equipment/fire-fighting-equipments';

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 启用日期控件
	Utils.input.date('input[type=datetime]');

	// 配置表格列
	var fields = [
			{
				header : '存在地点',
				name : 'location'
			},
			{
				header : '编号',
				name : 'equipmentCode'
			},
			{
				header : '沙箱容积',
				align : 'right',
				width : 80,
				name : 'sandBoxCapacity'
			},
			{
				header : "CO2（4Kg）",
				children : [ {
					header : '数量',
					name : 'amount1'
				}, {
					header : '维修时间',
					name : 'maintenanceDate1'
				} ]
			},
			{
				header : "干粉（8Kg）",
				children : [ {
					header : '数量',
					align : 'right',
					width : 80,
					name : 'amount2'
				}, {
					header : '维修时间',
					name : 'maintenance2'
				} ]
			},
			{
				header : '消防斧',
				align : 'right',
				width : 80,
				name : 'fireAxe'
			},
			{
				header : '消防钩',
				align : 'right',
				width : 80,
				name : 'fireHook'
			},
			{
				header : '消防桶',
				align : 'right',
				width : 80,
				name : 'fireBucket'
			},
			{
				header : '消防锹',
				align : 'right',
				width : 80,
				name : 'fireShovel'
			},
			{
				header : '其他',
				name : 'others'
			},
			{
				header : '记录日期',
				width : 90,
				name : 'recordDate'
			},
			{
				header : '查看',
				name : 'id',
				width : 50,
				align : 'center',
				render : function(value) {
					return '<i data-role="detail" class="icon-list" style="cursor:pointer;"></i>';
				}
			} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height()
			- ($('.navbar').height() + $('.page-toolbar').height()
					+ $('.page-header').height() + 100);
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
	var defaultUrl = contextPath + operateUri
			+ '?orderBy=id&order=desc&pageSize=' + pageSize;
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

		if (model.location === '') {
			errorMsg.push('请输入存在地点');
		}

		if (model.equipmentCode === '') {
			errorMsg.push('请输入编号');
		}

		if (model.sandBoxCapacity !== '' && !$.isNumeric(model.sandBoxCapacity)) {
			errorMsg.push('沙箱容积为数字格式');
		}

		if (model.amount2 !== '' && !$.isNumeric(model.amount2)) {
			errorMsg.push('数量为数字格式');
		}

		if (model.maintenance2 === '') {
			errorMsg.push('请输入维修时间');
		}

		if (model.fireAxe === '') {
			errorMsg.push('请输入消防斧');
		}

		if (model.fireAxe !== '' && !$.isNumeric(model.fireAxe)) {
			errorMsg.push('消防斧为数字格式');
		}

		if (model.fireHook !== '' && !$.isNumeric(model.fireHook)) {
			errorMsg.push('消防钩为数字格式');
		}

		if (model.fireBucket !== '' && !$.isNumeric(model.fireBucket)) {
			errorMsg.push('消防桶为数字格式');
		}

		if (model.fireShovel !== '' && !$.isNumeric(model.fireShovel)) {
			errorMsg.push('消防锹为数字格式');
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
		});
	});

	// 更新
	$('#edit-save').click(
			function() {
				var object = Utils.form.serialize('edit');

				// 验证
				if (!validate('edit', object)) {
					return false;
				}

				// 处理属性
				var selectId = grid.selectedData('id');
				object.id = selectId;
				$.put(operateUri + '/' + selectId, JSON.stringify(object),
						function(data) {
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

	// 导出
	$('#export').click(
			function() {
				if (Utils.button.isDisable('export')) {
					return;
				}

				window.location.href = operateUri + '/export-excel?'
						+ Utils.form.buildParams('search-form');
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