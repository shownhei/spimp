<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>年度油耗统计 - 山西王庄煤业数字矿山综合管理平台</title>
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
			<div class="page-content">
				<div class="row">
					<div class="span11" id="tablePanel"></div>
				</div>
				<div class="row">
					<div class="span11">
						<div class="widget-box">
							<div class="widget-header widget-header-flat widget-header-small">
								<h5>
									<i class="icon-signal"></i>
									<span id="chartPanel-title"></span>
								</h5>
							</div>
							<div class="widget-body">
								<div id="chartPanel" class="widget-main" style="height: 200px;"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/electr/car/annual-oil/index');
	</script>
</body>
</html>