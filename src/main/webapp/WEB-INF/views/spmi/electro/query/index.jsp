<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>现场处置方案 - 安全生产综合管理平台</title>
<%@ include file="../../../common/head.jsp"%>
<%@ include file="../../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../../common/sidebar.jsp"%>
		<div class="main-content">
			<!-- 		<div class="page-toolbar">
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
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<span class="input-icon"> <input id="nav-search-input"
							name="search" type="text" placeholder="输入类别/制定人..."
							class="input-small nav-search-input" autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div> -->
			<div class="page-content">
				<form id="query-form">
					<div class="row-fluid">
						<div class="span3">
							<input type="text" name="deviceVersion" style="height: 20px;"
								placeholder="设备型号">
						</div>
						<div class="span3">
							<input type="text" name="electroVersion" style="height: 20px;"
								placeholder="机电型号">
						</div>
						<div class="span3">
							<input type="text" name="electricity" style="height: 20px;"
								placeholder="额定电流">
						</div>
						<div class="span3">
							<input type="text" name="voltage" style="height: 20px;"
								placeholder="额定电压">
						</div>
					</div>
					<div class="row-fluid">
						<div class="span3">
							<input type="text" name="power" style="height: 20px;"
								placeholder="额定功率">
						</div>
						<div class="span3">
							<input type="text" name="frequency" style="height: 20px;"
								placeholder="额定频率">
						</div>
						<div class="span3">
							<input type="text" name="explosion" style="height: 20px;"
								placeholder="防爆合格证信息">
						</div>
						<div class="span3">
							<input type="text" name="mineSecurity" style="height: 20px;"
								placeholder="煤安标志信息">
						</div>
					</div>
					<div class="row-fluid">
						<div class="span3">
							<input type="text" name="phase" style="height: 20px;"
								placeholder="相数">
						</div>
						<div class="span3">
							<input type="text" name="rolloutNum" style="height: 20px;"
								placeholder="出厂编号">
						</div>
						<div class="span3">
							<input type="text" name="size" style="height: 20px;"
								placeholder="外形尺寸">
						</div>
					</div>
				</form>
				<div class="btn-toolbar">
					<button id="create" class="btn btn-small btn-success">
						<i class="icon-plus-sign-alt"></i> 新建
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled">
						<i class="icon-edit"></i> 编辑
					</button>
					<button id="remove" class="btn btn-small btn-danger disabled">
						<i class="icon-trash"></i> 删除
					</button>
					<button id="submit" type="button"
						class="btn btn-primary btn-small pull-right">查询</button>
					<button id="reset" type="button"
						class="btn btn-primary btn-small pull-right">重置</button>
					<div class="input-append pull-right">
						<input id="endTime" name="endDate" type="datetime"
							placeholder="结束日期" class="input-small" autocomplete="off">
						<span class="add-on nav-add-on"> <i class="icon-calendar"></i>
						</span>
					</div>
					<div class="input-append pull-right">
						<input id="startTime" name="startDate" type="datetime"
							placeholder="开始日期" class="input-small" autocomplete="off">
						<span class="add-on nav-add-on"> <i class="icon-calendar"></i>
						</span>
					</div>
				</div>
			</div>
			<div class="row-fluid" id="material-table"></div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal hide">
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
							<label class="control-label span2" for="principal">设备型号</label>
							<div class="controls">
								<input id="deviceVersion" name="deviceVersion" type="text"
									class="span11">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label span2" for="principal">机电型号</label>
							<div class="controls">
								<input id="electroVersion" name="electroVersion" type="text"
									class="span11">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label span2" for="principal">额定电流</label>
							<div class="controls">
								<input id="electricity" name="electricity" type="text"
									class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定电压</label>
							<div class="controls">
								<input id="voltage" name="voltage" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定功率</label>
							<div class="controls">
								<input id="power" name="power" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定频率</label>
							<div class="controls">
								<input id="frequency" name="frequency" type="text"
									class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span3" for="principal">防爆合格证信息</label>
							<div class="controls">
								<input id="explosion" name="voltage" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定电压</label>
							<div class="controls">
								<input id="voltage" name="voltage" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定电压</label>
							<div class="controls">
								<input id="voltage" name="explosion" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span3" for="principal">煤安标志信息</label>
							<div class="controls">
								<input id="mineSecurity" name="mineSecurity" type="text"
									class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">相数</label>
							<div class="controls">
								<input id="phase" name="phase" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">出厂编号</label>
							<div class="controls">
								<input id="rolloutNum" name="rolloutNum" type="text"
									class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">尺寸</label>
							<div class="controls">
								<input id="size" name="size" type="text" class="span11">
							</div>
						</div>
						<div class="input-append">
							<label class="control-label span3" for="principal">出厂日期</label>
							<div class="controls">
								<input name="rolloutDate" type="datetime" placeholder="出厂日期"
									class="input-small" autocomplete="off"> <span
									class="add-on nav-add-on"> <i class="icon-calendar"></i>
								</span>
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
	<!-- 查看 -->
	<div id="view-modal" class="modal hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 查看
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="view-form" class="form-horizontal"
						style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label span2" for="principal">设备型号</label>
							<div class="controls">
								<input id="deviceVersion" name="deviceVersion" type="text"
									readonly="readonly" class="span11">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label span2" for="principal">机电型号</label>
							<div class="controls">
								<input id="electroVersion" name="electroVersion" type="text"
									readonly="readonly" class="span11">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label span2" for="principal">额定电流</label>
							<div class="controls">
								<input id="electricity" name="electricity" type="text"
									readonly="readonly" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定电压</label>
							<div class="controls">
								<input id="voltage" name="voltage" type="text"
									readonly="readonly" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定功率</label>
							<div class="controls">
								<input id="power" name="power" type="text" readonly="readonly"
									class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定频率</label>
							<div class="controls">
								<input id="frequency" name="frequency" type="text"
									readonly="readonly" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span3" for="principal">防爆合格证信息</label>
							<div class="controls">
								<input id="explosion" name="voltage" type="text"
									readonly="readonly" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定电压</label>
							<div class="controls">
								<input id="voltage" name="voltage" type="text"
									readonly="readonly" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定电压</label>
							<div class="controls">
								<input id="voltage" name="explosion" type="text"
									readonly="readonly" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span3" for="principal">煤安标志信息</label>
							<div class="controls">
								<input id="mineSecurity" name="mineSecurity" type="text"
									readonly="readonly" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">相数</label>
							<div class="controls">
								<input id="phase" name="phase" type="text" readonly="readonly"
									class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">出厂编号</label>
							<div class="controls">
								<input id="rolloutNum" name="rolloutNum" type="text"
									readonly="readonly" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">尺寸</label>
							<div class="controls">
								<input id="size" name="size" type="text" readonly="readonly"
									class="span11">
							</div>
						</div>
						<div class="input-append">
							<label class="control-label span2" for="principal">出厂日期</label>
							<div class="controls">
								<input name="rolloutDate" type="datetime" placeholder="出厂日期"
									readonly="readonly" class="input-small" autocomplete="off">
								<span class="add-on nav-add-on"> <i class="icon-calendar"></i>
								</span>
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
	<!-- 编辑 -->
	<div id="edit-modal" class="modal hide">
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
							<label class="control-label span2" for="principal">设备型号</label>
							<div class="controls">
								<input id="deviceVersion" name="deviceVersion" type="text"
									class="span11">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label span2" for="principal">机电型号</label>
							<div class="controls">
								<input id="electroVersion" name="electroVersion" type="text"
									class="span11">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label span2" for="principal">额定电流</label>
							<div class="controls">
								<input id="electricity" name="electricity" type="text"
									class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定电压</label>
							<div class="controls">
								<input id="voltage" name="voltage" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定功率</label>
							<div class="controls">
								<input id="power" name="power" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定频率</label>
							<div class="controls">
								<input id="frequency" name="frequency" type="text"
									class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span3" for="principal">防爆合格证信息</label>
							<div class="controls">
								<input id="explosion" name="voltage" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定电压</label>
							<div class="controls">
								<input id="voltage" name="voltage" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">额定电压</label>
							<div class="controls">
								<input id="voltage" name="explosion" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span3" for="principal">煤安标志信息</label>
							<div class="controls">
								<input id="mineSecurity" name="mineSecurity" type="text"
									class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">相数</label>
							<div class="controls">
								<input id="phase" name="phase" type="text" class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">出厂编号</label>
							<div class="controls">
								<input id="rolloutNum" name="rolloutNum" type="text"
									class="span11">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">尺寸</label>
							<div class="controls">
								<input id="size" name="size" type="text" class="span11">
							</div>
						</div>
						<div class="input-append">
							<label class="control-label span2" for="principal">出厂日期</label>
							<div class="controls">
								<input name="rolloutDate" type="datetime" placeholder="出厂日期"
									class="input-small" autocomplete="off"> <span
									class="add-on nav-add-on"> <i class="icon-calendar"></i>
								</span>
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
		seajs.use('${resources}/scripts/app/spmi/electro/query/index');
	</script>
</body>
</html>
