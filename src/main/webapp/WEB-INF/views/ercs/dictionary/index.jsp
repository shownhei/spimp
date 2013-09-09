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
			<div class="breadcrumbs" id="breadcrumbs">
				<ul class="breadcrumb">
					<li class="active">字典管理</li>
				</ul>
				<div class="nav-search" id="nav-search">
					<form class="form-search">
						<span class="input-icon"> <input type="text"
							placeholder="搜索..." class="input-small nav-search-input"
							id="nav-search-input" autocomplete="off"> <i
							class="icon-search nav-search-icon"></i>
						</span>
					</form>
				</div>
			</div>
			<div class="page-content">
			
				<div class="page-header position-relative">
					<div class="row-fluid">
						<div class="span12">
							<div class="row-fluid">
								<div class="span12">
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
							</div>
							<div class="tabbable">
								<ul id="myTab" class="nav nav-tabs">
									<li class="active"><a href="#plan_type" data-toggle="tab">
											<i class="green icon-home bigger-110"></i>应急预案种类
									</a></li>

									<li><a href="#personal_category" data-toggle="tab">
											人员类别 </a></li>
									<li><a href="#expertise_area" data-toggle="tab"> 专业领域

									</a></li>
									<li><a href="#accident_category" data-toggle="tab">
											事故类别 </a></li>
									<li><a href="#response_level" data-toggle="tab">
											事故响应级别 </a></li>

								</ul>

								<div class="tab-content">
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
								</div>
							</div>
						</div>
						<!-- /span -->
					</div>
				</div>
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
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/ercs/dictionary/index');
	</script>
</body>
</html>
