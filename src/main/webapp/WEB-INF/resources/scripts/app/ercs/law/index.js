define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});


	// 配置表格列
	var fields = [{
        header : '文件号',
        name : 'fileNo'
    },{
        header : '文件名',
        name : 'fileName'
    },{
        header : '发布单位',
        name : 'department'
    },{
        header : '发布时间',
        name : 'addTime'
    }];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 21);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected ) {
			Utils.button.enable([ 'edit', 'remove' ]);
		} else {
			Utils.button.disable([ 'edit', 'remove']);
		}
	}

	// 配置表格
	var defaultUrl = contextPath + '/ercs/emergency-laws?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#law-table',
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

		if(object.department===''){
			delete object.department;
		}

		$.post('/ercs/emergency-laws', JSON.stringify(object), function(data) {
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
		$.get('/ercs/emergency-laws/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		if(object.department===''){
			delete object.department;
		}
		// 处理属性
		var selectId = grid.selectedData('id');
		$.put('/ercs/emergency-laws/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('/ercs/emergency-laws/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});


	/**
	 * 更新部分属性
	 */
	function update(locked, credential) {
		var selectId = grid.selectedData('id');
		$.get('/ercs/emergency-laws/' + selectId, function(data) {
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

			$.put('/ercs/emergency-laws/' + selectId, JSON.stringify(object), function(data) {
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
