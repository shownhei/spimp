<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<a class="menu-toggler" id="menu-toggler" href="#">
	<span class="menu-text"></span>
</a>
<div class="sidebar fixed" id="sidebar">
	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
		<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
			<a class="btn btn-small btn-success" href="${contextPath}/" title="首页">
				<i class="icon-wrench"></i>
			</a>
			<button class="btn btn-small btn-info">
				<i class="icon-medkit"></i>
			</button>
			<a class="btn btn-small btn-warning" href="${contextPath}/3d" target="_blank" title="三维综合管理">
				<i class="icon-picture"></i>
			</a>
			<a class="btn btn-small btn-danger" href="${contextPath}/auto" target="_blank" title="综合自动化管控平台">
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
	</ul>
	<div class="sidebar-collapse" id="sidebar-collapse">
		<i class="icon-double-angle-left"></i>
	</div>
</div>
