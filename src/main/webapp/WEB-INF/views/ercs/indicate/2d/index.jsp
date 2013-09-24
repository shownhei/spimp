<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>二维展示 - 安全生产综合管理平台</title>
<%@ include file="../../../common/head.jsp"%>
<%@ include file="../../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar"></div>
			<div class="page-content">
				<div class="row-fluid" id="svgContainer"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.config({
			alias : {
				svg : 'ikj/svg/1.0.0/svg'
			}
		});
		seajs.use('${resources}/scripts/app/ercs/indicate/2d/index');
	</script>
</body>
</html>
