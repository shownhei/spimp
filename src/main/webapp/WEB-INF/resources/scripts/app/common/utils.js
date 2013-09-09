/**
 * 工具类。
 */
define(function(require, exports, module) {
	var $ = require('kjquery');

	var utils = {};

	/**
	 * 弹出框。
	 */
	utils.modal = {};

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
