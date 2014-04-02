<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>三维综合管理 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="head.jsp"%>
</head>
<body>
	<object id="WebMineSystem" classid="CLSID:481854E7-4443-4E9E-873B-05CDB7C070B8" height="500" width="500"></object>
	<SCRIPT FOR=WebMineSystem EVENT=Platform3DStarted()>
		define(function(require, exports, module) {
			var $ = require('kjquery');

			$.get(contextPath + '/update?prefix=sywz&suffix=MDocSegment', function(data) {
				var paths = data.data.split('/');

				WebMineSystem.SetSysParam("资源地址", 'http://' + location.hostname + ':' + location.port + '/' + paths[1] + '/' + paths[2] + '/');
				WebMineSystem.LoadProjectFile(paths[3]);
			});
		});
	</SCRIPT>
</body>