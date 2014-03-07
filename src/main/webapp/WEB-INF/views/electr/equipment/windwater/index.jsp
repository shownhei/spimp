<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>井下消防设备台账管理 - 山西王庄煤业数字矿山综合管理平台</title>
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
					<button id="export" class="btn btn-small btn-pink disabled">
						<i class="icon-download-alt"></i> 导出
					</button>
				</div>
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<input name="search" type="text" style="height: 15px; width: 130px; font-size: 12px;" placeholder="输入安装位置/编号...">
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
							<label class="control-label" for="location">安装位置</label>
							<div class="controls">
								<input id="create_location" name="location" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="equipmentCode">编号</label>
							<div class="controls">
								<input id="create_equipmentCode" name="equipmentCode" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="windAmount">安装套数</label>
							<div class="controls">
								<input id="create_windAmount" name="windAmount" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="windCycle">维护周期</label>
							<div class="controls">
								<input id="create_windCycle" name="windCycle" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="waterAmount">安装套数</label>
							<div class="controls">
								<input id="create_waterAmount" name="waterAmount" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="waterCycle">维护周期</label>
							<div class="controls">
								<input id="create_waterCycle" name="waterCycle" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="chargePerson">负责人</label>
							<div class="controls">
								<input id="create_chargePerson" name="chargePerson" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="phoneNumber">电话号码</label>
							<div class="controls">
								<input id="create_phoneNumber" name="phoneNumber" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="remark">备注</label>
							<div class="controls">
								<input id="create_remark" name="remark" type="text">
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
							<label class="control-label" for="location">安装位置</label>
							<div class="controls">
								<input id="edit_location" name="location" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="equipmentCode">编号</label>
							<div class="controls">
								<input id="edit_equipmentCode" name="equipmentCode" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="windAmount">安装套数</label>
							<div class="controls">
								<input id="edit_windAmount" name="windAmount" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="windCycle">维护周期</label>
							<div class="controls">
								<input id="edit_windCycle" name="windCycle" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="waterAmount">安装套数</label>
							<div class="controls">
								<input id="edit_waterAmount" name="waterAmount" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="waterCycle">维护周期</label>
							<div class="controls">
								<input id="edit_waterCycle" name="waterCycle" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="chargePerson">负责人</label>
							<div class="controls">
								<input id="edit_chargePerson" name="chargePerson" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="phoneNumber">电话号码</label>
							<div class="controls">
								<input id="edit_phoneNumber" name="phoneNumber" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="remark">备注</label>
							<div class="controls">
								<input id="edit_remark" name="remark" type="text">
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
							<label class="control-label" for="location">安装位置</label>
							<div class="controls">
								<input id="detail_location" name="location" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="equipmentCode">编号</label>
							<div class="controls">
								<input id="detail_equipmentCode" name="equipmentCode" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="windAmount">安装套数</label>
							<div class="controls">
								<input id="detail_windAmount" name="windAmount" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="windCycle">维护周期</label>
							<div class="controls">
								<input id="detail_windCycle" name="windCycle" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="waterAmount">安装套数</label>
							<div class="controls">
								<input id="detail_waterAmount" name="waterAmount" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="waterCycle">维护周期</label>
							<div class="controls">
								<input id="detail_waterCycle" name="waterCycle" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="chargePerson">负责人</label>
							<div class="controls">
								<input id="detail_chargePerson" name="chargePerson" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="phoneNumber">电话号码</label>
							<div class="controls">
								<input id="detail_phoneNumber" name="phoneNumber" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="remark">备注</label>
							<div class="controls">
								<input id="detail_remark" name="remark" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="recordDate">记录日期</label>
							<div class="controls">
								<input id="detail_recordDate" name="recordDate" type="text" readonly="readonly">
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
		seajs.use('${resources}/scripts/app/electr/equipment/windwater/index');
	</script>
</body>
</html>