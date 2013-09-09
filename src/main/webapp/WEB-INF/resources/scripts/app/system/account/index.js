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
		header : '编号',
		name : 'id',
		width : 50,
		align : 'center'
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
		header : '是否锁定',
		name : 'locked',
		render : function(value) {
			switch (value) {
				case false:
					return '<span style="color:#96CE8D">否</span>';
				case true:
					return '<span style="color:orange">是</span>';
			}
		}
	}, {
		header : '创建时间',
		name : 'createTime',
		width : 150
	} ];

	// 计算表格高度
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 20);

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
	var grid = new Grid({
		parentNode : '#account-table',
		url : contextPath + '/system/accounts?orderBy=id&order=desc&pageSize=' + pageSize,
		model : {
			fields : fields,
			height : gridHeight
		},
		onClick : function(target, data) {
			changeButtonsStatus(this.selected, data);
		},
		onSort : function(name, direction) {
			console.log(name, direction);
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

		var selectId = grid.selected.data('data').id;
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
		object.locked = grid.selected.data('data').locked;

		var selectId = grid.selected.data('data').id;
		$.put('accounts/' + selectId, JSON.stringify(object), function(data) {
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

	/**
	 * 更新部分属性
	 */
	function update(locked, credential) {
		var selectId = grid.selected.data('data').id;
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
});
