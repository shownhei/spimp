<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>综合自动化管控平台 - 安全生产综合管理平台</title>
<%@ include file="head.jsp"%>
<%@ include file="../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="sidebar.jsp"%>
		<div class="main-content">
			<div class="page-content" style="padding: 0">
				<div class="row-fluid">
					<div class="span12">
						<img id="map-image" src="${resources}/images/auto/主界面.png" style="width: 100%">
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/auto/index');
	</script>
</body>
</html>
