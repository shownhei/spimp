/**
 * 工具类。
 */
define(function(require, exports, module) {
	var $ = require('kjquery');

	var utils = {};

	/**
	 * 按钮
	 */
	utils.button = {};
	var buttonDisableCSS = 'disabled';

	utils.button.disable = function(ids) {
		$.each(ids, function(key, value) {
			$('#' + value).addClass(buttonDisableCSS);
		});
	};

	utils.button.enable = function(ids) {
		$.each(ids, function(key, value) {
			$('#' + value).removeClass(buttonDisableCSS);
		});
	};

	utils.button.isDisable = function(id) {
		return $('#' + id).hasClass(buttonDisableCSS);
	};

	/**
	 * 弹出框。
	 */
	utils.modal = {};

	utils.modal.show = function(prefix) {
		var $modal = $('#' + prefix + '-modal');
		$modal.modal({
			backdrop : 'static'
		});

		// jquery-ui没有考虑具有marginLeft时left的取值问题
		function getContainerment() {
			var marginLeft = parseInt($modal.css('marginLeft'), 10) || 0, marginTop = parseInt($modal.css('marginTop'), 10) || 0, width = $modal.outerWidth(), height = $modal
					.outerHeight(), wholeWidth = $(document).width(), wholeHeight = $(document).height();

			return [ -marginLeft, -marginTop, wholeWidth - width - marginLeft, wholeHeight - height - marginTop ];
		}

		$modal.draggable({
			addClasses : false,
			handle : '.modal-header',
			containment : getContainerment()
		});
	};

	utils.modal.hide = function(prefix) {
		$('#' + prefix + '-modal').modal('hide');
	};

	utils.modal.reset = function(prefix) {
		document.getElementById(prefix + '-form').reset();
		$('#' + prefix + '-message-content').html('');
		$('#' + prefix + '-message-alert').hide();
	};

	utils.modal.message = function(prefix, messages) {
		var message = '发生错误，请重试';

		if (messages !== null) {
			$.each(messages, function(key, value) {
				message = value;
			});
		}

		$('#' + prefix + '-message-content').html(message);
		$('#' + prefix + '-message-alert').show();
	};

	/**
	 * 下拉列表。
	 */
	utils.select = {};

	utils.select.remote = function(ids, url, value, display, blank, blankText) {
		$.get(url, function(data) {
			var html = '';
			if (blank) {
				if (blankText !== undefined) {
					html += '<option value="">' + blankText + '</option>';
				} else {
					html += '<option value=""></option>';
				}
			}
			$.each(data.data, function(entryIndex, entry) {
				html += '<option value="' + entry[value] + '">' + utils.html.encode(entry[display]) + '</option>';
			});

			$.each(ids, function(key, value) {
				$('#' + value).html(html);
			});
		});
	};

	utils.select.setOption = function(selectId, optionValue) {
		/**
		 * 移除已选择的option，在非IE浏览器可使用： $('#' + id + ' >
		 * option').removeAttr('selected'); 为兼容IE浏览器，只能如此循环替换。。。
		 */
		$.each($('#' + selectId + ' > option'), function(key, value) {
			var option = $(value);
			option.replaceWith('<option value="' + option.attr('value') + '">' + option.text() + '</option>');
		});
		var selectOption = $('#' + selectId + ' > option[value="' + optionValue + '"]');
		selectOption.replaceWith('<option value="' + optionValue + '" selected="selected">' + selectOption.text() + '</option>');
	};

	/**
	 * 表单
	 */
	utils.form = {};

	utils.form.fill = function(prefix, model) {
		var inputs = $('#' + prefix + '-form').find(':input');

		$.each(inputs, function(key, value) {
			if (value.tagName === 'SELECT') {
				var propertys = $(value).attr('name').replace(/\[/g, '.').replace(/\]/g, '').split('.');

				// 目前只处理2层或3层嵌套的情况
				if (propertys.length === 2) {
					utils.select.setOption($(value).attr('id'), model[propertys[0]][propertys[1]]);
				} else if (propertys.length === 3) {
					utils.select.setOption($(value).attr('id'), model[propertys[0]][propertys[1]][propertys[2]]);
				}
			} else if (value.tagName === 'TEXTAREA') {
				$(value).html(model[$(value).attr('name')]);
			} else {
				$(value).val(model[$(value).attr('name')]);
			}
		});
	};

	utils.form.serialize = function(prefix) {
		return $('#' + prefix + '-form').serializeObject();
	};

	/**
	 * 编码
	 */
	utils.html = {};

	utils.html.encode = function(string) {
		return $('<div/>').text(string).html();
	};

	utils.html.decode = function(string) {
		return $('<div/>').html(string).text();
	};

	module.exports = utils;
});
