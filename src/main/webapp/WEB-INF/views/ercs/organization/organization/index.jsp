<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应急机构管理 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="../../../common/head.jsp"%>
<%@ include file="../../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar"></div>
			<div class="page-content">
				<div class="row-fluid">
					<div class="span3">
						<div class="widget-box tree-widget-box">
							<div class="widget-body tree-widget-body">
								<div class="widget-main padding-8 tree-widget-main">
									<div id="groups-tree" class="ztree"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="span9">
						<div class="tabbable">
							<ul class="nav nav-tabs">
								<li class="active">
									<a data-toggle="tab" href="#normal">
										<i class="green icon-user"></i> 人员组成
									</a>
								</li>
							</ul>
							<div id="tab-content" class="tab-content" style="padding-top: 0px; padding-left: 2px;">
								<div id="normal" class="tab-pane in active">
									<div class="row-fluid page-toolbar span12">
										<div class=" toolbar ">
											<button id="normal-create" class="btn btn-small btn-success ">
												<i class="icon-plus-sign-alt"></i>
												<span class="hidden-phone">新建</span>
											</button>
											<button id="normal-remove" class="btn btn-small btn-danger disabled">
												<i class="icon-trash"></i>
												<span class="hidden-phone">删除</span>
											</button>
										</div>
									</div>
									<div class="row-fluid" style="margin-top: 5px;">
										<div class="span12" id="normal-table"></div>
									</div>
								</div>
								<div id="expert" class="tab-pane in ">
									<div class="row-fluid page-toolbar span12">
										<div class="toolbar ">
											<button id="expert-create" class="btn btn-small btn-success ">
												<i class="icon-plus-sign-alt"></i>
												<span class="hidden-phone">新建</span>
											</button>
											<button id="expert-remove" class="btn btn-small btn-danger disabled">
												<i class="icon-trash"></i>
												<span class="hidden-phone">删除</span>
											</button>
										</div>
									</div>
									<div class="row-fluid" style="margin-top: 5px;">
										<div class="span12" id="expert-table"></div>
									</div>
								</div>
							</div>
						</div>
						<!-- end1 -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-normal-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 添加人员
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-normal-form" class="form-horizontal" onsubmit="return false;">
						<div class="control-group">
							<input name="memberType" type="hidden" value="normal">
							<input name="memberLevel" type="hidden" value="1">
							<input name="team" type="hidden" id="normal_team">
							<div class="control-group">
								<label class="control-label" for="memberLevel">人员类别</label>
								<div class="controls">
									<select id="memberLevel" name="memberLevel" class="input-small">
										<option value="1">总指挥</option>
										<option value="2">副总指挥</option>
										<option value="3">成员</option>
									</select>
								</div>
							</div>
							<label class="control-label" for="normalMember">人员名称</label>
							<div class="controls">
								<input id="create-normal-name" name="normalMember" type="text">
							</div>
						</div>
					</form>
				</div>
			</div>
			<div id="create-normal-message-alert" class="row-fluid hide">
				<div class="span12">
					<div class="alert alert-error">
						<i class="icon-remove"></i>
						<span id="create-normal-message-content"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="create-normal-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 新建专家 -->
	<div id="create-expert-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 添加专家
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-expert-form" class="form-horizontal" onsubmit="return false;">
						<div class="control-group">
							<input name="memberType" type="hidden" value="expert">
							<input name="memberLevel" type="hidden" value="1">
							<input name="team" type="hidden" id="expert_team">
							<label class="control-label" for="name">专家名称</label>
							<div class="controls">
								<input id="create-expert-name" name="expertMember" type="text">
							</div>
						</div>
					</form>
				</div>
			</div>
			<div id="create-expert-message-alert" class="row-fluid hide">
				<div class="span12">
					<div class="alert alert-error">
						<i class="icon-remove"></i>
						<span id="create-expert-message-content"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="create-expert-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 删除 -->
	<div id="remove-normal-modal" class="modal modal-xs hide">
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
			<div id="remove-normal-message-alert" class="row-fluid hide">
				<div class="span12">
					<div class="alert alert-error">
						<i class="icon-remove"></i>
						<span id="remove-normal-message-content"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="normal-remove-save" class="btn btn-small btn-danger">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 删除 -->
	<div id="remove-expert-modal" class="modal modal-xs hide">
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
			<div id="remove-expert-message-alert" class="row-fluid hide">
				<div class="span12">
					<div class="alert alert-error">
						<i class="icon-remove"></i>
						<span id="remove-expert-message-content"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="expert-remove-save" class="btn btn-small btn-danger">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/ercs/organization/organization/index');
	</script>
</body>
</html>
