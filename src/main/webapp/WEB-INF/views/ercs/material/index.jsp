<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应急资源管理 - 安全生产综合管理平台</title>
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
						<i class="icon-plus-sign-alt"></i> 新建
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled">
						<i class="icon-edit"></i> 编辑
					</button>
					<button id="remove" class="btn btn-small btn-danger disabled">
						<i class="icon-trash"></i> 删除
					</button>
				</div>
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
					    <span class="input-icon">
							<select id="resourceTypeSelect" name="refugeType" class="input-small span2"></select>
						</span>
						<span class="input-icon">
							<input id="nav-search-input" name="resourceName" type="text" placeholder="输入资源名称..." class="input-small nav-search-input" autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="material-table"></div>
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
							<label class="control-label span2" for="principal">资源名称</label>
							<div class="controls">
								<input id="resourceName" name="resourceName" type="text" class="span11">
							</div>
						</div>
	        
					    <div class="control-group">
							<label class="control-label span2" for="principal">编号</label>
							<div class="controls">
								<input id="resourceNo" name="resourceNo" type="text" class="span11">
							</div>
						</div>
	        
					    <div class="control-group">
							<label class="control-label span2" for="principal">资源类别</label>
							<div class="controls">
								<select id="create-resourceType" name="resourceType[id]"  class="span11" ></select>
							</div>
						</div>
	        
					    <div class="control-group">
							<label class="control-label span2" for="principal">所属单位</label>
							<div class="controls">
								<input id="department" name="department" type="text" class="span11">
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
						<input  name="id" type="hidden" class="span11">
						
					    <div class="control-group">
							<label class="control-label span2" for="principal">资源名称</label>
							<div class="controls">
								<input  name="resourceName" type="text" class="span11">
							</div>
						</div>
	        
					    <div class="control-group">
							<label class="control-label span2" for="principal">编号</label>
							<div class="controls">
								<input  name="resourceNo" type="text" class="span11">
							</div>
						</div>
	        
					    <div class="control-group">
							<label class="control-label span2" for="principal">资源类别</label>
							<div class="controls">
								<select id="edit-resourceType" name="resourceType[id]"  class="span11" ></select>
							</div>
						</div>
	        
					    <div class="control-group">
							<label class="control-label span2" for="principal">所属单位</label>
							<div class="controls">
								<input  name="department" type="text" class="span11">
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
					<i class="icon-warning-sign"></i> 提示：删除选中的数据？
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
		seajs.use('${resources}/scripts/app/ercs/material/index');
	</script>
</body>
</html>
