<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>定期检修设置管理 - 安全生产综合管理平台</title>
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
					<button id="create_detail" class="btn btn-small btn-success">
						<i class="icon-plus-sign-alt"></i> 添加配件
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
						<select id="search_deviceClass" name="deviceClass" style="height: 25px; width: 120px; font-size: 12px;"></select> <select id="search_deviceCategory"
							name="deviceCategory" style="height: 25px; width: 120px; font-size: 12px;"></select> <select id="search_deviceType" name="deviceType"
							style="height: 25px; width: 120px; font-size: 12px;"></select> <select id="search_serviceEnvironment" name="serviceEnvironment"
							style="height: 25px; width: 120px; font-size: 12px;"></select> <select id="search_deviceArea" name="deviceArea"
							style="height: 25px; width: 120px; font-size: 12px;"></select> <select id="search_stowedPosition" name="stowedPosition"
							style="height: 25px; width: 120px; font-size: 12px;"></select>

						<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
						<button id="reset" type="reset" class="btn btn-primary btn-small">重置</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="material-table" style="scorll-x: auto;"></div>
				<div class="row-fluid" style="background-color: white; margin-left: 0px;" id="info_panel"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 新增设备
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="refugeType">设备分类</label>
								<div class="controls">
									<select id="create_deviceClass" name="deviceClass[id]" class="span2" style="width: 130px;"></select>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="quantity">设备种类</label>
								<div class="controls">
									<select id="create_deviceCategory" name="deviceCategory[id]" class="span2" style="width: 122px;"></select>
								</div>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="deviceType">设备类型</label>
							<div class="controls">
								<select id="create_deviceType" name="deviceType[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceName">设备名称</label>
							<div class="controls">
								<input id="create_deviceName" name="deviceName" type="text">
							</div>
						</div>

						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="deviceModel">设备型号</label>
								<div class="controls">
									<input name="deviceModel" type="text" class="span2" style="width: 130px;">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="serviceEnvironment">使用环境</label>
								<div class="controls">
									<select id="create_serviceEnvironment" name="serviceEnvironment[id]" class="span2" style="width: 122px;"></select>
								</div>
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="create_deviceArea">所属区域</label>
								<div class="controls">
									<select id="create_deviceArea" name="deviceArea[id]" class="span2" style="width: 130px;"></select>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="create_stowedPosition">存放地点</label>
								<div class="controls">
									<select id="create_stowedPosition" name="stowedPosition[id]" class="span2" style="width: 122px;"></select>
								</div>
							</div>
						</div>


						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="usage">用途</label>
								<div class="controls">
									<input id="create_usage" name="usage" type="text" class="span2" style="width: 130px;">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="producer">生产厂家</label>
								<div class="controls">
									<input name="producer" type="text" class="span2" style="width: 122px;">
								</div>
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="deviceNumber">设备编号</label>
								<div class="controls">
									<input id="create_deviceNumber" name="deviceNumber" type="text" class="span2" style="width: 130px;">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="factoryNumber">出厂编号</label>
								<div class="controls">
									<input id="create_factoryNumber" name="factoryNumber" type="text" class="span2" style="width: 122px;">
								</div>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="productionDate">出厂日期</label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="create_productionDate" name="productionDate">
							</div>
						</div>

						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="chargePerson">包机人</label>
								<div class="controls">
									<input id="create_chargePerson" name="chargePerson" type="text" class="span2" style="width: 130px;">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="monitor">班长/组长</label>
								<div class="controls">
									<input id="create_monitor" name="monitor" type="text" class="span2" style="width: 122px;">
								</div>
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="openLocker">三开一防锁</label>
								<div class="controls">
									<input name="openLocker" type="text" class="span2" style="width: 130px;">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="lockerNumber">数量</label>
								<div class="controls">
									<input name="lockerNumber" type="text" class="span2" style="width: 122px;">
								</div>
							</div>
						</div>

						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="chargePerson">速度</label>
								<div class="controls">
									<input name="speed" type="text" class="span2" style="width: 130px;">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="deliveryValue">运输量</label>
								<div class="controls">
									<input name="deliveryValue" type="text" class="span2" style="width: 122px;">
								</div>
							</div>
						</div>
						
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="layoutLength">布置长度</label>
								<div class="controls">
									<input name="layoutLength" type="text" class="span2" style="width: 130px;">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="status">是否已拆除</label>
								<div class="controls">
									<input name="status" type="text" class="span2" style="width: 122px;">
								</div>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="pictureURL">图片路径</label>
							<div class="controls">
								<input id="create_pictureURL" name="pictureURL" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="specificationURL">说明书路径</label>
							<div class="controls">
								<input id="create_specificationURL" name="specificationURL" type="text">
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
	<div id="create_detail-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 添加配件
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create_detail-form" class="form-horizontal" style="margin-bottom: 0px;">
						<input type="hidden" id="create_detail-equipmentId" name="equipmentId">
						<div class="control-group">
							<label class="control-label" for="accessoryModel">配件型号</label>
							<div class="controls">
								<input id="create_detail_accessoryModel" name="accessoryModel" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="accessoryNumber">配件编号</label>
							<div class="controls">
								<input id="create_detail_accessoryNumber" name="accessoryNumber" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="productionDate">出厂日期</label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="create_detail_productionDate" name="productionDate">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="producer">生产厂家</label>
							<div class="controls">
								<input id="create_detail_producer" name="producer" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="serviceRating">运输功率</label>
							<div class="controls">
								<input id="create_detail_serviceRating" name="serviceRating" type="number" value="0">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="transmissionRatio">传动比</label>
							<div class="controls">
								<input id="create_detail_transmissionRatio" name="transmissionRatio" type="number" value="0">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="remark">备注</label>
							<div class="controls">
								<input id="create_detail_remark" name="remark" type="text">
							</div>
						</div>
					</form>
				</div>
				<div id="create-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i> <span id="create_detail-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="create_detail-save" class="btn btn-small btn-success">
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
							<label class="control-label" for="deviceCategory">设备种类</label>
							<div class="controls">
								<select id="edit_deviceCategory" name="deviceCategory[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceType">设备类型</label>
							<div class="controls">
								<select id="edit_deviceType" name="deviceType[id]"></select>
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
							<label class="control-label" for="serviceEnvironment">使用环境</label>
							<div class="controls">
								<select id="edit_serviceEnvironment" name="serviceEnvironment[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceArea">所属区域</label>
							<div class="controls">
								<select id="edit_deviceArea" name="deviceArea[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="stowedPosition">存放地点</label>
							<div class="controls">
								<select id="edit_stowedPosition" name="stowedPosition[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usage">用途</label>
							<div class="controls">
								<input id="edit_usage" name="usage" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="producer">生产厂家</label>
							<div class="controls">
								<input id="edit_producer" name="producer" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceNumber">设备编号</label>
							<div class="controls">
								<input id="edit_deviceNumber" name="deviceNumber" type="text">
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
							<label class="control-label" for="chargePerson">包机人</label>
							<div class="controls">
								<input id="edit_chargePerson" name="chargePerson" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="monitor">班长/组长</label>
							<div class="controls">
								<input id="edit_monitor" name="monitor" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="openLocker"></label>
							<div class="controls">
								<input id="edit_openLocker" name="openLocker" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="lockerNumber"></label>
							<div class="controls">
								<input id="edit_lockerNumber" name="lockerNumber" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="speed">速度</label>
							<div class="controls">
								<input id="edit_speed" name="speed" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deliveryValue">运输量</label>
							<div class="controls">
								<input id="edit_deliveryValue" name="deliveryValue" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="layoutLength">布置长度</label>
							<div class="controls">
								<input id="edit_layoutLength" name="layoutLength" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="status">是否已拆除</label>
							<div class="controls">
								<input id="edit_status" name="status" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="pictureURL">图片路径</label>
							<div class="controls">
								<input id="edit_pictureURL" name="pictureURL" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="specificationURL">说明书路径</label>
							<div class="controls">
								<input id="edit_specificationURL" name="specificationURL" type="text">
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
					<form id="detail-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="deviceClass">设备分类</label>
							<div class="controls">
								<input id="detail_deviceClass" name="deviceClass" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceCategory">设备种类</label>
							<div class="controls">
								<input id="detail_deviceCategory" name="deviceCategory" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceType">设备类型</label>
							<div class="controls">
								<input id="detail_deviceType" name="deviceType" type="text" readonly="readonly">
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
							<label class="control-label" for="serviceEnvironment">使用环境</label>
							<div class="controls">
								<input id="detail_serviceEnvironment" name="serviceEnvironment" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceArea">所属区域</label>
							<div class="controls">
								<input id="detail_deviceArea" name="deviceArea" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="stowedPosition">存放地点</label>
							<div class="controls">
								<input id="detail_stowedPosition" name="stowedPosition" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="usage">用途</label>
							<div class="controls">
								<input id="detail_usage" name="usage" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="producer">生产厂家</label>
							<div class="controls">
								<input id="detail_producer" name="producer" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deviceNumber">设备编号</label>
							<div class="controls">
								<input id="detail_deviceNumber" name="deviceNumber" type="text" readonly="readonly">
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
							<label class="control-label" for="chargePerson">包机人</label>
							<div class="controls">
								<input id="detail_chargePerson" name="chargePerson" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="monitor">班长/组长</label>
							<div class="controls">
								<input id="detail_monitor" name="monitor" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="openLocker"></label>
							<div class="controls">
								<input id="detail_openLocker" name="openLocker" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="lockerNumber"></label>
							<div class="controls">
								<input id="detail_lockerNumber" name="lockerNumber" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="speed">速度</label>
							<div class="controls">
								<input id="detail_speed" name="speed" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="deliveryValue">运输量</label>
							<div class="controls">
								<input id="detail_deliveryValue" name="deliveryValue" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="layoutLength">布置长度</label>
							<div class="controls">
								<input id="detail_layoutLength" name="layoutLength" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="status">是否已拆除</label>
							<div class="controls">
								<input id="detail_status" name="status" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="pictureURL">图片路径</label>
							<div class="controls">
								<input id="detail_pictureURL" name="pictureURL" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="specificationURL">说明书路径</label>
							<div class="controls">
								<input id="detail_specificationURL" name="specificationURL" type="text" readonly="readonly">
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
		seajs.use('${resources}/scripts/app/electr/equipment/detail/index');
	</script>
</body>
</html>