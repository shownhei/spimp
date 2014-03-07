<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应急救援人员管理 - 山西王庄煤业数字矿山综合管理平台</title>
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
					<button id="associate" class="btn btn-small btn-info disabled">
						<i class="icon-upload"></i> 账户关联
					</button>
				</div>
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<span class="input-icon">
							<input id="nav-search-input" name="staffName" type="text" placeholder="输入人员名称" class="input-small nav-search-input" autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
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
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="education">文化程度</label>
								<div class="controls">
									<select id="create-education" name="education[id]" class="span12" style="width: 130px;"></select>
								</div>
							</div>
							<div class="control-group  span6">
								<label class="control-label span4" for="enqueueDate">政治面貌</label>
								<div class="controls">
									<input id="policitalStatus" name="policitalStatus" class="span11" style="width: 122px;">
								</div>
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="principal">工作时间</label>
								<div class="controls">
									<input id="workDate" type="datetime" name="workDate" class="span12" style="width: 130px;">
								</div>
							</div>
							<div class="control-group  span6">
								<label class="control-label span4" for="enqueueDate">入队时间</label>
								<div class="controls">
									<input id="enqueueDate" name="enqueueDate" type="datetime" class="span11" style="width: 122px;">
								</div>
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
							<label class="control-label " for="department">部门</label>
							<div class="controls">
								<input type='text' id="create-department" name="department" class="span10" style="width: 350px;">
								<input type="button" value="选择" id="create_selectGroup" class="btn btn-small btn-success" style="width: 48px;">
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="expertiseArea">专业领域</label>
								<div class="controls">
									<select id="create-expertiseArea" name="expertiseArea[id]" class="span12" style="width: 130px;"></select>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label span4" for="responseLevel">事故响应级别</label>
								<div class="controls">
									<select id="create-responseLevel" name="responseLevel[id]" class="span11" style="width: 122px;"></select>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="remark">备注</label>
							<div class="controls">
								<input name="remark" type="text">
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
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="education">文化程度</label>
								<div class="controls">
									<select id="edit-education" name="education[id]" class="span12" style="width: 130px;"></select>
								</div>
							</div>
							<div class="control-group  span6">
								<label class="control-label span4" for="enqueueDate">政治面貌</label>
								<div class="controls">
									<input name="policitalStatus" class="span11" style="width: 122px;">
								</div>
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="principal">工作时间</label>
								<div class="controls">
									<input type="datetime" name="workDate" class="span12" style="width: 130px;">
								</div>
							</div>
							<div class="control-group  span6">
								<label class="control-label span4" for="enqueueDate">入队时间</label>
								<div class="controls">
									<input name="enqueueDate" type="datetime" class="span11" style="width: 122px;">
								</div>
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
							<label class="control-label " for="department">部门</label>
							<div class="controls">
								<input type='text' id="edit-department" name="department" class="span10" style="width: 350px;">
								<input type="button" value="选择" id="edit_selectGroup" class="btn btn-small btn-success" style="width: 48px;">
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="expertiseArea">专业领域</label>
								<div class="controls">
									<select id="edit-expertiseArea" name="expertiseArea[id]" class="span12" style="width: 130px;"></select>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label span4" for="responseLevel">事故响应级别</label>
								<div class="controls">
									<select id="edit-responseLevel" name="responseLevel[id]" class="span11" style="width: 122px;"></select>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="remark">备注</label>
							<div class="controls">
								<input name="remark" type="text">
							</div>
						</div>
						<input type="hidden" name="account" id="edit-account">
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
	<!-- 关联账户 -->
	<div id="associate-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 关联到账户
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="associate-form" class="form-horizontal">
						<input id="staffId" name="staffId" type="hidden">
						<div class="control-group">
							<label class="control-label " for="account">账户选择</label>
							<div class="controls">
								<input id="associate-account" name="account" type="text">
							</div>
						</div>
					</form>
				</div>
				<div id="associate-message-alert" class="row-fluid hide">
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
			<button id="associate-save" class="btn btn-small btn-success">
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
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/ercs/staff-index/staff/index');
	</script>
</body>
</html>
