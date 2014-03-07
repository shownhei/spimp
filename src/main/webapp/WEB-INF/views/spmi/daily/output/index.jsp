<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>产量统计 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="../../../common/head.jsp"%>
<%@ include file="../../../common/template.jsp"%>
<script src="${resources}/scripts/app/common/echarts-plain.js" type="text/javascript"></script>
</head>
<body class="navbar-fixed">
	<%@ include file="../../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar">
				<div class="nav-query">
					<form id="query-form" class="form-inline" onsubmit="return false;">
						<div class="input-append">
							<input id="startDate" name="startDate" type="datetime" placeholder="开始日期" class="input-small" autocomplete="off">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<div class="input-append">
							<input id="endDate" name="endDate" type="datetime" placeholder="结束日期" class="input-small" autocomplete="off">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<button id="query" class="btn btn-small btn-primary">
							<i class="icon-search"></i> 统计
						</button>
						<div id="message" class="alert hide" style="padding: 2px; font-size: 12px">请选择日期范围</div>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid">
					<div id="chart1" class="span12"></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/spmi/daily/output/index');
	</script>
</body>
</html>
