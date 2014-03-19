define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/electr/maintenance/maintenances';

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	var fields = [ {
		header : '车牌号',
		width : 90,
		name : 'car',
		render : function(val) {
			return val ? val.carNo : '';
		}
	}, {
		header : '保养类别',
		name : 'maintenanceLevel',
		render : function(val) {
			return '日常保养';
		}
	}, {
		header : '保养日期',
		width : 90,
		name : 'maintenanceDate'
	}, {
		header : '保养人',
		width:100,
		name : 'maintenancePeople'
	}, {
		header : '验收人',
		width:100,
		name : 'accepter'
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 400);
	var pageSize = Math.floor(gridHeight / 21);

	// 配置表格
	var defaultUrl = contextPath + operateUri + '?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#statisticPanel',
		url : defaultUrl,
		model : {
			fields : fields,
			needOrder : true,
			orderWidth : 50,
			height : gridHeight
		},
		onClick : function(target, data) {
			changeButtonsStatus(this.selected, data);
			loadMaintenance(data.id);
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
	
	// 下拉列表初始化
	Utils.select.remote([ 'create_car', 'query_car' ], '/electr/car/carslist', 'id', 'carNo', true, '维修车辆');

	// 下拉列表change事件
	$('#query_car,#query_maintenanceDate,#query_maintenanceLevel').bind('change', function() {
		$('#submit').trigger('click');
	});
	$(document).ready(function() {
		var currentDate = new Date();
		var currentDateStr = currentDate.getFullYear() + '-' + (currentDate.getMonth() + 1) + '-' + currentDate.getDate();
		$('#query_maintenanceDate').val(currentDateStr);
	});
	$(document).click(function(event) {
		var el = $(event.target);
		var detailId = el.attr('detailId');
		if (detailId) {
			if (el.attr('buttonType') === 'edit') {
				// 编辑明细
				var selectId = detailId;
				$.get('/electr/maintenance-details/' + selectId, function(data) {
					var object = data.data;
					Utils.modal.reset('edit_maintenance_detail');
					Utils.form.fill('edit_maintenance_detail', object);
					Utils.modal.show('edit_maintenance_detail');
					$('#edit_detail_maintenance').val(object.maintenance.id);
				});
			} else if (el.attr('buttonType') === 'delete') {
				// 删除明细
				$.del('/electr/maintenance-details/' + detailId, function(data) {
					loadMaintenance(grid.selectedData('id'));
					Utils.modal.hide('remove');
				});
			}
		}
	});
	// 验证
	function validateMantenance(showType, model) {
		var errorMsg = [];

		if (model.car.id === '') {
			errorMsg.push('请输入车牌号');
		}

		if (model.maintenanceLevel === '') {
			errorMsg.push('请输入保养类别');
		}

		if (model.maintenanceLevel === '') {
			errorMsg.push('保养类别为数字格式');
		}

		if (model.maintenanceDate === '') {
			errorMsg.push('请输入保养日期');
		}

		if (model.maintenancePeople === '') {
			errorMsg.push('请输入保养人');
		}

		if (errorMsg.length > 0) {
			Utils.modal.message(showType, [ errorMsg.join(',') ]);
			return false;
		}

		return true;
	}
	function validateMantenanceDetail(showType, model) {
		var errorMsg = [];

		if (model.checkItem === '') {
			errorMsg.push('请输检查维修项目');
		}

		if (model.maintenanceWay === '') {
			errorMsg.push('请输入保养方式');
		}

		if (errorMsg.length > 0) {
			Utils.modal.message(showType, [ errorMsg.join(',') ]);
			return false;
		}

		return true;
	}
	var loadMaintenance = function(maintenanceId) {
		changeButtonsStatus(false);
		$('#tablePanel').html('');
		var data = '';
		if (maintenanceId) {
			data += 'maintenanceId=' + maintenanceId;
		} else {
			var queryCar = $('#query_car').val();
			if (queryCar === '') {
				Utils.modal.show('error');
				return;
			}
			data = $("#query-form").serialize();
		}
		$.ajax({
			type : 'get',
			data : data,
			dataType : 'text',
			url : '/electr/maintenance/getdailymaintenance',
			success : function(data) {
				$('#tablePanel').html(data);
				changeButtonsStatus($('#tablePanel').children().length>0);
				var mId = $('#sample-table-1').attr('data-id');
				$('#edit_detail_maintenance').val(mId);
				if(!mId){
					changeButtonsStatus(false);
					Utils.button.enable([ 'create_maintenance']);
				}
			}
		});
	};

	// 日期时间选择控件
	$('#create_recordDateTime').datetimepicker();
	$('#edit_recordDateTime').datetimepicker();

	// 启用日期控件
	Utils.input.date('input[type=datetime]');

	// 配置表格列

	// 计算表格高度和行数
	function changeButtonsStatus(loaded, data) {
		if (loaded) {
			Utils.button.enable([ 'create_maintenance_detail', 'remove_maintenance','export_maintenance' ]);
			Utils.button.disable([  'create_maintenance']);
		} else {
			Utils.button.disable([  'create_maintenance_detail', 'remove_maintenance','export_maintenance' ]);
			Utils.button.enable([ 'create_maintenance']);
		}
	}

	// 新建保养单
	$('#create_maintenance').click(function() {
		if (Utils.button.isDisable('create_maintenance')) {
			return;
		}
		Utils.modal.reset('create_maintenance');
		Utils.modal.show('create_maintenance');
		$('#create_car').val($('#query_car').val());
		$('#create_maintenanceDate').val($('#query_maintenanceDate').val());
	});
	// 添加保养明细
	$('#create_maintenance_detail').click(function() {
		if (Utils.button.isDisable('create_maintenance_detail')) {
			return;
		}
		Utils.modal.reset('create_maintenance_detail');
		Utils.modal.show('create_maintenance_detail');
		var mId = $('#sample-table-1').attr('data-id');
		$('#detail_maintenance').val(mId);
	});

	// 保存
	$('#create_maintenance-save').click(function() {
		var object = Utils.form.serialize('create_maintenance');

		// 验证
		if (!validateMantenance('create_maintenance', object)) {
			return false;
		}

		$.post("/electr/maintenance/maintenances", JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				changeButtonsStatus(true);
				Utils.modal.hide('create_maintenance');
			} else {
				Utils.modal.message('create_maintenance', data.errors);
			}
		});
	});
	$('#edit_maintenance_detail-save').click(function() {
		var object = Utils.form.serialize('edit_maintenance_detail');
		// 验证
		if (!validateMantenanceDetail('edit_maintenance_detail', object)) {
			return false;
		}

		var _maintenanceId=$('#edit_detail_maintenance').val();
		var maintenance = {id:_maintenanceId};
		delete object.maintenance;
		object.maintenance=maintenance;
		$.put("/electr/maintenance/maintenance-details/"+object.id, JSON.stringify(object), function(data) {
			if (data.success) {
				loadMaintenance(_maintenanceId);
				Utils.modal.hide('edit_maintenance_detail');
			} else {
				Utils.modal.message('edit_maintenance_detail', data.errors);
			}
		});
	});
	// 保存
	$('#create_maintenance_detail-save').click(function() {
		
		var object = Utils.form.serialize('create_maintenance_detail');

		// 验证
		if (!validateMantenanceDetail('create_maintenance_detail', object)) {
			return false;
		}
		var maintenance = {
			id : object.maintenance
		};
		delete object.maintenance;
		object.maintenance = maintenance;
		$.post("/electr/maintenance/maintenance-details", JSON.stringify(object), function(data) {
			if (data.success) {
				loadMaintenance($('#detail_maintenance').val());
				Utils.modal.hide('create_maintenance_detail');
			} else {
				Utils.modal.message('create_maintenance_detail', data.errors);
			}
		});
	});
	// 删除
	$('#remove_maintenance').click(function() {
		if (Utils.button.isDisable('remove_maintenance')) {
			return;
		}
		Utils.modal.show('remove');
	});

	// 删除确认
	$('#remove-save').click(function() {
		var selectId = $('#sample-table-1').attr('data-id');
		$.del('/electr/maintenance/maintenances/' + selectId, function(data) {
			Utils.modal.hide('remove');
			$('#tablePanel').html('');
			grid.refresh();
		});
	});

	// 导出
	$('#export_maintenance').click(function() {
		if (Utils.button.isDisable('export_maintenance')) {
			return;
		}
		window.location.href = operateUri + '/export-excel?id='+$('#sample-table-1').attr('data-id');
	});

	// 搜索
	$('#submit').click(function() {
		loadMaintenance();
	});

	// 查询条件重置
	$('#reset').click(function() {
		grid.set('url', defaultUrl);
		grid.refresh();
	});
});
