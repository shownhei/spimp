<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a href="/" class="brand">
				<small> <i class="icon-leaf"></i> 安全生产综合管理平台
				</small>
			</a>
			<ul class="nav ace-nav pull-right">
				<li class="purple">
					<a data-toggle="dropdown" class="dropdown-toggle" href="#">
						<i class="icon-bell-alt icon-animated-bell"></i>
						<span class="badge badge-important">0</span>
					</a>
					<ul class="pull-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-closer">
						<li class="nav-header">
							<i class="icon-warning-sign"></i> 0 个报警
						</li>
						<li>
							<a href="elements">
								查看所有报警 <i class="icon-arrow-right"></i>
							</a>
						</li>
					</ul>
				</li>
				<li class="green">
					<a data-toggle="dropdown" class="dropdown-toggle" href="#">
						<i class="icon-envelope icon-animated-vertical"></i>
						<span class="badge badge-success">0</span>
					</a>
					<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-closer">
						<li class="nav-header">
							<i class="icon-envelope-alt"></i> 0 条消息
						</li>
						<li>
							<a href="#">
								查看所有消息 <i class="icon-arrow-right"></i>
							</a>
						</li>
					</ul>
				</li>
				<li class="light-blue">
					<a data-toggle="dropdown" href="#" class="dropdown-toggle">
						<img class="nav-user-photo" src="${resources}/images/avatar.png" alt="Jason's Photo">
						<span class="user-info">
							<small>欢迎，</small> ${account.realName}
						</span>
						<i class="icon-caret-down"></i>
					</a>
					<ul class="user-menu pull-right dropdown-menu dropdown-light dropdown-caret dropdown-closer">
						<li>
							<a href="#">
								<i class="icon-cog"></i> 设置
							</a>
						</li>
						<li>
							<a href="#">
								<i class="icon-user"></i> 账号
							</a>
						</li>
						<li class="divider"></li>
						<li>
							<a href="/logout">
								<i class="icon-off"></i> 退出
							</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</div>
