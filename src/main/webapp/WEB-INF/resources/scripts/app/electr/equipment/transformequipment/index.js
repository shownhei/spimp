define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/electr/equipment/transform-equipments';
	/** 加载配件信息 */
	function loadDeviceInfo(equipmentId) {
		$('#deviceInfoPanel').html('');
		var data = 'id=' + equipmentId;
		$.ajax({
			type : 'get',
			data : data,
			dataType : 'text',
			url : '/electr/equipment/transform-equipments/device',
			success : function(data) {
				var lastSelect = activeTab;
				$('#deviceInfoPanel').html(data);
				$('#' + lastSelect).find('a:first').trigger('click');
				registClickHandler();
			}
		});
	}
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 下拉列表初始化
	Utils.select.remote([ 'search_deviceClass', 'create_deviceClass',
			'edit_deviceClass' ],
			'/system/dictionaries?list=true&typeCode=transform_device_class',
			'id', 'itemName', true, '设备分类');

	// 下拉列表change事件
	$('#search_deviceClass').bind('change', function() {
		$('#submit').trigger('click');
	});

	// 启用日期控件
	Utils.input.date('input[type=datetime]');

	// 配置表格列
	var fields = [
			{
				header : '设备分类',
				name : 'deviceClass',
				render : function(value) {
					return value === null ? '' : value.itemName;
				}
			},
			{
				header : '设备名称',
				name : 'deviceName'
			},
			{
				header : '设备型号',
				name : 'deviceModel'
			},
			{
				header : '速度(m/s)',
				name : 'speed'
			},
			{
				header : '输送量(T/h)',
				name : 'conveyingCapacity'
			},
			{
				header : '出厂编号',
				name : 'factoryNumber'
			},
			{
				header : '出厂日期',
				width : 90,
				name : 'productionDate'
			},
			{
				header : '设备编号',
				name : 'equipmentNumber'
			},
			{
				header : '使用地点',
				name : 'location'
			},
			{
				header : '生产厂家',
				name : 'producer'
			},
			{
				header : '布置长度(m)',
				align : 'right',
				width : 80,
				name : 'layoutLength'
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
					+ $('.page-header').height() + 370);
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
			loadDeviceInfo(data.id);
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

		if (model.deviceClass.id === '') {
			errorMsg.push('请输入设备分类');
		}

		if (model.deviceName === '') {
			errorMsg.push('请输入设备名称');
		}

		if (model.deviceModel === '') {
			errorMsg.push('请输入设备型号');
		}

		if (model.speed === '') {
			errorMsg.push('请输入速度(m/s)');
		}

		if (model.conveyingCapacity === '') {
			errorMsg.push('请输入输送量(T/h)');
		}

		if (model.productionDate === '') {
			errorMsg.push('请输入出厂日期');
		}

		if (model.layoutLength !== '' && !$.isNumeric(model.layoutLength)) {
			errorMsg.push('布置长度(m)为数字格式');
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
		object.deviceClass = object.deviceClass.itemName;
		object.recordGroup = object.recordGroup.name;

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
	$('#export').click(function() {
		if (Utils.button.isDisable('export')) {
			return;
		}

		window.location.href = operateUri + '/export-excel';
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
	/*
	 * (1):减速机-ReducerDevice; (2):电动机-ElectromotorDevice; (3):制动器-BrakeDevice;
	 * (4):拉紧装置-TensioningDevice;
	 */
	$('#reducer_create-save').click(
			function() {
				var object = Utils.form.serialize('reducer_create');
				object.transformEquipmentId = grid.selectedData('id');
				$.post('/electr/equipment/reducer-devices', JSON
						.stringify(object), function(data) {
					if (data.success) {
						Utils.modal.hide('reducer_create');
					} else {
						Utils.modal.message('reducer_create', data.errors);
					}
					loadDeviceInfo(grid.selectedData('id'));
				});
			});
	$('#electromotor_create-save').click(
			function() {
				var object = Utils.form.serialize('electromotor_create');
				object.transformEquipmentId = grid.selectedData('id');
				$.post('/electr/equipment/electromotor-devices', JSON
						.stringify(object),
						function(data) {
							if (data.success) {
								Utils.modal.hide('electromotor_create');
							} else {
								Utils.modal.message('electromotor_create',
										data.errors);
							}
							loadDeviceInfo(grid.selectedData('id'));
						});
			});
	$('#brake_create-save').click(
			function() {
				var object = Utils.form.serialize('brake_create');
				object.transformEquipmentId = grid.selectedData('id');
				$.post('/electr/equipment/brake-devices', JSON
						.stringify(object), function(data) {
					if (data.success) {
						Utils.modal.hide('brake_create');
					} else {
						Utils.modal.message('brake_create', data.errors);
					}
					loadDeviceInfo(grid.selectedData('id'));
				});
			});
	$('#tensioning_create-save').click(
			function() {
				var object = Utils.form.serialize('tensioning_create');
				object.transformEquipmentId = grid.selectedData('id');
				$.post('/electr/equipment/tensioning-devices', JSON
						.stringify(object), function(data) {
					if (data.success) {
						Utils.modal.hide('tensioning_create');
					} else {
						Utils.modal.message('tensioning_create', data.errors);
					}
					loadDeviceInfo(grid.selectedData('id'));
				});
			});
	var registClickHandler = function() {
		// 新建减速机
		$('#reducer_create').click(function() {
			Utils.modal.reset('reducer_create');
			Utils.modal.show('reducer_create');
		});

		// 新建电动机
		$('#electromotor_create').click(function() {
			Utils.modal.reset('electromotor_create');
			Utils.modal.show('electromotor_create');
		});

		// 新建制动器
		$('#brake_create').click(function() {
			Utils.modal.reset('brake_create');
			Utils.modal.show('brake_create');
		});

		// 新建拉紧装置
		$('#tensioning_create').click(function() {
			Utils.modal.reset('tensioning_create');
			Utils.modal.show('tensioning_create');
		});
	};
	var activeTab = '';
	var showTip = function(x, y, html) {
		var panel = $('#show_tips');
		panel.css({
			left : x,
			top : y-100
		});
		panel.html(html);
		panel.show();
	};
	$(document).mouseover(function(event) {
		var el = $(event.target);
		var parent=el.parent();
		if (el.is('td') && el.attr('dataType') == 'showRemark'||(el.is('div')&&parent.is('td')&&parent.attr('dataType') == 'showRemark')) {
			if($('#show_tips').is(":hidden")){
				showTip(event.pageX-150, event.pageY-40, el.find('div:first').html());
			}else{
				$('#show_tips').html(el.find('div:first').html());
			}
		} else {
			$('#show_tips').hide();
		}
	});
	$(document).click(
			function(event) {
				var el = $(event.target);
				var elType = el.attr('elType');
				var temp = el;
				if (elType == 'tab') {
					while (!temp.is('li')) {
						temp = temp.parent();
					}
					activeTab = $('.active').attr('id');
					return;
				}
				var dataType = el.attr('dataType');
				if (dataType == 'reducer') {
					var dataId = el.attr('dataId');
					var buttonType = el.attr('buttonType');
					if (buttonType == 'delete') {
						$.del('/electr/equipment/reducer-devices/' + dataId,
								function(data) {
									loadDeviceInfo(grid.selectedData('id'));
								});
					}
					return;
				}
				if (dataType == 'electromotor') {
					var dataId = el.attr('dataId');
					var buttonType = el.attr('buttonType');
					if (buttonType == 'delete') {
						$.del('/electr/equipment/electromotor-devices/'
								+ dataId, function(data) {
							loadDeviceInfo(grid.selectedData('id'));
						});
					}return;
				}
				if (dataType == 'brake') {
					var dataId = el.attr('dataId');
					var buttonType = el.attr('buttonType');
					if (buttonType == 'delete') {
						$.del('/electr/equipment/brake-devices/' + dataId,
								function(data) {
									loadDeviceInfo(grid.selectedData('id'));
								});
					}return;
				}
				if (dataType == 'tensioning') {
					var dataId = el.attr('dataId');
					var buttonType = el.attr('buttonType');
					if (buttonType == 'delete') {
						$.del('/electr/equipment/tensioning-devices/' + dataId,
								function(data) {
									loadDeviceInfo(grid.selectedData('id'));
								});
					}return;
				}
			});
});