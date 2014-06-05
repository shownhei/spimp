define(function(require, exports, module) {
	var $ = require('kjquery');
	var Utils = require('${resources}/scripts/app/common/utils');

	function startVideoPreview(videoObject, option) {
		videoObject.Channel = 1; // 0: 主码流; 1: 子码流
		videoObject.ocxid = 1000;
		videoObject.Language = 2; // 2: 中文; 默认英文
		videoObject.Url = option.IP + ':' + option.PORT;
		videoObject.User = option.USERNAME || 'guest';
		videoObject.Password = option.PASSWORD || '';
		setTimeout(function() {
			videoObject.StartView();
			videoObject.VideoParamGet();
		}, 1);
	}

	function addVideo(camera) {
		var videoId = camera.ID;
		var videoObject = document.getElementById(videoId);
		var scriptElement = document.createElement('script');
		var head = document.getElementsByTagName('head')[0];
		startVideoPreview(videoObject, camera);
		scriptElement.setAttribute('language', 'JavaScript');
		scriptElement.setAttribute('event', 'EventNotify(ocxid, type, param)');
		scriptElement.setAttribute('for', videoId);
		scriptElement.text = 'changeChannel("' + videoId + '");';
		head.appendChild(scriptElement);
	}

	function resizeVideos(blockId) {
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

	function resizeBlocks(blockId) {
		var $block;
		var $window = $(window);
		var $windowHeight = $window.height();
		var $windowWidth = $window.width();
		if (blockId) {
			$block = $('#' + blockId);
			if ($block.attr('resize-state') === 'full') { // Block is full
				// windowed.
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

	function toggleFullWindow(event) {
		var windowWidth = $(window).width();
		var windowHeight = $(window).height();
		var block, blockId, reference, row, $block, $button;
		if (event.target.tagName === 'I') { // icon
			block = event.target.parentNode.parentNode;
		} else {
			// DIV
			block = event.target.parentNode;
		}
		blockId = block.id;
		$block = $(block);
		if ($block.attr('resize-state') !== 'full') { // Small size
			window['full-window'] = blockId;
			// Save block attributes for later use.
			$block.attr('resize-state', 'full');
			$block.attr('orig-height', $block.height());
			// Move the block to the first child node of the body element.
			reference = $('.main-container')[0];
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
			row = $('#row' + block.id.charAt(3))[0];
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

	function initBlocks() {
		resizeBlocks();
		$(window).resize(function() {
			resizeBlocks();
		});
		$('.complex-button-resize').click(toggleFullWindow);
		$('.widget-header').dblclick(toggleFullWindow);
		resizeVideos('col0_0');
	}

	// 人机环区域切换
	$('#renJiHuanArea').change(function() {
		var $area = $('#renJiHuanArea');
		if ($area.val()) {
			var param = JSON.stringify({
				'NAME' : $area.val(),
				'JSNAME' : 'rjhProcess'
			});
			external.RequireAreaRJH(param);
		}
	});

	window.rjhProcess = function(data) {
		alert('rjhProcess');
		if (!data || data === 'null') {alert('null');
			$('#renJiHuanInfo').html('');
			Utils.modal.showAlert("没有可显示的数据。", "提示");
			return;
		}
		var url = '/location/location-areas/rjhcommand?time=' + (new Date()).getTime();
		var cameras = $.parseJSON(data).CAMERA;
		alert('ajax');
		$.ajax({
			type : 'post',
			dataType : 'text',
			data : 'rjhParam=' + encodeURI(data),
			url : url,
			success : function(data) {
				alert('ajax success');
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

	window.changeChannel = function(objectId) {
		var videoObject = document.getElementById(objectId);
		videoObject.Channel = (videoObject.Channel + 1) % 2;
		videoObject.StopView();
		videoObject.StartView();
	};
	$(document).click(function(event) {
		var docId = $(event.target).attr('data-id');
		var swf = $(event.target).attr('data-swf');
		if (swf) {
			$('#showDocument').attr('src', '/ignore/ercs/view-pdf/' + docId + "?t=" + new Date().getTime());
			Utils.modal.show('view');
		}
	});
});
