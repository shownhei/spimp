<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>文档录入 - 山西王庄煤业数字矿山综合管理平台</title>
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
						<select id="query_projectType" name="projectType" style="height: 25px; width: 120px; font-size: 12px;"></select>
						<input name="search" type="text" style="height: 15px; width: 130px; font-size: 12px;" placeholder="输入关键字...">
						<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
						<button id="reset" type="reset" class="btn btn-primary btn-small">重置</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="plan-table"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-md hide">
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
							<label class="control-label " for="principal">文档名称</label>
							<div class="controls">
								<input id="create_documentName" name="documentName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="planType">工程分类</label>
							<div class="controls">
								<select id="create_projectType" name="projectType[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="principal">查询关键字</label>
							<div class="controls">
								<input id="create_keyWord" name="keyWord" type="text">
							</div>
						</div>
						<div class="control-group" style="display: none;">
							<label class="control-label" for="credential">附件</label>
							<div class="controls">
								<input id="create_attachment" readonly name="attachment" type="text" class="span10" style="width: 350px;">
								<input value="删除" type="button" id="create-file-delete" class="btn btn-small btn-success span2" style="width: 48px;">
							</div>
						</div>
					</form>
					<form id="create-file-form" action="/simpleupload" class="form-horizontal" method="post" enctype="multipart/form-data" target="acceptFrame">
						<div class="control-group">
							<label class="control-label" for="credential">附件</label>
							<div class="controls">
								<input name="file" id="file" type="file">
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
						<input name="id" type="hidden">
						<input name="office" type="hidden">
						<input name="createTime" type="hidden">
						<input name="createBy" type="hidden">
						<div class="control-group">
							<label class="control-label " for="principal">文档名称</label>
							<div class="controls">
								<input id="edit_documentName" name="documentName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="planType">工程分类</label>
							<div class="controls">
								<select id="edit_projectType" name="projectType[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="principal">查询关键字</label>
							<div class="controls">
								<input id="edit_keyWord" name="keyWord" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="credential">附件</label>
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
	<div id="remove-modal" class="modal modal-xs hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="red">
				<i class="icon-trash"></i> 删除
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<i class="icon-warning-sign"></i> 提示：确认删除选中的数据？
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
	<iframe name="acceptFrame" border="1" frameborder="1" width="100" height="100" style="display: none"></iframe>
	<script type="text/javascript">
		var officeCode = 'develop';
		seajs.use('${resources}/scripts/app/spmi/document/document/index');
	</script>
	<div id="view-modal" class="modal modal-xl hide">
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
