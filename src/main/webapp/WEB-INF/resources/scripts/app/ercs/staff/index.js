define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	Utils.select.remote([ 'edit-expertiseArea', 'create-expertiseArea' ], '/ercs/dictionaries?typeCode=expertise_area&list=true', 'id', 'itemName');
	Utils.select.remote([ 'edit-responseLevel', 'create-responseLevel' ], '/ercs/dictionaries?typeCode=response_level&list=true', 'id', 'itemName');

	// 配置表格列
	var fields = [ {
		header : '姓名',
		width:100,
		name : 'staffName'
	}, {
		header : '专业领域',
		name : 'expertiseArea',
		render : function(val) {
			if (val) {
				return val.itemName;
			}
			return '';
		}
	},{
		header : '事故响应级别',
		name : 'responseLevel',
		width:100,
		render : function(val) {
			if (val) {
				return val.itemName;
			}
			return '';
		}
	},  {
		header : '部门',
		render:function(v){
			return v?v.name:'';
		},
		name : 'department'
	},{
		header : '员工类型',
		width:100,
		name : 'staffType'
	},  {
		header : '职称',
		name : 'title'
	}, {
		header : '经验',
		name : 'experience'
	}, {
		header : '联系方式',
		name : 'phone'
	}, {
		header : '创建时间',
		width:150,
		name : 'addTime'
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
	var defaultUrl = contextPath + '/ercs/rescuers?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#staff-table',
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
		Utils.button.enable([ 'create-save' ]);
	});

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');

		if (object.staffType === '') {
			Utils.modal.message('create', [ '请输入员工类型' ]);
			return;
		}
		if (object.staffName === '') {
			Utils.modal.message('create', [ '请输入姓名' ]);
			return;
		}
		if (object.phone === '') {
			Utils.modal.message('create', [ '请输入联系方式' ]);
			return;
		}
		var department={id:$('#create-department').attr('data-id'),name:object.department};
		delete object.department;
		object.department=department;
		Utils.button.disable([ 'create-save' ]);
		$.post('/ercs/rescuers', JSON.stringify(object), function(data) {
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
		$.get('/ercs/rescuers/' + selectId, function(data) {
			var object = data.data;
			Utils.form.fill('edit', object);
			$('#edit-department').val(object.department.name);
			$('#edit-department').attr('data-id',object.department.id);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		if (object.staffType === '') {
			Utils.modal.message('edit', [ '请输入员工类型' ]);
			return;
		}
		if (object.staffName === '') {
			Utils.modal.message('edit', [ '请输入姓名' ]);
			return;
		}
		if (object.phone === '') {
			Utils.modal.message('edit', [ '请输入联系方式' ]);
			return;
		}
		var department={id:$('#edit-department').attr('data-id'),name:object.department};
		delete object.department;
		object.department=department;
		var selectId = grid.selectedData('id');
		$.put('/ercs/rescuers/' + selectId, JSON.stringify(object), function(data) {
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
		$.del('/ercs/rescuers/' + selectId, function(data) {
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
	function groupTree(treewindow,_treePanel,_triggerName,aimElm){
		var me = this;
		this.beforeClick=function(treeId, treeNode){
			return true;
		};
		this.onClick=function(e, treeId, treeNode){
			var zTree = $.fn.zTree.getZTreeObj(_treePanel),
			nodes = zTree.getSelectedNodes();
			var cityObj = $("#"+aimElm);
			cityObj.val(nodes[0].name);
			cityObj.attr("data-id", nodes[0].id);
		};
		this.setting={
				view: {
					dblClickExpand: false
				},
				async : {
					enable : true,
					url : contextPath + '/system/groups',
					type : "get",
					dataFilter : function(treeId, parentNode, responseData) {
						return responseData.data[0].groupEntities;
					}
				},
				data : {
					key : {
						children : 'groupEntities'
					}
				},
				callback: {
					beforeClick: me.beforeClick,
					onClick: me.onClick
				}
		};
		
		this.onKeyDown=function(){
			if (!(event.target.id == "menuBtn" || event.target.id == treewindow || $(event.target).parents("#"+treewindow).length>0)) {
				me.hideTree();
			}
		};
		this.showTree=function(){
			var cityObj = $("#"+aimElm);
			var cityOffset = cityObj.offset();
			$("#"+treewindow).css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("#"+treewindow).css('z-index',1090);
			$("#"+treewindow).css("background-color",'white');
			$("#"+treewindow).css("-webkit-box-shadow",'0 3px 7px rgba(0, 0, 0, 0.3)');
			$("#"+treewindow).css("border",'1px solid rgba(0, 0, 0, 0.3)');
			$("body").bind("mousedown", me.onKeyDown);
		};
		this.hideTree=function(){
			$("#"+treewindow).fadeOut("fast");
			$("body").unbind("mousedown", me.onKeyDown);
		};
		var currentTree = $.fn.zTree.init($('#'+_treePanel), me.setting);
		$('#'+_triggerName).bind('click',function(){
			me.showTree();
		});
		return currentTree;
	}
	//创建edit_selectGroup
	new groupTree('create_groupSelectTree','create_treeDemo','create_selectGroup','create-department');
	new groupTree('edit_groupSelectTree','edit_treeDemo','edit_selectGroup','edit-department');
});
