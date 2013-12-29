<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>首页 - 安全生产综合管理平台</title>
<%@ include file="common/head.jsp"%>
<%@ include file="common/template.jsp"%>
</head>
<body class="navbar-fixed breadcrumbs-fixed">
	<%@ include file="common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="common/sidebar.jsp"%>
		<div class="main-content">
			<div class="breadcrumbs fixed" id="breadcrumbs"></div>
			<div class="page-content">
				<div class="page-header position-relative">
					<h1></h1>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<button id="spmi" class="btn btn-app btn-success">
							<i class="icon-wrench bigger-230"></i> 安全生产管理
						</button>
						<button id="ercs" class="btn btn-app btn-primary">
							<i class="icon-medkit bigger-230"></i> 应急救援指挥
						</button>
						<button id="system" class="btn btn-app btn-default">
							<i class="icon-cogs bigger-230"></i> 系统管理
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/index');
	</script>
</body>
</html>
