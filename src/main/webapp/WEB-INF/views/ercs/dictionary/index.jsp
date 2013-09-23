<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应急法规管理 - 安全生产综合管理平台</title>
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
						<select id="typeCodeSelect" name="typeCode" class="input-small span2">
							<option value="" title="请选择字典类型" class="light-grey">请选择字典类型..</option>
							<option value="plan_type" title="引用标记：plan_type">应急预案种类</option>
							<option value="personal_category" title="引用标记：personal_category">人员类别</option>
							<option value="expertise_area" title="引用标记：expertise_area">专业领域</option>
							<option value="accident_category" title="引用标记：accident_category">事故类别</option>
							<option value="accident_level" title="引用标记：accident_level">事故严重程度</option>
							<option value="response_level" title="引用标记：response_level">事故响应级别</option>
							<option value="refuge_type" title="引用标记：refuge_type">避险场所种类</option>
							<option value="resource_type" title="引用标记：resource_type">应急资源种类</option>
						</select>
						<span class="input-icon">
							<input id="nav-search-input" name="itemName" type="text" placeholder="输入字典名称..." class="input-small nav-search-input" autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="dictionary-table"></div>
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
						<input id="typeCode" name="typeCode" type="hidden">
						<div class="control-group">
							<label class="control-label span2" for="itemName">字典项名称</label>
							<div class="controls">
								<input id="itemName" name="itemName" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="typeCode">字典分类</label>
							<div class="controls">
								<select id="typeCode" name="typeCode" class="input-small span11">
									<option value="plan_type">应急预案种类</option>
									<option value="personal_category">人员类别</option>
									<option value="expertise_area">专业领域</option>
									<option value="accident_category">事故类别</option>
									<option value="accident_level">事故严重程度</option>
									<option value="response_level">事故响应级别</option>
									<option value="refuge_type">避险场所种类</option>
									<option value="resource_type">应急资源种类</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="itemName">字典值</label>
							<div class="controls">
								<input id="itemValue" name="itemValue" type="text" class="span11">
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
						<input name="id" type="hidden" class="span11">
						<div class="control-group">
							<label class="control-label span2" for="itemName">文件名</label>
							<div class="controls">
								<input name="itemName" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="fileNo">字典类型</label>
							<div class="controls">
								<select id="edit-typeCode" name="typeCode" class="input-small span11">
									<option value="plan_type">应急预案种类</option>
									<option value="personal_category">人员类别</option>
									<option value="expertise_area">专业领域</option>
									<option value="accident_category">事故类别</option>
									<option value="accident_level">事故严重程度</option>
									<option value="response_level">事故响应级别</option>
									<option value="refuge_type">避险场所种类</option>
									<option value="resource_type">应急资源种类</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="itemName">字典值</label>
							<div class="controls">
								<input id="itemValue" name="itemValue" type="text" class="span11">
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
		seajs.use('${resources}/scripts/app/ercs/dictionary/index');
	</script>
</body>
</html>
