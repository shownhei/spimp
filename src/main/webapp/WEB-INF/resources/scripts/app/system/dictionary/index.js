define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	var map = {
		plan_type : '应急预案种类',
		personal_category : '人员类别',
		expertise_area : '专业领域',
		accident_category : '事故类别',
		accident_level : '事故严重程度',
		response_level : '事故响应级别',
		refuge_type : '避险场所种类',
		resource_type : '应急资源种类',
		education_level : '文化程度',
		organization_type : '应急保障机构类型',
		schedule_coal_type : '煤种',
		schedule_coal_series : '煤系',
		schedule_duty : '班次',
		schedule_team_type : '队组类型',
		schedule_injury_type : '受伤类型',
		schedule_working_face : '工作面',
		schedule_exploit_type : '开采方式',
		schedule_tunnel_type : '巷道类型',
		schedule_working_place : '工作地点',
		schedule_meeting_type : '会议类型',
		schedule_gas_emissions_type : '瓦斯排放类型',
		schedule_hidden_type : '隐患类型',
		schedule_wellheads : '井口',
		schedule_alarm_type : '报警类型',
		document_project_type : '工程分类'
	};
	// 配置表格列
	var fields = [ {
		header : '字典分类',
		render : function(v) {
			return v ? map[v] : '';
		},
		name : 'typeCode'
	}, {
		header : '字典名称',
		name : 'itemName'
	}, {
		header : '排序值',
		name : 'sortIndex'
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 84);
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

	$('#typeCodeSelect').bind('change', function() {
		$('#nav-search-button').trigger('click');
	});

	// 配置表格
	var defaultUrl = contextPath + '/system/dictionaries?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#dictionary-table',
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
		// 验证
		if (object.itemName === '') {
			Utils.modal.message('create', [ '请输入字典值' ]);
			return;
		}

		$.post('/system/dictionaries', JSON.stringify(object), function(data) {
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
		$.get('/system/dictionaries/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		// 验证
		if (object.itemName === '') {
			Utils.modal.message('edit', [ '请输入字典值' ]);
			return;
		}

		// 处理属性
		var selectId = grid.selectedData('id');
		$.put('/system/dictionaries/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('/system/dictionaries/' + selectId, function(data) {
			Utils.modal.hide('remove');
			if (data.success) {
				grid.refresh();
			} else {
				Utils.modal.show('remove_error');
			}
		});
	});

	// 搜索
	$('#nav-search-button').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});
});
