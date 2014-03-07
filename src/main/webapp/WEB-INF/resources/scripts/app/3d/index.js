define(function(require, exports, module) {
	var $ = require('kjquery');

	$('#layer-control-button').click(function() {
		$('#layer-control-div').toggleClass('open');
		$('#layer-control-icon').toggleClass('icon-chevron-right').toggleClass('icon-chevron-left');
	});

	function resize() {
		var tabHeight = $(window).height() - 170;
		$('div[class*="tab-pane"]').filter('div[data-level="first"]').css({
			'max-height' : tabHeight + 'px',
			'height' : tabHeight + 'px'
		});
		$('#map-image').height($(window).height() - 86);
	}
	resize();
	window.onresize = resize;

	/**
	 * 处理图标路径
	 */
	function handleIcon(childrens) {
		if (childrens) {
			$.each(childrens, function(index, item) {
				if (item.groupEntities) {
					handleIcon(item.groupEntities);
				}
				switch (item.queryLabel) {
					case '1':
						item.icon = resources + '/images/icons/building.png';
						break;
					case '2':
						item.icon = resources + '/images/icons/monitor.png';
						break;
					case '3':
						item.icon = resources + '/images/icons/plugin_disabled.png';
						break;
					case '4':
						item.icon = resources + '/images/icons/house.png';
						break;
					default:
						item.icon = resources + '/images/icons/page_white.png';
						break;
				}
			});
		}
	}

	// 机构树配置
	var layerTreeSetting = {
		view : {
			selectedMulti : false,
			showTitle : false
		},
		async : {
			enable : true,
			url : resources + '/datas/layer-data.json',
			type : "get",
			dataFilter : function(treeId, parentNode, responseData) {
				responseData.data.iconOpen = resources + "/images/icons/chart_organisation.png";
				responseData.data.iconClose = resources + "/images/icons/chart_organisation.png";

				handleIcon(responseData.data.groupEntities);

				return responseData.data;
			}
		},
		check : {
			enable : true
		},
		data : {
			key : {
				children : 'groupEntities'
			}
		},
		callback : {
			onClick : function(event, treeId, treeNode, clickFlag) {
				var groupTree = $.fn.zTree.getZTreeObj(treeId);
			},
			onAsyncSuccess : function(event, treeId, treeNode, msg) {
				var groupTree = $.fn.zTree.getZTreeObj(treeId);
				groupTree.expandAll(true);
			}
		}
	};

	var layerTree = $.fn.zTree.init($('#layer-tree'), layerTreeSetting);

	$('button[data-image],a[data-image]').click(function() {
		if ($(this).data('type') !== undefined) {
			switch ($(this).data('type')) {
				case 'fullscreen':
					$('#layer-tab').trigger('click');
					$('#map-image').attr('src', resources + '/images/3d/capture/' + $(this).data('image'));

					$('#layer-control-div').removeClass('open');
					$('#layer-control-icon').removeClass('icon-chevron-right').addClass('icon-chevron-left');
					$('#sidebar').removeClass('fixed');
					$('body').removeClass('navbar-fixed');
					$('.navbar-fixed-top').hide();
					$('#map-image').height($(window).height() - 45);
					break;
				case 'main':
					$('#layer-tab').trigger('click');
					$('#map-image').attr('src', resources + '/images/3d/capture/' + $(this).data('image'));

					$('#layer-control-div').addClass('open');
					$('#layer-control-icon').addClass('icon-chevron-right').removeClass('icon-chevron-left');
					$('#sidebar').addClass('fixed');
					$('body').addClass('navbar-fixed');
					$('.navbar-fixed-top').show();
					resize();
					break;
				case 'zoom-in':
					$('#layer-tab').trigger('click');
					var pic1 = $(this).data('image').split(',')[0];
					var pic2 = $(this).data('image').split(',')[1];

					$('#map-image').attr('src', resources + '/images/3d/capture/' + pic1);
					setTimeout(function() {
						$('#map-image').attr('src', resources + '/images/3d/capture/' + pic2);
					}, 800);
					break;
				case 'switch':
					pic1 = $(this).data('image').split(',')[0];
					pic2 = $(this).data('image').split(',')[1];

					setInterval(function() {
						$('#map-image').attr('src', resources + '/images/3d/capture/' + pic1);
					}, 500);
					setInterval(function() {
						$('#map-image').attr('src', resources + '/images/3d/capture/' + pic2);
					}, 600);
					break;
				case 'info':
					$('#info-tab').trigger('click');
					$('#object').html('<img src="' + resources + '/images/3d/capture/信息统计-右侧属性栏.jpg" style="width: 100%">');
					break;
				default:
					break;
			}
		} else {
			$('#layer-tab').trigger('click');
			$('#map-image').attr('src', resources + '/images/3d/capture/' + $(this).data('image'));
		}
	});
});
