<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>现场处置方案 - 安全生产综合管理平台</title>
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
							<input id="nav-search-input" name="search" type="text" placeholder="输入类别/制定人..." class="input-small nav-search-input" autocomplete="off">
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
	<div id="create-modal" class="modal  modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 新建
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label " for="principal">事故类别</label>
							<div class="controls">
								<select id="create-type" name="type[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="principal">事故现场</label>
							<div class="controls">
								<input id="address" name="address" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="principal">方案制定人</label>
							<div class="controls">
								<input id="decide" name="decide" type="text">
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="resourceNo">事故发生日期</label>
								<div class="controls">
									<input id="startTime" name="startTime" type="datetime" class="span12" style="width: 130px;">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label span4" for="amount">事故发生时间</label>
								<div class="controls">
									<input id="create_startTime_tail" name="startTime_tail" type="time" value="1" class="span11" style="width: 122px;">
								</div>
							</div>
						</div>
						<div class="control-group" style="display: none;">
							<label class="control-label span2" for="credential">处置方案</label>
							<div class="controls">
								<input id="attachment" readonly name="attachment" type="text" class="span10" style="width: 350px;">
								<input value="删除" type="button" id="create-file-delete" class="btn btn-small btn-success" style="width: 50px;">
							</div>
						</div>
					</form>
					<form id="create-file-form" action="/simpleupload" class="form-horizontal" style="margin-bottom: 0px;" method="post" enctype="multipart/form-data"
						target="acceptFrame">
						<div class="control-group">
							<label class="control-label span2" for="credential">处置方案</label>
							<div class="controls">
								<input name="file" id="file" type="file" class="span11">
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
	<div id="edit-modal" class="modal  modal-md hide">
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
						<input name="id" type="hidden" class="span11">
						<div class="control-group">
							<label class="control-label " for="principal">事故类别</label>
							<div class="controls">
								<select id="edit-type" name="type[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="principal">事故现场</label>
							<div class="controls">
								<input name="address" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="principal">方案指定人</label>
							<div class="controls">
								<input name="decide" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="principal">事故发生时间</label>
							<div class="controls">
								<input name="startTime" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="principal">处置方案</label>
							<div class="controls">
								<input name="attachment" id="edit_attachment" readonly type="text">
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
		seajs.use('${resources}/scripts/app/ercs/scheme/index');
	</script>
	<iframe name="acceptFrame" border="1" frameborder="1" width="100" height="100" style="display: none"></iframe>
	<div id="view-modal" class="modal hide" style="width: 800px;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="red">
				<i class="icon-trash"></i> 查看
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<iframe id="showDocument" src="" width="100%" height=355 border=0 margin=0 frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 关闭
			</button>
		</div>
	</div>
</body>
</html>
