define(function(require, exports, module) {
	var Center={};
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');
	
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 配置表格列
	var fields = [ {
		header : '编号',
		name : 'id',
		width : 50,
		align : 'typeCode'
	}, {
		header : '字典名称',
		name : 'itemName'
	}];

	// 计算表格高度
	var gridHeight = $(window).height() - ($('.breadcrumbs').height() + $('.navbar').height() + $('.page-header').height() + 115);
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
});
