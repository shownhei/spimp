define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	Utils.input.date('input[type=datetime]');
	// 配置表格列
	var fields = [{
	            header : '器材/药品名称',
	            name : 'name'
	        },{
	            header : '数量',
	            width:80,
	            name : 'amount'
	        },{
	            header : '型号',
	            width:100,
	            name : 'model'
	        },{
	            header : '产地',
	            width:80,
	            name : 'origin'
	        },{
	            header : '购置时间',
	            width:80,
	            name : 'buyTime'
	        },{
	            header : '有效使用期限',
	            width:100,
	            name : 'expiration'
	        },{
	            header : '使用、更换、报废情况',
	            name : 'situation'
	        },{
	            header : '备注',
	            name : 'remark'
	        }];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 20);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
	    if (selected ) {
		   Utils.button.enable([ 'edit', 'remove' ]);
		}else{
		   Utils.button.disable([ 'edit', 'remove' ]);
		}
		
	}

	// 配置表格
	var defaultUrl = contextPath + '/ercs/medical-supplies?orderBy=id&order=desc&pageSize='+pageSize;
	var grid = new Grid({
		parentNode : '#medicalSupplies-table',
		url : defaultUrl,
		model : {
			needOrder:true,
			fields : fields,
			height : gridHeight
		},
		onClick : function(target, data) {
			changeButtonsStatus(this.selected, data);
		},
		onLoaded : function() {
			changeButtonsStatus();
		}
	}).render();

	// 新建
	$('#create').click(function() {
		Utils.modal.reset('create');
		Utils.modal.show('create');
	});

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');

		// 验证
		if (object.name === '') {
			Utils.modal.message('create', [ '请输入名称' ]);
			return;
		}
		if (object.amount === '') {
			Utils.modal.message('create', [ '请输入数量' ]);
			return;
		}
		if (object.model === '') {
			Utils.modal.message('create', [ '请输入型号' ]);
			return;
		}
		if (object.buyTime === '') {
			Utils.modal.message('create', [ '请输入购置时间' ]);
			return;
		}
		if (object.expiration === '') {
			Utils.modal.message('create', [ '请输入有效使用期限' ]);
			return;
		}

		$.post('/ercs/medical-supplies', JSON.stringify(object), function(data) {
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
		$.get('/ercs/medical-supplies/' + selectId, function(data) {
			var object = data.data;
			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		// 验证
		if (object.name === '') {
			Utils.modal.message('edit', [ '请输入名称' ]);
			return;
		}
		if (object.amount === '') {
			Utils.modal.message('edit', [ '请输入数量' ]);
			return;
		}
		if (object.model === '') {
			Utils.modal.message('edit', [ '请输入型号' ]);
			return;
		}
		if (object.buyTime === '') {
			Utils.modal.message('edit', [ '请输入购置时间' ]);
			return;
		}
		if (object.expiration === '') {
			Utils.modal.message('edit', [ '请输入有效使用期限' ]);
			return;
		}
		var selectId = grid.selectedData('id');
		$.put('/ercs/medical-supplies/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('/ercs/medical-supplies/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	$('#nav-search-button').click(function() {
		grid.set({
			url : defaultUrl + '&' + $('#search-form').serialize()
		});
	});
	
});