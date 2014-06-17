<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>历史曲线 - 山西王庄煤业数字矿山综合管理平台</title>
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
						<select id="monitorSensorType" name="monitorSensorType" style="width: 100px; float: left; margin-right: 5px">
						</select>
						<select id="nodePlace" name="nodePlace" style="width: 100px; float: left; margin-right: 5px" disabled title="先选择测点类型">
							<option value="">选择位置</option>
						</select>
						<div class="input-append" style="float: left; margin-right: 5px">
							<input name="date" type="datetime" placeholder="选择日期" class="input-small" autocomplete="off">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<button id="query" class="btn btn-small btn-primary">
							<i class="icon-search"></i> 查询
						</button>
					</form>
				</div>
			</div>
			<div class="page-content">	
				<div class="row" id="historyChart"/></div>
			</div>
		</div>
	</div>
	<script src="${resources}/scripts/app/common/echarts-plain.js" type="text/javascript"></script>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/monitor/curve/index');
	</script>
</body>
</html>
