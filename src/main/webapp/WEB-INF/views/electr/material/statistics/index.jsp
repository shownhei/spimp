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
						<input id="query_year" name="year" type="number" style="height: 15px; width: 130px; font-size: 12px;" value="2013" /> <select id="query_month"
							name="month" style="height: 25px; width: 130px; font-size: 12px;">
							<option value="1">1月份</option>
							<option value="2">2月份</option>
							<option value="3">3月份</option>
							<option value="4">4月份</option>
							<option value="5">5月份</option>
							<option value="6">6月份</option>
							<option value="7">7月份</option>
							<option value="8">8月份</option>
							<option value="9">9月份</option>
							<option value="10">10月份</option>
							<option value="11">11月份</option>
							<option value="12">12月份</option>
						</select>
						<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="table-responsive" id="table_panel"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/electr/material/statistics/index');
	</script>
</body>
</html>