<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>年度公里统计 - 安全生产综合管理平台</title>
<%@ include file="../../../common/head.jsp"%>
<%@ include file="../../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar">
				<div class="nav-search">
					<form id="query-form" class="form-search" onsubmit="return false;">
						<select id="search_car" name="car" style="height: 25px; width: 120px; font-size: 12px;"></select> <input id="query_year" name="year" type="number"
							style="height: 15px; width: 130px; font-size: 12px;" value="2013" /> <select id="query_month" name="month"
							style="height: 25px; width: 130px; font-size: 12px;">
							<option value="1">1月</option>
							<option value="2">2月</option>
							<option value="3">3月</option>
							<option value="4">4月</option>
							<option value="5">5月</option>
							<option value="6">6月</option>
							<option value="7">7月</option>
							<option value="8">8月</option>
							<option value="9">9月</option>
							<option value="10">10月</option>
							<option value="11">11月</option>
							<option value="12">12月</option>
						</select>
						<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
					</form>
				</div>
			</div>
			<div class="page-content" id="tablePanel" style="height:400px;overflow: auto;"></div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/electr/car/monthly-run/index');
	</script>
</body>
</html>