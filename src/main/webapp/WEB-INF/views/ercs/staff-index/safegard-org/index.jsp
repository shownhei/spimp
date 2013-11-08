<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应急保障机构 - 安全生产综合管理平台</title>
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
				</div>
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<span class="input-icon">
						</span> <span class="input-icon"> <input id="nav-search-input" name="organizationName" type="text" placeholder="输入机构名称..." class="input-small nav-search-input"
							autocomplete="off"> <i class="icon-search nav-search-icon"></i>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="safegardOrganization-table"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 新增保障机构
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal">

						<div class="control-group">
							<label class="control-label " for="organizationName">资源名称</label>
							<div class="controls">
								<input id="organizationName" name="organizationName" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label " for="organizationType">资源类型</label>
							<div class="controls">
								<select id="create-organizationType" name="organizationType[id]"></select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label " for="administrativeDivision">行政区划分</label>
							<div class="controls">
								<input id="administrativeDivision" name="administrativeDivision" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label " for="qualification">救援资质</label>
							<div class="controls">
								<input id="qualification" name="qualification" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label " for="personInCharge">负责人</label>
							<div class="controls">
								<input id="personInCharge" name="personInCharge" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label " for="telephone">固话</label>
							<div class="controls">
								<input id="telephone" name="telephone" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label " for="mobilePhone">手机</label>
							<div class="controls">
								<input id="mobilePhone" name="mobilePhone" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label " for="remark">备注</label>
							<div class="controls">
								<input id="remark" name="remark" type="text">
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
	<div id="edit-modal" class="modal modal-md  hide">
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
							<label class="control-label" for="organizationName">资源名称</label>
							<div class="controls">
								<input name="organizationName" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="organizationType">资源类型</label>
							<div class="controls">
								<select id="edit-organizationType" name="organizationType[id]"></select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="administrativeDivision">行政区划分</label>
							<div class="controls">
								<input name="administrativeDivision" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="qualification">救援资质</label>
							<div class="controls">
								<input name="qualification" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="personInCharge">负责人</label>
							<div class="controls">
								<input name="personInCharge" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="telephone">固话</label>
							<div class="controls">
								<input name="telephone" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="mobilePhone">手机</label>
							<div class="controls">
								<input name="mobilePhone" type="text">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="remark">备注</label>
							<div class="controls">
								<input name="remark" type="text">
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
		seajs.use('${resources}/scripts/app/ercs/staff-index/safegard-org/index');
	</script>

</body>
</html>
