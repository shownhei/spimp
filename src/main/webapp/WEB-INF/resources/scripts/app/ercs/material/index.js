define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	Utils.select.remote([ 'create-resourceType', 'edit-resourceType', 'resourceTypeSelect' ], '/ercs/dictionaries?typeCode=resource_type&list=true', 'id',
			'itemName');

	// 配置表格列
	var fields = [ {
		header : '资源名称',
		name : 'resourceName'
	}, {
		header : '编号',
		width:150,
		name : 'resourceNo'
	}, {
		header : '资源类别',
		width:150,
		name : 'resourceType',
		render : function(val) {
			if (val) {
				return val.itemName;
			} else {
				return '';
			}
		}
	}, {
		header : '所属单位',
		width:150,
		name : 'department',
		render:function(v){
			return v?v.name:'';
		}
	}, {
		header : '录入时间',
		width:150,
		name : 'addTime'
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
	var defaultUrl = contextPath + '/ercs/emergency-resources?orderBy=id&order=desc&pageSize=' + pageSize;
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
		if (object.resourceName === '') {
			Utils.modal.message('create', [ '请输入资源名称' ]);
			return;
		}
		if (object.resourceNo === '') {
			Utils.modal.message('create', [ '请输入资源编号' ]);
			return;
		}
		if (object.department === '') {
			Utils.modal.message('create', [ '请输入所属单位' ]);
			return;
		}
		if (object.resourceType === '') {
			delete object.resourceType;
		}

		var department={id:$('#create_department').attr('data-id'),name:object.department};
		delete object.department;
		object.department=department;
		$.post('/ercs/emergency-resources', JSON.stringify(object), function(data) {
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
		$.get('/ercs/emergency-resources/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
			if(object.department.name){
				$('#edit_department').val(object.department.name);
				$('#edit_department').attr('data-id',object.department.id);
			}
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		if (object.resourceName === '') {
			Utils.modal.message('edit', [ '请输入资源名称' ]);
			return;
		}
		if (object.resourceNo === '') {
			Utils.modal.message('edit', [ '请输入资源编号' ]);
			return;
		}
		if (object.department === '') {
			Utils.modal.message('edit', [ '请输入所属单位' ]);
			return;
		}
		if (object.resourceType === '') {
			delete object.resourceType;
		}
		var department={id:$('#edit_department').attr('data-id'),name:object.department};
		delete object.department;
		object.department=department;
		// 处理属性
		var selectId = grid.selectedData('id');
		$.put('/ercs/emergency-resources/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('/ercs/emergency-resources/' + selectId, function(data) {
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
	//创建
	new Utils.form.groupTree('create_groupSelectTree','create_treeDemo','create_selectGroup','create_department');
	//编辑
	new Utils.form.groupTree('edit_groupSelectTree','edit_treeDemo','edit_selectGroup','edit_department');
});
