<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>三维综合管理 - 安全生产综合管理平台</title>
<%@ include file="head.jsp"%>
<%@ include file="../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar">
				<div class="toolbar">
					<button class="btn btn-small btn-success">
						<i class="icon-location-arrow"></i>
						<span>定位</span>
					</button>
					<button class="btn btn-small btn-success">
						<i class="icon-map-marker"></i>
						<span>路径漫游</span>
					</button>
					<button class="btn btn-small btn-success">
						<i class="icon-picture"></i>
						<span>抓图</span>
					</button>
					<button class="btn btn-small btn-success">
						<i class="icon-crop"></i>
						<span>坐标转换</span>
					</button>
				</div>
			</div>
			<div class="page-content" style="padding: 0">
				<div class="row-fluid">
					<div class="span10"></div>
					<div class="span2"></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/3d/index');
	</script>
</body>
</html>
