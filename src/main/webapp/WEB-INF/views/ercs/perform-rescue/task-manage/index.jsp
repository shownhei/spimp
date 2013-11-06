<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>任务管理 - 安全生产综合管理平台</title>
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
					<button id="create" class="btn btn-small btn-success">
						<i class="icon-plus-sign-alt"></i> 新建
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled">
						<i class="icon-edit"></i> 编辑
					</button>
					<button id="remove" class="btn btn-small btn-danger disabled">
						<i class="icon-trash"></i> 删除
					</button>
					<button id="view" class="btn btn-small disabled">
						<i class="icon-th-list"></i> 查看
					</button>
				</div>
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<span class="input-icon">
							<select id="emergencyCategorySelect" name="emergencyCategory" class="input-small span2"></select>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="refuge-table"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 新增救援措施模板
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal">
						<div class="control-group">
							<label class="control-label span2" for="emergencyCategory">事故类型</label>
							<div class="controls">
								<select id="create-emergencyCategory" name="emergencyCategory[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="emergencyLevel">事故严重程度</label>
							<div class="controls">
								<select id="create-emergencyLevel" name="emergencyLevel[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="team">专业组</label>
							<div class="controls">
								<select id="create-team" name="team[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="taskContent">救援措施内容</label>
							<div class="controls">
								<textarea name="taskContent" rows=5></textarea>
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
	<div id="edit-modal" class="modal modal-md  hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-edit"></i> 编辑救援措施模板
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="edit-form" class="form-horizontal">
						<input name="id" type="hidden">
						<div class="control-group">
							<label class="control-label span2" for="emergencyCategory">事故类型</label>
							<div class="controls">
								<select id="edit-emergencyCategory" name="emergencyCategory[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="emergencyLevel">事故严重程度</label>
							<div class="controls">
								<select id="edit-emergencyLevel" name="emergencyLevel[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="team">专业组</label>
							<div class="controls">
								<select id="edit-team" name="team[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="taskContent">救援措施内容</label>
							<div class="controls">
								<textarea name="taskContent" rows=5></textarea>
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
	<!-- 查看 -->
	<div id="view-modal" class="modal modal-md  hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-th-list"></i> 查看救援措施模板
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="view-form" class="form-horizontal">
						<input name="id" type="hidden">
						<div class="control-group">
							<label class="control-label span2" for="emergencyCategory">事故类型</label>
							<div class="controls">
								<input id="view-emergencyCategory" readOnly>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="emergencyLevel">事故严重程度</label>
							<div class="controls">
								<input id="view-emergencyLevel" readOnly>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="team">专业组</label>
							<div class="controls">
								<input id="view-team" readOnly>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="taskContent">救援措施内容</label>
							<div class="controls">
								<textarea name="taskContent" readOnly rows=5></textarea>
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
	<!-- 删除 -->
	<div id="remove-modal" class="modal  modal-xs  hide">
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
		seajs.use('${resources}/scripts/app/ercs/perform-rescue/task-manage/index');
	</script>
</body>
</html>
