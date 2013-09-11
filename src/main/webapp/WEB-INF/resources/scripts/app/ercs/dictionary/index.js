define(function(require, exports, module) {
	var Center={};
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');
	
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 配置表格列
	var fields = [ {
		header : '字典名称',
		name : 'itemName'
	}];
	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 110);
	var pageSize = Math.floor(gridHeight / 20);
	// 计算表格高度
	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected) {
			$('#edit,#delete').removeClass('disabled');
		} else {
			$('#edit,#delete').addClass('disabled');
		}
	}

	var tableKeyArray=['plan_type','personal_category','expertise_area','accident_category','response_level'];
	var tableArray={};
	for(var i=0;i<tableKeyArray.length;i++){
		// 配置表格
		var grid = new Grid({
			parentNode : '#'+tableKeyArray[i]+'-table',
			url : contextPath + '/ercs/dictionaries?orderBy=id&order=desc&pageSize=18&typeCode='+tableKeyArray[i],
			urlParser : /(grid_)\d+(.*)/,
			model : {
				needOrder:true,
				fields : fields,
				height : gridHeight
			},
			onClick : function(target, data) {
				changeButtonsStatus(this.selected, data);
			},
			onSort : function(name, direction) {
				console.log(name, direction);
			},
			onLoaded : function() {
				changeButtonsStatus();
			}
		}).render();
		grid['table_key']=tableKeyArray[i];
		tableArray[tableKeyArray[i]+'-table']=grid;
		
	}
	
	$('a[data-toggle="tab"]').on('shown', function (e) {
		var flag=e.target.href;
		var tableKey=flag.substr(flag.indexOf('#')+1);
		$('#typeCode').val(tableKey);
		Center['currentTable']=tableArray[tableKey+'-table'];
		Center['currentTable'].refresh();
	});
	// 新建
	$('#create').click(function() {
		$('#create-modal').modal({
			backdrop : 'static'
		});
		$('#itemName').val('');
		$('#itemName')[0].focus();
	});
	// 删除
	$('#delete').click(function() {
		if (Utils.button.isDisable('delete')) {
			return;
		}
		Utils.modal.show('delete');
	});
	// 删除确认
	$('#delete-save').click(function() {
		var selectId = Center['currentTable'].selectedData('id');
		$.del('/ercs/dictionaries/' + selectId, function(data) {
			Center['currentTable'].refresh();
			Utils.modal.hide('delete');
		});
	});
	// 编辑
	$('#edit').click(function() {
		if (Utils.button.isDisable('edit')) {
			return;
		}
		Utils.modal.reset('edit');
		var selectId = Center['currentTable'].selectedData('id');
		$.get('/ercs/dictionaries/' + selectId, function(data) {
			var object = data.data;
			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});
	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');
		// 验证
		if (object.itemName === '') {
			Utils.modal.message('create', [ '请输入字典值' ]);
			return;
		}
		// 处理属性
		var selectId = Center['currentTable'].selectedData('id');
		$.put('/ercs/dictionaries/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				Center['currentTable'].refresh();
				Utils.modal.hide('edit');
			} else {
				Utils.modal.message('edit', data.errors);
			}
		});
	});
	// 保存
	$('#create-save').click(function() {
		var object = $('#create-form').serializeObject();
		// 验证
		if (object.itemName === '') {
			Utils.modal.message('create', [ '请输入字典值' ]);
			return;
		}
		$.post('/ercs/dictionaries', JSON.stringify(object), function(data) {
			if (data.success) {
				Center['currentTable'].refresh();
				$('#create-modal').modal('hide');
			} else {
				Utils.modal.message('create', data.errors);
			}
		});
	});
	Center['currentTable']=tableArray[tableKeyArray[0]+'-table'];
	// 搜索
	$('#query').click(function() {
		Center['currentTable'].set({
			url : contextPath + '/ercs/dictionaries?orderBy=id&order=desc&pageSize=18&typeCode='+Center['currentTable']['table_key'] + Utils.form.buildParams('query-form')
		});
	});
});
