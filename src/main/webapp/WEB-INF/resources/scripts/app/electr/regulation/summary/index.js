define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/electr/regulation/summaries';

	window.$ = $;
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 下拉列表初始化
	Utils.select.remote([ 'search_summaryType', 'create_summaryType', 'edit_summaryType' ], '/system/dictionaries?typeCode=summary_type&list=true', 'id',
			'itemName', true, '总结分类');

	// 下拉列表change事件
	$('#search_summaryType').bind('change', function() {
		$('#submit').trigger('click');
	});

	// 日期时间选择控件
	$('#create_recordTime').datetimepicker();
	$('#edit_recordTime').datetimepicker();

	// 启用日期控件
	Utils.input.date('input[type=datetime]');

	// 配置表格列
	var fields = [ {
		header : '材料名称',
		name : 'summaryTitle'
	}, {
		header : '上传日期',
		width : 90,
		name : 'uploadDate'
	}, {
		header : '总结分类',
		name : 'summaryType',
		render : function(value) {
			return value === null ? '' : value.itemName;
		}
	}, {
		header : '附件',
		name : 'summary',
		render : function(v) {
			var name = v.simpleName;
			var html = '<a href="javascript:void(0);" doc_id=' + v.id + ' title=' + name + '>' + name.substring(0, 20) + '</a>&nbsp;&nbsp;';
			html += '<a href="' + v.filePath + '" target="_blank" class="pull-right">下载</a>';
			return v ? html : '';
		}

	}, {
		header : '查看',
		name : 'id',
		width : 50,
		align : 'center',
		render : function(value) {
			return '<i data-role="detail" class="icon-list" style="cursor:pointer;"></i>';
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
	var defaultUrl = contextPath + operateUri + '?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#material-table',
		url : defaultUrl,
		model : {
			fields : fields,
			needOrder : true,
			orderWidth : 50,
			height : gridHeight
		},
		onClick : function(target, data) {
			changeButtonsStatus(this.selected, data);

			if (target.attr('data-role') === 'detail') {
				showDetail(data);
			}
		},
		onLoaded : function() {
			changeButtonsStatus();

			// 改变导出按钮状态
			if (this.data.totalCount > 0) {
				Utils.button.enable([ 'export' ]);
			} else {
				Utils.button.disable([ 'export' ]);
			}
		}
	}).render();

	// 新建
	$('#create').click(function() {
		Utils.modal.reset('create');
		Utils.modal.show('create');
		$('#summary').parent().parent().hide();
		$('#create-file-form')[0].reset();
		$('#create-file-form').show();
	});

	// 验证
	function validate(showType, model) {
		var errorMsg = [];

		if (model.summaryTitle === '') {
			errorMsg.push('请输入材料名称');
		}

		if (model.summary === '') {
			errorMsg.push('请输入附件');
		}

		if (model.summaryType.id === '') {
			errorMsg.push('请输入总结分类');
		}

		if (model.uploadDate === '') {
			errorMsg.push('请输入上传日期');
		}

		if (errorMsg.length > 0) {
			Utils.modal.message(showType, [ errorMsg.join(',') ]);
			return false;
		}

		return true;
	}

	// 查看
	function showDetail(data) {
		Utils.modal.reset('detail');
		var object = $.extend({}, data);
		object.summaryType = object.summaryType.itemName;
		object.summary = object.summary.simpleName;
		Utils.form.fill('detail', object);
		Utils.modal.show('detail');
	}

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');

		// 验证
		if (!validate('create', object)) {
			return false;
		}

		var summary = {
			id : $('#summary').attr('data-id')
		};
		delete object.summary;
		object.summary = summary;
		$.post(operateUri, JSON.stringify(object), function(data) {
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
		$.get(operateUri + '/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
			$('#edit_summary').val(object.summary.simpleName);
			$('#edit_summary').attr('data-id', object.summary.id);
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		// 验证
		if (!validate('edit', object)) {
			return false;
		}
		var summary = {
			id : $('#edit_summary').attr('data-id')
		};
		delete object.summary;
		object.summary = summary;
		// 处理属性
		var selectId = grid.selectedData('id');
		object.id = selectId;
		$.put(operateUri + '/' + selectId, JSON.stringify(object), function(data) {
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
		$.del(operateUri + '/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	// 导出
	$('#export').click(function() {
		if (Utils.button.isDisable('export')) {
			return;
		}

		window.location.href = operateUri + '/export-excel?' + Utils.form.buildParams('search-form');
	});

	// 搜索
	$('#submit').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});

	// 查询条件重置
	$('#file').bind('change', function() {
		if ($('#file').val() !== '') {
			$('#create-file-form').submit();
			var process = new Utils.modal.showProcess('process');
			window.process = process;
		}
	});
	$('#create-file-delete').bind('click', function() {
		var process = new Utils.modal.showProcess('process');
		window.process = process;
		$.del('/ercs/uploaded-files/' + $('#summary').attr('data-id'), function(data) {
			window.process.stop();
			window.process = null;
			$('#summary').parent().parent().hide();
			$('#create-file-form')[0].reset();
			$('#create-file-form').show();
		});
	});
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
	if (!data.success) {
		alert("上传失败..." + data.data);
		return false;
	} else {
		$('#summary').val(data.data.filePath);
		$('#summary').attr('data-id', data.data.id);
		$('#create-file-form').hide();
		$('#summary').parent().parent().show();
	}
}
