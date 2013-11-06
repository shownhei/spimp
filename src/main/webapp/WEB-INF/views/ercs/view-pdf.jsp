<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="resources" value="${pageContext.request.contextPath}/static-resources" />
<!doctype html>
<html>
<head>
<title>FlexPaper</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
<style type="text/css" media="screen">
html,body {
	height: 100%;
}

body {
	margin: 0;
	padding: 0;
	overflow: auto;
}

#flashContent {
	display: none;
}
</style>
<script src="${resources}/scripts/sea-modules/seajs/seajs/2.0.0/sea.js" type="text/javascript"></script>
</head>
<body>
	<div style="padding: 0px; margin: 0px; border: opx solid white;">
		<div id="documentViewer" class="flexpaper_viewer" style="width: 100%; height: 345px"></div>
		<script type="text/javascript">
		var flexPaperViewer = "${resources}/scripts/sea-modules/ikj/flexpaper/1.0.0/FlexPaperViewer.swf";
		var swfFile='${file.swfPath}?t=' + new Date().getTime();
		seajs.config({
	        alias: {
	          $: 'jquery/jquery/1.10.1/jquery',
	          flexpaper : 'ikj/flexpaper/1.0.0/flexpaper'
	        }
	      });
		seajs.use('${resources}/scripts/app/ercs/view-pdf');
		</script>
	</div>
</body>
</html>