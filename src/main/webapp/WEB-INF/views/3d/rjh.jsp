<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="resources" value="${pageContext.request.contextPath}/static-resources" />
<!DOCTYPE html>
<html>
<head>
<title>三维综合管理 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="head.jsp"%>
<link href="${resources}/styles/complex.css" rel="stylesheet">
</head>
<body style="overflow: hidden;">
	<div class="main-container container-fluid">
		<div class="main-content" style="margin-left: 0;">
			<div class="page-toolbar">
				<div class="nav-search">
					<form id="query-form" class="form-inline" onsubmit="return false;">
						<div style="display: block; margin-bottom: 5px;">
							<div class="input-append">
								<select id="renJiHuanArea" style="height: 25px; width: 150px; font-size: 12px;">
									<option value="">请选择区域</option>
								<c:forEach items="${areas}" var="area" varStatus="status">
									<option value="${area}">${area}</option>
								</c:forEach>
								</select>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div id="renJiHuanInfo"></div>
			</div>
		</div>
	</div>
	<div id="view-modal" class="modal modal-xl hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-th-list"></i> 查看
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<iframe id="showDocument" src="" width="100%" height=355 border=0 margin=0 frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 关闭
			</button>
		</div>
	</div>
	<script type="text/javascript">
	   seajs.use('${resources}/scripts/app/3d/rjh-module');
	</script>
</body>
</html>