<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<a class="menu-toggler" id="menu-toggler" href="#">
	<span class="menu-text"></span>
</a>
<div class="sidebar fixed menu-min" id="sidebar">
	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
		<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
			<a class="btn btn-small btn-success" href="${contextPath}/" target="_blank" title="首页">
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
		<li>
			<a title="综采系统" data-image="综采系统.png">
				<i class="icon-upload-alt"></i>
			</a>
		</li>
		<li>
			<a title="皮带运输系统" data-image="皮带运输系统.png">
				<i class="icon-truck"></i>
			</a>
		</li>
		<li>
			<a title="通风机系统" data-image="通风机系统.png">
				<i class="icon-upload"></i>
			</a>
		</li>
		<li>
			<a title="洗煤厂系统" data-image="洗煤厂-重介.png">
				<i class="icon-tint"></i>
			</a>
		</li>
		<li>
			<a title="供电系统" data-image="供电系统-中央.png">
				<i class="icon-cogs"></i>
			</a>
		</li>
		<li>
			<a title="主排水系统" data-image="主排水系统.png">
				<i class="icon-crop"></i>
			</a>
		</li>
		<li>
			<a title="压风机系统" data-image="压风机系统.png">
				<i class="icon-puzzle-piece"></i>
			</a>
		</li>
		<li>
			<a title="顶板在线系统" data-image="顶板在线.png">
				<i class="icon-inbox"></i>
			</a>
		</li>
	</ul>
</div>
