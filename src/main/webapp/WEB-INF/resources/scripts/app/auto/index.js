define(function(require, exports, module) {
	var $ = require('kjquery');

	function resize() {
		var tabHeight = $(window).height() - 170;
		$('div[class*="tab-pane"]').filter('div[data-level="first"]').css({
			'max-height' : tabHeight + 'px',
			'height' : tabHeight + 'px'
		});
		$('#map-image').height($(window).height() - 45);
	}
	resize();
	window.onresize = resize;

	$('button[data-image],a[data-image]').click(function() {
		$('#map-image').attr('src', resources + '/images/auto/' + $(this).data('image'));
	});
});
