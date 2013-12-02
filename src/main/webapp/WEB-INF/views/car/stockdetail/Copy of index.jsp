<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>故障管理 - 安全生产综合管理平台</title>
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
					<button id="export" class="btn btn-small btn-pink disabled">
						<i class="icon-download-alt"></i> 导出
					</button>
				</div>

				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
					    <input name="search" type="datetime" style="height: 20px; width: 130px; font-size: 12px;" placeholder="请输入年月">
						<input name="search" type="text" style="height: 15px; width: 130px; font-size: 12px;" placeholder="输入材料名称...">
						<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
						<button id="reset" type="reset" class="btn btn-primary btn-small">重置</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="table-responsive">
					<table id="sample-table-1" class="table table-striped table-bordered table-hover">
						<thead>
						     <tr>
								<th colspan=12 ><center>2013年8月份胶轮车配件进货使用剩余量明细表</center></th>
							</tr>
							<tr>
								<th colspan=4><center>胶轮车配件进货明细表</center></th>
								<th colspan=4><center>胶轮车材料使用明细表</center></th>
								<th colspan=4><center>胶轮车配件剩余量统计表</center></th>
							</tr>
							<tr>
								<th>物料名称</th>
								<th>单位</th>
								<th>数量</th>
								<th>备注</th>
								<th>物料名称</th>
								<th>单位</th>
								<th>数量</th>
								<th>备注</th>
								<th>物料名称</th>
								<th>单位</th>
								<th>数量</th>
								<th>备注</th>
							</tr>
						</thead>

						<tbody>
							<tr>
								<td><a href="#">ace.com</a></td>
								<td>$45</td>
								<td class="hidden-480">3,330</td>
								<td>Feb 12</td>
								<td><a href="#">ace.com</a></td>
								<td>$45</td>
								<td class="hidden-480">3,330</td>
								<td>Feb 12</td>
								<td><a href="#">ace.com</a></td>
								<td>$45</td>
								<td class="hidden-480">3,330</td>
								<td>Feb 12</td>
							</tr>

							<tr class="grid-row">
								<td><a href="#">base.com</a></td>
								<td>$35</td>
								<td class="hidden-480">2,595</td>
								<td>Feb 18</td>
								<td><a href="#">base.com</a></td>
								<td>$35</td>
								<td class="hidden-480">2,595</td>
								<td>Feb 18</td>
								<td><a href="#">base.com</a></td>
								<td>$35</td>
								<td class="hidden-480">2,595</td>
								<td>Feb 18</td>
							</tr>

						</tbody>
					</table>
				</div>
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
							<label class="control-label" for="materialName">材料名称</label>
							<div class="controls">
								<input id="create_materialName" name="materialName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="measureUnit">度量单位</label>
							<div class="controls">
								<input id="create_measureUnit" name="measureUnit" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="quantity">数量</label>
							<div class="controls">
								<input id="create_quantity" name="quantity" type="number">
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
							<label class="control-label" for="materialName">材料名称</label>
							<div class="controls">
								<input id="edit_materialName" name="materialName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="measureUnit">度量单位</label>
							<div class="controls">
								<input id="edit_measureUnit" name="measureUnit" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="quantity">数量</label>
							<div class="controls">
								<input id="edit_quantity" name="quantity" type="number">
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
							<label class="control-label" for="materialName">材料名称</label>
							<div class="controls">
								<input id="detail_materialName" name="materialName" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="measureUnit">度量单位</label>
							<div class="controls">
								<input id="detail_measureUnit" name="measureUnit" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="quantity">数量</label>
							<div class="controls">
								<input id="detail_quantity" name="quantity" type="text" readonly="readonly">
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
		seajs.use('${resources}/scripts/app/spmi/car/stockdetail/index');
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