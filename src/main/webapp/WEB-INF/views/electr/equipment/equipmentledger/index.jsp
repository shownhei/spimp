<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>设备台账 - 安全生产综合管理平台</title>
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
					<button id="upload" class="btn btn-small btn-primary " data-param="">
						<i class="icon-upload"></i> 上传
					</button>
					<button id="download" class="btn btn-small btn-primary ">
						<i class="icon-download"></i> 模板下载
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
							placeholder="输入设备名称/设备编号/出厂编号/在籍..."> <select
							id="search_deviceClass" name="deviceClass"
							style="height: 25px; width: 120px; font-size: 12px;"></select>

						<button id="submit" type="button"
							class="btn btn-primary btn-small">查询</button>
						<button id="reset" type="reset" class="btn btn-primary btn-small">重置</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="material-table"></div>
				<div class="modal-body" id="detailInfo"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-xl hide">
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
						<table style="padding-left: 100px;">
							<tbody>
								<tr>
									<td style="width: 100px;">设备分类</td>
									<td style="width: 150px;"><select id="create_deviceClass"
										name="deviceClass[id]" style="width: 150px;" type="text"></select></td>
									<td style="width: 100px;">设备名称</td>
									<td style="width: 150px;"><input id="create_deviceName"
										name="deviceName" style="width: 150px;" type="text"></td>
									<td style="width: 100px;">设备编号</td>
									<td style="width: 150px;"><input id="create_equipmentID"
										name="equipmentID" style="width: 150px;" type="text"></td>
								</tr>

								<tr>
									<td style="width: 100px;">规格型号</td>
									<td style="width: 150px;"><input id="create_deviceModel"
										name="deviceModel" style="width: 150px;" type="text"></td>
									<td>生产厂家</td>
									<td><input id="create_producer" name="producer"
										style="width: 150px;" type="text"></td>
									<td>技术特征</td>
									<td><input id="create_technology" name="technology"
										style="width: 150px;" type="text"></td>
								</tr>

								<tr>
									<td>单位</td>
									<td><input id="create_measureUnit" name="measureUnit"
										style="width: 150px;" type="text"></td>
									<td>数量</td>
									<td><input id="create_amount" name="amount"
										style="width: 150px;" type="number"></td>
									<td>出厂日期</td>
									<td><input placeholder="请选择" class="input-small"
										autocomplete="off" id="create_productionDate"
										name="productionDate" style="width: 150px;" type="datetime"></td>
								</tr>

								<tr>
									<td>出厂编号</td>
									<td><input id="create_factoryNumber" name="factoryNumber"
										style="width: 150px;" type="text"></td>
									<td>购买日期</td>
									<td><input placeholder="请选择" class="input-small"
										autocomplete="off" id="create_buyDate" name="buyDate"
										style="width: 150px;" type="datetime"></td>
									<td>使用日期</td>
									<td><input placeholder="请选择" class="input-small"
										autocomplete="off" id="create_useDate" name="useDate"
										style="width: 150px;" type="datetime"></td>
								</tr>
								<tr>
									<td>使用年限</td>
									<td><input id="create_serviceLife" name="serviceLife"
										style="width: 150px;" type="number"></td>
									<td>在籍</td>
									<td><input id="create_inMembership" name="inMembership"
										style="width: 150px;" type="text"></td>
									<td>使用</td>
									<td><input id="create_inUse" name="inUse"
										style="width: 150px;" type="text"></td>
								</tr>
								<tr>
									<td>备用</td>
									<td><input id="create_isSpare" name="isSpare"
										style="width: 150px;" type="text"></td>
									<td>闲置</td>
									<td><input id="create_isIdle" name="isIdle"
										style="width: 150px;" type="text"></td>
									<td>待修</td>
									<td><input id="create_needsRepair" name="needsRepair"
										style="width: 150px;" type="text"></td>
								</tr>
								<tr>
									<td>待报废</td>
									<td><input id="create_prepareScrapped"
										name="prepareScrapped" style="width: 150px;" type="text"></td>
									<td>已报废</td>
									<td><input id="create_scrapped" name="scrapped"
										style="width: 150px;" type="text"></td>
									<td>借入</td>
									<td><input id="create_borrowed" name="borrowed"
										style="width: 150px;" type="text"></td>
								</tr>
								<tr>
									<td>借出</td>
									<td><input id="create_isLoan" name="isLoan"
										style="width: 150px;" type="text"></td>
									<td>主要附机</td>
									<td><input id="create_attachedDevice"
										name="attachedDevice" style="width: 150px;" type="text"></td>
									<td>原值</td>
									<td><input id="create_originalValue" name="originalValue"
										style="width: 150px;" type="text"></td>
								</tr>
								<tr>
									<td>净值</td>
									<td><input id="create_netWorth" name="netWorth"
										style="width: 150px;" type="text"></td>
									<td>使用地点</td>
									<td><input id="create_usePlace" name="usePlace"
										style="width: 150px;" type="text"></td>
									<td>是否防爆</td>
									<td><input id="create_explosionProof"
										name="explosionProof" style="width: 150px;" type="text"></td>
								</tr>
							</tbody>
						</table>
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
	<div id="edit-modal" class="modal modal-xl hide">
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
						<table style="padding-left: 100px;">
							<tbody>
								<tr>
									<td style="width: 100px;">设备分类</td>
									<td style="width: 150px;"><select id="edit_deviceClass"
										name="deviceClass[id]" style="width: 150px;" type="text"></select></td>

									<td style="width: 100px;">设备名称</td>
									<td style="width: 150px;"><input id="edit_deviceName"
										name="deviceName" style="width: 150px;" type="text"></td>
									<td style="width: 100px;">设备名称</td>
									<td style="width: 150px;"><input id="edit_deviceName"
										name="deviceName" style="width: 150px;" type="text"></td>
								</tr>

								<tr>
									<td style="width: 100px;">设备编号</td>
									<td style="width: 150px;"><input id="edit_equipmentID"
										name="equipmentID" style="width: 150px;" type="text"></td>
									<td style="width: 100px;">规格型号</td>
									<td style="width: 150px;"><input id="edit_deviceModel"
										name="deviceModel" style="width: 150px;" type="text"></td>
									<td>生产厂家</td>
									<td><input id="edit_producer" name="producer"
										style="width: 150px;" type="text"></td>
								</tr>

								<tr>
									<td>技术特征</td>
									<td><input id="edit_technology" name="technology"
										style="width: 150px;" type="text"></td>
									<td>单位</td>
									<td><input id="edit_measureUnit" name="measureUnit"
										style="width: 150px;" type="text"></td>
									<td>数量</td>
									<td><input id="edit_amount" name="amount"
										style="width: 150px;" type="number"></td>
								</tr>

								<tr>
									<td>出厂日期</td>
									<td><input placeholder="请选择" class="input-small"
										autocomplete="off" id="edit_productionDate"
										name="productionDate" style="width: 150px;" type="datetime"></td>
									<td>出厂编号</td>
									<td><input id="edit_factoryNumber" name="factoryNumber"
										style="width: 150px;" type="text"></td>
									<td>购买日期</td>
									<td><input placeholder="请选择" class="input-small"
										autocomplete="off" id="edit_buyDate" name="buyDate"
										style="width: 150px;" type="datetime"></td>
								</tr>
								<tr>
									<td>使用日期</td>
									<td><input placeholder="请选择" class="input-small"
										autocomplete="off" id="edit_useDate" name="useDate"
										style="width: 150px;" type="datetime"></td>
									<td>使用年限</td>
									<td><input id="edit_serviceLife" name="serviceLife"
										style="width: 150px;" type="number"></td>
									<td>在籍</td>
									<td><input id="edit_inMembership" name="inMembership"
										style="width: 150px;" type="text"></td>
								</tr>
								<tr>
									<td>使用</td>
									<td><input id="edit_inUse" name="inUse"
										style="width: 150px;" type="text"></td>
									<td>备用</td>
									<td><input id="edit_isSpare" name="isSpare"
										style="width: 150px;" type="text"></td>
									<td>闲置</td>
									<td><input id="edit_isIdle" name="isIdle"
										style="width: 150px;" type="text"></td>
								</tr>
								<tr>
									<td>待修</td>
									<td><input id="edit_needsRepair" name="needsRepair"
										style="width: 150px;" type="text"></td>
									<td>待报废</td>
									<td><input id="edit_prepareScrapped"
										name="prepareScrapped" style="width: 150px;" type="text"></td>
									<td>已报废</td>
									<td><input id="edit_scrapped" name="scrapped"
										style="width: 150px;" type="text"></td>
								</tr>
								<tr>
									<td>借入</td>
									<td><input id="edit_borrowed" name="borrowed"
										style="width: 150px;" type="text"></td>
									<td>借出</td>
									<td><input id="edit_isLoan" name="isLoan"
										style="width: 150px;" type="text"></td>
									<td>主要附机</td>
									<td><input id="edit_attachedDevice" name="attachedDevice"
										style="width: 150px;" type="text"></td>
								</tr>
								<tr>
									<td>原值</td>
									<td><input id="edit_originalValue" name="originalValue"
										style="width: 150px;" type="text"></td>
									<td>净值</td>
									<td><input id="edit_netWorth" name="netWorth"
										style="width: 150px;" type="text"></td>
									<td>使用地点</td>
									<td><input id="edit_usePlace" name="usePlace"
										style="width: 150px;" type="text"></td>
								</tr>
								<tr>
									<td>是否防爆</td>
									<td><input id="edit_explosionProof" name="explosionProof"
										style="width: 150px;" type="text"></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
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
		seajs.use('${resources}/scripts/app/electr/equipment/equipmentledger/index');
	</script>
</body>
</html>