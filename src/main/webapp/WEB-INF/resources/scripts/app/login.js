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
				var html = '<div id="message" style="height:20px"><img style="margin-top:2px;float:left" src="' + resourcesPath
						+ '/images/icons/exclamation.png" width="16"><div style="margin-top:2px;float:left;height:20px">' + message + '</div></div>';
				$('#message').replaceWith(html);
				$('#message').fadeOut(8000);
			}
		});
	}

	// 背景及登录框自适应
	var loginBgWidth = 631;
	var loginBgHeight = 434;

	function showPage() {
		var w, h;
		if (!!(window.attachEvent && !window.opera)) {
			h = document.documentElement.clientHeight;
			w = document.documentElement.clientWidth;
		} else {
			h = window.innerHeight;
			w = window.innerWidth;
		}
		var bgImg = $('#bg');
		bgImg.width(w);
		bgImg.height(h);

		var offsetTop = (h - loginBgHeight) / 2;
		var offsetLeft = (w - loginBgWidth) / 2;

		if (offsetTop < 0) {
			offsetTop = 0;
		}
		if (offsetLeft < 0) {
			offsetLeft = 0;
		}
		$('#loginForm').offset({
			top : offsetTop + 140,
			left : offsetLeft + 320
		});
		$('#loginBg').offset({
			top : offsetTop,
			left : offsetLeft
		});

		$('body').show();
	}

	showPage();
	window.onresize = showPage;

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
