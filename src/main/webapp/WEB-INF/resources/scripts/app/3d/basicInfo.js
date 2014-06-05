define(function(require, exports, module) {
	var $ = require('kjquery');
	var Utils = require('../common/utils');
	var operation = {};
	operation.inoEdit = function() {
		KindEditor.basePath = resources+'/scripts/kindeditor/';
		window.keditor = KindEditor.create('textarea[name="content"]', {
			items : [ 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'removeformat', '|', 'justifyleft',
					'justifycenter', 'justifyright', 'insertorderedlist', 'insertunorderedlist','table', '|', 'emoticons', 'image', 'link' ]
		});
		$.ajax({
			type : 'get',
			dataType : 'text',
			url : '/ignore/document/basic-infomations',
			success : function(data) {
				$('#displayPanel').hide();
				$('#editPanel').show();
				$('#temp').html(data);
				keditor.html(data);
				Utils.button.enable([ 'save' ]);
				Utils.button.disable([ 'edit' ]);
			}
		});
	};
	operation.saveEdit = function() {
		var contentData = keditor.html();

		$.ajax({
			type : 'post',
			data : contentData,
			url : '/ignore/document/basic-infomations',
			success : function(datas) {
				Utils.button.enable([ 'edit' ]);
				Utils.button.disable([ 'save' ]);
				Utils.modal.showAlert("保存成功!", "提示");
			}
		});
	};
	operation.intoView = function() {
		$('#editPanel').hide();
		$('#displayPanel').show();
		Utils.button.disable([ 'save' ]);
	};

	$('#edit').click(function() {
		operation.inoEdit();
	});
	$('#save').click(function() {
		operation.saveEdit();
	});
});