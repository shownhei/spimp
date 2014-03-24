define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 启用日期控件
	Utils.input.date('#query-startDate');
	Utils.input.date('#query-endDate');
	$('#create-deadlineDate,#edit-deadlineDate').datepicker({
		autoclose : true,
		format : 'yyyy-mm-dd',
		startDate : '0d'
	});
	$('#create-checkDate,#edit-checkDate').datepicker({
		autoclose : true,
		format : 'yyyy-mm-dd',
		endDate : '0d'
	});

	// 初始化富文本编辑器
	var editMeasure = $('#edit-measure').xheditor({
		skin : 'nostyle',
		tools : 'simple'
	});

	// 流程图示
	var infoHtml = '<span class="label label-important arrowed-right">已下发</span>';
	infoHtml += '<span class="label label-warning arrowed-in arrowed-right">已指派</span>';
	infoHtml += '<span class="label label-success arrowed-in arrowed-right">已执行</span>';
	infoHtml += '<span class="label label-info arrowed-in">已审核</span>';
	$('#info').popover({
		placement : 'bottom',
		trigger : 'hover',
		html : true,
		title : '流程图示',
		content : infoHtml
	});

	// 获取机构
	Utils.select.remote([ 'query-testGroupIdSelect' ], contextPath + '/system/groups?label=office,team', 'id', 'name', true, '选择被检单位');

	// 配置表格列
	var fields = [ {
		header : '编号',
		name : 'number',
		width : 140
	}, {
		header : '安全问题或隐患',
		name : 'issue',
		render : function(value) {
			return '<span title="' + value + '">' + value + '</span>';
		}
	}, {
		header : '整改责任人',
		name : 'principal',
		width : 100
	}, {
		header : '被检单位',
		name : 'testGroup',
		width : 100
	}, {
		header : '隐患下发部门',
		name : 'sendGroup',
		width : 100
	}, {
		header : '整改期限',
		name : 'deadlineDate',
		width : 90
	}, {
		header : '检查日期',
		name : 'checkDate',
		width : 90
	}, {
		header : '状态',
		name : 'status',
		width : 60,
		align : 'center',
		render : function(value) {
			return getLabelByStatus(value);
		}
	}, {
		header : '查看',
		name : 'id',
		width : 50,
		align : 'center',
		render : function() {
			return '<i data-role="view" class="icon-list" style="cursor:pointer;"></i>';
		}
	} ];

	function getLabelByStatus(status) {
		var labelClass;
		switch (status) {
			case '未指派':
				labelClass = 'label-important';
				break;
			case '已下发':
				labelClass = 'label-important';
				break;
			case '已指派':
				labelClass = 'label-warning';
				break;
			case '已执行':
				labelClass = 'label-success';
				break;
			case '已完成':
				labelClass = 'label-info';
				break;
			case '已审核':
				labelClass = 'label-info';
				break;
			default:
				labelClass = '';
				break;
		}

		return '<span class="label ' + labelClass + ' label-small">' + status + '</span>';
	}

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 84);
	var pageSize = Math.floor((gridHeight - 1) / GRID_ROW_HEIGHT);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected) {
			switch (data.status) {
				case '已下发':
					Utils.button.enable([ 'edit', 'remove' ]);
					Utils.button.disable([ 'check' ]);
					break;
				case '已指派':
					Utils.button.disable([ 'edit', 'check', 'remove' ]);
					break;
				case '已执行':
					Utils.button.enable([ 'check' ]);
					Utils.button.disable([ 'edit', 'remove' ]);
					break;
				case '已审核':
					Utils.button.disable([ 'edit', 'check', 'remove' ]);
					break;
				default:
					Utils.button.disable([ 'edit', 'check', 'remove' ]);
					break;
			}
		} else {
			Utils.button.disable([ 'edit', 'check', 'remove' ]);
		}
	}

	// 配置表格
	var defaultUrl = contextPath + '/spmi/daily/reforms?orderBy=id&order=desc&pageSize=' + pageSize;
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

			// 查看
			if (target.data('role') === 'view') {
				$.each(data, function(k, v) {
					if (k === 'status') {
						$('#view-reform-' + k).html(getLabelByStatus(v));
					} else {
						$('#view-reform-' + k).html(v);
					}
				});

				$.get(contextPath + '/spmi/daily/plans/' + data.planId, function(data) {
					$.each(data.data, function(k, v) {
						if (k === 'status') {
							$('#view-plan-' + k).html(getLabelByStatus(v));
						} else {
							$('#view-plan-' + k).html(v);
						}
					});
					Utils.modal.show('view');
				});
			}
		},
		onLoaded : function() {
			changeButtonsStatus();
		}
	}).render();

	// 整改责任人
	$("#create-principalId").select2({
		placeholder : "输入用户名或者姓名搜索",
		minimumInputLength : 1,
		ajax : {
			url : contextPath + "/system/accounts",
			dataType : 'json',
			data : function(term, page) {
				return {
					q : term
				};
			},
			results : function(data, page) {
				return {
					results : data.data
				};
			}
		},
		formatSelection : function(item) {
			return item.realName;
		},
		formatResult : function(item) {
			return item.realName + '[' + item.groupEntity.name + ']';
		}
	});

	// 隐患下发部门
	$("#create-sendGroupId").select2({
		placeholder : "请选择隐患下发部门",
		ajax : {
			url : contextPath + "/system/groups?label=office,team",
			dataType : 'json',
			results : function(data, page) {
				return {
					results : data.data
				};
			}
		},
		formatSelection : function(item) {
			return item.name;
		},
		formatResult : function(item) {
			return item.name;
		}
	});
	// 被检单位
	$("#create-testGroupId").select2({
		placeholder : "请选择被检单位",
		ajax : {
			url : contextPath + "/system/groups?label=office,team",
			dataType : 'json',
			results : function(data, page) {
				return {
					results : data.data
				};
			}
		},
		formatSelection : function(item) {
			return item.name;
		},
		formatResult : function(item) {
			return item.name;
		}
	});

	// 新建
	$('#create').click(function() {
		Utils.modal.reset('create');
		$('#create-checkDate').val(new Date().format('yyyy-MM-dd'));
		$("#create-principalId,#create-sendGroupId,#create-testGroupId").select2('val', '');
		Utils.modal.show('create');
	});

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');

		$.post(contextPath + '/spmi/daily/reforms', JSON.stringify(object), function(data) {
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
		$.get(contextPath + '/spmi/daily/reforms/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			$("#edit-principalId").select2({
				placeholder : "输入用户名或者姓名搜索",
				minimumInputLength : 1,
				ajax : {
					url : contextPath + "/system/accounts",
					dataType : 'json',
					data : function(term, page) {
						return {
							q : term
						};
					},
					results : function(data, page) {
						return {
							results : data.data
						};
					}
				},
				initSelection : function(element, callback) {
					var id = $(element).val();
					if (id !== "") {
						$.ajax(contextPath + "/system/accounts/" + id, {
							dataType : "json"
						}).done(function(data) {
							callback(data.data);
						});
					}
				},
				formatSelection : function(item) {
					return item.realName;
				},
				formatResult : function(item) {
					return item.realName + '[' + item.groupEntity.name + ']';
				}
			});
			$("#edit-sendGroupId").select2({
				placeholder : "请选择隐患下发部门",
				ajax : {
					url : contextPath + "/system/groups?label=office,team",
					dataType : 'json',
					results : function(data, page) {
						return {
							results : data.data
						};
					}
				},
				initSelection : function(element, callback) {
					var id = $(element).val();
					if (id !== "") {
						$.ajax(contextPath + "/system/groups/" + id, {
							dataType : "json"
						}).done(function(data) {
							callback(data.data);
						});
					}
				},
				formatSelection : function(item) {
					return item.name;
				},
				formatResult : function(item) {
					return item.name;
				}
			});
			$("#edit-testGroupId").select2({
				placeholder : "请选择被检单位",
				ajax : {
					url : contextPath + "/system/groups?label=office,team",
					dataType : 'json',
					results : function(data, page) {
						return {
							results : data.data
						};
					}
				},
				initSelection : function(element, callback) {
					var id = $(element).val();
					if (id !== "") {
						$.ajax(contextPath + "/system/groups/" + id, {
							dataType : "json"
						}).done(function(data) {
							callback(data.data);
						});
					}
				},
				formatSelection : function(item) {
					return item.name;
				},
				formatResult : function(item) {
					return item.name;
				}
			});
			editMeasure.setSource(object.measure);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		// 处理属性
		var selectId = grid.selectedData('id');
		object.id = selectId;

		$.put(contextPath + '/spmi/daily/reforms/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.hide('edit');
			} else {
				Utils.modal.message('edit', data.errors);
			}
		});
	});

	// 审核
	$('#check').click(function() {
		if (Utils.button.isDisable('check')) {
			return;
		}

		Utils.modal.reset('check');

		var selectId = grid.selectedData('id');
		$.get(contextPath + '/spmi/daily/reforms/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('check', object);
			Utils.modal.show('check');
		});
	});

	// 审核确认
	$('#check-save').click(function() {
		var object = Utils.form.serialize('check');

		// 处理属性
		var selectId = grid.selectedData('id');
		object.id = selectId;
		object.deadlineDate = grid.selectedData('id');
		object.checkDate = grid.selectedData('checkDate');
		object.principalId = grid.selectedData('principalId');
		object.sendGroupId = grid.selectedData('sendGroupId');
		object.testGroupId = grid.selectedData('testGroupId');

		$.put(contextPath + '/spmi/daily/reforms/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				grid.refresh();
				Utils.modal.hide('check');
			} else {
				Utils.modal.message('check', data.errors);
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
		$.del(contextPath + '/spmi/daily/reforms/' + selectId, function(data) {
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});

	// 搜索
	$('#query-button').click(function() {
		grid.set('url', defaultUrl + Utils.form.buildParams('query-form'));
	});

	// 重置
	$('#reset-button').click(function() {
		grid.set('url', defaultUrl);
	});
});
