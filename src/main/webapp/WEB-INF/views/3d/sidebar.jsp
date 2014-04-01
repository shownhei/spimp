<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<a class="menu-toggler" id="menu-toggler" href="#">
	<span class="menu-text"></span>
</a>
<div class="sidebar fixed menu-min" id="sidebar">
	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
		<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
			<a class="btn btn-small btn-success" href="${contextPath}/" target="safe" title="首页">
				<i class="icon-wrench"></i>
			</a>
			<button class="btn btn-small btn-info">
				<i class="icon-medkit"></i>
			</button>
			<a class="btn btn-small btn-warning" href="${contextPath}/3d" target="_blank" title="三维综合管理">
				<i class="icon-picture"></i>
			</a>
			<a class="btn btn-small btn-danger" href="${contextPath}/auto" target="safe" title="综合自动化管控平台">
				<i class="icon-cogs"></i>
			</a>
		</div>
		<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
			<span class="btn btn-success"></span>
			<span class="btn btn-info"></span>
			<span class="btn btn-warning"></span>
			<span class="btn btn-danger"></span>
		</div>
	</div>
	<ul class="nav nav-list">
		<li>
			<a title="全屏" data-image="全屏.png" data-type="fullscreen">
				<i class="icon-fullscreen"></i>
			</a>
		</li>
		<li>
			<a title="全图显示" data-image="全图显示.png">
				<i class="icon-picture"></i>
			</a>
		</li>
		<li>
			<a title="巷道半显示" data-image="巷道半显示.png">
				<i class="icon-upload"></i>
			</a>
		</li>
		<li>
			<a title="巷道交叉处理" data-image="巷道交叉处理.png">
				<i class="icon-remove"></i>
			</a>
		</li>
		<li>
			<a title="拉框放大" data-image="拉框放大-放大前.png,拉框放大-放大后.png" data-type="zoom-in">
				<i class="icon-resize-full"></i>
			</a>
		</li>
		<li>
			<a title="量测工具" data-image="量测工具.png">
				<i class="icon-crop"></i>
			</a>
		</li>
		<li>
			<a title="巷道漫游" data-image="巷道漫游.png">
				<i class="icon-compass"></i>
			</a>
		</li>
	</ul>
</div>
