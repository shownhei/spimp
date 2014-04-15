define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var operateUri = '/electr/innovation/innovations';
	window.$ = $;
	window.Utils = Utils;
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 启用日期控件
	Utils.input.date('input[type=datetime]');
	var loadDetail = function(id) {
		window.innovationId = id;
		$('#detail-panel').html('');
		$.ajax({
			type : 'get',
			data : '',
			dataType : 'text',
			url : '/electr/innovation/detail/' + id,
			success : function(data) {
				Utils.button.disable([ 'submit', 'reset' ]);
				$('#button_bar').hide();
				$('#material-table').hide();
				$('#detail-panel').html(data);
				$('#detail-panel').show();
				$('#close_detail').bind('click', function() {
					$('#detail-panel').hide();
					$('#material-table').show();
					$('#button_bar').show();
					Utils.button.enable([ 'submit', 'reset' ]);
				});
			}
		});
	};
	// 配置表格列
	var fields = [ {
		header : '项目名称',
		name : 'projectName'
	}, {
		header : '负责人',
		width : 80,
		name : 'chargePerson'
	}, {
		header : '申报日期',
		width : 90,
		name : 'declarationDate'
	},{
		header : '是否专利',
		name : 'isPatent',
		width : 90,
		render:function(v){
			return v===1?'<i class="icon-check"></i>':'';
		}
	}, {
		header : '实施地点',
		name : 'implementationAddress'
	}, {
		header : '实施时间',
		width : 90,
		name : 'implementationPeriod'
	}, {
		header : '参与人',
		width : 80,
		name : 'participant'
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
			Utils.button.enable([ 'edit', 'upload', 'remove' ]);
		} else {
			Utils.button.disable([ 'edit', 'upload', 'remove','setPatent','unsetPatent' ]);
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
            if(data.isPatent===0){
            	Utils.button.disable([ 'unsetPatent' ]);
            	Utils.button.enable([ 'setPatent' ]);
            }
            else{
            	Utils.button.disable([ 'setPatent' ]);
            	Utils.button.enable([ 'unsetPatent' ]);
            }
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
	});

	// 验证
	function validate(showType, model) {
		var errorMsg = [];

		if (model.projectName === '') {
			errorMsg.push('请输入项目名称');
		}

		if (model.chargePerson === '') {
			errorMsg.push('请输入负责人');
		}

		if (model.implementationAddress === '') {
			errorMsg.push('请输入实施地点');
		}

		if (model.implementationPeriod === '') {
			errorMsg.push('请输入实施时间');
		}

		if (model.participant === '') {
			errorMsg.push('请输入参与人');
		}

		if (model.inventionPurpose === '') {
			errorMsg.push('请输入目的');
		}

		if (model.content === '') {
			errorMsg.push('请输入主要内容或原理');
		}

		if (model.analysis === '') {
			errorMsg.push('请输入效果及经济社会效益分析');
		}

		if (errorMsg.length > 0) {
			Utils.modal.message(showType, [ errorMsg.join(',') ]);
			return false;
		}

		return true;
	}

	// 查看
	function showDetail(data) {
		loadDetail(data.id);
	}

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');

		// 验证
		if (!validate('create', object)) {
			return false;
		}

		$.post(operateUri, JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.hide('create');
			} else {
				Utils.modal.message('create', data.errors);
			}
		});
	});
	// 图片上传
	$('#upload').click(function() {
		if (Utils.button.isDisable('upload')) {
			return;
		}
		Utils.modal.showUpload('/simpleupload', function(data) {
			var selectId = grid.selectedData('id');
			$.post('/electr/innovation/innovation-images', JSON.stringify({
				'innovationId' : selectId,
				'imagePath' : data.data
			}), function(data) {});
		}, '上传图片');
	});

	// 上传保存
	$('#upload-save').click(function() {
		var object = Utils.form.serialize('upload');
		$.post('/electr/innovation/innovation-images', JSON.stringify(object), function(data) {
			if (data.success) {
				Utils.modal.hide('upload');
			} else {
				Utils.modal.message('upload', data.errors);
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
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		// 验证
		if (!validate('edit', object)) {
			return false;
		}

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

	// 搜索
	$('#submit').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});
	$('#setPatent').click(function() {
		if (Utils.button.isDisable('setPatent')) {
			return;
		}
		var selectId = grid.selectedData('id');
		$.get('/electr/innovation/innovations/setpatent/' + selectId, function(data) {grid.refresh();});
	});
	$('#unsetPatent').click(function() {
		if (Utils.button.isDisable('unsetPatent')) {
			return;
		}
		var selectId = grid.selectedData('id');
		$.get('/electr/innovation/innovations/unsetpatent/' + selectId, function(data) {grid.refresh();});
	});
	$('#search-isPatent').change(function(){
		$('#submit').trigger('click');
	});
	// 查询条件重置
	$('#reset').click(function() {
		grid.set('url', defaultUrl);
		grid.refresh();
	});
	// 删除图片
	$(document).click(function(event) {
		var el = $(event.target);
		var dataType = el.attr('data-type');
		if (dataType === 'button_view') {
			show(el);
			return;
		}
		if (dataType === 'button_delete') {
			$.ajax({
				type : "delete",
				url : "/electr/innovation/innovation-images/" + el.attr('data-id'),
				dataType : 'json',
				success : function(json) {
					loadDetail(window.innovationId);
				}
			});
			return;
		}
	});
	function show(o) {
		$("#imgfile").attr("src", $(o).parent().prev().attr("src"));
		var tmpObj = new Image();
		tmpObj.src = $(o).parent().prev().attr("src");
		if (tmpObj.width > 500 && tmpObj.width > tmpObj.height) {
			tmpObj.height = parseInt(500 * tmpObj.height / tmpObj.width, 10);
			tmpObj.width = 500;
		} else if (tmpObj.height > 500 && tmpObj.width < tmpObj.height) {
			tmpObj.width = parseInt(500 * tmpObj.width / tmpObj.height, 10);
			tmpObj.height = 500;
		}
		tmpObj.onload = function() {
			var modalWidth = $("#details-modal").width();
			$("#details-modal").width(800);
			$("#imgfile").attr("style", "width:" + tmpObj.width + ";height:" + tmpObj.height + ";");
			$("#check").html($(o).parent().prev().attr('data-id'));
			Utils.modal.show('details');
		};
	}
});