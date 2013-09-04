<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="resources" value="${pageContext.request.contextPath}/static-resources" />
<!DOCTYPE HTML>
<html>
<head>
<title>安全生产综合管理平台</title>
<link href="${resources}/styles/ui/bootstrap.min.css" rel="stylesheet">
<link href="${resources}/styles/ui/bootstrap-responsive.min.css" rel="stylesheet">
<link href="${resources}/styles/ui/font-awesome.min.css" rel="stylesheet">
<link href="${resources}/styles/ui/ace.min.css" rel="stylesheet">
<link href="${resources}/styles/ui/ace-responsive.min.css" rel="stylesheet">
<link href="${resources}/styles/ui/ace-skins.min.css" rel="stylesheet">
<link href="${resources}/styles/main.css" rel="stylesheet">
<script src="${resources}/scripts/jquery/jquery-1.10.2.js" type="text/javascript"></script>
<script src="${resources}/scripts/app/login.js" type="text/javascript"></script>
</head>
<body class="login-layout">
	<div class="main-container container-fluid">
		<div class="main-content">
			<div class="row-fluid">
				<div class="span12">
					<div class="login-container">
						<div class="row-fluid">
							<div class="center">
								<h1>
									<i class="icon-leaf green"></i>
									<span class="red">SPIMP</span>
									<span class="white">临时登录页</span>
								</h1>
								<h5 class="blue">© CCRISE</h5>
							</div>
						</div>
						<div class="space-6"></div>
						<div class="row-fluid">
							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="icon-coffee green"></i> 请输入你的账户信息
											</h4>
											<div class="space-6"></div>
											<form>
												<fieldset>
													<label> <span class="block input-icon input-icon-right">
															<input id="principal" type="text" class="span12" placeholder="用户名">
															<i class="icon-user"></i>
														</span>
													</label> <label> <span class="block input-icon input-icon-right">
															<input id="credential" type="password" class="span12" placeholder="密码">
															<i class="icon-lock"></i>
														</span>
													</label>
													<div class="space"></div>
													<div class="clearfix">
														<button id="login" type="button" class="width-35 pull-right btn btn-small btn-primary">
															<i class="icon-key"></i> 登录
														</button>
													</div>
													<div class="space-4"></div>
													<div id="message" class="alert alert-error" style="display: none;"></div>
												</fieldset>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
