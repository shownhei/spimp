define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	window.$=$;
	window.Utils=Utils;
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	// 启用日期控件
	Utils.input.date('input[type=datetime]');
	// 配置表格列
	var fields = [ {
		header : '设备型号',
		name : 'deviceVersion'
	}, {
		header : '机电型号',
		name : 'electroVersion'
	},{
		header : '额定电流',
		name : 'electricity'
	}, {
		header : '额定电压',
		name : 'voltage'
	},  {
		header : '额定功率',
		name : 'power'
	},  {
		header : '额定频率',
		name : 'frequency'
	}, {
		header : '防爆合格证信息',
		name : 'explosion'
	}, {
		header : '煤安标志信息',
		name : 'mineSecurity'
	}, {
		header : '相数',
		name : 'phase'
	}, {
		header : '出厂日期',
		name : 'rolloutDate'
	}, {
		header : '出厂编号',
		name : 'rolloutNum'
	}, {
		header : '尺寸',
		name : 'size'
	}, {
		header : '详细信息',
		render:function(value){
			return '<a href="#" data-name=' + value + '>'+"图文详情"+'</a>';
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
	var defaultUrl = contextPath + '/spmi/electro/electro-queries?orderBy=id&order=desc&pageSize=' + pageSize;
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
		},
		onLoaded : function() {
			changeButtonsStatus();
		}
	}).render();
	/**
	 * grid行内点击事件
	 */
	$("#material-table").delegate('a[data-name]', 'click', function () {
		var selectId = grid.selectedData('id');
		$.get('/spmi/electro/electro-queries/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('view', object);
			Utils.modal.show('view');
		});
		return false;
	});
	// 新建
	$('#create').click(function() {
		Utils.modal.reset('create');
		Utils.modal.show('create');
	});

	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');
		// 验证
		$.post('/spmi/electro/electro-queries', JSON.stringify(object), function(data) {
			if (data.success) {
				grid.set({
					url : defaultUrl
				});
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
		$.get('/spmi/electro/electro-queries/' + selectId, function(data) {
			var object = data.data;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		// 处理属性
		var selectId = grid.selectedData('id');
		object.id = selectId;
		$.put('/spmi/electro/electro-queries/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
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
		$.del('/spmi/electro/electro-queries/' + selectId, function(data) {
			grid.set({
				url : defaultUrl
			});
			grid.refresh();
			Utils.modal.hide('remove');
		});
	});
	//查询
	$('#submit').click(function(){
		grid.set({
			url : '/spmi/electro/multi-query?'+'startTime='+$('#startTime').val()+'&endTime='+$('#endTime').val() + Utils.form.buildParams('query-form')
		});
		
	});
	//查询条件重置
	$('#reset').click(function(){
		$('#query-form')[0].reset();
		$('#startTime').val("");
		$('#endTime').val("");
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
			var process=new Utils.modal.showProcess('process');
			window.process=process;
		}
	});
	$('#create-file-delete').bind('click',function(){
		$('#attachment').parent().parent().hide();
		$('#create-file-form')[0].reset();
		$('#create-file-form').show();
	});
});
function callBack(data){
	$('#attachment').val(data.data.filePath);
	$('#attachment').attr('data-id',data.data.id);
	$('#create-file-form').hide();
	window.process.stop();
	window.process=null;
	$('#attachment').parent().parent().show();
}
function showDocument(id){
	$('#showDocument').attr('src','/ercs/view-pdf/'+id+"?t="+new Date().getTime());
	Utils.modal.show('view');
}