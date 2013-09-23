define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	Utils.select.remote([ 'create-responseLevel','edit-responseLevel'], '/ercs/dictionaries?typeCode=response_level&list=true', 'id',
	'itemName');
	// 配置表格列
	var fields = [ {
		header : '机构名称',
		name : 'teamName'
	}, {
		header : '总指挥',
		name : 'commander'
	}, {
		header : '副总指挥',
		name : 'deputyCommander'
	}, {
		header : '下属成员',
		name : 'members'
	},{
		header : '响应级别',
		name : 'responseLevel',
		render:function(val){
			if(val===null||val===undefined){
				return '';
			}
			return val.itemName;
		}
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
	var defaultUrl = contextPath + '/ercs/response-teams?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#team-table',
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
		if (object.teamName === '') {
			Utils.modal.message('create', [ '请输入机构名称' ]);
			return;
		}
		if (object.commander === '') {
			Utils.modal.message('create', [ '请输入总指挥' ]);
			return;
		}
		if (object.deputyCommander === '') {
			Utils.modal.message('create', [ '请输入副总指挥' ]);
			return;
		}
		if (object.members === '') {
			Utils.modal.message('create', [ '请输入下属成员' ]);
			return;
		}
		if (object.responseLevel === '') {
			Utils.modal.message('create', [ '请输入响应级别' ]);
			return;
		}
		$.post('/ercs/response-teams', JSON.stringify(object), function(data) {
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
		$.get('/ercs/response-teams/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		// 验证
		if (object.teamName === '') {
			Utils.modal.message('edit', [ '请输入机构名称' ]);
			return;
		}
		if (object.commander === '') {
			Utils.modal.message('edit', [ '请输入总指挥' ]);
			return;
		}
		if (object.deputyCommander === '') {
			Utils.modal.message('edit', [ '请输入副总指挥' ]);
			return;
		}
		if (object.members === '') {
			Utils.modal.message('edit', [ '请输入下属成员' ]);
			return;
		}
		if (object.responseLevel === '') {
			Utils.modal.message('edit', [ '请输入响应级别' ]);
			return;
		}
		
		// 处理属性
		var selectId = grid.selectedData('id');
		$.put('/ercs/response-teams/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('/ercs/response-teams/' + selectId, function(data) {
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
