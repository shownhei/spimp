<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>机电管理-车辆管理-运行日志管理 - 安全生产综合管理平台</title>
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
						<div class="input-append">
							<input name="startDate" type="datetime" placeholder="开始时间" class="input-small">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<div class="input-append">
							<input name="endDate" type="datetime" placeholder="结束时间" class="input-small">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<select id="search" name="search" style="width:120px;">
							 <option>零点班</option>
							 <option>四点班</option>
							 <option>八点班</option>
					    </select>
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
					<form id="create-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="car">车号</label>
							<div class="controls">
								<select id="create_car" name="car[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="classType">班次 </label>
							<div class="controls">
							    <select id="create_classType" name="classType" >
							       <option>零点班</option>
							       <option>四点班</option>
							       <option>八点班</option>
							    </select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="trainNumber">车次 </label>
							<div class="controls">
								<input id="create_trainNumber" name="trainNumber" type="number"  value="1">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="distance">路程 </label>
							<div class="controls">
								<input id="create_distance" name="distance" type="number"  value="0">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="refuelNumber">加油数 </label>
							<div class="controls">
								<input id="create_refuelNumber" name="refuelNumber" type="number" value="0">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="remark">备注 </label>
							<div class="controls">
								<input id="create_remark" name="remark" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="addDate">记录日期 </label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="create_addDate" name="addDate">
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
					<form id="edit-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="car">车号</label>
							<div class="controls">
								<select id="edit_car" name="car[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="classType">班次 </label>
							<div class="controls">
								<select id="edit_classType" name="classType" >
							       <option>零点班</option>
							       <option>四点班</option>
							       <option>八点班</option>
							    </select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="trainNumber">车次 </label>
							<div class="controls">
								<input id="edit_trainNumber" name="trainNumber" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="distance">路程 </label>
							<div class="controls">
								<input id="edit_distance" name="distance" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="refuelNumber">加油数 </label>
							<div class="controls">
								<input id="edit_refuelNumber" name="refuelNumber" type="number">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="remark">备注 </label>
							<div class="controls">
								<input id="edit_remark" name="remark" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="addDate">记录日期 </label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="edit_addDate" name="addDate">
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
							<label class="control-label" for="car">车号</label>
							<div class="controls">
								<input id="detail_car" name="car" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="classType">班次 </label>
							<div class="controls">
								<input id="detail_classType" name="classType" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="trainNumber">车次 </label>
							<div class="controls">
								<input id="detail_trainNumber" name="trainNumber" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="distance">路程 </label>
							<div class="controls">
								<input id="detail_distance" name="distance" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="refuelNumber">加油数 </label>
							<div class="controls">
								<input id="detail_refuelNumber" name="refuelNumber" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="remark">备注 </label>
							<div class="controls">
								<input id="detail_remark" name="remark" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="addDate">记录日期 </label>
							<div class="controls">
								<input id="detail_addDate" name="addDate" type="text" readonly="readonly">
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
		seajs.use('${resources}/scripts/app/electr/car/runlog/index');
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