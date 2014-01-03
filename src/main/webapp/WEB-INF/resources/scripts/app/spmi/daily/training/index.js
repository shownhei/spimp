define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 配置表格列
	var fields = [
			{
				header : '计划名称',
				name : 'name'
			},
			{
				header : '分类',
				name : 'category'
			},
			{
				header : '来源',
				name : 'origin'
			},
			{
				header : '附件',
				name : 'attachment',
				width : 300,
				render : function(v) {
					var name = v.simpleName;
					var html = '<a href="javascript:void(0);" doc_id=' + v.id + ' title=' + name + '>'
							+ name.substring(0, 20) + '</a>&nbsp;&nbsp;';
					html += '<a href="' + v.filePath + '" target="_blank" class="pull-right">下载</a>';
					return v ? html : '';
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
	var defaultUrl = contextPath + '/spmi/daily/trainings?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#records',
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

		// 处理属性
		var attachment = {
				id : $('#attachment').attr('data-id'),
				name : object.filePath
			};
		object.attachment = attachment;
		// 验证
		if(object.name===''){
			Utils.modal.message('create', [ '计划名称不能为空' ]);
			return;
		}
		if($('#attachment').val()===''){
			Utils.modal.message('create', [ '文件不能为空' ]);
			return;
		}
		if(object.category===''){
			Utils.modal.message('create', [ '分类不能为空' ]);
			return;
		}

		$.post(contextPath + '/spmi/daily/trainings', JSON.stringify(object), function(data) {
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
		$.get(contextPath + '/spmi/daily/trainings/' + selectId, function(data) {
			var object = data.data;
			$('#edit_attachment').val(object.attachment.filePath);
			$('#edit_attachment').attr('data-id', object.attachment.id);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		// 处理属性
		var selectId = grid.selectedData('id');
		var attachment = {
				id : $('#edit_attachment').attr('data-id')
			};
			delete object.attachment;
			object.attachment = attachment;
			if(object.name===''){
				Utils.modal.message('edit', [ '计划名称不能为空' ]);
				return;
			}
			if(object.category===''){
				Utils.modal.message('edit', [ '分类不能为空' ]);
				return;
			}
		$.put(contextPath + '/spmi/daily/trainings/' + selectId, JSON.stringify(object), function(data) {
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
		$.del(contextPath + '/spmi/daily/trainings/' + selectId, function(data) {
			grid.set('url', defaultUrl);
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	// 搜索
	$('#nav-search-button').click(function() {
		grid.set('url', defaultUrl + Utils.form.buildParams('search-form'));
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
		$('#attachment').parent().parent().hide();
		$('#create-file-form')[0].reset();
		if ($('#attachment').val()) {
			var process = new Utils.modal.showProcess('process');
			window.process = process;
			$.del('/ercs/uploaded-files/' + $('#attachment').attr('data-id'), function(data) {
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
	// 文件上传回调
	function callBack(data) {
		window.process.stop();
		window.process = null;
		if (!data.success) {
			alert("上传失败..." + data.data);
			return false;
		} else {
			var attachment = $('#attachment');
			attachment.val(data.data.filePath);
			attachment.attr('data-id', data.data.id);
			$('#create-file-form').hide();
			attachment.parent().parent().show();
			$('#create-save').removeClass('disabled');
		}
	}
	window.callBack=callBack;
});
