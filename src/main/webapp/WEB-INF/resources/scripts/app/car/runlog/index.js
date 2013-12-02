define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');
	var operateUri = '/car/runlog/run-logs';
	
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});
	
	// 下拉列表初始化
	Utils.select.remote([ 'search_car','create_car','edit_car' ], '/car/carslist', 'id', 'carNo',true,'车号');

	// 下拉列表change事件
	$('#search_car').bind('change',function(){
		$('#submit').trigger('click');
	});

	// 启用日期控件
	Utils.input.date('input[type=datetime]');

	// 配置表格列
	var fields = [
		{
			header : '车号',
			name : 'car'
			,render : function(value) {
				return value === null ? '' : value.carNo;
			}
		},
		{
			header : '班次 ',
			name : 'classType'
		},
		{
			header : '车次 ',
			align : 'right',
			width : 80,
			name : 'trainNumber'
		},
		{
			header : '路程 ',
			align : 'right',
			width : 80,
			name : 'distance'
		},
		{
			header : '加油数 ',
			align : 'right',
			width : 80,
			name : 'refuelNumber'
		},
		{
			header : '备注 ',
			name : 'remark'
		},
		{
			header : '记录日期 ',
			width : 90,
			name : 'addDate'
		},
		{
			header : '查看',
			name : 'id',
			width : 50,
			align : 'center',
			render : function(value) {
				return '<i data-role="detail" class="icon-list" style="cursor:pointer;"></i>';
			}
		}
	];

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
	function validate(showType, model){
		var errorMsg = [];
		
		if (model.classType === '') {
			errorMsg.push('请输入班次 ');
		}

		if (model.trainNumber === '') {
			errorMsg.push('请输入车次 ');
		}

		if (model.trainNumber !== '' && !$.isNumeric(model.trainNumber)) {
			errorMsg.push('车次 为数字格式');
		}

		if (model.distance === '') {
			errorMsg.push('请输入路程 ');
		}

		if (model.distance !== '' && !$.isNumeric(model.distance)) {
			errorMsg.push('路程 为数字格式');
		}

		if (model.refuelNumber === '') {
			errorMsg.push('请输入加油数 ');
		}

		if (model.refuelNumber !== '' && !$.isNumeric(model.refuelNumber)) {
			errorMsg.push('加油数 为数字格式');
		}

		if (model.remark === '') {
			errorMsg.push('请输入备注 ');
		}

		if (model.addDate === '') {
			errorMsg.push('请输入记录日期 ');
		}

		if(errorMsg.length > 0){
			Utils.modal.message(showType, [ errorMsg.join(',') ]);
			return false;
		}
		
		return true;
	}
	
	// 查看
	function showDetail(data){
		Utils.modal.reset('detail');
		
		var object = $.extend({},data);
		object.car = object.car.carNo;


		Utils.form.fill('detail', object);
		Utils.modal.show('detail');
	}
	
	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');
		
		// 验证
		if(!validate('create', object)){
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
		if(!validate('edit', object)){
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
	
	// 导出
	$('#export').click(function() {
		if (Utils.button.isDisable('export')) {
			return;
		}
		
		window.location.href = operateUri + '/export-excel?' + Utils.form.buildParams('search-form');
	});

	// 搜索
	$('#submit').click(function() {
		grid.set({
			url : defaultUrl + Utils.form.buildParams('search-form')
		});
	});
	
	// 查询条件重置
    $('#reset').click(function() {
        grid.set('url', defaultUrl);
        grid.refresh();
    });
});