define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	Utils.select.remote([ 'create-refugeType', 'edit-refugeType', 'refugeTypeSelect' ], '/ercs/dictionaries?typeCode=refuge_type&list=true', 'id', 'itemName');
	// 配置表格列
	var fields = [ {
		header : '种类',
		name : 'refugeType',
		render : function(val) {
			if (val) {
				return val.itemName;
			}
			return '';
		}
	}, {
		header : '名称',
		name : 'refugeName'
	}, {
		header : '数量',
		name : 'quantity'
	}, {
		header : '位置',
		name : 'position'
	}, {
		header : '基本情况',
		name : 'basicInfomation'
	}, {
		header : '面积',
		name : 'refugeArea'
	}, {
		header : '可容纳人数',
		name : 'capacity'
	}, {
		header : '基础设施',
		name : 'infrastructure'
	}, {
		header : '防护功能',
		name : 'protection'
	}, {
		header : '隶属单位',
		name : 'department'
	}, {
		header : '管理人',
		name : 'manager'
	}, {
		header : '联系方式',
		name : 'telepone'
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
	var defaultUrl = contextPath + '/ercs/refuges?orderBy=id&order=desc&pageSize=' + pageSize;
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
		$('#fileNo')[0].focus();
	});

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');

		if (object.department === '') {
			delete object.department;
		}
		if (object.refugeType === '') {
			delete object.refugeType;
		}
		$.post('/ercs/refuges', JSON.stringify(object), function(data) {
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
		$.get('/ercs/refuges/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		if (object.department === '') {
			delete object.department;
		}
		if (object.refugeType === '') {
			delete object.refugeType;
		}
		// 处理属性
		var selectId = grid.selectedData('id');
		$.put('/ercs/refuges/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('/ercs/refuges/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	/**
	 * 更新部分属性
	 */
	function update(locked, credential) {
		var selectId = grid.selectedData('id');
		$.get('/ercs/refuges/' + selectId, function(data) {
			var object = data.data;
			object.credential = credential;
			if (locked !== null) {
				object.locked = locked;
			}
			object.groupEntity = {
				id : object.groupEntity.id
			};
			object.roleEntity = {
				id : object.roleEntity.id
			};

			$.put('/ercs/refuges/' + selectId, JSON.stringify(object), function(data) {
				if (data.success === true) {
					grid.refresh();
				}
			});
		});
	}

	// 搜索
	$('#nav-search-button').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});
});
