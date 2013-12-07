<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>材料计划管理 - 安全生产综合管理平台</title>
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
					<button id="create_plan" class="btn btn-small btn-success" title="创建申请计划">
						<i class="icon-plus-sign-alt"></i> 创建计划
					</button>
					<button id="add_detail" class="btn btn-small btn-primary" title="添加采购明细">
						<i class="icon-edit"></i> 添加明细
					</button>
					<button id="remove_plan" class="btn btn-small btn-danger disabled" title="删除当前计划">
						<i class="icon-trash"></i> 删除计划
					</button>
					<button id="export_plan" class="btn btn-small btn-pink disabled" title="导出当前计划">
						<i class="icon-download-alt"></i> 导出本计划
					</button>
				</div>

				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<input id="query_year" name="year" type="number" style="height: 15px; width: 130px; font-size: 12px;" value="2013" /> <select id="query_month"
							name="month" style="height: 25px; width: 130px; font-size: 12px;">
							<option value="1">1月份</option>
							<option value="2">2月份</option>
							<option value="3">3月份</option>
							<option value="4">4月份</option>
							<option value="5">5月份</option>
							<option value="6">6月份</option>
							<option value="7">7月份</option>
							<option value="8">8月份</option>
							<option value="9">9月份</option>
							<option value="10">10月份</option>
							<option value="11">11月份</option>
							<option value="12">12月份</option>
						</select>
						<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
					</form>
				</div>
			</div>
			<div class="page-content" id="tablePanel" style="overflow-x:auto;"></div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create_plan-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 新建
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create_plan-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="planDate">计划日期</label>
							<div class="controls">
								<input type="text" placeholder="请选择" class="input-small" autocomplete="off" id="create_planDate" name="planDate">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="planGroup">单位</label>
							<div class="controls">
								<select id="planGroup" name="planGroup[id]" type="text"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="planTitle">计划名称</label>
							<div class="controls">
								<input id="create_planTitle" name="planTitle" type="text" value="胶轮车材料申请计划">
							</div>
						</div>
					</form>
				</div>
				<div id="create_plan-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i> <span id="create_plan-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="create_plan-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 添加明细 -->
	<div id="add_detail-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 添加明细
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="add_detail-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="materialName">材料名称</label>
							<div class="controls">
								<input id="create_materialName" name="materialName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="model">规格型号/设备号</label>
							<div class="controls">
								<input id="create_model" name="model" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="measureUnit">度量单位</label>
							<div class="controls">
								<input id="create_measureUnit" name="measureUnit" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="price">单价（元）</label>
							<div class="controls">
								<input id="create_price" name="price" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="quantity">数量</label>
							<div class="controls">
								<input id="create_quantity" name="quantity" type="number" value=1>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="sumMoney">金额</label>
							<div class="controls">
								<input id="create_sumMoney" name="sumMoney" type="number" value=1>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="remark">备注</label>
							<div class="controls">
								<input id="create_remark" name="remark" type="text">
							</div>
						</div>
						<input type="hidden" id="add_detail-plan" name="plan">
					</form>
				</div>
				<div id="add_detail-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i> <span id="add_detail-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="add_detail-saveandclose" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 保存并关闭
			</button>
			<button id="add_detail-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 保存
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 编辑明细 -->
	<div id="edit_detail-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-edit"></i> 编辑
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="edit_detail-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="materialName">材料名称</label>
							<div class="controls">
								<input id="edit_materialName" name="materialName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="model">规格型号/设备号</label>
							<div class="controls">
								<input id="edit_model" name="model" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="measureUnit">度量单位</label>
							<div class="controls">
								<input id="edit_measureUnit" name="measureUnit" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="price">单价（元）</label>
							<div class="controls">
								<input id="edit_price" name="price" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="price">数量</label>
							<div class="controls">
								<input id="edit_price" name="price" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="sumMoney">金额</label>
							<div class="controls">
								<input id="edit_sumMoney" name="sumMoney" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="remark">备注</label>
							<div class="controls">
								<input id="edit_remark" name="remark" type="text">
							</div>
						</div>
						<input type="hidden" id="edit_detail-plan" name="plan"> <input type="hidden" name="id">
					</form>
				</div>
				<div id="edit_detail-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i> <span id="edit_detail-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="edit_detail-save" class="btn btn-small btn-primary">
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
					<i class="icon-warning-sign"></i> 提示：确定要删除本计划？
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
		seajs.use('${resources}/scripts/app/electr/material/plan/index');
	</script>
</body>
</html>