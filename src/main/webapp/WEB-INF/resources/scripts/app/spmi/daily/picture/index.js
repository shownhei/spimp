define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	/**
	 * 重置页面状态
	 */
	function picture(path, j,id) {
		var htmlP = "";
		if (path !== '') {
			htmlP += "<li>";
			htmlP += "<div class='span12 imgFrame'  style='position:relative'>";
			htmlP +="<input type='text' value="+id+" style='display:none'>";
			htmlP += "<img  src=" + path + " width='100%' class='img-polaroid' >";
			htmlP += "<div class='imgContent' style='position:absolute;left:47px;bottom:5px;'>";
			htmlP += "<input name='view' type='button' value='查看' onclick='show(this)' class='btn btn-mini'/>&nbsp;&nbsp;";
			htmlP += "<input name='download' type='button' value='删除' onclick='deletePic(this)' class='btn btn-mini'/>";
			htmlP += "</div>";
			htmlP += "</div>";
			htmlP += "</li>";
			$("#column" + j).append(htmlP);
		} else {
			$("#column" + j).append("");
		}
	}
	function show(o){
		$("#imgfile").attr("src",$(o).parent().prev().attr("src"));
		 var tmpObj = new Image();
		 tmpObj.src = $(o).parent().prev().attr("src");
		if (tmpObj.width > 500 && tmpObj.width > tmpObj.height) {
			tmpObj.height = parseInt(500 * tmpObj.height / tmpObj.width);
			tmpObj.width = 500;
		} else if (tmpObj.height > 500 && tmpObj.width < tmpObj.height) {
			tmpObj.width = parseInt(500 * tmpObj.width / tmpObj.height);
			tmpObj.height = 500;
		}
		 tmpObj.onload=function(){
			var modalWidth = $("#detail-modal").width();
			 $("#detail-modal").width(tmpObj.width);  
				$("#imgfile").attr("style","width:"+tmpObj.width+";height:"+tmpObj.height+";");
				Utils.modal.show('detail');
		 };
	}
	function deletePic(o){
		Utils.modal.show('remove');
		$('#remove-save').click(function() {
			$.del('/spmi/daily/pictures/' + $(o).parent().prev().prev().attr("value"), function(data) {
				Utils.modal.hide('remove');
				$("#column1").html("");
				$("#column2").html("");
				$("#column3").html("");
				$("#column4").html("");
				groupId = groupTree.getSelectedNodes()[0].id;
				showPicture(groupId);
			});
		});
		
	}
	// 删除确认
	
	function showPicture(groupId) {
		$.get("/spmi/daily/pictures?groupId=" + groupId, function(data) {
			var columns = Math.ceil(data.data.result.length / 4);
			for (j = 1; j <= columns; j++) {
				for (var i = (j - 1) * 4; i < j * 4; i++) {
					var path = "";
					if (i < data.data.result.length) {
						path = data.data.result[i].attachment.filePath;
						id=data.data.result[i].id;
						picture(path, i % 4 + 1,id);
					}
				}
			}
		});
	}
	// 机构树配置
	var groupTreeSetting = {
		view : {
			selectedMulti : false,
			showTitle : false
		},
		async : {
			enable : true,
			url : contextPath + '/system/groups/1',
			type : "get",
			dataFilter : function(treeId, parentNode, responseData) {
				return responseData.data;
			}
		},
		data : {
			key : {
				children : 'groupEntities'
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode, clickFlag) {
				var groupTree = $.fn.zTree.getZTreeObj(treeId);
				var groupId = groupTree.getSelectedNodes()[0].id;
				$("#column1").html("");
				$("#column2").html("");
				$("#column3").html("");
				$("#column4").html("");
				showPicture(groupId);
				// 控制按钮状态/详细信息显示
				if (treeNode.level > 0) {
					Utils.button.enable([ 'create' ]);
				} else {
					reset();
				}
			},
			onAsyncSuccess : function(event, treeId, treeNode, msg) {
				var groupTree = $.fn.zTree.getZTreeObj(treeId);
				groupTree.expandAll(true);
			}
		}
	};

	var groupTree = $.fn.zTree.init($('#groups-tree'), groupTreeSetting);

	// 计算树和表格高度
	var treeHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 87);
	var gridHeight = $(window).height()
			- ($('.navbar').height() + $('.page-toolbar').height() + $('#group-detail').height() + 162);
	if ($(window).width() >= 768) {
		$('#groups-tree').height(treeHeight + 39);
		$('#tab-content').height(treeHeight);
	} else {
		$('#groups-tree').height(150);
		$('#tab-content').height(treeHeight - 170);
	}
	// 新建
	$('#create').click(function() {
		if (Utils.button.isDisable('create')) {
			return;
		}
		Utils.modal.reset('create');
		Utils.modal.show('create');
		$('#attachment').parent().parent().hide();
		$('#create-file-form')[0].reset();
		$('#create-file-form').show();
		Utils.modal.hide('remove');
	});
	// 保存
	$('#create-save').click(function() {
		var object = Utils.form.serialize('create');
		var attachment = {
			id : $('#attachment').attr('data-id'),
			name : object.filePath
		};
		object.attachment = attachment;
		object.groupId = groupTree.getSelectedNodes()[0].id;
		// 验证
		if (object.name === '') {
			Utils.modal.message('create', [ '图片不能为空' ]);
			return;
		}
		if ($('#attachment').val() === '') {
			Utils.modal.message('create', [ '文件不能为空' ]);
			return;
		}
		// 处理属性

		$.post(contextPath + '/spmi/daily/pictures', JSON.stringify(object), function(data) {
			if (data.success) {
				Utils.modal.hide('create');
				$("#column1").html("");
				$("#column2").html("");
				$("#column3").html("");
				$("#column4").html("");
				groupId = groupTree.getSelectedNodes()[0].id;
				showPicture(groupId);
			} else {
				Utils.modal.message('create', data.errors);
			}
		});
	});
	// 文件上传
	$('#file').bind('change', function() {
		var extArray = [".jpg", ".gif", ".jpeg", ".png", ".ico", ".bmp", ".tif"];
		var file = $('#file').val();
		var allowSubmit = false;
		if (!file) {
			return;
		}
		while (file.indexOf("\\") !== -1) {
			file = file.slice(file.indexOf("\\") + 1);
		}
		var ext = file.slice(file.indexOf(".")).toLowerCase();
		for (var i = 0; i < extArray.length; i++) {
			if (extArray[i] === ext) {
				allowSubmit = true;
				break;
			}
		}
		if (allowSubmit) {
			if ($('#file').val() !== '') {
				$('#create-file-form').submit();
				var process = new Utils.modal.showProcess('process');
				window.process = process;
			}
		} else {
			$('#file').val("");
			alert("只能上传以下格式的文件:" + (extArray.join("")) + "\n请重新选择再上传.");
		}
	});
	$('#create-file-delete').bind('click', function() {
		$('#attachment').parent().parent().hide();
		$('#create-file-form')[0].reset();
		if ($('#attachment').val()) {
			var process = new Utils.modal.showProcess('process');
			window.process = process;
			$.del('/ercs/uploaded-files/' + $('#attachment').attr('data-id'), function(data) {
				window.process.stop();
				window.process = null;
				Utils.modal.hide('remove');
			});
		}
		$('#create-file-form').show();
	});
	// 文件上传回调
	function callBack(data) {
		window.process.stop();
		window.process = null;
		if (!data.success) {
			alert("上传失败..." + data.data);
			return false;
		} else {
			var attachment = $('#attachment');
			attachment.val(data.data.filePath);
			attachment.attr('data-id', data.data.id);
			$('#create-file-form').hide();
			attachment.parent().parent().show();
			$('#create-save').removeClass('disabled');
		}
	}
	window.callBack = callBack;
	window.show=show;
	window.deletePic=deletePic;
	
});
