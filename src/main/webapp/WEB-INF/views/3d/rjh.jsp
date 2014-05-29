<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="resources" value="${pageContext.request.contextPath}/static-resources" />
<!DOCTYPE html>
<html>
<head>
<title>三维综合管理 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="head.jsp"%>
<link href="${resources}/styles/complex.css" rel="stylesheet">
</head>
<body style="overflow: hidden;">
	<div class="main-container container-fluid">
		<div class="main-content" style="margin-left: 0;">
			<div class="page-toolbar">
				<div class="nav-search">
					<form id="query-form" class="form-inline" onsubmit="return false;">
						<div style="display: block; margin-bottom: 5px;">
							<div class="input-append">
								<select id="renJiHuanArea" style="height: 25px; width: 150px; font-size: 12px;">
									<option value="">请选择区域</option>
								<c:forEach items="${areas}" var="area" varStatus="status">
									<option value="${area}">${area}</option>
								</c:forEach>
								</select>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div id="renJiHuanInfo"></div>
			</div>
		</div>
	</div>
	<div id="view-modal" class="modal modal-xl hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-th-list"></i> 查看
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<iframe id="showDocument" src="" width="100%" height=355 border=0 margin=0 frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 关闭
			</button>
		</div>
	</div>
	<script type="text/javascript">
	define(function(require, exports, module) {
		var $ = require('kjquery');
		var Utils = require('${resources}/scripts/app/common/utils');
		
		function startVideoPreview (videoObject, option) {
			videoObject.Channel = 1; //0: 主码流; 1: 子码流
			videoObject.ocxid = 1000;
			videoObject.Language = 2; //2: 中文; 默认英文
			videoObject.Url = option.IP + ':' + option.PORT;
			videoObject.User = option.USERNAME || 'guest';
			videoObject.Password = option.PASSWORD || '';
			setTimeout(function () {
				videoObject.StartView();
				videoObject.VideoParamGet();
			}, 1);
		}
		
		function addVideo (camera) {
			var videoId = camera.ID;
			var videoObject = document.getElementById(videoId);
			var scriptElement = document.createElement('script');
			startVideoPreview(videoObject, camera);
			scriptElement.setAttribute('language', 'JavaScript');
			scriptElement.setAttribute('event', 'EventNotify(ocxid, type, param)');
			scriptElement.setAttribute('for', videoId);
			scriptElement.innerHTML = 'changeChannel("' + videoId + '");';
			document.head.appendChild(scriptElement);
		}
		
		function resizeVideos (blockId) {
			var $block = $('#' + blockId); // Id of recently changed block.
			var videoWidth, videoHeight;
			
			if ($block.attr('resize-state') !== 'full') { // Original
				videoWidth = Math.floor(($('.widget-main', $block).width() - 40) / 2);
				videoHeight = Math.floor(videoWidth * 3 / 4);
			} else { // Full window
				if (blockId === 'col0_0') { // Video block
					videoWidth = Math.floor(($(window).width() - 80) / 3);
					videoHeight = Math.floor(videoWidth * 3 / 4);
				} else { // Other block
					videoWidth = 0;
					videoHeight = 0;
				}
			}
			$('object').width(videoWidth);
			$('object').height(videoHeight);
		}
		
		function resizeBlocks (blockId) {
			var $block;
			var $window = $(window);
			var $windowHeight = $window.height();
			var $windowWidth = $window.width();
			if (blockId) {
				$block = $('#' + blockId);
				if ($block.attr('resize-state') === 'full') { // Block is full windowed.
					$('.widget-body', $block).height('100%');
					$('.widget-main', $block).height($windowHeight - 50);
				} else { // Block restored to original size.
					$('.widget-body', $block).removeAttr('style');
					$('.widget-main').height(($windowHeight - 250) / 2);
				}
			} else {
				if (window['full-window']) {
					$block = $('#' + window['full-window']);
					$('.widget-main', $block).height($windowHeight - 50);
					$block.width($windowWidth);
				} else {
					$('.widget-main').height(($windowHeight - 250) / 2);
				}
			}
			$('#col2_0').height($windowHeight - 130);
		}
		
		function toggleFullWindow (event) {
			var windowWidth = $(window).width();
			var windowHeight = $(window).height();
			var block, blockId, reference, row, $block, $button;
			if (event.target.tagName === 'I') // icon
				block = event.target.parentNode.parentNode;
			else // DIV
				block = event.target.parentNode;
			blockId = block.id;
			$block = $(block);
			if ($block.attr('resize-state') !== 'full') { // Small size
				window['full-window'] = blockId;
				// Save block attributes for later use.
				$block.attr('resize-state', 'full');
				$block.attr('orig-height', $block.height());
				// Move the block to the first child node of the body element.
				reference = document.querySelector('.main-container');
				document.body.insertBefore(block, reference);
				// Enlarge the block.
				$block.css('position', 'absolute');
				$block.css('z-index', 1000);
				$block.css('margin-left', 0);
				$block.width(windowWidth);
				$block.height(windowHeight);
			} else { // Full size
				// Restore block attributes.
				$block.removeAttr('resize-state');
				delete window['full-window'];
				$block.removeAttr('width');
				$block.removeAttr('height');
				$block.removeAttr('style');
				// Move the block to the original place.
				// Row id looks like 'row0', block id looks like 'col0_0'.
				row = document.querySelector('#row' + block.id.charAt(3));
				if (block.id.charAt(5) === '0') { // First column of the row.
					reference = row.firstChild;
					row.insertBefore(block, reference);
				} else { // Second column of the row.
					row.appendChild(block);
				}
			}
			resizeBlocks(blockId);
			// Change button icon.
			$button = $('.complex-button-resize', $block);
			$button.toggleClass('icon-resize-full');
			$button.toggleClass('icon-resize-small');
			// Resize videos according to block type and state.
			resizeVideos(block.id);
		}
		
		function initBlocks () {
			resizeBlocks();
			$(window).resize(function() {
				resizeBlocks();
			});
			$('.complex-button-resize').click(toggleFullWindow);
			$('.widget-header').dblclick(toggleFullWindow);
			resizeVideos('col0_0');
		}
		
		//人机环区域切换
		$('#renJiHuanArea').change(function() {
			var $area = $('#renJiHuanArea');
			if ($area.val()) {
				var param = JSON.stringify({
					'NAME': $area.val(),
					'JSNAME': 'rjhProcess'
				});
				external.RequireAreaRJH(param);
			}
		});
		
		window.rjhProcess = function(data){
			if(!data || data === 'null') {
				$('#renJiHuanInfo').html('');
				Utils.modal.showAlert("没有可显示的数据。","提示");
				return;
			}
			var url='/location/location-areas/rjhcommand?time=' + (new Date()).getTime();
			var cameras = $.parseJSON(data).CAMERA;
			$.ajax({
				type: 'post',
				dataType: 'text',
				data: 'rjhParam=' + encodeURI(data),
				url: url,
				success: function(data) {
					$('#renJiHuanInfo').html(data);
					initBlocks();
					if (cameras) {
						for (var i = 0; i < cameras.length; i++) {
							addVideo(cameras[i]);
						}
					}
				}
			});
		};
		
		window.changeChannel = function (objectId) {
			var videoObject = document.getElementById(objectId);
			videoObject.Channel = (videoObject.Channel + 1) % 2;
			videoObject.StopView();
			videoObject.StartView();
		};
		$(document).click(function(event) {
			var docId = $(event.target).attr('data-id');
			var swf= $(event.target).attr('data-swf');
			if (swf) {
				$('#showDocument').attr('src', '/ignore/ercs/view-pdf/' + docId + "?t=" + new Date().getTime());
				Utils.modal.show('view');
			}
		});
	});
	</script>
</body>
</html>