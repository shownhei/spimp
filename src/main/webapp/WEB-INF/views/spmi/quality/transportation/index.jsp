<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="category" value="运输" />
<!DOCTYPE HTML>
<html>
<head>
<title>${category}专业 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="../../../common/head.jsp"%>
<%@ include file="../../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../../common/sidebar.jsp"%>
		<div class="main-content">
			<%@ include file="../common/toolbar-calculate.jsp"%>
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
										<input name="category" readonly="readonly" value="${category}">
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
								<tr>
									<td colspan="2">
										<label style="width: 100px">运输方式</label>
										<input name="way" type="text" style="width: 310px">
									</td>
									<td colspan="2">
										<label style="width: 100px">设备检测检验</label>
										<input name="checkout" type="text" style="width: 310px">
									</td>
								</tr>
								<tr>
									<td>
										<label style="width: 100px">设备综合完好率</label>
										<input name="synthesize" type="text" style="width: 95px">
									</td>
									<td>
										<label style="width: 100px">矿车完好率</label>
										<input name="tramcar" type="text" style="width: 95px">
									</td>
									<td>
										<label style="width: 100px">专用车辆完好率</label>
										<input name="specialCar" type="text" style="width: 95px">
									</td>
									<td>
										<label style="width: 100px">完全生产</label>
										<input name="production" type="text" style="width: 95px">
									</td>
								</tr>
							</tbody>
						</table>
						<%@ include file="../common/head-table.jsp"%>
						<div id="create-grade-record-table" style="height: 430px; overflow: auto;">
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
										<input name="category" readonly="readonly" value="${category}">
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
								<tr>
									<td colspan="2">
										<label style="width: 100px">运输方式</label>
										<input name="way" type="text" style="width: 310px">
									</td>
									<td colspan="2">
										<label style="width: 100px">设备检测检验</label>
										<input name="checkout" type="text" style="width: 310px">
									</td>
								</tr>
								<tr>
									<td>
										<label style="width: 100px">设备综合完好率</label>
										<input name="synthesize" type="text" style="width: 95px">
									</td>
									<td>
										<label style="width: 100px">矿车完好率</label>
										<input name="tramcar" type="text" style="width: 95px">
									</td>
									<td>
										<label style="width: 100px">专用车辆完好率</label>
										<input name="specialCar" type="text" style="width: 95px">
									</td>
									<td>
										<label style="width: 100px">完全生产</label>
										<input name="production" type="text" style="width: 95px">
									</td>
								</tr>
							</tbody>
						</table>
						<%@ include file="../common/head-table.jsp"%>
						<div id="edit-grade-record-table" style="height: 430px; overflow: auto;">
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
										<input name="category" readonly="readonly" value="${category}">
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
								<tr>
									<td colspan="2">
										<label style="width: 100px">运输方式</label>
										<input name="way" readonly="readonly" type="text" style="width: 310px">
									</td>
									<td colspan="2">
										<label style="width: 100px">设备检测检验</label>
										<input name="checkout" readonly="readonly" type="text" style="width: 310px">
									</td>
								</tr>
								<tr>
									<td>
										<label style="width: 100px">设备综合完好率</label>
										<input name="synthesize" readonly="readonly" type="text" style="width: 95px">
									</td>
									<td>
										<label style="width: 100px">矿车完好率</label>
										<input name="tramcar" readonly="readonly" type="text" style="width: 95px">
									</td>
									<td>
										<label style="width: 100px">专用车辆完好率</label>
										<input name="specialCar" readonly="readonly" type="text" style="width: 95px">
									</td>
									<td>
										<label style="width: 100px">完全生产</label>
										<input name="production" readonly="readonly" type="text" style="width: 95px">
									</td>
								</tr>
							</tbody>
						</table>
						<%@ include file="../common/head-table.jsp"%>
						<div id="view-grade-record-table" style="height: 430px; overflow: auto;">
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
	<!-- 统计 -->
	<div id="calculate-modal" class="modal modal-el hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-table"></i> 统计
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="calculate-form" class="form-horizontal" onsubmit="return false;">
						<div class="row-fluid">
							<div class="span12">
								<span>年份</span>
								<select id="calculate-yearSelect" name="year">
								</select>
								<span>月份</span>
								<select id="calculate-monthSelect" name="month">
								</select>
								<button id="calculate-query" class="btn btn-small btn-success">
									<i class="icon-table"></i>
									<span class="hidden-phone">统计</span>
								</button>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span12">
								<table class="grade-table" style="width: 1020px; margin-top: 5px; margin-bottom: 2px">
									<thead>
										<tr>
											<th rowspan="3">序号</th>
											<th rowspan="3">运输方式</th>
											<th rowspan="3">设备检测检验</th>
											<th colspan="3">运输三率</th>
											<th rowspan="3">完全生产</th>
											<th colspan="6">小项得分</th>
											<th rowspan="2">总分</th>
										</tr>
										<tr>
											<th rowspan="2">设备综合完好率</th>
											<th rowspan="2">矿车完好率</th>
											<th rowspan="2">专用车辆完好率</th>
											<th>巷道硐室</th>
											<th>运输线路</th>
											<th>运输设备</th>
											<th>运输安全设施</th>
											<th>文明生产</th>
											<th>运输管理</th>
										</tr>
										<tr>
											<th>5</th>
											<th>30</th>
											<th>22</th>
											<th>20</th>
											<th>5</th>
											<th>18</th>
											<th>100</th>
										</tr>
									</thead>
									<tbody id="calculate-result"></tbody>
								</table>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div id="calculate-message-alert" class="row-fluid hide">
				<div class="span12">
					<div class="alert alert-error">
						<i class="icon-remove"></i>
						<span id="calculate-message-content"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 关闭
			</button>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/spmi/quality/transportation/index');
	</script>
</body>
</html>
