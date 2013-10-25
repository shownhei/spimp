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
							<a id="settings" class="pointer">
								<i class="icon-cog"></i> 设置
							</a>
						</li>
						<li>
							<a id="account-settings" class="pointer">
								<i class="icon-user"></i> 账号
							</a>
						</li>
						<li class="divider"></li>
						<li>
							<a id="logout" class="pointer">
								<i class="icon-off"></i> 退出
							</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</div>
<!-- 设置 -->
<div id="settings-modal" class="modal modal-sm hide">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h5 class="blue">
			<i class="icon-user"></i> 设置
		</h5>
	</div>
	<div class="modal-body">
		<div class="row-fluid">
			<div class="span12">
				<form id="settings-form" class="form-horizontal" onsubmit="return false;">
					<div class="control-group">
						<label class="control-label" for="principal">用户名</label>
						<div class="controls">
							<input name="principal" type="text" readonly="readonly" value="${account.principal}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="realName">姓名</label>
						<div class="controls">
							<input name="realName" type="text" readonly="readonly" value="${account.realName}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="telephone">电话</label>
						<div class="controls">
							<input name="telephone" type="text" readonly="readonly" value="${account.telephone}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="telephone">其他</label>
						<div class="controls">
							<textarea readonly="readonly">详细情况</textarea>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button class="btn btn-small" data-dismiss="modal">
			<i class="icon-remove"></i> 关闭
		</button>
	</div>
</div>
<!-- 账号 -->
<div id="account-settings-modal" class="modal modal-sm hide">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h5 class="blue">
			<i class="icon-user"></i> 账号
		</h5>
	</div>
	<div class="modal-body">
		<div class="row-fluid">
			<div class="span12">
				<form id="account-settings-form" class="form-horizontal" onsubmit="return false;">
					<div class="control-group">
						<label class="control-label" for="principal">用户名</label>
						<div class="controls">
							<input name="principal" type="text" readonly="readonly" value="${account.principal}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="oldPassword">当前密码</label>
						<div class="controls">
							<input name="oldPassword" type="password">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="newPassword1">新密码</label>
						<div class="controls">
							<input name="newPassword1" type="password">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="newPassword2">确认密码</label>
						<div class="controls">
							<input name="newPassword2" type="password">
						</div>
					</div>
				</form>
			</div>
		</div>
		<div id="account-settings-message-alert" class="row-fluid hide">
			<div class="span12">
				<div class="alert alert-error">
					<i class="icon-remove"></i>
					<span id="account-settings-message-content"></span>
				</div>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button id="account-settings-save" class="btn btn-small btn-primary">
			<i class="icon-ok"></i> 确定
		</button>
		<button class="btn btn-small" data-dismiss="modal">
			<i class="icon-remove"></i> 取消
		</button>
	</div>
</div>
<!-- 退出 -->
<div id="logout-modal" class="modal modal-xs hide">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">×</button>
		<h5 class="red">
			<i class="icon-off"></i> 退出
		</h5>
	</div>
	<div class="modal-body">
		<div class="row-fluid">
			<div class="span12">
				<i class="icon-warning-sign"></i> 确认退出登录？
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button id="logout-save" class="btn btn-small btn-danger">
			<i class="icon-ok"></i> 确定
		</button>
		<button class="btn btn-small" data-dismiss="modal">
			<i class="icon-remove"></i> 取消
		</button>
	</div>
</div>
