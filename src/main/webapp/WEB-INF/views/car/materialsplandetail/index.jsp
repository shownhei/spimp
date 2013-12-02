<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>材料计划明细管理 - 安全生产综合管理平台</title>
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
						<select id="search_plan" name="plan" style="height:25px;width:120px;font-size:12px;"></select>
						<input name="search" type="text" style="height:15px;width:130px;font-size:12px;" placeholder="材料名称/规格型号、设备号...">
						<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
						<button id="reset" type="reset" class="btn btn-primary btn-small">重置</button>
					</form>
				</div>
			</div>
			<div class="page-content">
			    <div class="multi-query">
					<form id="query-form" class="form-inline" onsubmit="return false;">
						<div class="controls">
						    <select id="plan" name="plan[id]"  class="span2"  style="height:25px;width:120px;font-size:12px;"></select>
							<input id="planDate" name="planDate" value="" type="datetime" class="span2" placeholder="申请日期">
							<input id="planTitle" name="planTitle" type="text" class="span2" value="胶轮车材料申请计划" placeholder="胶轮车材料申请计划">
							<select id="create-groupEntity" name="create-groupEntity[id]"  class="span2"  style="height:25px;width:120px;font-size:12px;"></select>
						</div>
					</form>
				</div>
				<div class="row-fluid" id="material-table"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 添加明细
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal" style="margin-bottom: 0px;">
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
							<label class="control-label" for="price">数量</label>
							<div class="controls">
								<input id="create_quantity" name="quantity" type="number" value=1>
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
			<button id="create-saveandclose" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 保存并关闭
			</button>
			<button id="create-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 保存
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
							<label class="control-label" for="plan">单位</label>
							<div class="controls">
								<select id="edit_plan" name="plan[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="materialName">材料名称</label>
							<div class="controls">
								<input id="edit_materialName" name="materialName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="model">规格型号、设备号</label>
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
								<input id="edit_price" name="price" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="remark">单价（元）</label>
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
	<div id="detail-modal" class="modal modal-md hide">
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
							<label class="control-label" for="plan">单位</label>
							<div class="controls">
								<input id="detail_plan" name="plan" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="materialName">材料名称</label>
							<div class="controls">
								<input id="detail_materialName" name="materialName" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="model">规格型号、设备号</label>
							<div class="controls">
								<input id="detail_model" name="model" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="measureUnit">度量单位</label>
							<div class="controls">
								<input id="detail_measureUnit" name="measureUnit" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="price">单价（元）</label>
							<div class="controls">
								<input id="detail_price" name="price" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="remark">单价（元）</label>
							<div class="controls">
								<input id="detail_remark" name="remark" type="text" readonly="readonly">
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
		seajs.use('${resources}/scripts/app/spmi/car/materialsplandetail/index');
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