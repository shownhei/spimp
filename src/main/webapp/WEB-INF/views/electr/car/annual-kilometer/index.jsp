<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>年度公里统计 - 山西王庄煤业数字矿山综合管理平台</title>
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
					<button id="export_result" class="btn btn-small btn-pink disabled" title="导出当前统计结果">
						<i class="icon-download-alt"></i> 导出
					</button>
				</div>
				<div class="nav-search">
					<form id="query-form" class="form-search" onsubmit="return false;">
						<input id="query_year" name="year" type="number" style="height: 15px; width: 130px; font-size: 12px;" value="2013" />
						<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
					</form>
				</div>
			</div>
			<div class="page-content" id="tablePanel"></div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/electr/car/annual-kilometer/index');
	</script>
</body>
</html>