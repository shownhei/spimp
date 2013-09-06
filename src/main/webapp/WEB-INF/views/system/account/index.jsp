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
							<button id="create" class="btn btn-small btn-primary" data-toggle="modal" data-target="#create-modal">
								<i class="icon-plus-sign-alt"></i> 新建
							</button>
							<button id="edit" class="btn btn-small btn-primary disabled" data-toggle="modal" data-target="#edit-modal">
								<i class="icon-edit"></i> 编辑
							</button>
							<button id="remove" class="btn btn-small btn-danger disabled">
								<i class="icon-trash"></i> 删除
							</button>
							<button id="lock" class="btn btn-small disabled" title="锁定后账户无法登录">
								<i class="icon-lock"></i> 锁定
							</button>
							<button id="unlock" class="btn btn-small disabled" title="解锁后账户可以登录">
								<i class="icon-unlock"></i> 解锁
							</button>
							<button id="reset" class="btn btn-small btn-danger disabled" title="将密码重置为123456">
								<i class="icon-refresh"></i> 重置密码
							</button>
						</div>
					</div>
				</div>
				<div class="row-fluid" id="table"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal hide" tabindex="-1">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h4 class="blue bigger">新建</h4>
		</div>
		<div class="modal-body overflow-visible">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal">
						<div class="control-group">
							<label class="control-label span2" for="principal">用户名</label>
							<div class="controls">
								<input id="principal" name="principal" type="text" class="span10">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="realName">姓名</label>
							<div class="controls">
								<input id="realName" name="realName" type="text" class="span10">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="credential">密码</label>
							<div class="controls">
								<input id="credential" name="credential" type="password" class="span10">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="checkCredential">确认密码</label>
							<div class="controls">
								<input id="checkCredential" name="checkCredential" type="text" class="span10">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="telephone">电话</label>
							<div class="controls">
								<input id="telephone" name="telephone" type="text" class="span10">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="groupEntity">所属机构</label>
							<div class="controls">
								<select id="groupEntity" name="groupEntity[id]" class="span10"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="roleEntity">角色</label>
							<div class="controls">
								<select id="roleEntity" name="roleEntity[id]" class="span10"></select>
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
			</div>
		</div>
		<div class="modal-footer">
			<button id="save" class="btn btn-small btn-primary">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 编辑 -->
	<div id="edit-modal" class="modal hide" tabindex="-1">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h4 class="blue bigger">编辑</h4>
		</div>
		<div class="modal-body overflow-visible">
			<form>
				<div class="row-fluid">
					<div class="span6">
						<label class="span4" for="principal">用户名</label>
						<input id="principal" name="principal" class="span8" type="text">
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<label class="span4" for="realName">姓名</label>
						<input id="realName" name="realName" class="span8" type="text">
					</div>
					<div class="span6">
						<label class="span4" for="telephone">电话</label>
						<input id="telephone" name="telephone" class="span8" type="text">
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<label class="span4" for="credential">密码</label>
						<input id="credential" name="credential" class="span8" type="password">
					</div>
					<div class="span6">
						<label class="span4" for="checkCredential">确认密码</label>
						<input id="checkCredential" name="checkCredential" class="span8" type="password">
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<label class="span4" for="groupEntity">机构</label> <select id="groupEntity" name="groupEntity[id]" class="span8"></select>
					</div>
					<div class="span6">
						<label class="span4" for="roleEntity">角色</label> <select id="roleEntity" name="roleEntity[id]" class="span8"></select>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<label class="span4" for="groupEntity">是否锁定</label>
						<div class="span8">
							<input id="locked" name="locked" type="checkbox" class="ace">
							<span class="lbl"></span>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="alert alert-danger">
						<button type="button" class="close" data-dismiss="alert">
							<i class="icon-remove"></i>
						</button>
						<strong> <i class="icon-remove"></i> 
						</strong>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<button class="btn btn-small btn-primary">
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
