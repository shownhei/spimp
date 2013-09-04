<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<a class="menu-toggler" id="menu-toggler" href="#">
	<span class="menu-text"></span>
</a>
<div class="sidebar fixed" id="sidebar">
	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
		<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
			<button class="btn btn-small btn-success">
				<i class="icon-signal"></i>
			</button>
			<button class="btn btn-small btn-info">
				<i class="icon-pencil"></i>
			</button>
			<button class="btn btn-small btn-warning">
				<i class="icon-group"></i>
			</button>
			<button class="btn btn-small btn-danger">
				<i class="icon-cogs"></i>
			</button>
		</div>
		<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
			<span class="btn btn-success"></span>
			<span class="btn btn-info"></span>
			<span class="btn btn-warning"></span>
			<span class="btn btn-danger"></span>
		</div>
	</div>
	<ul class="nav nav-list">
	</ul>
	<div class="sidebar-collapse" id="sidebar-collapse">
		<i class="icon-double-angle-left"></i>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		var url = requestURI.replace('/WEB-INF/views', '').replace('/index.jsp', '');

		// 渲染菜单
		$.get(contextPath + '/menus', function(data) {
			var template = Handlebars.compile($('#menu-template').html());
			var html = template(data);

			$('#sidebar > ul').html(html);

			var activeMenu = $('#sidebar a[href="' + url + '"]');
			activeMenu.parent('li').addClass('active');
			var parentMenu = activeMenu.parent('li').parent('ul').parent('li');
			parentMenu.addClass('active open');
			parentMenu.parent('ul').parent('li').addClass('active open');

			handle_side_menu();
		});

		/**
		 * 菜单栏处理
		 */
		function handle_side_menu() {
			$('#menu-toggler').on('click', function() {
				$('#sidebar').toggleClass('display');
				$(this).toggleClass('display');
				return false;
			});

			var isMin = $('#sidebar').hasClass('menu-min');

			$('#sidebar-collapse').on('click', function() {
				$('#sidebar').toggleClass('menu-min');
				$(this).find('[class*="icon-"]:eq(0)').toggleClass('icon-double-angle-right');
				if (isMin) {
					$('.open > .submenu').removeClass('open');
				}
			});

			$('.nav-list').on('click', function(e) {
				var a = $(e.target).closest('a');
				if (!a || a.length === 0) {
					return;
				}
				if (!a.hasClass('dropdown-toggle')) {
					if (isMin && a.get(0).parentNode.parentNode === this) {
						var h = a.find('.menu-text').get(0);
						if (e.target !== h && !$.contains(h, e.target)) {
							return false;
						}
					}
					return;
				}
				var d = a.next().get(0);
				if (!$(d).is(':visible')) {
					var c = $(d.parentNode).closest('ul');
					if (isMin && c.hasClass('nav-list')) {
						return;
					}
					c.find('> .open > .submenu').each(function() {
						if (this !== d && !$(this.parentNode).hasClass('active')) {
							$(this).slideUp(200).parent().removeClass('open');
						}
					});
				}
				if (isMin && $(d.parentNode.parentNode).hasClass('nav-list')) {
					return false;
				}
				$(d).slideToggle(200).parent().toggleClass('open');
				return false;
			});
		}
	});
</script>