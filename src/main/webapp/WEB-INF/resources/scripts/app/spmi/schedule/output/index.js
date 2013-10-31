define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/spmi/schedule/outputs';
	
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	
	// 下拉列表初始化
	Utils.select.remote([ 'search_duty','create_duty','edit_duty' ], '/system/dictionaries?typeCode=schedule_duty&list=true', 'id', 'itemName',true,'班次');
	Utils.select.remote([ 'create_team','edit_team' ], '/spmi/schedule/teams?list=true', 'id', 'teamName',true,'队组');
	Utils.select.remote([ 'create_exploitType','edit_exploitType' ], '/system/dictionaries?typeCode=schedule_exploit_type&list=true', 'id', 'itemName',true,'开采方式');
	Utils.select.remote([ 'create_workingFace','edit_workingFace' ], '/system/dictionaries?typeCode=schedule_working_face&list=true', 'id', 'itemName',true,'工作面');
	Utils.select.remote([ 'create_workingPlace','edit_workingPlace' ], '/system/dictionaries?typeCode=schedule_working_place&list=true', 'id', 'itemName',true,'工作地点');

	// 下拉列表change事件
	$('#search_duty').bind('change',function(){
		$('#submit').trigger('click');
	});

	// 启用日期控件
	Utils.input.date('input[type=datetime]');
	
	// 配置表格列
	var fields = [
		{
			header : '开采日期',
			name : 'digDate'
		},
		{
			header : '班次',
			name : 'duty'
			,render : function(value) {
				if(value != null){
					return value.itemName;
				} else {
					return '';
				}
			}
		},
		{
			header : '队组',
			name : 'team'
			,render : function(value) {
				if(value != null){
					return value.teamName;
				} else {
					return '';
				}
			}
		},
		{
			header : '开采方式',
			name : 'exploitType'
			,render : function(value) {
				if(value != null){
					return value.itemName;
				} else {
					return '';
				}
			}
		},
		{
			header : '工作面',
			name : 'workingFace'
			,render : function(value) {
				if(value != null){
					return value.itemName;
				} else {
					return '';
				}
			}
		},
		{
			header : '工作地点',
			name : 'workingPlace'
			,render : function(value) {
				if(value != null){
					return value.itemName;
				} else {
					return '';
				}
			}
		},
		{
			header : '跟班队干',
			name : 'teamLeader'
		},
		{
			header : '当班班长',
			name : 'monitor'
		},
		{
			header : '安全员',
			name : 'safeChecker'
		},
		{
			header : '计划出勤人数',
			name : 'planMen'
		},
		{
			header : '实际出勤人数',
			name : 'infactMen'
		},
		{
			header : '产量计划吨数',
			name : 'planTons'
		},
		{
			header : '产量实际吨数',
			name : 'infactTons'
		},
		{
			header : '开机时间',
			name : 'powerOnTime'
		}
	];

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
	function validate(showType, model){
		var errorMsg = new Array();
		
		if (model.digDate === '') {
			errorMsg.push('请输入开采日期');
		}

		if (model.duty.id === '') {
			errorMsg.push('请输入班次');
		}

		if (model.team.id === '') {
			errorMsg.push('请输入队组');
		}

		if (model.exploitType.id === '') {
			errorMsg.push('请输入开采方式');
		}

		if (model.workingFace.id === '') {
			errorMsg.push('请输入工作面');
		}

		if (model.workingPlace.id === '') {
			errorMsg.push('请输入工作地点');
		}

		if (model.planMen !== '' && !$.isNumeric(model.planMen)) {
			errorMsg.push('计划出勤人数为数字格式');
		}

		if (model.infactMen !== '' && !$.isNumeric(model.infactMen)) {
			errorMsg.push('实际出勤人数为数字格式');
		}

		if (model.planTons !== '' && !$.isNumeric(model.planTons)) {
			errorMsg.push('产量计划吨数为数字格式');
		}

		if (model.infactTons !== '' && !$.isNumeric(model.infactTons)) {
			errorMsg.push('产量实际吨数为数字格式');
		}

		if(errorMsg.length > 0){
			Utils.modal.message(showType, [ errorMsg.join(',') ]);
			return false;
		}
		
		return true;
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

	// 搜索
	$('#submit').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});
});