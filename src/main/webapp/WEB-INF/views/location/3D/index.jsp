<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>实时监测 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="../../common/head3D.jsp"%>
<%@ include file="../../common/template.jsp"%>
</head>
<body>
	<div class="main-container container-fluid">
		<div >
			<div >
				<div class="row-fluid">
					<div class="span12">
						<div id="grid3" class="row-fluid"></div>
						<input id="nodeId" type="hidden" value="${nodeId}"/>  
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/location/3D/index');
	</script>
</body>
</html>
