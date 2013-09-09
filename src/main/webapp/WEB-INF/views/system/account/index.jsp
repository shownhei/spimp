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
			<div class="breadcrumbs" id="breadcrumbs">
				<ul class="breadcrumb">
					<li class="active">用户管理</li>
				</ul>
				<div class="nav-search" id="nav-search">
					<form class="form-search">
						<span class="input-icon">
							<input type="text" placeholder="搜索..." class="input-small nav-search-input" id="nav-search-input" autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="page-header position-relative">
					<div class="row-fluid">
						<div class="span12">
							<button id="create" class="btn btn-small btn-primary">
								<i class="icon-plus-sign-alt"></i> 新建
							</button>
							<button id="edit" class="btn btn-small btn-primary disabled">
								<i class="icon-edit"></i> 编辑
							</button>
							<button id="remove" class="btn btn-small btn-danger disabled">
								<i class="icon-trash"></i> 删除
							</button>
							<button id="lock" class="btn btn-small btn-primary disabled" title="锁定后账户无法登录">
								<i class="icon-lock"></i> 锁定
							</button>
							<button id="unlock" class="btn btn-small btn-primary disabled" title="解锁后账户可以登录">
								<i class="icon-unlock"></i> 解锁
							</button>
							<button id="reset" class="btn btn-small btn-danger disabled" title="将密码重置为123456">
								<i class="icon-refresh"></i> 重置密码
							</button>
						</div>
					</div>
				</div>
				<div class="row-fluid" id="account-table"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">新建</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal">
						<div class="control-group">
							<label class="control-label span2" for="principal">用户名</label>
							<div class="controls">
								<input id="principal" name="principal" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="realName">姓名</label>
							<div class="controls">
								<input id="realName" name="realName" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="credential">密码</label>
							<div class="controls">
								<input id="credential" name="credential" type="password" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="checkCredential">确认密码</label>
							<div class="controls">
								<input id="checkCredential" name="checkCredential" type="password" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="telephone">电话</label>
							<div class="controls">
								<input id="telephone" name="telephone" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="groupEntity">所属机构</label>
							<div class="controls">
								<select id="groupEntity" name="groupEntity[id]" class="span11"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="roleEntity">角色</label>
							<div class="controls">
								<select id="roleEntity" name="roleEntity[id]" class="span11"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="locked"></label>
							<div class="controls">
								<label> <input id="locked" name="locked" type="checkbox" class="ace"> <span class="lbl"> 锁定</span>
								</label>
							</div>
						</div>
					</form>
				</div>
				<div id="create-message-alert" class="row-fluid hide">
					<div class="span12">
						<div id="create-message-content" class="alert alert-error"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="create-save" class="btn btn-small btn-primary">
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
