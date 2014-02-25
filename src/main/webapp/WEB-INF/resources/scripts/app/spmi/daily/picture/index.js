define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');
	var fileID, pictureID;
	// 提示信息
	$('button[title]').tooltip({
		placement : 'bottom'
	});

	/**
	 * 重置页面状态
	 */
	function picture(path, j, id, fileId, name) {
		var htmlP = "";
		if (path !== '') {
			htmlP += "<li>";
			htmlP += "<div class='span12 imgFrame'  style='position:relative'>";
			// ID是机构ID，fileID是附件ID
			htmlP += "<input type='text' value=" + id + " data-id=" + id + " style='display:none'>";
			// 附件路径和名称
			htmlP += "<img  src=" + path + " width='100%' data-id=" + name + " class='img-polaroid' >";
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
	function show(o) {
		$("#imgfile").attr("src", $(o).parent().prev().attr("src"));
		var tmpObj = new Image();
		tmpObj.src = $(o).parent().prev().attr("src");
		if (tmpObj.width > 500 && tmpObj.width > tmpObj.height) {
			tmpObj.height = parseInt(500 * tmpObj.height / tmpObj.width, 10);
			tmpObj.width = 500;
		} else if (tmpObj.height > 500 && tmpObj.width < tmpObj.height) {
			tmpObj.width = parseInt(500 * tmpObj.width / tmpObj.height, 10);
			tmpObj.height = 500;
		}
		tmpObj.onload = function() {
			var modalWidth = $("#detail-modal").width();
			$("#detail-modal").width(tmpObj.width);
			$("#imgfile").attr("style", "width:" + tmpObj.width + ";height:" + tmpObj.height + ";");
			$("#check").html($(o).parent().prev().attr('data-id'));
			Utils.modal.show('detail');
		};
	}
	function deletePic(o) {
		Utils.modal.show('remove');
		fileID = $(o).parent().prev().prev().attr('data-id');
		pictureID = $(o).parent().prev().prev().attr("value");

	}

	// 删除确认
	$('#remove-save').click(function() {
		$.del('/spmi/daily/pictures/' + pictureID, function(data) {
			if (data.success === true) {
				$.del('/ercs/uploaded-files/' + fileID, function(data) {
					groupId = groupTree.getSelectedNodes()[0].id;
					Utils.modal.hide('remove');
					$("#column1").html("");
					$("#column2").html("");
					$("#column3").html("");
					$("#column4").html("");
					showPicture(groupId);
				});
			}
		});
	});
	function showPicture(groupId) {
		$.get("/spmi/daily/pictures?groupId=" + groupId, function(data) {
			var columns = Math.ceil(data.data.result.length / 4);
			for (j = 1; j <= columns; j++) {
				for (var i = (j - 1) * 4; i < j * 4; i++) {
					var path = "";
					if (i < data.data.result.length) {
						path = data.data.result[i].attachment;
						id = data.data.result[i].id;
						name = data.data.result[i].name;
						picture(path, i % 4 + 1, id, null, name);
					}
				}
			}
		});
	}

	/**
	 * 处理图标路径
	 */
	function handleIcon(childrens) {
		if (childrens) {
			$.each(childrens, function(index, item) {
				if (item.folders.length > 0) {
					handleIcon(item.folders);
				} else {
					item.icon = resources + '/images/icons/folder.png';
				}
			});
		}
	}
	// 机构树配置
	var groupTreeSetting = {
		view : {
			selectedMulti : false,
			showTitle : false
		},
		async : {
			enable : true,
			url : contextPath + '/spmi/daily/folders/1',
			type : "get",
			dataFilter : function(treeId, parentNode, responseData) {
				handleIcon(responseData.data.folders);
				return responseData.data;
			}
		},
		data : {
			key : {
				children : 'folders'
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
				if (treeNode.level >= 0) {
					Utils.button.enable([ 'new' ]);
				}
				// 控制按钮状态/详细信息显示
				if (treeNode.level > 0) {
					Utils.button.enable([ 'new', 'edit', 'create' ]);
					$("#nav-search-button").removeClass("disabled");
					$.get(contextPath + '/spmi/daily/pictures/' + treeNode.id + '/count', function(count) {
						if (treeNode.folders.length === 0 && count.data === 0) {
							Utils.button.enable([ 'delete' ]);
						} else {
							Utils.button.disable([ 'delete' ]);
						}

					});
				} else {
					Utils.button.disable([ 'create' ]);
					$("#nav-search-button").addClass("disabled");
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
		object.groupId = groupTree.getSelectedNodes()[0].id;
		console.log(object);
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
		var extArray = [ ".jpg", ".gif", ".jpeg", ".png", ".ico", ".bmp", ".tif" ];
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

	$('#new').click(function() {
		if (Utils.button.isDisable('new')) {
			return;
		}

		Utils.modal.reset('new');
		Utils.modal.show('new');
	});

	// 保存
	$('#new-save').click(function() {
		var object = Utils.form.serialize('new');

		// 验证
		if (object.name === '') {
			Utils.modal.message('new', [ '文件名' ]);
			return;
		}

		// 处理属性
		object.parentId = groupTree.getSelectedNodes()[0].id;
		$.post(contextPath + '/spmi/daily/folders', JSON.stringify(object), function(data) {
			if (data.success) {
				groupTree.reAsyncChildNodes(null, "refresh");
				Utils.modal.hide('new');
			} else {
				Utils.modal.message('new', data.errors);
			}
		});
	});

	// 编辑
	$('#edit').click(function() {
		if (Utils.button.isDisable('edit')) {
			return;
		}

		Utils.modal.reset('edit');

		var selectId = groupTree.getSelectedNodes()[0].id;
		$.get(contextPath + '/spmi/daily/folders/' + selectId, function(data) {
			var object = data.data;

			object.category = object.category + '-' + object.queryLabel;

			Utils.form.fill('edit', object);
			Utils.modal.show('edit');
		});
	});

	// 更新
	$('#edit-save').click(function() {
		var object = Utils.form.serialize('edit');

		// 验证
		if (object.name === '') {
			Utils.modal.message('edit', [ '请输入文件名' ]);
			return;
		}

		// 处理属性
		var selectId = groupTree.getSelectedNodes()[0].id;
		object.id = selectId;

		$.put(contextPath + '/spmi/daily/folders/' + selectId, JSON.stringify(object), function(data) {
			if (data.success) {
				groupTree.reAsyncChildNodes(null, "refresh");
				Utils.modal.hide('edit');
				reset();
			} else {
				Utils.modal.message('edit', data.errors);
			}
		});
	});

	// 删除
	$('#delete').click(function() {
		if (Utils.button.isDisable('delete')) {
			return;
		}

		Utils.modal.show('delete');
	});

	// 删除确认
	$('#delete-save').click(function() {
		var selectId = groupTree.getSelectedNodes()[0].id;
		$.del(contextPath + '/spmi/daily/folders/' + selectId, function(data) {
			groupTree.reAsyncChildNodes(null, "refresh");
			Utils.modal.hide('delete');
		});
	});
	// 搜索
	$('#nav-search-button').click(function() {
		var selectId = groupTree.getSelectedNodes()[0].id;
		$.get("/spmi/daily/pictures?groupId=" + selectId + Utils.form.buildParams('search-form'), function(data) {
			var columns = Math.ceil(data.data.result.length / 4);
			$("#column1").html("");
			$("#column2").html("");
			$("#column3").html("");
			$("#column4").html("");
			for (j = 1; j <= columns; j++) {
				for (var i = (j - 1) * 4; i < j * 4; i++) {
					var path = "";
					if (i < data.data.result.length) {
						path = data.data.result[i].attachment.filePath;
						fileId = data.data.result[i].attachment.id;
						id = data.data.result[i].id;
						name = data.data.result[i].name;
						picture(path, i % 4 + 1, id, fileId, name);
					}
				}
			}
		});
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
			attachment.val(data.data);
			$('#create-file-form').hide();
			attachment.parent().parent().show();
			$('#create-save').removeClass('disabled');
			$('#create-save').trigger('click');
			
		}
	}
	window.callBack = callBack;
	window.show = show;
	window.deletePic = deletePic;

});
