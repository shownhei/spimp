define(function(require, exports, module) {
	var $ = require('$');

	$('button[class*=btn-app]').click(function() {
		var isMin = $('#sidebar').hasClass('menu-min');
		var subMenu = $('a[href="/' + $(this).attr('id') + '"]').next().get(0);

		if (subMenu === undefined) {
			return;
		}

		if (!$(subMenu).is(':visible')) {
			var menu = $(subMenu.parentNode).closest('ul');
			if (isMin && menu.hasClass('nav-list')) {
				return;
			}
			menu.find('> .open > .submenu').each(function() {
				$(this).slideUp(200).parent().removeClass('open');
			});
		}

		if (isMin && $(subMenu.parentNode.parentNode).hasClass('nav-list')) {
			return;
		}

		$(subMenu).slideToggle(200).parent().toggleClass('open');
	});
});
