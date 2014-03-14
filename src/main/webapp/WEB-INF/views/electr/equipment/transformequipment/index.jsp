<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>运输设备管理 - 山西王庄煤业数字矿山综合管理平台</title>
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
					<button id="download" class="btn btn-small btn-primary ">
						<i class="icon-download"></i> 下载模板
					</button>
					<button id="import" class="btn btn-small btn-primary ">
						<i class="icon-upload"></i> 导入
					</button>
					<button id="export" class="btn btn-small btn-pink disabled">
						<i class="icon-download-alt"></i> 导出全部
					</button>
				</div>
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<input name="search" type="text" style="height: 15px; width: 130px; font-size: 12px;" placeholder="输入设备名称/设备型号...">
						<select id="search_deviceClass" name="deviceClass" style="height: 25px; width: 120px; font-size: 12px;"></select>
						<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
						<button id="reset" type="reset" class="btn btn-primary btn-small">重置</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="material-table"></div>
				<br>
				<div class="tabbable" id="deviceInfoPanel"></div>
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
							<label class="control-label" for="deviceClass">设备分类</label>
							<div class="controls">
								<select id="create_deviceClass" name="deviceClass[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceName">设备名称</label>
							<div class="controls">
								<input id="create_deviceName" name="deviceName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceModel">设备型号</label>
							<div class="controls">
								<input id="create_deviceModel" name="deviceModel" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="speed">速度(m/s)</label>
							<div class="controls">
								<input id="create_speed" name="speed" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="conveyingCapacity">输送量(T/h)</label>
							<div class="controls">
								<input id="create_conveyingCapacity" name="conveyingCapacity" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="factoryNumber">出厂编号</label>
							<div class="controls">
								<input id="create_factoryNumber" name="factoryNumber" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="productionDate">出厂日期</label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="create_productionDate" name="productionDate">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="equipmentNumber">设备编号</label>
							<div class="controls">
								<input id="create_equipmentNumber" name="equipmentNumber" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="location">使用地点</label>
							<div class="controls">
								<input id="create_location" name="location" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="producer">生产厂家</label>
							<div class="controls">
								<input id="create_producer" name="producer" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="layoutLength">布置长度(m)</label>
							<div class="controls">
								<input id="create_layoutLength" name="layoutLength" type="number">
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
							<label class="control-label" for="deviceClass">设备分类</label>
							<div class="controls">
								<select id="edit_deviceClass" name="deviceClass[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceName">设备名称</label>
							<div class="controls">
								<input id="edit_deviceName" name="deviceName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceModel">设备型号</label>
							<div class="controls">
								<input id="edit_deviceModel" name="deviceModel" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="speed">速度(m/s)</label>
							<div class="controls">
								<input id="edit_speed" name="speed" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="conveyingCapacity">输送量(T/h)</label>
							<div class="controls">
								<input id="edit_conveyingCapacity" name="conveyingCapacity" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="factoryNumber">出厂编号</label>
							<div class="controls">
								<input id="edit_factoryNumber" name="factoryNumber" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="productionDate">出厂日期</label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="edit_productionDate" name="productionDate">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="equipmentNumber">设备编号</label>
							<div class="controls">
								<input id="edit_equipmentNumber" name="equipmentNumber" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="location">使用地点</label>
							<div class="controls">
								<input id="edit_location" name="location" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="producer">生产厂家</label>
							<div class="controls">
								<input id="edit_producer" name="producer" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="layoutLength">布置长度(m)</label>
							<div class="controls">
								<input id="edit_layoutLength" name="layoutLength" type="number">
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
							<label class="control-label" for="deviceClass">设备分类</label>
							<div class="controls">
								<input id="detail_deviceClass" name="deviceClass" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceName">设备名称</label>
							<div class="controls">
								<input id="detail_deviceName" name="deviceName" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceModel">设备型号</label>
							<div class="controls">
								<input id="detail_deviceModel" name="deviceModel" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="speed">速度(m/s)</label>
							<div class="controls">
								<input id="detail_speed" name="speed" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="conveyingCapacity">输送量(T/h)</label>
							<div class="controls">
								<input id="detail_conveyingCapacity" name="conveyingCapacity" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="factoryNumber">出厂编号</label>
							<div class="controls">
								<input id="detail_factoryNumber" name="factoryNumber" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="productionDate">出厂日期</label>
							<div class="controls">
								<input id="detail_productionDate" name="productionDate" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="equipmentNumber">设备编号</label>
							<div class="controls">
								<input id="detail_equipmentNumber" name="equipmentNumber" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="location">使用地点</label>
							<div class="controls">
								<input id="detail_location" name="location" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="producer">生产厂家</label>
							<div class="controls">
								<input id="detail_producer" name="producer" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="layoutLength">布置长度(m)</label>
							<div class="controls">
								<input id="detail_layoutLength" name="layoutLength" type="text" readonly="readonly">
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
		seajs.use('${resources}/scripts/app/electr/equipment/transformequipment/index');
	</script>
	<!-- 减速机新建 -->
	<div id="reducer_create-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 添加减速机
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="reducer_create-form" class="form-horizontal" style="margin-bottom: 0px;">
						<input type="hidden" id="reducer_create_id" name="id" />
						<div class="control-group">
							<label class="control-label" for="deviceModel">型号</label>
							<div class="controls">
								<input id="reducer_create_deviceModel" name="deviceModel" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="runningPower">运行功率</label>
							<div class="controls">
								<input id="reducer_create_runningPower" name="runningPower" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="transmissionRatio">传动比</label>
							<div class="controls">
								<input id="reducer_create_transmissionRatio" name="transmissionRatio" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="factoryNumber">出厂编号</label>
							<div class="controls">
								<input id="reducer_create_factoryNumber" name="factoryNumber" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="producer">生产厂家</label>
							<div class="controls">
								<input id="reducer_create_producer" name="producer" type="text">
							</div>
						</div>
					</form>
				</div>
				<div id="reducer_create-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="reducer_create-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="reducer_create-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 电动机新建 ElectromotorDevice-->
	<div id="electromotor_create-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 添加电动机
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="electromotor_create-form" class="form-horizontal" style="margin-bottom: 0px;">
						<input type="hidden" id="electromotor_create_id" name="id" />
						<div class="control-group">
							<label class="control-label" for="deviceModel">型号</label>
							<div class="controls">
								<textArea id="electromotor_create_deviceModel" name="deviceModel"></textArea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="factoryNumber">编号</label>
							<div class="controls">
								<input id="electromotor_create_factoryNumber" name="factoryNumber" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="productionDate">出厂日期</label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="electromotor_create_productionDate" name="productionDate">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="producer">生产厂家</label>
							<div class="controls">
								<input id="electromotor_create_producer" name="producer" type="text">
							</div>
						</div>
					</form>
				</div>
				<div id="electromotor_create-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="electromotor_create-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="electromotor_create-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 制动器新建BrakeDevice -->
	<div id="brake_create-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 添加制动器
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="brake_create-form" class="form-horizontal" style="margin-bottom: 0px;">
						<input type="hidden" id="brake_create_id" name="id" />
						<div class="control-group">
							<label class="control-label" for="deviceModel">型号</label>
							<div class="controls">
								<textArea id="brake_create_deviceModel" name="deviceModel"></textArea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="factoryNumber">编号</label>
							<div class="controls">
								<input id="brake_create_factoryNumber" name="factoryNumber" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="productionDate">出厂日期</label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="brake_create_productionDate" name="productionDate">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="producer">生产厂家</label>
							<div class="controls">
								<input id="brake_create_producer" name="producer" type="text">
							</div>
						</div>
					</form>
				</div>
				<div id="brake_create-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="brake_create-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="brake_create-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 拉紧装置新建 TensioningDevice-->
	<div id="tensioning_create-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 添加拉紧装置
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="tensioning_create-form" class="form-horizontal" style="margin-bottom: 0px;">
						<input type="hidden" id="tensioning_create_id" name="id" />
						<div class="control-group">
							<label class="control-label" for="takeUp">拉紧方式</label>
							<div class="controls">
								<input id="tensioning_create_takeUp" name="takeUp" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceName">装置名称</label>
							<div class="controls">
								<input id="tensioning_create_deviceName" name="deviceName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceModel">型号</label>
							<div class="controls">
								<textArea id="tensioning_create_deviceModel" name="deviceModel"></textArea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceNumber">编号</label>
							<div class="controls">
								<input id="tensioning_create_deviceNumber" name="deviceNumber" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="productionDate">出厂日期</label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="tensioning_create_productionDate" name="productionDate">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="producer">生产厂家</label>
							<div class="controls">
								<input id="tensioning_create_producer" name="producer" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="techParameters">技术参数</label>
							<div class="controls">
								<textArea id="tensioning_create_techParameters" name="techParameters"></textArea>
							</div>
						</div>
					</form>
				</div>
				<div id="tensioning_create-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="tensioning_create-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="tensioning_create-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<div id="show_tips"
		style="position: absolute; display: none; width: 200px; height: 130px; left: 0px; top: 0px; z-index: 800; box-shadow: 0 -2px 3px 0 rgba(0, 0, 0, 0.15); background-color: #FFF; border: 1px solid #999; padding: 2px; overflow: auto"></div>
	<!-- import导入 -->
	<div id="import-modal" class="modal hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-upload"></i> 导入
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="upload-data-form" class="form-horizontal" action="/electr/equipment/transform-equipments/upload" method="post" enctype="multipart/form-data"
						target="acceptFrame" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="location">模板下载</label>
							<div class="controls">
								<a href="${resources }/template/electr/equipment/transform_equipments_upload_tpl.xls" target="_blank">wenjian</a>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="equipmentCode">上传文件</label>
							<div class="controls">
								<input id="upload_data_file" name="file" type="file">
							</div>
						</div>
					</form>
				</div>
				<div id="import-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="import-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="import-save" class="btn btn-small btn-danger">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
</body>
</html>