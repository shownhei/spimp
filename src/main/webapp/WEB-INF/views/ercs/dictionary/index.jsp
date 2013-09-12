<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>字典管理 - 安全生产综合管理平台</title>
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
					<button id="create" class="btn btn-small btn-primary">
						<i class="icon-plus-sign-alt"></i> 新建
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled">
						<i class="icon-edit"></i> 编辑
					</button>
					<button id="delete" class="btn btn-small btn-danger disabled">
						<i class="icon-trash"></i> 删除
					</button>
				</div>
				<div class="nav-search">
					<form id="query-form" class="form-search" onsubmit="return false;">
						<span class="input-icon"> <input 
							name="itemName" type="text" placeholder="输入预案名称"
							class="input-small nav-search-input" autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
						<button id="query" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid">
					<div class="span12">
						<div class="tabbable">
							<ul id="myTab" class="nav nav-tabs">
								<li class="active" title="应急预案种类:plan_type"><a href="#plan_type" data-toggle="tab">
										应急预案种类
								</a></li>

								<li title="人员类别:personal_category"><a href="#personal_category" data-toggle="tab">
										人员类别 </a></li>
								<li title="专业领域:expertise_area"><a href="#expertise_area" data-toggle="tab"> 专业领域 </a></li>
								<li title="事故类别:accident_category"><a href="#accident_category" data-toggle="tab">
										事故类别 </a></li>
								<li title="事故响应级别:response_level"><a href="#response_level" data-toggle="tab"> 事故响应级别
								</a></li>
								<li title="避险场所种类:refuge_type"><a href="#refuge_type" data-toggle="tab"> 避险场所种类
								</a></li>
								<li title="应急资源种类:resource_type"><a href="#resource_type" data-toggle="tab"> 应急资源种类
								</a></li>

							</ul>

							<div class="tab-content" style="padding: 0px 0px 0px 0px;">
								<div class="tab-pane in active" id="plan_type">
									<div class="row-fluid" id="plan_type-table"></div>
								</div>

								<div class="tab-pane" id="personal_category">
									<div class="row-fluid" id="personal_category-table"></div>
								</div>

								<div class="tab-pane" id="expertise_area">
									<div class="row-fluid" id="expertise_area-table"></div>
								</div>

								<div class="tab-pane" id="accident_category">
									<div class="row-fluid" id="accident_category-table"></div>
								</div>

								<div class="tab-pane" id="response_level">
									<div class="row-fluid" id="response_level-table"></div>
								</div>
								<div class="tab-pane" id="refuge_type">
									<div class="row-fluid" id="refuge_type-table"></div>
								</div>
								<div class="tab-pane" id="resource_type">
									<div class="row-fluid" id="resource_type-table"></div>
								</div>
							</div>
						</div>
					</div>
					<!-- /span -->
				</div>
			</div>
		</div>
	</div>
	<!-- 删除 -->
	<div id="delete-modal" class="modal hide">
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
				<div id="delete-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-delete"></i>
							<span id="delete-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="delete-save" class="btn btn-small btn-danger">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-delete"></i> 取消
			</button>
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
							<label class="control-label span2" for="realName">姓名</label>
							<div class="controls">
								<input id="itemName" name="itemName" type="text" class="span11">
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
	<div id="edit-modal" class="modal hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">新建</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="edit-form" class="form-horizontal">
					    <input  name="id" type="hidden">
						<input  name="typeCode" type="hidden">
						<div class="control-group">
							<label class="control-label span2" for="realName">姓名</label>
							<div class="controls">
								<input  name="itemName" type="text" class="span11">
							</div>
						</div>
					</form>
				</div>
				<div id="edit-message-alert" class="row-fluid hide">
					<div class="span12">
						<div id="edit-message-content" class="alert alert-error"></div>
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
	
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/ercs/dictionary/index');
	</script>
</body>
</html>
