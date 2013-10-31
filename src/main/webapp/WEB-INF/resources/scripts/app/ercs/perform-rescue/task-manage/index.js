define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	Utils.select.remote([ 'create-emergencyCategory', 'edit-emergencyCategory','view-emergencyCategory','emergencyCategorySelect'], '/system/dictionaries?typeCode=accident_category&list=true', 'id', 'itemName');
	Utils.select.remote([ 'create-emergencyLevel', 'edit-emergencyLevel','view-emergencyLevel'], '/system/dictionaries?typeCode=accident_level&list=true', 'id', 'itemName');
	Utils.select.remote([ 'create-team', 'edit-team','view-team'], '/ercs/response-team/tree', 'id', 'teamName');

	// 配置表格列
	var fields = [ {
		header : '事故类型',
		width:100,
		name : 'emergencyCategory',
		render : function(val) {
			if (val) {
				return val.itemName;
			}
			return '';
		}
	},{
		header : '严重程度',
		name : 'emergencyLevel',
		width:100,
		render : function(val) {
			if (val) {
				return val.itemName;
			}
			return '';
		}
	},{
		header : '专业组',
		name : 'team',
		width:100,
		render : function(val) {
			if (val) {
				return val.teamName;
			}
			return '';
		}
	},{
		header : '救援措施内容',
		name : 'taskContent'
	}];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 21);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected) {
			Utils.button.enable([ 'edit', 'remove','view' ]);
		} else {
			Utils.button.disable([ 'edit', 'remove','view'  ]);
		}
	}

	// 配置表格
	var defaultUrl = contextPath + '/ercs/emergency-plan-instances?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#refuge-table',
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
	});

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');
		if (object.emergencyCategory === '') {
			Utils.modal.message('create', [ '请输入事故类型' ]);
			return;
		}
		if (object.emergencyLevel === '') {
			Utils.modal.message('create', [ '请输入严重程度' ]);
			return;
		}
		if (object.team === '') {
			Utils.modal.message('create', [ '请输入专业组' ]);
			return;
		}
		if (object.taskContent === '') {
			Utils.modal.message('create', [ '请输入救援措施内容' ]);
			return;
		}
		$.post('/ercs/emergency-plan-instances', JSON.stringify(object), function(data) {
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
		$.get('/ercs/emergency-plan-instances/' + selectId, function(data) {
			var object = data.data;
			Utils.form.fill('edit', object);
			if(object.department){
				$('#edit_department').val(object.department.name);
				$('#edit_department').attr('data-id',object.department.id);
			}
			Utils.modal.show('edit');
		});
	});
	$('#view').click(function() {
		if (Utils.button.isDisable('view')) {
			return;
		}

		Utils.modal.reset('view');
		var selectId = grid.selectedData('id');
		$.get('/ercs/emergency-plan-instances/' + selectId, function(data) {
			var object = data.data;
			Utils.form.fill('view', object);
			if(object.emergencyCategory){
				$('#view-emergencyCategory').val(object.emergencyCategory.itemName);
			}
			if(object.emergencyLevel){
				$('#view-emergencyLevel').val(object.emergencyLevel.itemName);
			}
			if(object.team){
				$('#view-team').val(object.team.teamName);
			}
			Utils.modal.show('view');
		});
	});
	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		if (object.emergencyCategory === '') {
			Utils.modal.message('edit', [ '请输入事故类型' ]);
			return;
		}
		if (object.emergencyLevel === '') {
			Utils.modal.message('edit', [ '请输入严重程度' ]);
			return;
		}
		if (object.team === '') {
			Utils.modal.message('edit', [ '请输入专业组' ]);
			return;
		}
		if (object.taskContent === '') {
			Utils.modal.message('edit', [ '请输入救援措施内容' ]);
			return;
		}
		var selectId = grid.selectedData('id');
		$.put('/ercs/emergency-plan-instances/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('/ercs/emergency-plan-instances/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	$('#emergencyCategorySelect').bind('change',function(){
		$('#nav-search-button').trigger('click');
	});
	// 搜索
	$('#nav-search-button').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});
});
