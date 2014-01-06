<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>维修记录管理 - 安全生产综合管理平台</title>
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
					<button id="create_maintenance" class="btn btn-small btn-success">
						<i class="icon-plus-sign-alt"></i> 创建保养单
					</button>
					<button id="create_maintenance_detail" class="btn btn-small btn-primary disabled">
						<i class="icon-edit"></i> 添加保养明细
					</button>
					<button id="remove_maintenance" class="btn btn-small btn-danger disabled">
						<i class="icon-trash"></i> 删除保养单
					</button>
					<button id="export_maintenance" class="btn btn-small btn-pink disabled">
						<i class="icon-download-alt"></i> 导出保养单
					</button>
				</div>
				<div class="nav-search">
					<form id="query-form" class="form-search" onsubmit="return false;">
						<select id="query_car" name="car" style="height: 25px; width: 120px; font-size: 12px;"></select>
						<div class="input-append">
							<input id="query_maintenanceDate" name="maintenanceDate" type="datetime" placeholder="开始时间" class="input-small">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<div class="input-append">
							<input id="query_maintenancePeople" name="maintenancePeople" placeholder="保养人" style="height: 16px; width: 120px; font-size: 12px;">
						</div>
						<select id="query_maintenanceLevel" name="maintenanceLevel" style="height: 25px; width: 120px; font-size: 12px;">
							<option value=0 title="">日常保养</option>
						</select>
						<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
						<button id="reset" type="reset" class="btn btn-primary btn-small">重置</button>
					</form>
				</div>
			</div>
			<div class="page-content" id="tablePanel"></div>
		</div>
	</div>
	<!-- 创建保养单-->
	<div id="create_maintenance-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 新建保养单
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create_maintenance-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="car">车牌号</label>
							<div class="controls">
								<select id="create_car" name="car[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenanceLevel">保养类别</label>
							<div class="controls">
								<select id="create_maintenanceLevel" name="maintenanceLevel">
									<option value=0 title="">日常保养</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenanceDate">保养日期</label>
							<div class="controls">
								<input id="create_maintenanceDate" name="maintenanceDate" type="datetime">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenancePeople">保养人</label>
							<div class="controls">
								<input id="create_maintenancePeople" name="maintenancePeople" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="accepter">验收人</label>
							<div class="controls">
								<input id="create_accepter" name="accepter" type="text">
							</div>
						</div>
					</form>
				</div>
				<div id="create_maintenance-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="create_maintenance-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="create_maintenance-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 添加保养明细 -->
	<div id="create_maintenance_detail-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 添加保养明细
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create_maintenance_detail-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="checkItem">检查维修项目</label>
							<div class="controls">
								<input id="detail_checkItem" name="checkItem" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenanceWay">保养方式</label>
							<div class="controls">
								<input id="detail_maintenanceWay" name="maintenanceWay" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="treatment">检修处理情况</label>
							<div class="controls">
								<input id="detail_treatment" name="treatment" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="remark">备注</label>
							<div class="controls">
								<input id="detail_remark" name="remark" type="text">
							</div>
						</div>
						<input type="hidden" id="detail_maintenance" name="maintenance">
					</form>
				</div>
				<div id="create_maintenance_detail-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="create_maintenance_detail-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="create_maintenance_detail-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<div id="edit_maintenance_detail-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 添加保养明细
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="edit_maintenance_detail-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="checkItem">检查维修项目</label>
							<div class="controls">
								<input name="checkItem" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenanceWay">保养方式</label>
							<div class="controls">
								<input name="maintenanceWay" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="treatment">检修处理情况</label>
							<div class="controls">
								<input name="treatment" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="remark">备注</label>
							<div class="controls">
								<input name="remark" type="text">
							</div>
						</div>
						<input type="hidden" id="edit_detail_maintenance" name="maintenance">
						<input type="hidden" name="id">
					</form>
				</div>
				<div id="edit_maintenance_detail-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="edit_maintenance_detail-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="edit_maintenance_detail-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 删除保养单 -->
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
		seajs.use('${resources}/scripts/app/electr/maintenance/daily/index');
	</script>
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