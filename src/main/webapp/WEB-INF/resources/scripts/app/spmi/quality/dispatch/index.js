define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

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
	$('#yearSelect').html(yearOptionsHtml);

	// 月份
	var monthOptionsHtml = '';
	for (var j = 1; j < 13; j++) {
		monthOptionsHtml += '<option value="' + j + '">' + j + '</option>';
	}
	$('#monthSelect').html(monthOptionsHtml);

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
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 84);
	var pageSize = Math.floor(gridHeight / GRID_ROW_HEIGHT);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected) {
			Utils.button.enable([ 'edit', 'remove', 'view' ]);
		} else {
			Utils.button.disable([ 'edit', 'remove', 'view' ]);
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
		},
		onLoaded : function() {
			changeButtonsStatus();
		}
	}).render();

	// 评分表文本框
	$.each($('#grade-record-table tr:not(:first)'), function(key, value) {
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

	// 新建
	$('#create').click(function() {
		Utils.modal.reset('create');
		$('#grade-record-table input[name^=grade-record-]').css({
			'border' : '0',
			'background-color' : '#fff'
		});
		Utils.modal.show('create');
		$('#grade-record-table').parent().scrollTop(0);
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
		$.each($('#grade-record-table input[name^=grade-record-]'), function(key, value) {
			var inputName = $(value).attr('name');
			var inputValue = $(value).val();

			// 恢复样式
			$('input[name=' + inputName + ']').css({
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
			$('input[name=' + v + ']').css({
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
		$.each($('#grade-record-table textarea[name^=grade-record-]'), function(key, value) {
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

		var selectId = grid.selectedData('id');
		$.get(contextPath + '/spmi/quality/grades/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		var selectId = grid.selectedData('id');
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

	// 给查看表格添加行列id
	$.each($('#grade-record-view-table tr:not(:first)'), function(key, value) {
		var tds = $(value).find('td');
		$.each(tds, function(k, v) {
			if (tds.length >= 5) {
				if (k + (8 - tds.length + 1) > 6) {
					var rank = (key + 1) + '-' + (k + (8 - tds.length + 1));
					$(v).attr('id', 'grade-record-view-' + rank);
				}
			}
		});
	});

	// 查看
	$('#view').click(function() {
		if (Utils.button.isDisable('view')) {
			return;
		}
		Utils.form.fill('view', grid.selectedData());
		$.each(grid.selectedData('gradeRecords'), function(k, v) {
			$('#grade-record-view-table td[id=grade-record-view-' + v.row + '-' + v.col + ']').html(v.content);
		});
		Utils.modal.show('view');
		$('#grade-record-view-table').parent().scrollTop(0);
	});

	// 搜索
	$('#nav-search-button').click(function() {
		grid.set('url', defaultUrl + Utils.form.buildParams('search-form'));
	});
});
