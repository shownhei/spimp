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
			<div class="breadcrumbs fixed" id="breadcrumbs">
				<div class="nav-search" id="nav-search">
					<form class="form-search">
						<span class="input-icon">
							<input type="text" placeholder="搜索..." class="input-small nav-search-input" id="nav-search-input" autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="page-header position-relative">
					<h1></h1>
				</div>
				<div class="row-fluid">
					<div class="span12"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
