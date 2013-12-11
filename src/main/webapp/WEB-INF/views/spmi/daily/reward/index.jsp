<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>奖惩记录 - 安全生产综合管理平台</title>
<%@ include file="../../../common/head.jsp"%>
<%@ include file="../../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar">
				<div class="toolbar">
					<button id="create" class="btn btn-small btn-success" title="填写奖惩记录">
						<i class="icon-plus-sign-alt"></i>
						<span class="hidden-phone">新建</span>
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled">
						<i class="icon-edit"></i>
						<span class="hidden-phone">编辑</span>
					</button>
					<button id="remove" class="btn btn-small btn-danger disabled">
						<i class="icon-trash"></i>
						<span class="hidden-phone">删除</span>
					</button>
				</div>
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<span class="input-icon">
							<input id="nav-search-input" name="search" type="text" placeholder="输入奖惩人姓名..." class="input-small nav-search-input" autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="records"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-lg hide">
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
							<label class="control-label">奖惩人姓名</label>
							<div class="controls">
								<input name="name" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">奖惩人所属部门</label>
							<div class="controls">
								<select id="create-groupName" name="groupName"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">分类</label>
							<div class="controls">
								<select name="category">
									<option value="奖励">奖励</option>
									<option value="惩罚">惩罚</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">奖惩内容</label>
							<div class="controls">
								<input name="content" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">理由</label>
							<div class="controls">
								<textarea name="reason" class="xheditor {skin:'nostyle',tools:'simple'}"></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">授奖人</label>
							<div class="controls">
								<input name="executor" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">奖惩日期</label>
							<div class="controls">
								<input name="rewardDate" type="datetime">
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
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/spmi/daily/reward/index');
	</script>
</body>
</html>