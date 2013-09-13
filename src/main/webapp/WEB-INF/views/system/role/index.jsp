<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>角色管理 - 安全生产综合管理平台</title>
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
					<button id="create" class="btn btn-small btn-success" title="新建角色">
						<i class="icon-plus-sign-alt"></i>
						<span class="hidden-phone">新建</span>
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled" title="编辑角色名">
						<i class="icon-edit"></i>
						<span class="hidden-phone">编辑</span>
					</button>
					<button id="remove" class="btn btn-small btn-danger disabled" title="请先选择角色，包含用户的角色无法删除">
						<i class="icon-trash"></i>
						<span class="hidden-phone">删除</span>
					</button>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid">
					<div class="span3">
						<div class="widget-box tree-widget-box">
							<div class="widget-body tree-widget-body">
								<div class="widget-main padding-8">
									<div id="role-tree" class="ztree"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="span9">
						<div class="tabbable">
							<ul class="nav nav-tabs">
								<li class="active">
									<a data-toggle="tab" href="#basic">
										<i class="green icon-home bigger-110"></i> 基本信息
									</a>
								</li>
								<li>
									<a data-toggle="tab" href="#resources">
										<i class="green icon-home bigger-110"></i> 菜单权限
									</a>
								</li>
							</ul>
							<div id="tab-content" class="tab-content">
								<div id="basic" class="tab-pane in active">
									<div class="profile-user-info profile-user-info-striped">
										<div class="profile-info-row">
											<div class="profile-info-name">角色名</div>
											<div class="profile-info-value">
												<span class="editable editable-click" id="role-name">alexdoe</span>
											</div>
										</div>
									</div>
								</div>
								<div id="resources" class="tab-pane">
									<p>菜单权限</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 新建
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal">
						<div class="control-group">
							<label class="control-label span2" for="name">角色名</label>
							<div class="controls">
								<input name="name" type="text" class="span11">
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
	<div id="edit-modal" class="modal hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-edit"></i> 编辑
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="edit-form" class="form-horizontal">
						<div class="control-group">
							<label class="control-label span2" for="name">角色名</label>
							<div class="controls">
								<input name="name" type="text" class="span11">
							</div>
						</div>
					</form>
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
	<div id="remove-modal" class="modal hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="red">
				<i class="icon-trash"></i> 删除
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<i class="icon-warning-sign"></i> 提示：如果角色包含用户，将不能删除，确认删除选中的角色？
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
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/system/role/index');
	</script>
</body>
</html>
