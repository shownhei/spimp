<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>调度专业 - 安全生产综合管理平台</title>
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
					<button id="create" class="btn btn-small btn-success" title="新建评分表">
						<i class="icon-plus-sign-alt"></i>
						<span class="hidden-phone">评分</span>
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled">
						<i class="icon-edit"></i>
						<span class="hidden-phone">编辑</span>
					</button>
					<button id="remove" class="btn btn-small btn-danger disabled">
						<i class="icon-trash"></i>
						<span class="hidden-phone">删除</span>
					</button>
				</div>
				<div class="nav-search">
					<form id="query-form" class="form-inline" onsubmit="return false;">
						<select id="query-yearSelect" name="year" class="input-small">
							<option value="" class="light-grey">选择年份</option>
						</select>
						<select id="query-monthSelect" name="month" class="input-small">
							<option value="" class="light-grey">选择月份</option>
						</select>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="grade-table"></div>
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
					<form id="create-form" class="form-horizontal" onsubmit="return false;">
						<table class="table-col-4">
							<tbody>
								<tr>
									<td>
										<label>专业</label>
										<input name="category" readonly="readonly" value="调度">
									</td>
									<td>
										<label>年份</label>
										<select id="create-yearSelect" name="year">
										</select>
									</td>
									<td>
										<label>月份</label>
										<select id="create-monthSelect" name="month">
										</select>
									</td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<%@ include file="head-table.jsp"%>
						<div id="create-grade-record-table" style="height: 500px; overflow: auto;">
							<%@ include file="grade-table.jsp"%>
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
					<form id="edit-form" class="form-horizontal" onsubmit="return false;">
						<table class="table-col-4">
							<tbody>
								<tr>
									<td>
										<label>专业</label>
										<input name="category" readonly="readonly" value="调度">
									</td>
									<td>
										<label>年份</label>
										<select id="edit-yearSelect" name="year">
										</select>
									</td>
									<td>
										<label>月份</label>
										<select id="edit-monthSelect" name="month">
										</select>
									</td>
									<td>
										<label>合计</label>
										<input name="score" readonly="readonly">
									</td>
								</tr>
							</tbody>
						</table>
						<%@ include file="head-table.jsp"%>
						<div id="edit-grade-record-table" style="height: 500px; overflow: auto;">
							<%@ include file="grade-table.jsp"%>
						</div>
					</form>
				</div>
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
					<i class="icon-warning-sign"></i> 提示：确认删除选中的评分？
				</div>
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
		<div class="modal-footer">
			<button id="remove-save" class="btn btn-small btn-danger">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 查看 -->
	<div id="view-modal" class="modal modal-xl hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-list"></i> 查看
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="view-form" class="form-horizontal" onsubmit="return false;">
						<table class="table-col-4">
							<tbody>
								<tr>
									<td>
										<label>专业</label>
										<input name="category" readonly="readonly" value="调度">
									</td>
									<td>
										<label>年份</label>
										<input name="year" readonly="readonly">
									</td>
									<td>
										<label>月份</label>
										<input name="month" readonly="readonly">
									</td>
									<td>
										<label>合计</label>
										<input name="score" readonly="readonly">
									</td>
								</tr>
							</tbody>
						</table>
						<%@ include file="collect-table.jsp"%>
						<%@ include file="head-table.jsp"%>
						<div id="view-grade-record-table" style="height: 200px; overflow: auto;">
							<%@ include file="grade-table.jsp"%>
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
	<!-- 新建总表 -->
	<div id="collect-create-modal" class="modal modal-xl hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 新建
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="collect-create-form" class="form-horizontal" onsubmit="return false;">
						<%@ include file="collect-table.jsp"%>
					</form>
				</div>
				<div id="collect-create-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="collect-create-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="collect-create-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 编辑总表 -->
	<div id="collect-edit-modal" class="modal modal-xl hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-edit"></i> 编辑
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="collect-edit-form" class="form-horizontal" onsubmit="return false;">
						<%@ include file="collect-table.jsp"%>
					</form>
				</div>
				<div id="collect-edit-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="collect-edit-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="collect-edit-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/spmi/quality/dispatch/index');
	</script>
</body>
</html>
