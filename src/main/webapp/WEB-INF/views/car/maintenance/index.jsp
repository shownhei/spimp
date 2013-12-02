<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>故障管理 - 安全生产综合管理平台</title>
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
					<button id="export" class="btn btn-small btn-pink disabled">
						<i class="icon-download-alt"></i> 导出
					</button>
				</div>
				
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<input name="search" type="text" style="height:15px;width:130px;font-size:12px;" placeholder="输入保养人...">
						<select id="search_car" name="car" style="height:25px;width:120px;font-size:12px;"></select>

						<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
						<button id="reset" type="reset" class="btn btn-primary btn-small">重置</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="material-table"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-sm hide">
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
							<label class="control-label" for="car">车牌号</label>
							<div class="controls">
								<select id="create_car" name="car[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenanceLevel">保养类别</label>
							<div class="controls">
								<input id="create_maintenanceLevel" name="maintenanceLevel" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenanceDate">保养日期</label>
							<div class="controls">
								<input id="create_maintenanceDate" name="maintenanceDate" type="text">
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
	<div id="edit-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-edit"></i> 编辑
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="edit-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="car">车牌号</label>
							<div class="controls">
								<select id="edit_car" name="car[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenanceLevel">保养类别</label>
							<div class="controls">
								<input id="edit_maintenanceLevel" name="maintenanceLevel" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenanceDate">保养日期</label>
							<div class="controls">
								<input id="edit_maintenanceDate" name="maintenanceDate" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenancePeople">保养人</label>
							<div class="controls">
								<input id="edit_maintenancePeople" name="maintenancePeople" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="accepter">验收人</label>
							<div class="controls">
								<input id="edit_accepter" name="accepter" type="text">
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
	<!-- 详细信息 -->
	<div id="detail-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-list"></i> 详细信息
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="detail-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="car">车牌号</label>
							<div class="controls">
								<input id="detail_car" name="car" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenanceLevel">保养类别</label>
							<div class="controls">
								<input id="detail_maintenanceLevel" name="maintenanceLevel" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenanceDate">保养日期</label>
							<div class="controls">
								<input id="detail_maintenanceDate" name="maintenanceDate" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenancePeople">保养人</label>
							<div class="controls">
								<input id="detail_maintenancePeople" name="maintenancePeople" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="accepter">验收人</label>
							<div class="controls">
								<input id="detail_accepter" name="accepter" type="text" readonly="readonly">
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
		seajs.use('${resources}/scripts/app/car/maintenance/index');
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