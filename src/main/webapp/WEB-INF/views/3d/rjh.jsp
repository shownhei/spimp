<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>三维综合管理 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="head.jsp"%>
</head>
<body class="navbar-fixed">
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
		<div class="main-content">
			<div class="page-toolbar">
				<div class="toolbar"></div>
				<div class="nav-search">
					<form id="query-form" class="form-inline" onsubmit="return false;">
						<div style="display: block; margin-bottom: 5px;">
							<div class="input-append">
								<select id="renJiHuanArea"
									style="height: 25px; width: 150px; font-size: 12px;">
									<c:forEach items="${areas}" var="area" varStatus="status">
										<option value="${area }">${area }</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="renJiHuanInfo"></div>
			</div>
		</div>
	</div>

	<SCRIPT type="text/javascript">
		define(function(require, exports, module) {
			var $ = require('kjquery');
			var Utils = require('../common/utils');
			//人机环区域切换
			$('#renJiHuanArea').change(function() {
				var param = JSON.stringify({
					'NAME' : $('#renJiHuanArea').val(),
					'JSNAME':'rjhProcess'
				});
				external.RequireAreaRJH(param);
			});
			var rjhProcess=function(data){
				if(!data||data==='null'){
					Utils.modal.showAlert("没有数据","提示");
					return;
				}
				var url='/location/location-areas/rjhcommand';
				$.ajax({
					type : 'post',
					dataType : 'text',
					data:'rjhParam='+data,
					url : url,
					success : function(datas) {
						$('#renJiHuanInfo').html(datas);
					}
				});
			};
			window.rjhProcess=rjhProcess;
		});
	</SCRIPT>
</body>
</html>