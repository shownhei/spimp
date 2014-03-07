<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>设备参数查询 - 山西王庄煤业数字矿山综合管理平台</title>
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
				</div>
			</div>
			<div class="page-content">
				<div class="multi-query">
					<form id="query-form" class="form-inline" onsubmit="return false;">
						<div class="controls">
							<div class="input-append">
								<input name="startDate" type="datetime" placeholder="开始日期" class="input-small">
								<span class="add-on nav-add-on">
									<i class="icon-calendar"></i>
								</span>
							</div>
							<div class="input-append">
								<input name="endDate" type="datetime" placeholder="结束日期" class="input-small">
								<span class="add-on nav-add-on">
									<i class="icon-calendar"></i>
								</span>
							</div>
							<input name="deviceVersion" type="text" class="span2" placeholder="设备型号">
							<input name="electroVersion" type="text" class="span2" placeholder="机电型号">
							<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
							<button id="reset" type="reset" class="btn btn-primary btn-small">重置</button>
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
				<i class="icon-plus-sign-alt"></i> 新建
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label">设备型号</label>
							<div class="controls">
								<input name="deviceVersion" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">机电型号</label>
							<div class="controls">
								<input name="electroVersion" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定电流</label>
							<div class="controls">
								<input name="electricity" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定电压</label>
							<div class="controls">
								<input name="voltage" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定功率</label>
							<div class="controls">
								<input name="power" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定频率</label>
							<div class="controls">
								<input name="frequency" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">防爆合格证信息</label>
							<div class="controls">
								<input name="voltage" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定电压</label>
							<div class="controls">
								<input name="voltage" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定电压</label>
							<div class="controls">
								<input name="explosion" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">煤安标志信息</label>
							<div class="controls">
								<input name="mineSecurity" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">相数</label>
							<div class="controls">
								<input name="phase" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">出厂编号</label>
							<div class="controls">
								<input name="rolloutNum" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">尺寸</label>
							<div class="controls">
								<input name="size" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">出厂日期</label>
							<div class="controls">
								<input name="rolloutDate" type="datetime">
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
							<label class="control-label">设备型号</label>
							<div class="controls">
								<input name="deviceVersion" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">机电型号</label>
							<div class="controls">
								<input name="electroVersion" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定电流</label>
							<div class="controls">
								<input name="electricity" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定电压</label>
							<div class="controls">
								<input name="voltage" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定功率</label>
							<div class="controls">
								<input name="power" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定频率</label>
							<div class="controls">
								<input name="frequency" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">防爆合格证信息</label>
							<div class="controls">
								<input name="voltage" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定电压</label>
							<div class="controls">
								<input name="voltage" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定电压</label>
							<div class="controls">
								<input name="explosion" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">煤安标志信息</label>
							<div class="controls">
								<input name="mineSecurity" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">相数</label>
							<div class="controls">
								<input name="phase" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">出厂编号</label>
							<div class="controls">
								<input name="rolloutNum" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">尺寸</label>
							<div class="controls">
								<input name="size" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">出厂日期</label>
							<div class="controls">
								<input name="rolloutDate" type="datetime">
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
	<!-- 查看 -->
	<div id="view-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 查看
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="view-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label">设备型号</label>
							<div class="controls">
								<input name="deviceVersion" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">机电型号</label>
							<div class="controls">
								<input name="electroVersion" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定电流</label>
							<div class="controls">
								<input name="electricity" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定电压</label>
							<div class="controls">
								<input name="voltage" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定功率</label>
							<div class="controls">
								<input name="power" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定频率</label>
							<div class="controls">
								<input name="frequency" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">防爆合格证信息</label>
							<div class="controls">
								<input name="voltage" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定电压</label>
							<div class="controls">
								<input name="voltage" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">额定电压</label>
							<div class="controls">
								<input name="explosion" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">煤安标志信息</label>
							<div class="controls">
								<input name="mineSecurity" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">相数</label>
							<div class="controls">
								<input name="phase" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">出厂编号</label>
							<div class="controls">
								<input name="rolloutNum" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">尺寸</label>
							<div class="controls">
								<input name="size" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">出厂日期</label>
							<div class="controls">
								<input name="rolloutDate" type="text" readonly="readonly">
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
	<div id="remove-modal" class="modal modal-xs hide">
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
		seajs.use('${resources}/scripts/app/spmi/electro/query/index');
	</script>
</body>
</html>
