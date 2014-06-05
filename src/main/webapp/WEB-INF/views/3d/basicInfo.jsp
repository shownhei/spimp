<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>三维综合管理 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="head.jsp"%>
<script type="text/javascript"
	src="${resources}/scripts/kindeditor/kindeditor-all-min.js">
	
</script>
<script type="text/javascript">
	
</script>
</head>
<body class="navbar-fixed" style="min-width: 820px;">
	<div class="main-container container-fluid">
		<div class="navbar navbar-fixed-top" id="navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container-fluid">
					<a href="/" class="brand" target="safe"> <small> <i
							class="icon-leaf"></i> 王庄煤业数字矿山综合管理平台
					</small>
					</a>
				</div>
			</div>
		</div>
		<div class="main-content" style="margin-left: 0;">
			<div class="page-toolbar">
				<div class="toolbar">
					<button id="edit" class="btn btn-small btn-primary">
						<i class="icon-edit"></i> 编辑
					</button>
					<button id="save" class="btn btn-small btn-primary disabled">
						<i class="icon-ok"></i> 保存
					</button>
				</div>
				<div class="nav-search">
					<form class="form-inline"></form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="editPanel">
					<textarea name="content" id="content"
						style="width: 100%; height: 400px; display: none;"></textarea>
				</div>
				<div class="row-fluid" id="displayPanel">${content}</div>
			</div>
		</div>
	</div>
	<SCRIPT type="text/javascript">
	   seajs.use('${resources}/scripts/app/3d/basicInfo');
	</SCRIPT>
</body>
</html>