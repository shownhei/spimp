define(function(require, exports, module) {
	var $ = require('kjquery'), Handlebars = require('handlebars'), Utils = require('../common/utils');

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
});
