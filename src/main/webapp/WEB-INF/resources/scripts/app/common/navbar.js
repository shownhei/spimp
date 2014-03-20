define(function(require, exports, module) {
	var $ = require('kjquery'), Handlebars = require('handlebars'), Utils = require('../common/utils');

	window.$ = $;

	$('#settings').click(function() {
		Utils.modal.reset('settings');
		Utils.modal.show('settings');
	});

	$('#account-settings').click(function() {
		Utils.modal.reset('account-settings');
		Utils.modal.show('account-settings');
	});

	// 保存
	$('#account-settings-save').click(function() {
		var object = Utils.form.serialize('account-settings');

		// 验证
		if (object.oldPassword === '') {
			Utils.modal.message('account-settings', [ '请输入当前密码' ]);
			return;
		}
		if (object.newPassword1 === '') {
			Utils.modal.message('account-settings', [ '请输入新密码' ]);
			return;
		}
		if (object.newPassword2 === '') {
			Utils.modal.message('account-settings', [ '请输入确认密码' ]);
			return;
		}
		if (object.newPassword1 !== object.newPassword2) {
			Utils.modal.message('account-settings', [ '两次输入的密码不一致' ]);
			return;
		}

		$.put(contextPath + '/system/accounts/change?newPassword=' + object.newPassword1 + '&oldPassword=' + object.oldPassword, function(data) {
			if (data.success) {
				Utils.modal.hide('account-settings');
			} else {
				Utils.modal.message('account-settings', data.errors);
			}
		});
	});

	$('#logout').click(function() {
		Utils.modal.show('logout');
	});

	$('#logout-save').click(function() {
		window.location = contextPath + '/logout';
	});

	// 报警同步
	var showResult = function(len, operation) {
		var bell = $("#header_bell");
		bell.removeClass('icon-animated-bell');
		bell.addClass('icon-animated-bell');
		if (operation === 'start') {
			bell.css("-webkit-animation", "ringing 2.0s 10000 ease 1.0s");
		} else {
			bell.css("-webkit-animation", null);
		}
		bell.parent().find('span').html(len);
		$('.purple').find('li.nav-header').html(len + "个提醒");
	};
	var idarray = [];
	function asynGetAlarm() {
		$.ajax({
			type : 'GET',
			url : '/ercs/alarm/waitalarmtop',
			cache : false,
			dataType : "json",
			data : 'idArray=' + idarray.join(','),
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				var newIdArray = data.alarmList;
				for (var i = 0; i < newIdArray.length; i++) {
					if (idarray.indexOf(newIdArray[i]) === -1) {
						idarray.push(newIdArray[i]);
					}
				}
				showResult(idarray.length, 'start');
				asynGetAlarm();
			},
			error : function(data, textStatus) {
				showResult('0', 'end');
				idarray = [];
				asynGetAlarm();
			}
		});
	}
	// 邮件同步
	function asynGetTask() {
		$.ajax({
			type : 'GET',
			url : '/ercs/emergency-plan-instances',
			cache : false,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				var bell = $("#header_mail");
				bell.removeClass('icon-animated-vertical');
				bell.addClass('icon-animated-vertical');
				bell.css("-webkit-animation", "vertical 2.0s 10000 ease 2.0s");
				bell.parent().find('span').html(data.data.result.length);
			},
			error : function(data, textStatus) {
				if (textStatus !== 'error') {
					asynGetTask();
				}
			}
		});
	}
	// asynGetAlarm();
	// asynGetTask();

	// 根据分类获取图标
	function getIcon(category) {
		var clszz;
		switch (category) {
			case '工作安排':
				clszz = 'icon-check';
				break;
			case '报警':
				clszz = 'icon-exclamation-sign';
				break;
			default:
				clszz = 'icon-comment';
				break;
		}

		return '<i class="' + clszz + '" style="font-size:14px;margin:0 5px"></i> ';
	}

	// 显示提醒
	function displayNotification(data) {
		var notificationsArray = [];
		var notificationsCount = 0;
		var messageArray = [];
		var messageCount = 0;
		$.each(data.messages, function(key, val) {
			if (val.messageType === 1) {
				notificationsArray.push(val);
				notificationsCount += val.count;
			} else if (val.messageType === 2) {
				messageArray.push(val);
				messageCount += val.count;
			}
		});

		// notifications
		$('#notifications').children('li:not(:first)').remove();
		$('#notifications-count').html(notificationsCount);
		$('#notifications-title').html(notificationsCount + '个提醒');

		if (notificationsCount > 0) {
			$("#header_bell").addClass('icon-animated-bell');
		} else {
			$("#header_bell").removeClass('icon-animated-bell');
		}

		var html = '';
		$.each(notificationsArray, function(k, v) {
			html += '<li><a href="' + v.link + '">';
			html += '<div class="clearfix"><span class="pull-left">' + getIcon(v.category) + v.message;
			html += '</span><span class="pull-right badge badge-info">' + v.count + '</span></div></a></li><li style="display:none"></li>';
		});
		$('#notifications').append(html);

		// ====message
		$('#messages').children('li:not(:first)').remove();
		$('#messages-count').html(messageCount);
		$('#messages-title').html(messageCount + '个消息');

		if (messageCount > 0) {
			$("#header_mail").addClass('icon-animated-bell');
		} else {
			$("#header_mail").removeClass('icon-animated-bell');
		}

		html = '';
		$.each(messageArray, function(k, v) {
			html += '<li><a href="' + v.link + '">';
			html += '<div class="clearfix"><span class="pull-left">' + getIcon(v.category) + v.message;
			html += '</span><span class="pull-right badge badge-info">' + v.count + '</span></div></a></li><li style="display:none"></li>';
		});
		$('#messages').append(html);
	}

	// 推送提醒
	var errorCount = 0;
	function pushNotification() {
		$.ajax({
			type : 'GET',
			url : contextPath + '/reminder/notification/push',
			cache : false,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				displayNotification(data);
				pushNotification();
			},
			error : function(jqXHR, textStatus) {
				errorCount++;
				if (errorCount < 48) { // 只重试48次，每次超时1小时。
					pushNotification();
				}
			}
		});
	}
	// 拉取提醒
	function pullNotification() {
		$.get(contextPath + '/reminder/notification/pull', function(data) {
			displayNotification(data);
		});
	}

	pullNotification();
	pushNotification();
});
