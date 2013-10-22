
define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');
	window.$=$;
	window.Utils=Utils;
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	Utils.select.remote([ 'create-planType', 'edit-planType', 'planTypeSelect' ], '/ercs/dictionaries?typeCode=plan_type&list=true', 'id', 'itemName');

	// 配置表格列
	var fields = [ {
		header : '物资名称',
		name : 'resource',
		render:function(v){
			return v.resourceName;
		}
	}, {
		header : '物资编号',
		name : 'resource',
		width:80,
		render:function(v){
			return v.resourceNo;
		}
	},{
		header : '使用时间',
		name : 'useTime'
	},{
		header : '使用数量',
		width:70,
		name : 'useAmount'
	}, {
		header : '检查维修时间',
		name : 'maintenanceTime'
	}, {
		header : '更换情况',
		name : 'replacement'
	} ,{
		header : '报废情况',
		name : 'scrapped'
	},{
		header : '备注',
		name : 'remark',
		width : 80
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
	var defaultUrl = contextPath + '/ercs/resource-use-records?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#plan-table',
		url : defaultUrl,
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
		}
	}).render();

	// 新建
	$('#create').click(function() {
		Utils.modal.reset('create');
		Utils.modal.show('create');
		$('#create-file-delete').trigger('click');
	});

	// 保存
	$('#create-save').click(function() {
		if($('#create-save').hasClass('disabled')){
			return ;
		}
		var object = Utils.form.serialize('create');
		// 验证
		object.resource={id:1};
		$.post('resource-use-records', JSON.stringify(object), function(data) {
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
		$.get('resource-use-records/' + selectId, function(data) {
			var object = data.data;
			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		
		// 验证
		// 处理属性
		object.resource={id:1};
		var selectId = grid.selectedData('id');
		$.put('resource-use-records/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('resource-use-records/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	// 搜索
	$('#nav-search-button').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});
	//文件上传
	$('#planTypeSelect').bind('change',function(){
		$('#nav-search-button').trigger('click');
	});
});