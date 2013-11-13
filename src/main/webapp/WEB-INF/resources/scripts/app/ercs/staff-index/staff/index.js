define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	Utils.input.date('input[type=datetime]');
	Utils.select.remote([ 'edit-expertiseArea', 'create-expertiseArea' ], '/system/dictionaries?typeCode=expertise_area&list=true', 'id', 'itemName');
	Utils.select.remote([ 'edit-responseLevel', 'create-responseLevel' ], '/system/dictionaries?typeCode=response_level&list=true', 'id', 'itemName');
	Utils.select.remote([ 'edit-education', 'create-education' ], '/system/dictionaries?typeCode=education_level&list=true', 'id', 'itemName');

	// 配置表格列
	var fields = [ {
		header : "队员名称",
		name : "staffName"
	}, {
		header : "出生年月",
		name : "birthDay"
	}, {
		header : "文化程度",
		render : function(val) {
			if (val) {
				return val.itemName;
			}
			return '';
		},
		name : "education"
	}, {
		header : "整治面貌",
		name : "policitalStatus"
	}, {
		header : "参加工作时间",
		name : "workDate"
	}, {
		header : "入队时间",
		name : "enqueueDate"
	}, {
		header : "住址",
		name : "address"
	}, {
		header : "手机",
		name : "telephone"
	}, {
		header : "身份证号码",
		name : "iDNumber"
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
		header : '事故响应级别',
		name : 'responseLevel',
		width : 100,
		render : function(val) {
			if (val) {
				return val.itemName;
			}
			return '';
		}
	}, {
		header : '部门',
		render : function(v) {
			return v ? v.name : '';
		},
		name : 'department'
	}, {
		header : "备注",
		name : "remark"
	}, {
		header : "账户关联",
		render:function(v){
			return v?v.realName:'';
		},
		name : "account"
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 21);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected) {
			Utils.button.enable([ 'edit', 'remove' ,'associate']);
		} else {
			Utils.button.disable([ 'edit', 'remove' ,'associate']);
		}
	}

	// 配置表格
	var defaultUrl = contextPath + '/ercs/rescuers?orderBy=id&order=desc&pageSize=' + pageSize;
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

		if (object.staffName === '') {
			Utils.modal.message('create', [ '请输入姓名' ]);
			return;
		}
		if (object.birthDay === '') {
			Utils.modal.message('create', [ '请输入出生日期' ]);
			return;
		}
		if (object.telephone === '') {
			Utils.modal.message('create', [ '请输入电话' ]);
			return;
		}
		if (object.telephone === '') {
			Utils.modal.message('create', [ '请输入电话' ]);
			return;
		}
		if (object.department === '') {
			Utils.modal.message('create', [ '请输入部门' ]);
			return;
		}
		if ($('#create-expertiseArea').val() === null) {
			Utils.modal.message('create', [ '请输入专业领域' ]);
			return;
		}
		if ($('#create-responseLevel').val() === null) {
			Utils.modal.message('create', [ '请输入事故响应级别' ]);
			return;
		}
		var department = {
			id : $('#create-department').attr('data-id'),
			name : object.department
		};
		delete object.department;
		object.department = department;
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
			$('#edit-department').val(object.department.name);
			$('#edit-department').attr('data-id', object.department.id);
			Utils.modal.show('edit');
			$('#edit_selectGroup').val('选择');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		if (object.staffName === '') {
			Utils.modal.message('edit', [ '请输入姓名' ]);
			return;
		}
		if (object.birthDay === '') {
			Utils.modal.message('edit', [ '请输入出生日期' ]);
			return;
		}
		if (object.telephone === '') {
			Utils.modal.message('edit', [ '请输入电话' ]);
			return;
		}
		if (object.telephone === '') {
			Utils.modal.message('edit', [ '请输入电话' ]);
			return;
		}
		if (object.department === '') {
			Utils.modal.message('edit', [ '请输入部门' ]);
			return;
		}
		if ($('#edit-expertiseArea').val() === null) {
			Utils.modal.message('edit', [ '请输入专业领域' ]);
			return;
		}
		if ($('#edit-responseLevel').val() === null) {
			Utils.modal.message('edit', [ '请输入事故响应级别' ]);
			return;
		}
		var department = {
			id : $('#edit-department').attr('data-id'),
			name : object.department
		};
		delete object.department;
		object.department = department;
		var selectId = grid.selectedData('id');
		$.put('/ercs/rescuers/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.hide('edit');
			} else {
				Utils.modal.message('edit', data.errors);
			}
		});
	});
	$('#associate').click(function() {
		if (Utils.button.isDisable('associate')) {
			return;
		}
		Utils.modal.reset('associate');
		Utils.modal.show('associate');
		var selectId = grid.selectedData('id');
		$('#staffId').val(selectId);
	});
	$("#associate-account").autocomplete('/system/accounts', {
		dataType : "json",
		mustMatch : true,
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
			return item.realName;
		}
	}).result(function(e, item) {
		$('#associate-account').attr('data-id', item.id);
	});
	$('#associate-save').click(function(){
		var staffId=$('#staffId').val();
		var accountId=$('#associate-account').attr('data-id');
		var object={staffId:staffId,accountId:accountId};
		$.post('/ercs/rescuers/associate?staffId='+staffId+'&accountId='+accountId,function(data){
			grid.refresh();
			Utils.modal.hide('associate');
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
	// 创建
	new Utils.form.groupTree('create_groupSelectTree', 'create_treeDemo', 'create_selectGroup', 'create-department');
	// 编辑
	new Utils.form.groupTree('edit_groupSelectTree', 'edit_treeDemo', 'edit_selectGroup', 'edit-department');
});
