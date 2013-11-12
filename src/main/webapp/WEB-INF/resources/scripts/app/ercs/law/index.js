define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');
	window.$ = $;
	window.Utils = Utils;
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 配置表格列
	var fields = [ {
		header : '文件名',
		name : 'fileName'
	}, {
		header : '文件号',
		name : 'fileNo'
	}, {
		header : '发布单位',
		render : function(v) {
			return v ? v.name : '';
		},
		name : 'department',
		width : 100
	}, {
		header : '附件',
		name : 'attachment',
		width : 300,
		render : function(v) {
			var name = v.simpleName;
			var html = '<a href="javascript:void(0);" doc_id=' + v.id + ' title=' + name + '>' + name.substring(0, 20) + '</a>&nbsp;&nbsp;';
			html += '<a href="' + v.filePath + '" target="_blank" class="pull-right">下载</a>';
			return v ? html : '';
		}
	}, {
		header : '发布时间',
		name : 'addTime',
		width : 150
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
		$('#attachment').parent().parent().hide();
		Utils.modal.show('create');
	});

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');
		// 验证
		if (object.fileNo === '') {
			Utils.modal.message('create', [ '请输入文件号' ]);
			return;
		}
		if (object.fileName === '') {
			Utils.modal.message('create', [ '请输入文件名' ]);
			return;
		}
		
		if (object.department === '') {
			Utils.modal.message('create', [ '发布单位不能为空' ]);
			return;
		}
		if (object.attachment === '') {
			Utils.modal.message('create', [ '附件不能为空' ]);
			return;
		}
		if (object.department === '') {
			delete object.department;
		}
		var department = {
			id : $('#create-department').attr('data-id'),
			name : object.department
		};
		delete object.department;
		object.department = department;

		var attachment = {
			id : $('#attachment').attr('data-id')
		};
		delete object.filePath;
		object.attachment = attachment;

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
			if (object.department.name) {
				$('#edit-department').val(object.department.name);
				$('#edit-department').attr('data-id', object.department.id);
			}
			$('#edit_attachment').val(object.attachment.filePath);
			$('#edit_attachment').attr('data-id', object.attachment.id);
			if (object.attachment) {
				$('#attachment').parent().parent().show();
			} else {
				$('#create-file-form').show();
			}
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		// 验证
		if (object.fileNo === '') {
			Utils.modal.message('edit', [ '请输入文件号' ]);
			return;
		}
		// 验证
		if (object.fileName === '') {
			Utils.modal.message('edit', [ '请输入文件名' ]);
			return;
		}
		// 验证
		if (object.attachment === '') {
			Utils.modal.message('create', [ '附件不能为空' ]);
			return;
		}
		if (object.department === '') {
			delete object.department;
		}
		var department = {
			id : $('#edit-department').attr('data-id'),
			name : object.department
		};
		delete object.department;
		object.department = department;

		var attachment = {
			id : $('#edit_attachment').attr('data-id')
		};
		delete object.attachment;
		object.attachment = attachment;
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

	// 搜索
	$('#nav-search-button').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});
	$('#file').bind('change', function() {
		var fileVal = $('#file').val();
		if (fileVal !== '') {
			// 待验证特定类型
			$('#create-file-form').submit();
			var process = new Utils.modal.showProcess('process');
			window.process = process;
		}
	});
	$('#create-file-delete').bind('click', function() {
		var process = new Utils.modal.showProcess('process');
		window.process = process;
		$.del('/ercs/uploaded-files/' + $('#attachment').attr('data-id'), function(data) {
			window.process.stop();
			window.process = null;
		});
		$('#attachment').parent().parent().hide();
		$('#create-file-form')[0].reset();
		$('#create-file-form').show();
	});

	// 创建
	new Utils.form.groupTree('create_groupSelectTree', 'create_treeDemo', 'create-selectGroup', 'create-department');
	// 编辑
	new Utils.form.groupTree('edit_groupSelectTree', 'edit_treeDemo', 'edit-selectGroup', 'edit-department');
	$(document).click(function(event) {
		var docId = $(event.target).attr('doc_id');
		if (docId) {
			$('#showDocument').attr('src', '/ercs/view-pdf/' + docId + "?t=" + new Date().getTime());
			Utils.modal.show('view');
		}
	});
});
function callBack(data) {
	window.process.stop();
	window.process = null;
	if(!data.success){
		alert("上传失败..."+data.data);
		return false;
	}else{
		$('#attachment').val(data.data.filePath);
		$('#attachment').attr('data-id', data.data.id);
		$('#create-file-form').hide();
		$('#attachment').parent().parent().show();
	}
}