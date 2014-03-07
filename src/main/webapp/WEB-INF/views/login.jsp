<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="resources" value="${pageContext.request.contextPath}/static-resources" />
<!DOCTYPE HTML>
<html>
<head>
<title>山西王庄煤业数字矿山综合管理平台</title>
<script src="${resources}/scripts/sea-modules/seajs/seajs/2.0.0/sea.js" type="text/javascript"></script>
<script type="text/javascript">
	seajs.config({
		alias : {
			$ : 'jquery/jquery/1.10.1/jquery'
		}
	});
	seajs.use('${resources}/scripts/app/login');
</script>
<style type="text/css">
.login_text {
	width: 220px;
	height: 26px;
	line-height: 24px;
	vertical-align: middle;
	border: 1px solid #BDBDBD;
	background: url("${resources}/images/inputbg.min.jpg") repeat
		transparent;
}

.login_text_onfocus {
	width: 218px;
	height: 24px;
	line-height: 24px;
	vertical-align: middle;
	border: 2px solid #85C026;
	background: url("${resources}/images/inputbg.min.jpg") repeat
		transparent;
}

.login_btn {
	display: block;
	width: 220px;
	line-height: 35px;
	border: 1px solid #4C5576;
	font-size: 12px;
	color: #FFFFFF;
	font-weight: bold;
	text-align: center;
	cursor: pointer;
	background: url("${resources}/images/btn_bg.gif") repeat transparent;
}
</style>
<script type="text/javascript">
	var resourcesPath = '${resources}';
</script>
</head>
<body style="display: none; overflow: hidden;">
	<div style="top: 0px; left: 0px; position: absolute; z-index: -2;">
		<img id="bg" src="${resources}/images/loginbg.min.jpg" width="960" height="600" alt="背景">
	</div>
	<div style="position: absolute; z-index: -1;">
		<img id="loginBg" src="${resources}/images/login.png" width="622" height="403" alt="登录背景框">
	</div>
	<div id="loginForm" style="position: absolute; z-index: 0; color: #747B95; margin: auto;">
		<table style="margin-top: 20px;">
			<tr height="45">
				<td align="right" valign="middle" style="font-size: 12px; color: #4C5576;">账&nbsp;&nbsp;号：</td>
				<td>
					<input id="principal" name="loginName" class="login_text" onfocus="this.className='login_text_onfocus'" onblur="this.className='login_text'" type="text"
						autofocus="autofocus">
				</td>
			</tr>
			<tr height="45">
				<td align="right" valign="middle" style="font-size: 12px; color: #4C5576;">密&nbsp;&nbsp;码：</td>
				<td>
					<input id="credential" name="password" class="login_text" onfocus="this.className='login_text_onfocus'" onblur="this.className='login_text'"
						type="password">
				</td>
			</tr>
			<tr>
				<td></td>
				<td align="right">
					<a class="login_btn" id="login">登&nbsp;录</a>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center" style="color: #B10000; font-size: 12px;">
					<span id="message"></span>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
