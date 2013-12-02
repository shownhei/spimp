define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/electr/material/plans';
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	var loadPlan = function(planId) {
		$('#add_detail-plan').val('');
		var queryYear = $('#query_year').val();
		var month = $('#query_month').val();
		var yearMonth = queryYear + '-' + month;
		var data = '';
		data += 'planId=' + planId;
		data += '&yearMonth=' + yearMonth;
		$.ajax({
			type : 'get',
			data : data,
			dataType : 'text',
			url : '/electr/material/getplan',
			success : function(data) {
				$('#tablePanel').html(data);
				$('#add_detail-plan').val($('#sample-table-1').attr('data-id'));
				changeButtonsStatus(true,data);
			}
		});
	};
	// 启用日期控件
	Utils.input.date('input[type=datetime]');
	Utils.select.remote([ 'planGroup' ], '/system/groups', 'id', 'name');
	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(loaded, data) {
		if (loaded &&($('#add_detail-plan').val()!=='')) {
			Utils.button.enable([ 'add_detail', 'remove_plan', 'export_plan' ]);
			Utils.button.disable([ 'create_plan' ]);
		} else {
			Utils.button.disable([ 'add_detail', 'remove_plan', 'export_plan' ]);
			Utils.button.enable([ 'create_plan' ]);
		}
	}

	// 新建
	$('#create_plan').click(function() {
		if (Utils.button.isDisable('create_plan')) {
			return;
		}
		Utils.modal.reset('create_plan');
		Utils.modal.show('create_plan');
		var queryYear = $('#query_year');
		$('#create_planDate').val(queryYear.val() + '-' + $('#query_month').val());
	});

	// 验证
	function validate(showType, model) {
		var errorMsg = [];

		if (model.planDate === '') {
			errorMsg.push('请输入计划日期');
		}

		if (model.planGroup === '') {
			errorMsg.push('请输入单位');
		}

		if (model.planTitle === '') {
			errorMsg.push('请输入计划名称');
		}

		if (errorMsg.length > 0) {
			Utils.modal.message(showType, [ errorMsg.join(',') ]);
			return false;
		}

		return true;
	}
	function detailValidate(showType, model) {
		var errorMsg = [];

		if (model.plan === '') {
			errorMsg.push('请输入单位');
		}

		if (model.materialName === '') {
			errorMsg.push('请输入材料名称');
		}

		if (model.model === '') {
			errorMsg.push('请输入规格型号、设备号');
		}

		if (model.measureUnit === '') {
			errorMsg.push('请输入度量单位');
		}

		if (model.price === '') {
			errorMsg.push('请输入单价（元）');
		}
		if (model.quantity === '') {
			errorMsg.push('请输入数量');
		}
		if (model.sumMoney === '') {
			errorMsg.push('请输入金额');
		}
		
		if (errorMsg.length > 0) {
			Utils.modal.message(showType, [ errorMsg.join(',') ]);
			return false;
		}
		return true;
	}
	// 保存
	$('#create_plan-save').click(function() {
		var object = Utils.form.serialize('create_plan');
		// 验证
		if (!validate('create_plan', object)) {
			return false;
		}
		changeButtonsStatus();
		$.post(operateUri, JSON.stringify(object), function(data) {
			if (data.success) {
				loadPlan(data.data.id);
				Utils.modal.hide('create_plan');
			} else {
				Utils.modal.message('create_plan', data.errors);
			}
		});
	});

	// 编辑
	$('#add_detail').click(function() {
		if (Utils.button.isDisable('add_detail')) {
			return;
		}
		Utils.modal.reset('add_detail');
		Utils.modal.show('add_detail');
	});

	// 保存明细
	$('#add_detail-save,#add_detail-saveandclose').click(function(event) {
		var object = Utils.form.serialize('add_detail');
		// 验证
		if (!detailValidate('add_detail', object)) {
			return false;
		}

		var plan = {
			id : object.plan
		};
		delete object.plan;
		object.plan = plan;
		// 处理属性
		$.post('/electr/material/plan-details', JSON.stringify(object), function(data) {
			if (data.success) {
				var el = $(event.target);
				if (el.attr('id') !== 'add_detail-save') {
					Utils.modal.hide('add_detail');
				} else {
					Utils.modal.reset('add_detail');
				}
				loadPlan($('#add_detail-plan').val());
			} else {
				Utils.modal.message('add_detail', data.errors);
			}
		});
	});
	$('#edit_detail-save').click(function(event) {
		var object = Utils.form.serialize('edit_detail');
		// 验证
		if (!detailValidate('edit_detail', object)) {
			return false;
		}

		var plan = {
			id : object.plan
		};
		delete object.plan;
		object.plan = plan;
		// 处理属性
		$.put('/electr/material/plan-details/'+object.id, JSON.stringify(object), function(data) {
			if (data.success) {
				Utils.modal.hide('edit_detail');
				loadPlan($('#edit_detail-plan').val());
			} else {
				Utils.modal.message('edit_detail', data.errors);
			}
		});
	});
	// 删除
	$('#remove_plan').click(function() {
		if (Utils.button.isDisable('remove_plan')) {
			return;
		}
		Utils.modal.show('remove');
	});

	// 删除确认
	$('#remove-save').click(function() {
		var selectId = $('#add_detail-plan').val();
		$.del(operateUri + '/' + selectId, function(data) {
			$('#tablePanel').html('');
			$('#submit').trigger('click');
			Utils.modal.hide('remove');
		});
	});

	// 导出
	$('#export_plan').click(function() {
		if (Utils.button.isDisable('export')) {
			return;
		}
		window.location.href = '/electr/material/plans/export-excel?' + Utils.form.buildParams('search-form');
	});

	// 搜索
	$('#submit').click(function() {
		loadPlan('');
	});

	$(document).ready(function() {
		var queryYear = $('#query_year');
		var queryMonth = $('#query_month');
		var currentDate = new Date();
		queryYear.val(currentDate.getFullYear());
		queryMonth.val(currentDate.getMonth() + 1);
		queryYear.bind('keydown', function() {
			return false;
		});
		queryYear.bind('change', function() {
			$('#submit').trigger('click');
		});
		queryMonth.bind('change', function() {
			$('#submit').trigger('click');
		});
		$('#submit').trigger('click');
		$(document).click(function(event) {
			var el = $(event.target);
			var detailId = el.attr('detailId');
			if (detailId) {
				if (el.attr('buttonType') === 'edit') {
					// 编辑明细
					var selectId = detailId;
					$.get('/electr/material/plan-details/' + selectId, function(data) {
						var object = data.data;
						Utils.modal.reset('edit_detail');
						Utils.form.fill('edit_detail', object);
						Utils.modal.show('edit_detail');
						$('#edit_detail-plan').val(object.plan.id);
					});
				} else if (el.attr('buttonType') === 'delete') {
					// 删除明细
					$.del('/electr/material/plan-details/' + detailId, function(data) {
						$('#submit').trigger('click');
						Utils.modal.hide('remove');
					});
				}
			}
		});
	});
});