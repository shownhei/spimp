<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应急救援人员管理 - 安全生产综合管理平台</title>
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
						<span class="input-icon"> <input id="nav-search-input" name="staffName" type="text" placeholder="输入人员名称" class="input-small nav-search-input"
							autocomplete="off"> <i class="icon-search nav-search-icon"></i>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="staff-table"></div>
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
					<form id="create-form" class="form-horizontal">
						<div class="control-group">
							<label class="control-label " for="staffName">姓名</label>
							<div class="controls">
								<input name="staffName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="birthDay">出生日期</label>
							<div class="controls">
								<input name="birthDay" type="datetime">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="education">文化程度</label>
							<div class="controls">
								<select id="create-education" name="education[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="policitalStatus">政治面貌</label>
							<div class="controls">
								<input name="policitalStatus" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="workDate">工作时间</label>
							<div class="controls">
								<input name="workDate" type="datetime">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="enqueueDate">入队时间</label>
							<div class="controls">
								<input name="enqueueDate" type="datetime">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="address">住址</label>
							<div class="controls">
								<input name="address" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="telephone">电话</label>
							<div class="controls">
								<input name="telephone" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="iDNumber">身份证号</label>
							<div class="controls">
								<input name="iDNumber" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="remark">备注</label>
							<div class="controls">
								<input name="remark" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="department">部门</label>
							<div class="controls">
								<input type='text' readonly id="create-department" name="department" class="span10"> <input type="button" value="选择" id="create_selectGroup">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="expertiseArea">专业领域</label>
							<div class="controls">
								<select id="create-expertiseArea" name="expertiseArea[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="responseLevel">事故响应级别</label>
							<div class="controls">
								<select id="create-responseLevel" name="responseLevel[id]"></select>
							</div>
						</div>
					</form>
				</div>
				<div id="create-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i> <span id="create-message-content"></span>
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
	<div id="edit-modal" class="modal modal-md hide">
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
						<div class="control-group">
							<label class="control-label" for="principal">员工类型</label>
							<div class="controls">
								<input name="staffType" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="principal">姓名</label>
							<div class="controls">
								<input name="staffName" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="principal">部门</label>
							<div class="controls">
								<input type='text' readonly id="edit-department" name="department" class="span10">
								<button type="button" id="edit_selectGroup">选择</button>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="principal">专业领域</label>
							<div class="controls">
								<select id="edit-expertiseArea" name="expertiseArea[id]"></select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="principal">职称</label>
							<div class="controls">
								<input name="title" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="principal">经验</label>
							<div class="controls">
								<input name="experience" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="principal">联系方式</label>
							<div class="controls">
								<input name="phone" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="principal">事故响应级别</label>
							<div class="controls">
								<select id="edit-responseLevel" name="responseLevel[id]"></select>
							</div>
						</div>

					</form>
				</div>
				<div id="edit-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i> <span id="edit-message-content"></span>
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
							<i class="icon-remove"></i> <span id="remove-message-content"></span>
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
		seajs.use('${resources}/scripts/app/ercs/staff/index');
	</script>
</body>
</html>
