define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	window.$ = $;
	window.Utils = Utils;
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	
	var office;
	switch(officeCode){
		case('schedule'):
			office = '调度室';
			break;
		case('safe'):
			office = '安全科';
			break;
		case('machine'):
			office = '机电科';
			break;
		case('wind'):
			office = '通风科';
			break;
		case('produce'):
			office = '生产技术科';
			break;
		case('water'):
			office = '防治水科';
			break;
		case('dig'):
			office = '综掘队';
			break;
		case('exploit'):
			office = '综采队';
			break;
		case('develop'):
			office = '开拓队';
			break;
		default:
			office = '未指定';
	}
	
	Utils.select.remote([ 'create_projectType', 'edit_projectType', 'query_projectType' ], '/system/dictionaries?typeCode=document_project_type&list=true', 'id', 'itemName', true, '工程分类');

	$('#query_projectType').bind('change', function() {
		$('#submit').trigger('click');
	});
	
	// 配置表格列
	var fields = [ {
		header : '文档名称',
		name : 'documentName'
	}, {
		header : '工程分类',
		name : 'projectType',
		width : 100,
		render : function(value) {
			return value.itemName;
		}
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
		header : '创建人',
		name : 'createBy',
		width : 80
	}, {
		header : '创建时间',
		name : 'createTime',
		width : 145
	}, {
		header : '更新人',
		name : 'updateBy',
		width : 80
	}, {
		header : '更新时间',
		name : 'lastModifyTime',
		width : 145
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
	var defaultUrl = contextPath + '/spmi/document/documents?orderBy=id&order=desc&pageSize=' + pageSize + '&office=' + office;
	var grid = new Grid({
		parentNode : '#plan-table',
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
		$('#create-file-delete').trigger('click');
	});

	// 保存
	$('#create-save').click(function() {
		if ($('#create-save').hasClass('disabled')) {
			return;
		}
		
		var object = Utils.form.serialize('create');
		// 验证
		if (object.documentName === '') {
			Utils.modal.message('create', [ '请输入文档名称' ]);
			return;
		}
		if (object.projectType.id === '') {
			Utils.modal.message('create', [ '请添选择工程分类' ]);
			return;
		}
		if (object.attachment === '') {
			Utils.modal.message('create', [ '请添加附件' ]);
			return;
		}
		var attachment = {
			id : $('#create_attachment').attr('data-id'),
			name : object.simpleName
		};
		
		delete object.filePath;
		object.attachment = attachment;
		
		object.office = office;
		$.post('/spmi/document/documents', JSON.stringify(object), function(data) {
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
		$.get('/spmi/document/documents/' + selectId, function(data) {
			var object = data.data;
			Utils.form.fill('edit', object);
			$('#edit_attachment').val(object.attachment.simpleName);
			$('#edit_attachment').attr('data-id', object.attachment.id);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		// 验证
		if (object.documentName === '') {
			Utils.modal.message('edit', [ '请输入文档名称' ]);
			return;
		}
		if (object.projectType.id === '') {
			Utils.modal.message('edit', [ '请添选择工程分类' ]);
			return;
		}
		
		var attachment = {
			id : $('#edit_attachment').attr('data-id')
		};
		delete object.attachment;
		object.attachment = attachment;
		
		// 处理属性
		var selectId = grid.selectedData('id');
		$.put('/spmi/document/documents/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('/spmi/document/documents/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	// 搜索
	$('#submit').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});
	
	// 重置
	$('#reset').click(function() {
		grid.set('url', defaultUrl);
        grid.refresh();
	});
	
	// 文件上传
	$('#file').bind('change', function() {
		if ($('#file').val() !== '') {
			$('#create-file-form').submit();
			var process = new Utils.modal.showProcess('process');
			window.process = process;
		}
	});
	
	$('#create-file-delete').bind('click', function() {
		$('#create_attachment').parent().parent().hide();
		$('#create-file-form')[0].reset();
		if ($('#create_attachment').val()) {
			var process = new Utils.modal.showProcess('process');
			window.process = process;
			$.del('/ercs/uploaded-files/' + $('#create_attachment').attr('data-id'), function(data) {
				grid.refresh();
				window.process.stop();
				window.process = null;
				Utils.modal.hide('remove');
			});
		}
		$('#create-file-form').show();
	});
	
	$(document).click(function(event) {
		var docId = $(event.target).attr('doc_id');
		if (docId) {
			$('#showDocument').attr('src', '/ercs/view-pdf/' + docId + "?t=" + new Date().getTime());
			Utils.modal.show('view');
		}
	});
});
// 文件上传回调
function callBack(data) {
	var attachment = $('#create_attachment');
	attachment.val(data.data.simpleName);
	attachment.attr('data-id', data.data.id);
	$('#create-file-form').hide();
	attachment.parent().parent().show();
	$('#create-save').removeClass('disabled');
	window.process.stop();
	window.process = null;
}