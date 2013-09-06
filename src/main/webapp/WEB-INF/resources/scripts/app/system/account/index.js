define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	// 配置表格列
	var fields = [ {
		header : '编号',
		name : 'id',
		width : 50,
		align : 'center'
	}, {
		header : '用户名',
		name : 'principal'
	}, {
		header : '角色名',
		name : 'roleEntity',
		render : function(value) {
			return value.name;
		}
	}, {
		header : '所属机构',
		name : 'groupEntity',
		render : function(value) {
			return value.name;
		}
	}, {
		header : '姓名',
		name : 'realName'
	}, {
		header : '电话',
		name : 'telephone'
	}, {
		header : '是否锁定',
		name : 'locked',
		render : function(value) {
			switch (value) {
				case false:
					return '<span style="color:#96CE8D">否</span>';
				case true:
					return '<span style="color:orange">是</span>';
			}
		}
	}, {
		header : '创建时间',
		name : 'createTime',
		width : 150
	} ];

	// 计算表格高度
	var gridHeight = $(window).height() - ($('.breadcrumbs').height() + $('.navbar').height() + $('.page-header').height() + 115);

	/**
	 * 修改/重置按钮状态
	 */
	function changeButtonsStatus(selected, data) {
		if (selected) {
			$('#edit,#remove,#reset').removeClass('disabled');
			if (data.locked) {
				$('#lock').addClass('disabled');
				$('#unlock').removeClass('disabled');
			} else {
				$('#lock').removeClass('disabled');
				$('#unlock').addClass('disabled');
			}
		} else {
			$('#edit,#remove,#lock,#unlock,#reset').addClass('disabled');
		}
	}

	// 配置表格
	new Grid({
		parentNode : '#table',
		url : contextPath + '/system/accounts?orderBy=id&order=desc&pageSize=18',
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

	// 获取机构
	$.get('groups?label=mine', function(data) {
		var html;
		$.each(data.data, function(index, item) {
			html += '<option value="' + item.id + '">' + item.name + '</option>';
		});
		$('#groupEntity').html(html);
	});
	
	// 获取角色
	$.get('roles', function(data) {
		var html;
		$.each(data.data, function(index, item) {
			html += '<option value="' + item.id + '">' + item.name + '</option>';
		});
		$('#roleEntity').html(html);
	});

	// 新建
	$('#create').click(function() {
	});
});
