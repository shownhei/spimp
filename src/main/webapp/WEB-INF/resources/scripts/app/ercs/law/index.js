define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');
	window.$=$;
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
		render:function(v){
			return v?v.name:'';
		},
		name : 'department',
		width:100
	}, {
		header : '附件',
		name : 'attachment',
		render:function(v){
			return v?'<a href="'+v+'" target="_blank">'+(v.substring(v.lastIndexOf('&#x2F;')+6))+'</a>':'';
		}
	},{
		header : '发布时间',
		name : 'addTime',
		width:150
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
		// 验证
		if (object.fileName === '') {
			Utils.modal.message('create', [ '请输入文件名' ]);
			return;
		}
		if (object.department === '') {
			delete object.department;
		}
		var department={id:$('#create-department').attr('data-id'),name:object.department};
		delete object.department;
		object.department=department;
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
			if(object.department.name){
				$('#edit-department').val(object.department.name);
				$('#edit-department').attr('data-id',object.department.id);
			}
			if(object.attachment){
				$('#attachment').parent().parent().show();
			}else{
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
		if (object.department === '') {
			delete object.department;
		}
		var department={id:$('#edit-department').attr('data-id'),name:object.department};
		delete object.department;
		object.department=department;
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
	$('#file').bind('change',function(){
		if($('#file').val()!==''){
			$('#create-file-form').submit();
		}
	});
	$('#create-file-delete').bind('click',function(){
		$('#attachment').parent().parent().hide();
		$('#create-file-form')[0].reset();
		$('#create-file-form').show();
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
	//创建
	new groupTree('create_groupSelectTree','create_treeDemo','create-selectGroup','create-department');
	//编辑
	new groupTree('edit_groupSelectTree','edit_treeDemo','edit-selectGroup','edit-department');
});
function callBack(data){
	$('#attachment').val(data.data);
	$('#create-file-form').hide();
	$('#attachment').parent().parent().show();
}