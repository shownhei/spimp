define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 获取机构
	Utils.select.remote([ 'create-groupEntity', 'edit-groupEntity' ], 'groups', 'id', 'name');

	// 配置表格列
	var fields = [ {
		header : '姓名',
		name : 'name'
	}, {
		header : '性别',
		name : 'gender'
	}, {
		header : '所属机构',
		name : 'groupEntity',
		render : function(value) {
			return value.name;
		}
	}, {
		header : '工种',
		name : 'workType'
	}, {
		header : '资格证号',
		name : 'qualification'
	}, {
		header : '创建时间',
		name : 'createTime',
		hidden : 'phone',
		width : 150
	}, {
		header : '最后更新时间',
		name : 'updateTime',
		hidden : 'phone',
		width : 150
	}, {
		header : '变更记录',
		name : 'id',
		width : 80,
		align : 'center',
		render : function(value) {
			return '<i data-role="view" class="icon-list" style="cursor:pointer;"></i>';
		}
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 84);
	var pageSize = Math.floor(gridHeight / GRID_ROW_HEIGHT);

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
	var defaultUrl = contextPath + '/system/staffs?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#staff-table',
		url : defaultUrl,
		model : {
			fields : fields,
			needOrder : true,
			orderWidth : 50,
			height : gridHeight
		},
		onClick : function(target, data) {
			changeButtonsStatus(this.selected, data);

			// 查看
			if (target.data('role') === 'view') {
				Utils.modal.reset('view');
				if (data.alterations.length === 0) {
					$('#view-staff-name').html(data.name);
					$('#view-no-records').show();
				} else {
					$.each(data.alterations, function(k, v) {
					});
				}
				Utils.modal.show('view');
			}
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

		// 处理属性

		$.post(contextPath + '/system/staffs', JSON.stringify(object), function(data) {
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
		$.get(contextPath + '/system/staffs/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		var selectId = grid.selectedData('id');

		// 处理属性
		object.id = selectId;

		$.put(contextPath + '/system/staffs/' + selectId, JSON.stringify(object), function(data) {
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
		$.del(contextPath + '/system/staffs/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	// 搜索
	$('#nav-search-button').click(function() {
		grid.set('url', defaultUrl + Utils.form.buildParams('search-form'));
	});

	// 自动完成
	$('#nav-search-input').autocomplete(contextPath + '/system/staffs', {
		dataType : "json",
		mustMatch : true,
		delay : 1000,
		cacheLength : 0,
		parse : function(data) {
			return $.map(data.data, function(row) {
				return {
					data : row,
					value : row.realName,
					result : row.realName
				};
			});
		},
		formatItem : function(item) {
			return item.realName + '[' + item.principal + ']';
		}
	}).result(function(e, item) {
		grid.set('url', defaultUrl + Utils.form.buildParams('search-form'));
	});
});
