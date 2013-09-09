define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

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
	var gridHeight = $(window).height() - ($('.breadcrumbs').height() + $('.navbar').height() + $('.page-header').height() + 115);
	var pageSize = Math.floor(gridHeight / 20);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected && data.id !== 1) {
			$('#edit,#remove,#reset').removeClass('disabled');
			if (data.locked) {
				$('#lock').addClass('disabled');
				$('#unlock').removeClass('disabled');
			} else {
				$('#lock').removeClass('disabled');
				$('#unlock').addClass('disabled');
			}
		} else {
			$('#edit,#remove,#lock,#unlock,#reset').addClass('disabled');
		}
	}

	// 配置表格
	var grid = new Grid({
		parentNode : '#account-table',
		url : contextPath + '/system/accounts?orderBy=id&order=desc&pageSize=' + pageSize,
		urlParser : /(grid_)\d+(.*)/,
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

	// 获取机构
	Utils.select.remote([ 'groupEntity' ], 'groups?label=mine', 'id', 'name');
	// 获取角色
	Utils.select.remote([ 'roleEntity' ], 'roles', 'id', 'name');

	// 新建
	$('#create').click(function() {
		Utils.modal.reset('create');
		$('#create-modal').modal({
			backdrop : 'static'
		});
	});

	// 保存
	$('#create-save').click(function() {
		var object = $('#create-form').serializeObject();

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
				$('#create-modal').modal('hide');
			} else {
				Utils.modal.message('create', data.errors);
			}
		});
	});
});
