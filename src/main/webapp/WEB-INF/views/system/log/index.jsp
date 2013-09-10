<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>日志查询 - 安全生产综合管理平台</title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar">
				<div class="nav-query">
					<form id="query-form" class="form-inline" onsubmit="return false;">
						<input id="startDate" name="startDate" type="datetime" placeholder="开始日期" class="input-small" autocomplete="off">
						<input id="endDate" name="endDate" type="datetime" placeholder="结束日期" class="input-small" autocomplete="off">
						<select id="level" name="level" class="input-small">
							<option value="">选择级别</option>
							<option value="INFO" class="green">消息</option>
							<option value="WARN" class="orange">警告</option>
							<option value="SEVERE" class="red">严重</option>
						</select>
						<button id="query" class="btn btn-small btn-primary">
							<i class="icon-search"></i> 查询
						</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="log-table"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/system/log/index');
	</script>
</body>
</html>
