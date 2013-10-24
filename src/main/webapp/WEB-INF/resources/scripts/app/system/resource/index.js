define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 计算fieldset宽度
	var fieldsetWidth = $('#menus').width() / 4;

	// 菜单样式
	var fieldsetCss = {
		margin : '1px',
		width : fieldsetWidth + 'px',
		float : 'left',
		padding : '1px'
	};

	function load() {
		$.get(contextPath + '/system/resources/2?order=true', function(data) {
			$('#menus').empty();

			// 一级菜单
			var firstMenuCount = data.data.resourceEntities.length; // 菜单个数
			$.each(data.data.resourceEntities, function(entryIndex, entry) {
				var legend = $('<legend class="blue"><i class="' + entry.iconCls + '" style="margin:0 4px 0 0"></i>' + entry.name + '</legend>').css('margin',
						'0px').css('cursor', 'pointer');
				var fieldset = $('<fieldset>').css(fieldsetCss).append(legend);

				var up = $(icon('up_' + entry.id, 'icon-chevron-left', 'green', '向左'));
				var down = $(icon('down_' + entry.id, 'icon-chevron-right', 'green', '向右'));
				var editMenu = $(icon('edit_' + entry.id, 'icon-edit', 'blue', '编辑'));

				// 显示操作按钮
				legend.hover(function() {
					legend.css('background-color', '#f9f2ba');
					$(this).append(editMenu);
					if (entry.sequence === 1) {
						$(this).append(down);
					} else if (entry.sequence === firstMenuCount) {
						$(this).append(up);
					} else {
						$(this).append(up).append(down);
					}
				}, function() {
					legend.css('background-color', 'transparent');
					editMenu.detach();
					up.detach();
					down.detach();
				});

				// 生成多级菜单
				generateMultilevelMenu(entry, fieldset, -15);

				$('#menus').append(fieldset);
			});
		});
	}

	// 编辑/向上/向下事件
	$('#menus').on('click', 'i[id^=edit_],i[id^=up_],i[id^=down_]', function() {
		var splitId = $(this).attr('id').split('_');
		var action = splitId[0];
		var id = splitId[1];

		if (action === 'edit') { // 编辑
			Utils.modal.reset('edit');

			$.get(contextPath + '/system/resources/' + id, function(data) {
				var object = data.data;

				Utils.form.fill('edit', object);
				Utils.modal.show('edit');
			});
		} else { // 向上/向下
			$.put(contextPath + '/system/resources/' + id + '?action=' + action, function(data) {
				load();
			});
		}
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		// 验证
		if (object.name === '') {
			Utils.modal.message('edit', [ '请输入菜单名称' ]);
			return;
		}

		$.put(contextPath + '/system/resources/' + object.id, JSON.stringify(object), function(data) {
			if (data.success) {
				Utils.modal.hide('edit');
				load();
			} else {
				Utils.modal.message('edit', data.errors);
			}
		});
	});

	function icon(id, iconClass, colorClass, title) {
		return '<i id="' + id + '" class="' + iconClass + ' ' + colorClass + '" title="' + title + '" style="margin:0 0 0 4px"></i>';
	}

	load();

	/**
	 * 生成多级菜单
	 */
	function generateMultilevelMenu(resource, appendTo, marginLeft) {
		marginLeft += 15;
		var menuCount = resource.resourceEntities.length; // 菜单个数
		$.each(resource.resourceEntities, function(index, menu) {
			var menuHtml = $('<div style="font-size:14px">' + menu.name + '</div>').css('margin', '1px 1px 1px ' + marginLeft + 'px').css('cursor', 'pointer');

			var up = $(icon('up_' + menu.id, 'icon-chevron-up', 'green', '向上'));
			var down = $(icon('down_' + menu.id, 'icon-chevron-down', 'green', '向下'));
			var edit = $(icon('edit_' + menu.id, 'icon-edit', 'blue', '编辑'));

			menuHtml.hover(function() {
				menuHtml.css('background-color', '#f9f2ba');
				$(this).append(edit);
				if (menuCount !== 1) {
					if (menu.sequence === 1) {
						$(this).append(down);
					} else if (menu.sequence === menuCount) {
						$(this).append(up);
					} else {
						$(this).append(up).append(down);
					}
				}
			}, function() {
				menuHtml.css('background-color', 'transparent');
				edit.detach();
				up.detach();
				down.detach();
			});

			appendTo.append(menuHtml);

			generateMultilevelMenu(menu, appendTo, marginLeft);
		});
	}
});
