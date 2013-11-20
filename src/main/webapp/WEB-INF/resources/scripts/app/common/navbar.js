define(function(require, exports, module) {
	var $ = require('kjquery'), Handlebars = require('handlebars'), Utils = require('../common/utils');

	window.$=$;
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
	//报警同步
	var showResult=function(len,operation){
		var bell=$("#header_bell");
		bell.removeClass('icon-animated-bell');
		bell.addClass('icon-animated-bell');
		if(operation==='start'){
			bell.css("-webkit-animation","ringing 2.0s 10000 ease 1.0s");
		}else{
			bell.css("-webkit-animation",null);
		}
		bell.parent().find('span').html(len);
		$('.purple').find('li.nav-header').html(len+"个报警");
	};
	var idarray=[];
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
					if(idarray.indexOf(newIdArray[i])===-1){
						idarray.push(newIdArray[i]);
					}
				}
				showResult(idarray.length,'start');
				asynGetAlarm();
			},
			error : function(data, textStatus) {
				showResult('0','end');
				idarray=[];
				asynGetAlarm();
			}
		});
	}
	//邮件同步
	function asynGetTask() {
		$.ajax({
			type : 'GET',
			url : '/ercs/emergency-plan-instances',
			cache : false,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				var bell=$("#header_mail");
				bell.removeClass('icon-animated-vertical');
				bell.addClass('icon-animated-vertical');
				bell.css("-webkit-animation","vertical 2.0s 10000 ease 2.0s");
				bell.parent().find('span').html(data.data.result.length);
			},
			error : function(data, textStatus) {
				if (textStatus !== 'error') {
					asynGetTask();
				}
			}
		});
	}
	asynGetAlarm();
	asynGetTask();
});
