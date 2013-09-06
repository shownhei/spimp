/**
 * 安全生产综合管理平台 © 煤炭科学研究总院
 * 
 * 登录。
 * 
 * 
 * @author shelltea
 */
define(function(require, exports, module) {
	var $ = require('$');

	function login() {
		$.post('auth', {
			'principal' : $('#principal').val(),
			'credential' : $('#credential').val()
		}, function(data) {
			if (data.success === true) {
				window.location = '/';
			} else {
				var message = '';
				$.each(data.errors, function(key, value) {
					message = value;
				});
				$('#message').replaceWith('<div id="message" class="alert alert-error">' + message + '</div>').show();
				$('#message').fadeOut(5000);
			}
		});
	}

	$(function() {
		$('#principal').focus();

		// 点击登录按钮登录
		$('#login').click(function() {
			login();
		});

		// 点击回车登录
		$("body").bind('keyup', function(event) {
			if (event.keyCode === 13) {
				login();
			}
		});
	});
});
