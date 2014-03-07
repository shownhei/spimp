<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>经验信息库 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar">
				<div class="nav-query">
					<form id="query-form" class="form-inline" onsubmit="return false;">
						<input name="content" type="text" placeholder="安全问题或隐患" class="input-medium">
						<input name="feedback" type="text" placeholder="反馈" class="input-medium">
						<div class="input-append">
							<input id="query-startDate" name="feedbackStartDate" type="datetime" placeholder="反馈开始日期" class="input-small" autocomplete="off">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<div class="input-append">
							<input id="query-endDate" name="feedbackEndDate" type="datetime" placeholder="反馈结束日期" class="input-small" autocomplete="off">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<button id="query-button" class="btn btn-small btn-primary">查询</button>
						<button id="reset-button" type="reset" class="btn btn-primary btn-small">重置</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="records"></div>
			</div>
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
			<table style="width: 100%" class="reform-table">
				<tbody>
					<tr>
						<td>安全问题或隐患</td>
						<td id="view-plan-content" colspan="5"></td>
					</tr>
					<tr>
						<td>创建时间</td>
						<td id="view-plan-createTime"></td>
						<td>创建人</td>
						<td id="view-plan-creater"></td>
						<td>执行人</td>
						<td id="view-plan-executor"></td>
					</tr>
					<tr>
						<td>反馈</td>
						<td id="view-plan-feedback" colspan="5"></td>
					</tr>
					<tr>
						<td>反馈时间</td>
						<td id="view-plan-feedbackTime"></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="modal-footer">
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 关闭
			</button>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/spmi/info/index');
	</script>
</body>
</html>
