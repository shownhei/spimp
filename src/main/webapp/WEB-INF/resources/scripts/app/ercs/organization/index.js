define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 机构树配置
	var groupTreeSetting = {
		view : {
			selectedMulti : false,
			showTitle : false
		},
		async : {
			enable : true,
			url : contextPath + '/ercs/response-team-tree',
			type : "get",
			autoParam : [ "id" ],
			dataFilter : function(treeId, parentNode, responseData) {
				return responseData.data;
			}
		},
		data : {
			key : {
				name : 'teamName'
			},
			simpleData : {
				pIdKey : "parentId"
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode, clickFlag) {
				var groupTree = $.fn.zTree.getZTreeObj(treeId);
				var teamName = treeNode.teamName;
				if (teamName === "技术专家") {
					$('#normal').hide();
					$('#expert').show();
					$('#expert_team').val(treeNode.id);
				} else {
					$('#normal').show();
					$('#expert').hide();
					normal_grid.set({
						url : contextPath + '/ercs/response-team-members?teamId=' + treeNode.id + '&memberType=normal&orderBy=id&order=desc&pageSize='
								+ pageSize
					});
					$('#normal_team').val(treeNode.id);
				}
			},
			onAsyncSuccess : function(event, treeId, msg) {
				var groupTree = $.fn.zTree.getZTreeObj(treeId);
				try {
					// 调用默认展开第一个结点
					var selectedNode = groupTree.getSelectedNodes();
					var nodes = groupTree.getNodes();
					groupTree.expandNode(nodes[0], true);

					var childNodes = groupTree.transformToArray(nodes[0]);
					groupTree.expandNode(childNodes[1], true);
					groupTree.selectNode(childNodes[1]);
					var childNodes1 = groupTree.transformToArray(childNodes[1]);
					groupTree.checkNode(childNodes1[1], true, true);
					firstAsyncSuccessFlag = 1;
				} catch (err) {
				}
			}
		}
	};

	var groupTree = $.fn.zTree.init($('#groups-tree'), groupTreeSetting);

	// 计算树和表格高度
	var treeHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 100);
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 175);
	if ($(window).width() >= 768) {
		$('#groups-tree').height(treeHeight + 39);
		$('#tab-content').height(treeHeight);
	} else {
		$('#groups-tree').height(150);
		$('#tab-content').height(treeHeight - 170);
	}

	// 新建
	$('#normal-create').click(function() {
		Utils.modal.reset('create-normal');
		Utils.modal.show('create-normal');
	});
	// 保存
	$('#create-normal-save').click(function() {
		if ($("#create-normal-name").attr('data-id') === '') {
			$("#create-normal-name").val('');
			$("#create-normal-name")[0].focus();
		}
		var object = Utils.form.serialize('create-normal');
		// 验证
		if (object.normalMember === '') {
			Utils.modal.message('create-normal', [ '请输入人员名称' ]);
			return;
		}
		// 处理属性
		var normalMember = {
			id : $('#create-normal-name').attr('data-id')
		};
		delete object.normalMember;
		object.normalMember = {
			id : $('#create-normal-name').attr('data-id')
		};

		var team = {
			id : $('#normal_team').val()
		};
		delete object.team;
		object.team = team;

		$.post(contextPath + '/ercs/response-team-members', JSON.stringify(object), function(data) {
			if (data.success) {
				Utils.modal.hide('create-normal');
				normal_grid.refresh();
			} else {
				Utils.modal.message('create-normal', data.errors);
			}
		});
	});

	// 删除
	$('#normal-remove').click(function() {
		if (Utils.button.isDisable('normal-remove')) {
			return;
		}

		Utils.modal.show('remove-normal');
	});

	// 删除确认
	$('#normal-remove-save').click(function() {
		var selectId = normal_grid.selectedData('id');
		$.del(contextPath + '/ercs/response-team-members/' + selectId, function(data) {
			Utils.modal.hide('remove-normal');
			normal_grid.refresh();
		});
	});
	$("#create-normal-name").autocomplete('/ercs/rescuers', {
		dataType : "json",
		mustMatch : true,
		cacheLength : 0,
		parse : function(data) {
			return $.map(data.data.result, function(row) {
				return {
					data : row,
					value : row.staffName,
					result : row.staffName
				};
			});
		},
		formatItem : function(item) {
			return item.staffName;
		}
	}).result(function(e, item) {
		$('#create-normal-name').attr('data-id', item.id);
	});
	// ==========普通成员==
	var normal_fields = [ {
		header : '名称',
		name : 'normalMember',
		render : function(v) {
			return v ? v.staffName : '';
		}
	}, {
		header : '职务',
		name : 'memberLevel',
		width : 65,
		render : function(v) {
			switch (v) {
				case 1:
					return '总指挥';
				case 2:
					return '副总指挥';
				case 3:
					return '成员';
				default:
					return '';
			}
		}
	}, {
		header : '出生日期',
		name : 'normalMember',
		render : function(v) {
			return v ? v.birthDay : '';
		}
	}, {
		header : '文化程度',
		name : 'normalMember',
		width : 60,
		render : function(v) {
			return v ? v.education.itemName : '';
		}
	}, {
		header : '参加工作时间',
		name : 'normalMember',
		render : function(v) {
			return v ? v.workDate : '';
		}
	}, {
		header : '入队时间',
		name : 'normalMember',
		render : function(v) {
			return v ? v.enqueueDate : '';
		}
	}, {
		header : '住址',
		name : 'normalMember',
		render : function(v) {
			return v ? v.address : '';
		}
	}, {
		header : '手机号',
		name : 'normalMember',
		render : function(v) {
			return v ? v.telephone : '';
		}
	} ];
	function changeNormalButtonsStatus(selected, data) {
		if (selected) {
			Utils.button.enable([ 'normal-remove' ]);
		} else {
			Utils.button.disable([ 'normal-remove' ]);
		}
	}
	var normal_gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 220);
	var pageSize = Math.floor(normal_gridHeight / 21);
	var normal_Url = contextPath + '/ercs/response-team-members?memberType=normal&orderBy=id&order=desc&pageSize=' + pageSize;
	var normal_grid = new Grid({
		parentNode : '#normal-table',
		url : normal_Url,
		model : {
			fields : normal_fields,
			needOrder : true,
			orderWidth : 50,
			height : normal_gridHeight
		},
		onClick : function(target, data) {
			changeNormalButtonsStatus(this.selected, data);
		},
		onLoaded : function() {
			changeNormalButtonsStatus();
		}
	}).render();

	$('#expert-create').click(function() {
		Utils.modal.reset('create-expert');
		Utils.modal.show('create-expert');
	});
	$("#create-expert-name").autocomplete('/ercs/specia-lists', {
		dataType : "json",
		mustMatch : true,
		cacheLength : 0,
		parse : function(data) {
			return $.map(data.data.result, function(row) {
				return {
					data : row,
					value : row.name,
					result : row.name
				};
			});
		},
		formatItem : function(item) {
			return item.name;
		}
	}).result(function(e, item) {
		$('#create-expert-name').attr('data-id', item.id);
	});
	// 保存
	$('#create-expert-save').click(function() {
		var expertNameEl = $("#create-expert-name");
		if (expertNameEl.attr('data-id') === '') {
			expertNameEl.val('');
			expertNameEl[0].focus();
		}
		var object = Utils.form.serialize('create-expert');
		// 验证
		if (object.expertMember === '') {
			Utils.modal.message('create-expert', [ '请输入人员名称' ]);
			return;
		}
		// 处理属性
		var expertMember = {
			id : $('#create-expert-name').attr('data-id')
		};
		delete object.expertMember;
		object.expertMember = expertMember;
		var team = {
			id : $('#expert_team').val()
		};
		delete object.team;
		object.team = team;
		$.post(contextPath + '/ercs/response-team-members', JSON.stringify(object), function(data) {
			if (data.success) {
				Utils.modal.hide('create-expert');
				expertGrid.refresh();
			} else {
				Utils.modal.message('create-expert', data.errors);
			}
		});
	});

	// 编辑
	$('#edit').click(function() {
		if (Utils.button.isDisable('edit')) {
			return;
		}

		Utils.modal.reset('edit');

		var selectId = groupTree.getSelectedNodes()[0].id;
		$.get(contextPath + '/system/groups/' + selectId, function(data) {
			var object = data.data;

			object.category = object.category + '-' + object.queryLabel;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		// 验证
		if (object.name === '') {
			Utils.modal.message('edit', [ '请输入机构名称' ]);
			return;
		}
		if (object.number === '') {
			Utils.modal.message('edit', [ '请输入机构编号' ]);
			return;
		}

		// 处理属性
		var selectId = groupTree.getSelectedNodes()[0].id;
		object.id = selectId;
		object.topLevel = false;
		object.queryLabel = object.category.split('-')[1];
		object.category = object.category.split('-')[0];

		$.put(contextPath + '/system/groups/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				groupTree.reAsyncChildNodes(null, "refresh");
				Utils.modal.hide('edit');
				reset();
			} else {
				Utils.modal.message('edit', data.errors);
			}
		});
	});

	// 删除
	$('#expert-remove').click(function() {
		if (Utils.button.isDisable('expert-remove')) {
			return;
		}
		Utils.modal.show('remove-expert');
	});
	function changeExpertButtonsStatus(selected, data) {
		if (selected) {
			Utils.button.enable([ 'expert-remove' ]);
		} else {
			Utils.button.disable([ 'expert-remove' ]);
		}
	}
	// 删除确认
	$('#expert-remove-save').click(function() {
		var selectId = expertGrid.selectedData('id');
		$.del(contextPath + '/ercs/response-team-members/' + selectId, function(data) {
			Utils.modal.hide('remove-expert');
			expertGrid.refresh();
		});
	});
	var expertFields = [ {
		header : '人员名称',
		render : function(v) {
			return v ? v.name : '';
		},
		name : 'expertMember'
	}, {
		header : '性别',
		width : 60,
		render : function(v) {
			return v ? (v.gender === 1 ? '男' : '女') : '';
		},
		name : 'expertMember'
	}, {
		header : '出生日期',
		render : function(v) {
			return v ? v.birthDay : '';
		},
		name : 'expertMember'
	}, {
		header : '专业类型',
		render : function(v) {
			return v ? v.specialty.itemName : '';
		},
		name : 'expertMember'
	}, {
		header : '住址',
		name : 'expertMember',
		render : function(v) {
			return v ? v.address : '';
		}
	}, {
		header : '手机',
		name : 'expertMember',
		render : function(v) {
			return v ? v.mobile : '';
		}
	} ];

	var expertGridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 220);
	var expertPageSize = Math.floor(expertGridHeight / 21);
	var expertUrl = contextPath + '/ercs/response-team-members?memberType=expert&orderBy=id&order=desc&pageSize=' + expertPageSize;
	var expertGrid = new Grid({
		parentNode : '#expert-table',
		url : expertUrl,
		model : {
			fields : expertFields,
			needOrder : true,
			orderWidth : 50,
			height : expertGridHeight
		},
		onClick : function(target, data) {
			changeExpertButtonsStatus(this.selected, data);
		},
		onLoaded : function() {
			changeExpertButtonsStatus();
		}
	}).render();

});
