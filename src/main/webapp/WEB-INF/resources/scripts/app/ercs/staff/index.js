define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	Utils.select.remote([ 'edit-expertiseArea', 'create-expertiseArea' ],
			'/ercs/dictionaries?typeCode=expertise_area&list=true', 'id',
			'itemName');
	Utils.select.remote([ 'edit-responseLevel', 'create-responseLevel' ],
			'/ercs/dictionaries?typeCode=response_level&list=true', 'id',
			'itemName');

	// 配置表格列
	var fields = [ {
		header : '姓名',
		name : 'staffName'
	}, {
		header : '员工类型',
		name : 'staffType'
	}, {
		header : '专业领域',
		name : 'expertiseArea',
		render : function(val) {
			if (val) {
				return val.itemName;
			}
			return '';
		}
	}, {
		header : '部门',
		name : 'department'
	}, {
		header : '职称',
		name : 'title'
	}, {
		header : '经验',
		name : 'experience'
	}, {
		header : '联系方式',
		name : 'phone'
	}, {
		header : '事故响应级别',
		name : 'responseLevel',
		render : function(val) {
			if (val) {
				return val.itemName;
			}
			return '';
		}
	}, {
		header : '创建时间',
		name : 'addTime'
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height()
			- ($('.navbar').height() + $('.page-toolbar').height()
					+ $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 20);

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
	var defaultUrl = contextPath
			+ '/ercs/rescuers?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#staff-table',
		url : defaultUrl,
		model : {
			needOrder : true,
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
		Utils.button.enable([ 'create-save' ]);
	});

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');
		if (object.staffType === '') {
			Utils.modal.message('create', [ '请输入员工类型' ]);
			return;
		}
		if (object.staffName === '') {
			Utils.modal.message('create', [ '请输入姓名' ]);
			return;
		}
		if (object.phone === '') {
			Utils.modal.message('create', [ '请输入联系方式' ]);
			return;
		}
		Utils.button.disable([ 'create-save' ]);
		$.post('/ercs/rescuers', JSON.stringify(object), function(data) {
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
		$.get('/ercs/rescuers/' + selectId, function(data) {
			var object = data.data;
			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(
			function() {
				var object = Utils.form.serialize('edit');
				if (object.staffType === '') {
					Utils.modal.message('edit', [ '请输入员工类型' ]);
					return;
				}
				if (object.staffName === '') {
					Utils.modal.message('edit', [ '请输入姓名' ]);
					return;
				}
				if (object.phone === '') {
					Utils.modal.message('edit', [ '请输入联系方式' ]);
					return;
				}
				// 处理属性
				var selectId = grid.selectedData('id');
				$.put('/ercs/rescuers/' + selectId, JSON.stringify(object),
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
		$.del('/ercs/rescuers/' + selectId, function(data) {
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
});
