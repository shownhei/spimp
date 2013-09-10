define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 获取机构
	Utils.select.remote([ 'create-groupEntity', 'edit-groupEntity' ], 'groups', 'id', 'name');
	// 获取角色
	Utils.select.remote([ 'create-roleEntity', 'edit-roleEntity' ], 'roles', 'id', 'name');

	// 配置表格列
	var fields = [ {
		header : '',
		name : 'locked',
		align : 'center',
		width : 20,
		render : function(value) {
			switch (value) {
				case false:
					return '';
				case true:
					return '<i class="icon-lock"></i>';
			}
		}
	}, {
		header : '用户名',
		name : 'principal'
	}, {
		header : '角色名',
		name : 'roleEntity',
		render : function(value) {
			return value.name;
		}
	}, {
		header : '所属机构',
		name : 'groupEntity',
		render : function(value) {
			return value.name;
		}
	}, {
		header : '姓名',
		name : 'realName'
	}, {
		header : '电话',
		name : 'telephone'
	}, {
		header : '创建时间',
		name : 'createTime',
		width : 150
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 21);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected && data.id !== 1) {
			Utils.button.enable([ 'edit', 'remove', 'reset' ]);
			if (data.locked) {
				Utils.button.enable([ 'unlock' ]);
				Utils.button.disable([ 'lock' ]);
			} else {
				Utils.button.enable([ 'lock' ]);
				Utils.button.disable([ 'unlock' ]);
			}
		} else {
			Utils.button.disable([ 'edit', 'remove', 'lock', 'unlock', 'reset' ]);
		}
	}

	// 配置表格
	var defaultUrl = contextPath + '/system/accounts?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#account-table',
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
		if (object.credential === '') {
			Utils.modal.message('create', [ '请输入密码' ]);
			return;
		}
		if (object.checkCredential === '') {
			Utils.modal.message('create', [ '请输入确认密码' ]);
			return;
		}
		if (object.credential !== object.checkCredential) {
			Utils.modal.message('create', [ '两次输入的密码不一致，请重新输入' ]);
			return;
		}

		// 处理属性
		if (object.locked === 'on') {
			object.locked = true;
		} else {
			object.locked = false;
		}
		delete object.checkCredential;

		$.post('accounts', JSON.stringify(object), function(data) {
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
		$.get('accounts/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		// 处理属性
		object.credential = null;
		object.locked = grid.selectedData('locked');

		var selectId = grid.selectedData('id');
		$.put('accounts/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.hide('edit');
			} else {
				Utils.modal.message('edit', data.errors);
			}
		});
	});

	// 锁定
	$('#lock').click(function() {
		if (Utils.button.isDisable('lock')) {
			return;
		}

		update(true, null);
	});

	// 解锁
	$('#unlock').click(function() {
		if (Utils.button.isDisable('unlock')) {
			return;
		}

		update(false, null);
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
		$.del('accounts/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	// 删除
	$('#reset').click(function() {
		if (Utils.button.isDisable('reset')) {
			return;
		}

		Utils.modal.show('reset');
	});

	// 删除确认
	$('#reset-save').click(function() {
		update(null, '123456');
		Utils.modal.hide('reset');
	});

	/**
	 * 更新部分属性
	 */
	function update(locked, credential) {
		var selectId = grid.selectedData('id');
		$.get('accounts/' + selectId, function(data) {
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

			$.put('accounts/' + selectId, JSON.stringify(object), function(data) {
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
