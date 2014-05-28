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
	<script type="text/javascript">
		define(function(require, exports, module) {
			var $ = require('kjquery');
			var Utils = require('${resources}/scripts/app/common/utils');
			
			function startVideoPreview (videoObject, option) {
				videoObject.Channel = 1; //0: 主码流; 1: 子码流
				videoObject.ocxid = 1000;
				videoObject.Language = 2;	//2: 中文; 默认英文
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
			
			function resizeVideo (blockId) {
				if (blockId !== 'col1_1') {
					$('object').width(0);
					$('object').height(0);
				}
			}
			
			function toggleBlockFullWindow (event) {
				var windowWidth = $(window).width();
				var windowHeight = $(window).height();
				var block, newBlock, content, $block, $newBlock;
				if (event.target.tagName === 'I') // icon
					block = event.target.parentNode.parentNode;
				else // DIV
					block = event.target.parentNode;
				$block = $(block);
				if ($block.attr('resize-state') !== 'full') { // original size
					$block.attr('orig-width', $block.width());
					$block.attr('orig-height',  $block.height());
					$block.attr('orig-margin-left', $block.css('margin-left'));
					$block.attr('orig-position', $block.css('position'));
					$block.attr('orig-z-index', $block.css('z-index'));
					$block.attr('resize-state', 'full');
					content = document.querySelector('.main-container');
					newBlock = document.body.insertBefore(block, content);
					$newBlock = $(newBlock);
					$newBlock.css('position', 'absolute');
					$newBlock.css('z-index', 1000);
					$newBlock.css('margin-left', 0);
					$newBlock.width(windowWidth);
					$newBlock.height(windowHeight);
					resizeVideo(block.id);
				}
			}
			
			function initBlocks () {
				$('.widget-main').height(($(window).height() - 250) / 2);
				$('.complex-button-resize').click(toggleBlockFullWindow);
				$('.widget-header').dblclick(toggleBlockFullWindow);
			}
			
			//人机环区域切换
			$('#renJiHuanArea').change(function() {
				var param = JSON.stringify({
					'NAME': $('#renJiHuanArea').val(),
					'JSNAME': 'rjhProcess'
				});
				external.RequireAreaRJH(param);
			});
			
			window.rjhProcess = function(data){
				if(!data || data === 'null') {
					$('#renJiHuanInfo').html('');
					Utils.modal.showAlert("没有可显示的数据。","提示");
					return;
				}
				var url='/location/location-areas/rjhcommand?time=' + new Date();
				var cameras = $.parseJSON(data).CAMERA;
				$.ajax({
					type: 'post',
					dataType: 'text',
					data: 'rjhParam=' + data,
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
		});
	</script>
</body>
</html>