<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>用户管理 - 安全生产综合管理平台</title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar">
				<div class="toolbar">
					<button id="create" class="btn btn-small btn-success">
						<i class="icon-plus-sign-alt"></i>
						<span class="hidden-phone">新建</span>
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled">
						<i class="icon-edit"></i>
						<span class="hidden-phone">编辑</span>
					</button>
					<button id="lock" class="btn btn-small btn-primary disabled" title="锁定后账户无法登录">
						<i class="icon-lock"></i>
						<span class="hidden-phone">锁定</span>
					</button>
					<button id="unlock" class="btn btn-small btn-primary disabled" title="解锁后账户可以登录">
						<i class="icon-unlock"></i>
						<span class="hidden-phone">解锁</span>
					</button>
					<button id="remove" class="btn btn-small btn-danger disabled">
						<i class="icon-trash"></i>
						<span class="hidden-phone">删除</span>
					</button>
					<button id="reset" class="btn btn-small btn-danger disabled" title="将密码重置为123456">
						<i class="icon-refresh"></i>
						<span class="hidden-phone">重置密码</span>
					</button>
				</div>
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<span class="input-icon">
							<input id="nav-search-input" name="search" type="text" placeholder="输入用户名或姓名..." class="input-small nav-search-input" autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="account-table"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 新建
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal" onsubmit="return false;">
						<div class="control-group">
							<label class="control-label" for="principal">用户名</label>
							<div class="controls">
								<input name="principal" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="realName">姓名</label>
							<div class="controls">
								<input name="realName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="credential">密码</label>
							<div class="controls">
								<input name="credential" type="password">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="checkCredential">确认密码</label>
							<div class="controls">
								<input name="checkCredential" type="password">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="telephone">电话</label>
							<div class="controls">
								<input name="telephone" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="groupEntity">所属机构</label>
							<div class="controls">
								<select id="create-groupEntity" name="groupEntity[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="roleEntity">角色</label>
							<div class="controls">
								<select id="create-roleEntity" name="roleEntity[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="locked"></label>
							<div class="controls">
								<label> <input name="locked" type="checkbox" class="ace"> <span class="lbl"> 锁定</span>
								</label>
							</div>
						</div>
					</form>
				</div>
				<div id="create-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="create-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="create-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 编辑 -->
	<div id="edit-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-edit"></i> 编辑
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="edit-form" class="form-horizontal" onsubmit="return false;">
						<div class="control-group">
							<label class="control-label" for="principal">用户名</label>
							<div class="controls">
								<input name="principal" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="realName">姓名</label>
							<div class="controls">
								<input name="realName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="telephone">电话</label>
							<div class="controls">
								<input name="telephone" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="groupEntity">所属机构</label>
							<div class="controls">
								<select id="edit-groupEntity" name="groupEntity[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="roleEntity">角色</label>
							<div class="controls">
								<select id="edit-roleEntity" name="roleEntity[id]"></select>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div id="edit-message-alert" class="row-fluid hide">
				<div class="span12">
					<div class="alert alert-error">
						<i class="icon-remove"></i>
						<span id="edit-message-content"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="edit-save" class="btn btn-small btn-primary">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 删除 -->
	<div id="remove-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="red">
				<i class="icon-trash"></i> 删除
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<i class="icon-warning-sign"></i> 提示：删除用户将删除与此用户关联的所有数据，确认删除选中的用户？
				</div>
			</div>
			<div id="remove-message-alert" class="row-fluid hide">
				<div class="span12">
					<div class="alert alert-error">
						<i class="icon-remove"></i>
						<span id="remove-message-content"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="remove-save" class="btn btn-small btn-danger">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 重置密码 -->
	<div id="reset-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="red">
				<i class="icon-refresh"></i> 重置密码
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<i class="icon-warning-sign"></i> 确认重置选中用户的密码为：<b>123456</b> 吗？
				</div>
			</div>
			<div id="reset-message-alert" class="row-fluid hide">
				<div class="span12">
					<div class="alert alert-error">
						<i class="icon-remove"></i>
						<span id="reset-message-content"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="reset-save" class="btn btn-small btn-danger">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/system/account/index');
	</script>
</body>
</html>
