define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/electr/equipment/equipments';

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	var loadMaintenance = function(equipmentId) {
		var data = '';
		if (equipmentId) {
			data += 'equipmentId=' + equipmentId;
		}
		$.ajax({
			type : 'get',
			data : data,
			dataType : 'text',
			url : '/electr/equipment/info',
			success : function(data) {
				$('#info_panel').html(data);
				$('#' + activeTab).find('a:first').trigger('click');
			}
		});
	};
	// 下拉列表初始化
	Utils.select.remote([ 'create_deviceClass', 'edit_deviceClass' ], '/system/dictionaries?list=true&typeCode=', 'id', 'itemName', true, '设备分类');
	Utils.select.remote([ 'search_deviceCategory', 'create_deviceCategory', 'edit_deviceCategory' ],
			'/system/dictionaries?list=true&typeCode=equipment_deviceCategory', 'id', 'itemName', true, '设备种类');
	Utils.select.remote([ 'search_deviceType', 'create_deviceType', 'edit_deviceType' ], '/system/dictionaries?list=true&typeCode=equipment_deviceType', 'id',
			'itemName', true, '设备类型');
	Utils.select.remote([ 'search_serviceEnvironment', 'create_serviceEnvironment', 'edit_serviceEnvironment' ],
			'/system/dictionaries?list=true&typeCode=equipment_serviceEnvironment', 'id', 'itemName', true, '使用环境');
	Utils.select.remote([ 'search_deviceArea', 'create_deviceArea', 'edit_deviceArea' ], '/system/dictionaries?list=true&typeCode=equipment_deviceArea', 'id',
			'itemName', true, '所属区域');
	Utils.select.remote([ 'search_stowedPosition', 'create_stowedPosition', 'edit_stowedPosition' ],
			'/system/dictionaries?list=true&typeCode=equipment_stowedPosition', 'id', 'itemName', true, '存放地点');

	// 下拉列表change事件
	$('#search_deviceClass').bind('change', function() {
		$('#submit').trigger('click');
	});
	$('#search_deviceCategory').bind('change', function() {
		$('#submit').trigger('click');
	});
	$('#search_deviceType').bind('change', function() {
		$('#submit').trigger('click');
	});
	$('#search_serviceEnvironment').bind('change', function() {
		$('#submit').trigger('click');
	});
	$('#search_deviceArea').bind('change', function() {
		$('#submit').trigger('click');
	});
	$('#search_stowedPosition').bind('change', function() {
		$('#submit').trigger('click');
	});

	// 启用日期控件
	Utils.input.date('input[type=datetime]');

	// 配置表格列
	var fields = [ {
		header : '设备种类',
		name : 'deviceCategory',
		hidden : 'tablet',
		render : function(value) {
			return value === null ? '' : value.itemName;
		}
	}, {
		header : '设备类型',
		name : 'deviceType',
		render : function(value) {
			return value === null ? '' : value.itemName;
		}
	}, {
		header : '设备名称',
		name : 'deviceName'
	}, {
		header : '设备型号',
		name : 'deviceModel'
	}, {
		header : '使用环境',
		name : 'serviceEnvironment',
		render : function(value) {
			return value === null ? '' : value.itemName;
		}
	}, {
		header : '所属区域',
		name : 'deviceArea',
		hidden : 'tablet',
		render : function(value) {
			return value === null ? '' : value.itemName;
		}
	}, {
		header : '存放地点',
		name : 'stowedPosition',
		render : function(value) {
			return value === null ? '' : value.itemName;
		}
	}, {
		header : '用途',
		hidden : 'phone',
		name : 'usage'
	}, {
		header : '生产厂家',
		hidden : 'tablet',
		name : 'producer'
	}, {
		header : '出厂日期',
		width : 90,
		name : 'productionDate'
	}, {
		header : '包机人',
		width : 80,
		name : 'chargePerson'
	}, {
		header : '班长/组长',
		name : 'monitor'
	}, {
		header : '三开一防锁',
		hidden : 'tablet',
		name : 'openLocker'
	}, {
		header : '数量',
		name : 'lockerNumber'
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 380);
	var pageSize = Math.floor(gridHeight / 21);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected) {
			Utils.button.enable([ 'edit', 'remove', 'create_detail' ]);
		} else {
			Utils.button.disable([ 'edit', 'remove', 'create_detail' ]);
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
			loadMaintenance(data.id);
			if (target.attr('data-role') === 'detail') {
				showDetail(data);
			}
		},
		onLoaded : function() {
			loadMaintenance(null);
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
	$('#create_detail').click(function() {
		if (Utils.button.isDisable('create_detail')) {
			return;
		}
		Utils.modal.reset('create_detail');
		Utils.modal.show('create_detail');
		var selectId = grid.selectedData('id');
		$('#create_detail-equipmentId').val(selectId);
	});

	// 验证
	function validate(showType, model) {
		var errorMsg = [];

		if (model.deviceClass.id === '') {
			errorMsg.push('请输入设备分类');
		}

		if (model.deviceCategory.id === '') {
			errorMsg.push('请输入设备种类');
		}

		if (model.deviceType.id === '') {
			errorMsg.push('请输入设备类型');
		}

		if (model.deviceName === '') {
			errorMsg.push('请输入设备名称');
		}

		if (model.deviceModel === '') {
			errorMsg.push('请输入设备型号');
		}

		if (model.serviceEnvironment.id === '') {
			errorMsg.push('请输入使用环境');
		}

		if (model.deviceArea.id === '') {
			errorMsg.push('请输入所属区域');
		}

		if (model.stowedPosition.id === '') {
			errorMsg.push('请输入存放地点');
		}

		if (model.deviceNumber === '') {
			errorMsg.push('请输入设备编号');
		}

		if (model.productionDate === '') {
			errorMsg.push('请输入出厂日期');
		}

		if (errorMsg.length > 0) {
			Utils.modal.message(showType, [ errorMsg.join(',') ]);
			return false;
		}

		return true;
	}
	// 验证
	function validateDetail(showType, model) {
		var errorMsg = [];

		if (model.accessoryModel === '') {
			errorMsg.push('请输入配件型号');
		}

		if (model.accessoryNumber === '') {
			errorMsg.push('请输入配件编号');
		}

		if (model.productionDate === '') {
			errorMsg.push('请输入出厂日期');
		}

		if (model.producer === '') {
			errorMsg.push('请输入生产厂家');
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
		object.deviceCategory = object.deviceCategory.itemName;
		object.deviceType = object.deviceType.itemName;
		object.serviceEnvironment = object.serviceEnvironment.itemName;
		object.deviceArea = object.deviceArea.itemName;
		object.stowedPosition = object.stowedPosition.itemName;

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
		if(object.pictureURL===''){
			delete object.pictureURL;
		}
		if(object.specificationURL===''){
			delete object.specificationURL;
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
	$('#create_detail-save').click(function() {
		var object = Utils.form.serialize('create_detail');
		// 验证
		if (!validateDetail('create_detail', object)) {
			return false;
		}
		$.post('/electr/equipment/accessories', JSON.stringify(object), function(data) {
			if (data.success) {
				loadMaintenance(grid.selectedData('id'));
				Utils.modal.hide('create_detail');
			} else {
				Utils.modal.message('create_detail', data.errors);
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
			$('#edit_specificationURL').attr('data-id', object.specificationURL.id);
			$('#edit_specificationURL').val(object.specificationURL.simpleName);
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		// 验证
		if (!validate('edit', object)) {
			return false;
		}

		var selectId = grid.selectedData('id');
		if(object.pictureURL===''){
			delete object.pictureURL;
		}
		if(object.specificationURL===''){
			delete object.specificationURL;
		}else{
			// 处理属性
			object.id = selectId;
			var specificationURL = {
					id : $('#edit_specificationURL').attr('data-id')
			};
			delete object.specificationURL;
			object.specificationURL = specificationURL;
		}
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

	// 导出
	$('#export').click(function() {
		if (Utils.button.isDisable('export')) {
			return;
		}

		window.location.href = operateUri + '/export-excel?' + Utils.form.buildParams('search-form');
	});
	// 导入
	$('#import').click(function() {
		if (Utils.button.isDisable('import')) {
			return;
		}
		Utils.modal.showUpload('/electr/equipment/equipments/upload', function(data) {
			grid.refresh();
		}, '数据上传');
	});
	$('#download').click(function(){
		open(resources+'/template/electr/equipment/electric_equipment_upload_tpl.xls');
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
	$('#file').bind('change', function() {
		if ($('#file').val() !== '') {
			$('#create-file-form').submit();
			var process = new Utils.modal.showProcess('process');
			window.process = process;
		}
	});
	/** 关闭提示框 */
	var closeTips = function(el) {
		var parent = el.parent();
		var b1 = el.is('td') && (el.attr('dataType') === 'showRemark');
		var b2 = el.is('div') && parent.is('td') && (parent.attr('dataType') === 'showRemark');
		if (!(b1 || b2)) {
			$('#show_tips').hide();
		}
	};
	var activeTab = '';
	$(document).click(function(event) {
		var el = $(event.target);
		closeTips(el);
		var elType = el.attr('elType');
		if (el.is('a') && elType === 'showDocument') {
			$('#showDocument').attr('src', '/ercs/view-pdf/' + el.attr('data-id') + "?t=" + new Date().getTime());
			Utils.modal.show('view');
			return;
		}
		var temp = el;
		if (elType === 'tab') {
			while (!temp.is('li')) {
				temp = temp.parent();
			}
			activeTab = temp.attr('id');
			return;
		}
		var buttonType = el.attr('buttonType');
		if (buttonType === 'delete') {
			var selectId = grid.selectedData('id');
			$.del('/electr/equipment/accessories/' + el.attr('data-id'), function(data) {
				loadMaintenance(selectId);
			});
		}
		if (buttonType === 'upload') {
			accessoryId = el.attr('data-id');
			Utils.modal.reset('upload');
			Utils.modal.show('upload');
			$('#create-file-form')[0].reset();
			$('#create-file-form').show();
		}
		if (buttonType === 'upload-instructions') {
			accessoryId = el.attr('data-id');
			Utils.modal.reset('upload-instructions');
			Utils.modal.show('upload-instructions');
			$('#create-file-form')[0].reset();
			$('#create-file-form').show();
		}
	});
	var showTip = function(x, y, html) {
		var panel = $('#show_tips');
		panel.css({
			left : x,
			top : y - 100
		});
		panel.html(html);
		panel.show();
	};
	$(document).mouseover(function(event) {
		var el = $(event.target);
		if (el.attr('buttonType') === 'upload') {
			$('#show_tips').hide();
			return;
		}
		var parent = el.parent();
		if ((el.is('td') && el.attr('dataType') === 'showRemark') || (el.is('div') && parent.is('td') && parent.attr('dataType') === 'showRemark')) {
			var _html = '';
			if (el.is('td')) {
				_html = '<image src=' + el.find('div:first').html() + '>';
			} else {
				_html = '<image src=' + el.html() + '>';
			}
			showTip(event.pageX - 250, event.pageY - 40, _html);
		}
	});
	var accessoryId = '';
	function callBack(data) {
		window.process.stop();
		window.process = null;
		if (!data.success) {
			alert("上传失败..." + data.data);
			return false;
		} else {
			var selectId = grid.selectedData('id');
			$.get('/electr/equipment/accessories/setpictureurl?id=' + accessoryId + '&pictureUrl=' + encodeURI(data.data), function(data) {
				Utils.modal.hide('upload');
				loadMaintenance(selectId);
			});
		}
	}
	window.callBack = callBack;
	loadMaintenance(null);
	// 数据导入
	$('#upload_data_file').bind('change', function() {
		$('#upload-data-form').submit();
	});
	// 上传说明书
	$('#upload-instructions-file').bind('change', function() {
		$('#upload-instructions-form').submit();
	});
	// 上传说明书回调
	var uploadInstructionsCallback = function(data) {
		$.ajax({
			type : 'get',
			data : "accessoryId=" + accessoryId + "&uploadFileId=" + data.data.id,
			dataType : 'text',
			url : '/electr/equipment/accessories/setinstructions',
			success : function(data) {
				Utils.modal.hide('upload-instructions');
			}
		});
	};
	window.uploadInstructionsCallback = uploadInstructionsCallback;
	$('#create_pictureURL,#edit_pictureURL').bind('click', function() {
		var uploadUrl = '/simpleupload';
		var that = this;
		Utils.modal.showUpload(uploadUrl, function(data) {
			$(that).val(data.data);
		}, '上传图片');
	});
	$('#create_specificationURL,#edit_specificationURL').bind('click', function() {
		var uploadUrl = '/simpleupload';
		var that = this;
		Utils.modal.showUpload(uploadUrl, function(data) {
			$(that).val(data.data.simpleName);
			$(that).attr('data-id', data.data.id);
		}, '上传说明书');
	});
});