<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>首页 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="common/head.jsp"%>
<%@ include file="common/template.jsp"%>
</head>
<body class="navbar-fixed breadcrumbs-fixed">
	<%@ include file="common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="common/sidebar.jsp"%>
		<div class="main-content">
			<div class="breadcrumbs fixed" id="breadcrumbs">
				<ul class="breadcrumb">
					<li>
						<i class="icon-home home-icon"></i>
						<a href="/">仪表盘</a>
					</li>
				</ul>
			</div>
			<div class="page-content">
				<div class="row-fluid">
					<div class="span6">
						<div class="widget-box">
							<div class="widget-header widget-header-flat widget-header-small">
								<h5>
									<i class="icon-info"></i> 信息统计
								</h5>
							</div>
							<div class="widget-body">
								<div id="infobox" class="widget-main infobox-container"></div>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="widget-box">
							<div class="widget-header widget-header-flat widget-header-small">
								<h5>
									<i class="icon-signal"></i>
									<span id="chart1-title"></span>
								</h5>
							</div>
							<div class="widget-body">
								<div id="chart1" class="widget-main"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="widget-box">
							<div class="widget-header widget-header-flat widget-header-small">
								<h5>
									<i class="icon-signal"></i>
									<span id="chart2-title"></span>
								</h5>
							</div>
							<div class="widget-body">
								<div id="chart2" class="widget-main"></div>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="widget-box">
							<div class="widget-header widget-header-flat widget-header-small">
								<h5>
									<i class="icon-signal"></i>
									<span id="chart3-title"></span>
								</h5>
							</div>
							<div class="widget-body">
								<div id="chart3" class="widget-main"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
<script src="${resources}/scripts/app/common/echarts-plain.js" type="text/javascript"></script>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/index');
	</script>
</body>
</html>
