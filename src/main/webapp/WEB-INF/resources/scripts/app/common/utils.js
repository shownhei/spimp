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

		return $modal;
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

	utils.select.setOptionByName = function(formPrefix, selectName, optionValue) {
		$.each($('#' + formPrefix + '-form select[name="' + selectName + '"] > option'), function(key, value) {
			var option = $(value);
			option.replaceWith('<option value="' + option.attr('value') + '">' + option.text() + '</option>');
		});
		var selectOption = $('#' + formPrefix + '-form select[name="' + selectName + '"] > option[value="' + optionValue + '"]');
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

				// 目前只处理1层、2层或3层嵌套的情况
				if (propertys.length === 1) {
					utils.select.setOptionByName(prefix, $(value).attr('name'), model[propertys[0]]);
				} else if (propertys.length === 2) {
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

	utils.form.buildParams = function(formId, ignoreEmptyParam) {
		var ignoreParam = true;
		var urlParams = '';

		$.each($('#' + formId).serializeArray(), function(key, value) {
			if (ignoreEmptyParam !== undefined) {
				ignoreParam = ignoreEmptyParam;
			}

			if (ignoreParam) {
				if (value.value !== '') {
					urlParams += '&' + value.name + '=' + value.value;
				}
			} else {
				urlParams += '&' + value.name + '=' + value.value;
			}

		});
		return encodeURI(urlParams);
	};

	utils.form.groupTree=function(treewindow,_treePanel,_triggerName,aimElm){
		var me = this;
		if($('#'+treewindow).length==0){
			var _html='<div id="'+treewindow+'" class="menuContent " style="display: none; position: absolute;">';
			_html+='    <ul id="'+_treePanel+'" class="ztree" style="margin-top: 0; width: 160px;"></ul>';
			_html+='   </div>';
			$('body').append($(_html));
		}
		this.beforeClick=function(treeId, treeNode){
			return true;
		};
		this.onClick=function(e, treeId, treeNode){
			var zTree = $.fn.zTree.getZTreeObj(_treePanel),
			nodes = zTree.getSelectedNodes();
			var cityObj = $("#"+aimElm);
			cityObj.val(nodes[0].name);
			cityObj.attr("data-id", nodes[0].id);
		};
		this.setting={
				view: {
					dblClickExpand: false
				},
				async : {
					enable : true,
					url : contextPath + '/system/groups',
					type : "get",
					dataFilter : function(treeId, parentNode, responseData) {
						return responseData.data[0].groupEntities;
					}
				},
				data : {
					key : {
						children : 'groupEntities'
					}
				},
				callback: {
					beforeClick: me.beforeClick,
					onClick: me.onClick
				}
		};
		
		this.onKeyDown=function(){
			if (!(event.target.id == "menuBtn" || event.target.id == treewindow || $(event.target).parents("#"+treewindow).length>0)) {
				me.hideTree();
			}
		};
		this.showTree=function(){
			var cityObj = $("#"+aimElm);
			var cityOffset = cityObj.offset();
			$("#"+treewindow).css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("#"+treewindow).css('z-index',1090);
			$("#"+treewindow).css("background-color",'white');
			$("#"+treewindow).css("-webkit-box-shadow",'0 3px 7px rgba(0, 0, 0, 0.3)');
			$("#"+treewindow).css("border",'1px solid rgba(0, 0, 0, 0.3)');
			$("body").bind("mousedown", me.onKeyDown);
		};
		this.hideTree=function(){
			$("#"+treewindow).fadeOut("fast");
			$("body").unbind("mousedown", me.onKeyDown);
		};
		var currentTree = $.fn.zTree.init($('#'+_treePanel), me.setting);
		$('#'+_triggerName).bind('click',function(){
			me.showTree();
		});
		return currentTree;
	};
	/**
	 * 日期控件
	 */
	utils.input = {};

	utils.input.date = function(selector, format) {
		$(selector).datepicker({
			autoclose : true,
			format : format === undefined ? 'yyyy-mm-dd' : format
		}).next().on('click', function() {
			$(this).prev().focus();
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
