define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var selectedId; // 选中的行

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 处理表单
	// 年份
	var thisYear = new Date().getFullYear();
	var yearOptionsHtml = '';
	for (var i = 0; i < 6; i++) {
		yearOptionsHtml += '<option value="' + (thisYear + i) + '">' + (thisYear + i) + '</option>';
	}
	$('#create-yearSelect').html(yearOptionsHtml);
	$('#edit-yearSelect').html(yearOptionsHtml);
	$('#query-yearSelect').append(yearOptionsHtml);

	// 月份
	var monthOptionsHtml = '';
	for (var j = 1; j < 13; j++) {
		monthOptionsHtml += '<option value="' + j + '">' + j + '</option>';
	}
	$('#create-monthSelect').html(monthOptionsHtml);
	$('#edit-monthSelect').html(monthOptionsHtml);
	$('#query-monthSelect').append(monthOptionsHtml);

	// 选择触发查询
	$('#query-yearSelect,#query-monthSelect').change(function() {
		grid.set('url', defaultUrl + Utils.form.buildParams('query-form'));
	});

	// 配置表格列
	var fields = [ {
		header : '年份',
		name : 'year',
		align : 'right'
	}, {
		header : '月份',
		name : 'month',
		align : 'right'
	}, {
		header : '专业',
		name : 'category'
	}, {
		header : '分数',
		name : 'score',
		align : 'right'
	}, {
		header : '评分时间',
		name : 'gradedTime',
		width : 150
	}, {
		header : '最后更新时间',
		name : 'updateTime',
		width : 150
	}, {
		header : '总表',
		name : 'id',
		width : 50,
		align : 'center',
		render : function(value) {
			return '<i data-role="collect" class="icon-table" title="编辑总表" style="cursor:pointer;"></i>';
		}
	}, {
		header : '查看',
		name : 'id',
		width : 50,
		align : 'center',
		render : function(value) {
			return '<i data-role="view" class="icon-list" style="cursor:pointer;"></i>';
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
	var defaultUrl = contextPath + '/spmi/quality/grades?orderBy=year,month&order=desc,desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#grade-table',
		url : defaultUrl,
		model : {
			fields : fields,
			needOrder : true,
			orderWidth : 50,
			height : gridHeight
		},
		onClick : function(target, data) {
			changeButtonsStatus(this.selected, data);

			// 查看
			if (target.data('role') === 'view') {
				Utils.modal.reset('view');
				Utils.form.fill('view', data);
				$.each(data.gradeRecords, function(k, v) {
					$('#view-grade-record-table td[id=view-grade-record-' + v.row + '-' + v.col + ']').html(v.content);
				});
				$.each(data.collectRecords, function(k, v) {
					$('#view-form input[name=collect-record-' + v.row + '-' + v.col + ']').val(v.content).attr('readonly', 'readonly');
				});
				Utils.modal.show('view');
				$('#view-grade-record-table').scrollTop(0);
			}

			// 总表
			if (target.data('role') === 'collect') {
				selectedId = data.id;
				if (data.collectRecords.length === 0) { // 新建
					Utils.modal.reset('collect-create');
					Utils.modal.show('collect-create');
				} else { // 编辑
					Utils.modal.reset('collect-edit');
					$.each(data.collectRecords, function(k, v) {
						$('#collect-edit-form input[name=collect-record-' + v.row + '-' + v.col + ']').val(v.content);
					});
					Utils.modal.show('collect-edit');
				}
			}
		},
		onLoaded : function() {
			changeButtonsStatus();

			$('i[title]').tooltip({
				placement : 'bottom'
			});
		}
	}).render();

	// 评分表文本框
	$.each($('#create-grade-record-table tr:not(:first)'), function(key, value) {
		var tds = $(value).find('td');
		$.each(tds, function(k, v) {
			if (tds.length >= 5) {
				var rank = (key + 1) + '-' + (k + (8 - tds.length + 1));
				var standardScore = $(v).prev().prev().html();

				if (k === tds.length - 2) {
					$(v).html('<textarea name="grade-record-' + rank + '" style="width: 146px; height: 100%;resize: none;border: 0"></textarea>');
				}
				if (k === tds.length - 1) {
					$(v).html(
							'<input name="grade-record-' + rank + '" type="text" style="width: 26px; height: 100%;border: 0" data-max="' + standardScore
									+ '" value="' + standardScore + '">');
				}
			}
		});
	});
	$.each($('#edit-grade-record-table tr:not(:first)'), function(key, value) {
		var tds = $(value).find('td');
		$.each(tds, function(k, v) {
			if (tds.length >= 5) {
				var rank = (key + 1) + '-' + (k + (8 - tds.length + 1));
				var standardScore = $(v).prev().prev().html();

				if (k === tds.length - 2) {
					$(v).html('<textarea name="grade-record-' + rank + '" style="width: 146px; height: 100%;resize: none;border: 0"></textarea>');
				}
				if (k === tds.length - 1) {
					$(v).html(
							'<input name="grade-record-' + rank + '" type="text" style="width: 26px; height: 100%;border: 0" data-max="' + standardScore
									+ '" value="' + standardScore + '">');
				}
			}
		});
	});
	// 给查看表格添加行列id
	$.each($('#view-grade-record-table tr:not(:first)'), function(key, value) {
		var tds = $(value).find('td');
		$.each(tds, function(k, v) {
			if (tds.length >= 5) {
				if (k + (8 - tds.length + 1) > 6) {
					var rank = (key + 1) + '-' + (k + (8 - tds.length + 1));
					$(v).attr('id', 'view-grade-record-' + rank);
				}
			}
		});
	});

	// 新建
	$('#create').click(function() {
		Utils.modal.reset('create');
		$('#create-grade-record-table input[name^=grade-record-]').css({
			'border' : '0',
			'background-color' : '#fff'
		});
		Utils.modal.show('create');
		$('#create-grade-record-table').scrollTop(0);
	});

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');
		object.gradeRecords = [];
		object.score = 0;

		// 验证
		// 实得分
		var validateResult1 = true;
		var validateResult2 = true;
		var validateResult3 = true;
		var errorInputNames = [];
		$.each($('#create-grade-record-table input[name^=grade-record-]'), function(key, value) {
			var inputName = $(value).attr('name');
			var inputValue = $(value).val();

			// 恢复样式
			$('#create-grade-record-table input[name=' + inputName + ']').css({
				'border' : '0',
				'background-color' : '#fff'
			});

			// 不能为空
			if (inputValue === '') {
				validateResult1 &= false;
				errorInputNames.push(inputName);
			}

			// 不能为负数
			if (!(/^\d+(.\d+)?$/.test(inputValue))) {
				validateResult2 &= false;
				errorInputNames.push(inputName);
			}

			// 不能大于标准分
			if (inputValue > $(value).data('max')) {
				validateResult3 &= false;
				errorInputNames.push(inputName);
			}

			object.gradeRecords.push({
				row : inputName.split('-')[2],
				col : inputName.split('-')[3],
				content : inputValue
			});

			object.score += parseFloat(inputValue);
		});

		// 高亮错误的input
		$.each(errorInputNames, function(k, v) {
			$('#create-grade-record-table input[name=' + v + ']').css({
				'border' : '1px solid #b94a48',
				'background-color' : '#f2dede'
			});
		});
		if (!validateResult1) {
			Utils.modal.message('create', [ '请输入全部实得分' ]);
			return;
		}
		if (!validateResult2) {
			Utils.modal.message('create', [ '实得分必须为正数' ]);
			return;
		}
		if (!validateResult3) {
			Utils.modal.message('create', [ '实得分不能大于标准分' ]);
			return;
		}

		// 扣分原因
		$.each($('#create-grade-record-table textarea[name^=grade-record-]'), function(key, value) {
			object.gradeRecords.push({
				row : $(value).attr('name').split('-')[2],
				col : $(value).attr('name').split('-')[3],
				content : $(value).val()
			});
		});

		$.post(contextPath + '/spmi/quality/grades', JSON.stringify(object), function(data) {
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
		$('#edit-grade-record-table input[name^=grade-record-]').css({
			'border' : '0',
			'background-color' : '#fff'
		});

		var selectId = grid.selectedData('id');
		$.get(contextPath + '/spmi/quality/grades/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			$.each(grid.selectedData('gradeRecords'), function(k, v) {
				$('#edit-grade-record-table input[name=grade-record-' + v.row + '-' + v.col + ']').val(v.content);
				$('#edit-grade-record-table textarea[name=grade-record-' + v.row + '-' + v.col + ']').val(v.content);
			});
			Utils.modal.show('edit');
			$('#edit-grade-record-table').scrollTop(0);
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		object.gradeRecords = [];
		object.score = 0;

		// 验证
		// 实得分
		var validateResult1 = true;
		var validateResult2 = true;
		var validateResult3 = true;
		var errorInputNames = [];
		$.each($('#edit-grade-record-table input[name^=grade-record-]'), function(key, value) {
			var inputName = $(value).attr('name');
			var inputValue = $(value).val();

			// 恢复样式
			$('#edit-grade-record-table input[name=' + inputName + ']').css({
				'border' : '0',
				'background-color' : '#fff'
			});

			// 不能为空
			if (inputValue === '') {
				validateResult1 &= false;
				errorInputNames.push(inputName);
			}

			// 不能为负数
			if (!(/^\d+(.\d+)?$/.test(inputValue))) {
				validateResult2 &= false;
				errorInputNames.push(inputName);
			}

			// 不能大于标准分
			if (inputValue > $(value).data('max')) {
				validateResult3 &= false;
				errorInputNames.push(inputName);
			}

			object.gradeRecords.push({
				row : inputName.split('-')[2],
				col : inputName.split('-')[3],
				content : inputValue
			});

			object.score += parseFloat(inputValue);
		});

		// 高亮错误的input
		$.each(errorInputNames, function(k, v) {
			$('#edit-grade-record-table input[name=' + v + ']').css({
				'border' : '1px solid #b94a48',
				'background-color' : '#f2dede'
			});
		});
		if (!validateResult1) {
			Utils.modal.message('edit', [ '请输入全部实得分' ]);
			return;
		}
		if (!validateResult2) {
			Utils.modal.message('edit', [ '实得分必须为正数' ]);
			return;
		}
		if (!validateResult3) {
			Utils.modal.message('edit', [ '实得分不能大于标准分' ]);
			return;
		}

		// 扣分原因
		$.each($('#edit-grade-record-table textarea[name^=grade-record-]'), function(key, value) {
			object.gradeRecords.push({
				row : $(value).attr('name').split('-')[2],
				col : $(value).attr('name').split('-')[3],
				content : $(value).val()
			});
		});

		var selectId = grid.selectedData('id');
		object.id = selectId;
		$.put(contextPath + '/spmi/quality/grades/' + selectId, JSON.stringify(object), function(data) {
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
		$.del(contextPath + '/spmi/quality/grades/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	// 保存总表
	$('#collect-create-save').click(function() {
		var object = Utils.form.serialize('collect-create');
		object.collectRecords = [];

		$.each($('#collect-create-form input[name^=collect-record-]'), function(key, value) {
			var inputName = $(value).attr('name');
			var inputValue = $(value).val();

			object.collectRecords.push({
				row : inputName.split('-')[2],
				col : inputName.split('-')[3],
				content : inputValue
			});
		});

		$.put(contextPath + '/spmi/quality/grades/' + selectedId, JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.hide('collect-create');
			} else {
				Utils.modal.message('collect-create', data.errors);
			}
		});
	});

	// 更新总表
	$('#collect-edit-save').click(function() {
		var object = Utils.form.serialize('collect-edit');
		object.collectRecords = [];

		$.each($('#collect-edit-form input[name^=collect-record-]'), function(key, value) {
			var inputName = $(value).attr('name');
			var inputValue = $(value).val();

			object.collectRecords.push({
				row : inputName.split('-')[2],
				col : inputName.split('-')[3],
				content : inputValue
			});
		});

		$.put(contextPath + '/spmi/quality/grades/' + selectedId, JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.hide('collect-edit');
			} else {
				Utils.modal.message('collect-edit', data.errors);
			}
		});
	});
});
