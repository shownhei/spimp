define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/electr/equipment/equipment-ledgers';
	
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	
	// 下拉列表初始化
	Utils.select.remote([ 'search_deviceClass','create_deviceClass','edit_deviceClass' ], '/system/dictionaries?list=true&typeCode=', 'id', 'itemName',true,'设备分类');

	// 下拉列表change事件
	$('#search_deviceClass').bind('change',function(){
		$('#submit').trigger('click');
	});

	// 启用日期控件
	Utils.input.date('input[type=datetime]');

	// 配置表格列
	var fields = [ {
		header : '设备名称',
		name : 'deviceName'
	}, {
		header : '设备编号',
		name : 'equipmentID'
	}, {
		header : '规格型号',
		name : 'deviceModel'
	}, {
		header : '生产厂家',
		name : 'producer'
	}, {
		header : '数量',
		align : 'right',
		width:50,
		name : 'amount'
	}, {
		header : '出厂编号',
		name : 'factoryNumber'
	}, {
		header : '使用年限',
		align : 'right',
		width:80,
		name : 'serviceLife'
	}, {
		header : '使用',
		width:50,
		name : 'inUse'
	}, {
		header : '待修',
		width:50,
		name : 'needsRepair'
	}, {
		header : '已报废',
		width:60,
		name : 'scrapped'
	}, {
		header : '使用地点',
		name : 'usePlace'
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 400);
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
			loadDetailInfo(data.id);
		},
		onLoaded : function() {
			changeButtonsStatus();
			
			// 改变导出按钮状态
			if (this.data.totalCount > 0) {
				Utils.button.enable([ 'export' ]);
			} else {
				Utils.button.disable([ 'export' ]);
			}
			loadDetailInfo();
		}
	}).render();
	var loadDetailInfo = function(equipmentId) {
		var data = '';
		if (equipmentId) {
			data += 'equipmentId=' + equipmentId;
		}
		$.ajax({
			type : 'get',
			data : data,
			dataType : 'text',
			url : '/ignore/equipment/equipment-ledgers/detail',
			success : function(data) {
				$('#detailInfo').html(data);
			}
		});
	};
	//上传
	$('#upload').click(function(){
		Utils.modal.showUpload('/electr/equipment/equipment-ledger/upload', function(data) {
			grid.refresh();
		}, '数据上传');
	});
	// 新建
	$('#create').click(function() {
		Utils.modal.reset('create');
		Utils.modal.show('create');
	});

	// 验证
	function validate(showType, model){
		var errorMsg = [];
		
		if (model.deviceClass.id === '') {
			errorMsg.push('请输入设备分类');
		}

		if (model.deviceName === '') {
			errorMsg.push('请输入设备名称');
		}

		if (model.deviceModel === '') {
			errorMsg.push('请输入规格型号');
		}

		if (model.measureUnit === '') {
			errorMsg.push('请输入单位');
		}

		if (model.amount === '') {
			errorMsg.push('请输入数量');
		}

		if (model.amount !== '' && !$.isNumeric(model.amount)) {
			errorMsg.push('数量为数字格式');
		}

		if (model.productionDate === '') {
			errorMsg.push('请输入出厂日期');
		}

		if (model.buyDate === '') {
			errorMsg.push('请输入购买日期');
		}

		if (model.useDate === '') {
			errorMsg.push('请输入使用日期');
		}

		if (model.serviceLife === '') {
			errorMsg.push('请输入使用年限');
		}

		if (model.serviceLife !== '' && !$.isNumeric(model.serviceLife)) {
			errorMsg.push('使用年限为数字格式');
		}

		if(errorMsg.length > 0){
			Utils.modal.message(showType, [ errorMsg.join(',') ]);
			return false;
		}
		
		return true;
	}
	
	// 查看
	function showDetail(data){
		Utils.modal.reset('detail');
		
		var object = $.extend({},data);
		object.deviceClass = object.deviceClass.itemName;


		Utils.form.fill('detail', object);
		Utils.modal.show('detail');
	}
	
	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');
		
		// 验证
		if(!validate('create', object)){
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
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		
		// 验证
		if(!validate('edit', object)){
			return false;
		}
		
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
	
	// 导出
	$('#export').click(function() {
		if (Utils.button.isDisable('export')) {
			return;
		}
		
		window.location.href = operateUri + '/export-excel?' + Utils.form.buildParams('search-form');
	});

	$('#download').click(function(){
		open(resources+'/template/electr/equipment/equipment-ledgers.xls');
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