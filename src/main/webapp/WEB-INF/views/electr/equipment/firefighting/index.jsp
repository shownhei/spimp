<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
						<input name="search" type="text"
							style="height: 15px; width: 130px; font-size: 12px;"
							placeholder="输入存在地点/编号...">
						<button id="submit" type="button"
							class="btn btn-primary btn-small">查询</button>
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
					<form id="create-form" class="form-horizontal"
						style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="location">存在地点</label>
							<div class="controls">
								<input id="create_location" name="location" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="equipmentCode">编号</label>
							<div class="controls">
								<input id="create_equipmentCode" name="equipmentCode"
									type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="sandBoxCapacity">沙箱容积</label>
							<div class="controls">
								<input id="create_sandBoxCapacity" name="sandBoxCapacity"
									type="number">
							</div>
						</div>

						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="amount1">数量(co2)</label>
								<div class="controls">
									<input id="create_amount1" name="amount1" type="text"
										class="span2" style="width: 130px;">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="maintenanceDate1">维修时间(co2)</label>
								<div class="controls">
									<input id="create_maintenanceDate1" name="maintenanceDate1"
										type="text" class="span2" style="width: 122px;">
								</div>
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="amount2">数量(干粉)</label>
								<div class="controls">
									<input id="create_amount2" name="amount2" type="text"
										class="span2" style="width: 130px;">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="maintenance2">维修时间(干粉)</label>
								<div class="controls">
									<input id="create_maintenance2" name="maintenance2" type="text"
										class="span2" style="width: 122px;">
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="fireAxe">消防斧</label>
							<div class="controls">
								<input id="create_fireAxe" name="fireAxe" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="fireHook">消防钩</label>
							<div class="controls">
								<input id="create_fireHook" name="fireHook" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="fireBucket">消防桶</label>
							<div class="controls">
								<input id="create_fireBucket" name="fireBucket" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="fireShovel">消防锹</label>
							<div class="controls">
								<input id="create_fireShovel" name="fireShovel" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="others">其他</label>
							<div class="controls">
								<input id="create_others" name="others" type="text">
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
					<form id="edit-form" class="form-horizontal"
						style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="location">存在地点</label>
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
							<label class="control-label" for="sandBoxCapacity">沙箱容积</label>
							<div class="controls">
								<input id="edit_sandBoxCapacity" name="sandBoxCapacity"
									type="number">
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="amount1">数量(co2)</label>
								<div class="controls">
									<input id="edit_amount1" name="amount1" type="text"
										class="span2" style="width: 130px;">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="maintenanceDate1">维修时间(co2)</label>
								<div class="controls">
									<input id="edit_maintenanceDate1" name="maintenanceDate1"
										type="text" class="span2" style="width: 122px;">
								</div>
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="amount2">数量(干粉)</label>
								<div class="controls">
									<input id="edit_amount2" name="amount2" type="text"
										class="span2" style="width: 130px;">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="maintenance2">维修时间(干粉)</label>
								<div class="controls">
									<input id="edit_maintenance2" name="maintenance2" type="datetime" 
										class="span2" style="width: 122px;">
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="fireAxe">消防斧</label>
							<div class="controls">
								<input id="edit_fireAxe" name="fireAxe" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="fireHook">消防钩</label>
							<div class="controls">
								<input id="edit_fireHook" name="fireHook" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="fireBucket">消防桶</label>
							<div class="controls">
								<input id="edit_fireBucket" name="fireBucket" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="fireShovel">消防锹</label>
							<div class="controls">
								<input id="edit_fireShovel" name="fireShovel" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="others">其他</label>
							<div class="controls">
								<input id="edit_others" name="others" type="text">
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
					<form id="detail-form" class="form-horizontal"
						style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="location">存在地点</label>
							<div class="controls">
								<input id="detail_location" name="location" type="text"
									readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="equipmentCode">编号</label>
							<div class="controls">
								<input id="detail_equipmentCode" name="equipmentCode"
									type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="sandBoxCapacity">沙箱容积</label>
							<div class="controls">
								<input id="detail_sandBoxCapacity" name="sandBoxCapacity"
									type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="amount1">数量</label>
							<div class="controls">
								<input id="detail_amount1" name="amount1" type="text"
									readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenanceDate1">维修时间</label>
							<div class="controls">
								<input id="detail_maintenanceDate1" name="maintenanceDate1"
									type="datetime" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="amount2">数量</label>
							<div class="controls">
								<input id="detail_amount2" name="amount2" type="text"
									readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="maintenance2">维修时间</label>
							<div class="controls">
								<input id="detail_maintenance2" name="maintenance2" type="text"
									readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="fireAxe">消防斧</label>
							<div class="controls">
								<input id="detail_fireAxe" name="fireAxe" type="text"
									readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="fireHook">消防钩</label>
							<div class="controls">
								<input id="detail_fireHook" name="fireHook" type="text"
									readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="fireBucket">消防桶</label>
							<div class="controls">
								<input id="detail_fireBucket" name="fireBucket" type="text"
									readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="fireShovel">消防锹</label>
							<div class="controls">
								<input id="detail_fireShovel" name="fireShovel" type="text"
									readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="others">其他</label>
							<div class="controls">
								<input id="detail_others" name="others" type="text"
									readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="recordDate">记录日期</label>
							<div class="controls">
								<input id="detail_recordDate" name="recordDate" type="text"
									readonly="readonly">
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
		seajs.use('${resources}/scripts/app/electr/equipment/firefighting/index');
	</script>
</body>
</html>