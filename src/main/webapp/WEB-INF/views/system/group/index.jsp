<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>机构管理 - 安全生产综合管理平台</title>
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
					<button id="create" class="btn btn-small btn-success disabled" title="请先选择要添加到的机构">
						<i class="icon-plus-sign-alt"></i>
						<span class="hidden-phone">新建</span>
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled" title="选择机构后可编辑机构信息">
						<i class="icon-edit"></i>
						<span class="hidden-phone">编辑</span>
					</button>
					<button id="remove" class="btn btn-small btn-danger disabled" title="如果机构包含其他机构或包含用户，此机构将不能删除">
						<i class="icon-trash"></i>
						<span class="hidden-phone">删除</span>
					</button>
				</div>
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<span class="input-icon">
							<input id="nav-search-input" name="search" type="text" placeholder="输入机构名称或机构编号..." class="input-small nav-search-input nav-search-input210"
								autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid">
					<div class="span4">
						<div class="widget-box tree-widget-box">
							<div class="widget-body tree-widget-body">
								<div class="widget-main padding-8 tree-widget-main">
									<div id="groups-tree" class="ztree"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="span8">
						<div class="tabbable">
							<ul class="nav nav-tabs">
								<li class="active">
									<a data-toggle="tab" href="#basic">
										<i class="green icon-home"></i> 基本信息
									</a>
								</li>
							</ul>
							<div id="tab-content" class="tab-content">
								<div id="basic" class="tab-pane in active">
									<div class="profile-user-info">
										<div class="profile-info-row">
											<div class="profile-info-name">机构名称</div>
											<div class="profile-info-value">
												<span id="group-name">&nbsp;</span>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">机构编号</div>
											<div class="profile-info-value">
												<span id="group-number">&nbsp;</span>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">分类</div>
											<div class="profile-info-value">
												<span id="group-category">&nbsp;</span>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">包含用户(个数)</div>
											<div class="profile-info-value">
												<span id="group-accounts-count">&nbsp;</span>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">下属机构(个数)</div>
											<div class="profile-info-value">
												<span id="group-childrens-count">&nbsp;</span>
											</div>
										</div>
									</div>
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
					<form id="create-form" class="form-horizontal" onsubmit="return false;">
						<div class="control-group">
							<label class="control-label span2" for="name">机构名称</label>
							<div class="controls">
								<input name="name" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="number">机构编号</label>
							<div class="controls">
								<input name="number" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="name">机构类别</label>
							<div class="controls">
								<select name="category" class="span11">
									<option value="公司-company">公司</option>
									<option value="办公室-office">办公室</option>
									<option value="煤矿-mine">煤矿</option>
									<option value="其他-other">其他</option>
								</select>
							</div>
						</div>
					</form>
				</div>
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
					<form id="edit-form" class="form-horizontal" onsubmit="return false;">
						<div class="control-group">
							<label class="control-label span2" for="name">机构名称</label>
							<div class="controls">
								<input name="name" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="number">机构编号</label>
							<div class="controls">
								<input name="number" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="name">机构类别</label>
							<div class="controls">
								<select name="category" class="span11">
									<option value="公司-company">公司</option>
									<option value="办公室-office">办公室</option>
									<option value="煤矿-mine">煤矿</option>
									<option value="其他-other">其他</option>
								</select>
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
					<i class="icon-warning-sign"></i> 提示：确认删除选中的机构？
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
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/system/group/index');
	</script>
</body>
</html>
