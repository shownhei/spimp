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

<link rel="stylesheet" type="text/css" href="${resources}/flexpaper/css/flexpaper.css" />
<script type="text/javascript" src="${resources}/flexpaper/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${resources}/flexpaper/js/flexpaper.js"></script>
<script type="text/javascript" src="${resources}/flexpaper/js/flexpaper_handlers_debug.js"></script>
</head>
<body >
	<div style="padding:0px;margin:0px;border:opx solid white;">
		<div id="documentViewer" class="flexpaper_viewer" style="width: 100%; height: 345px"></div>

		<script type="text/javascript">
			$('#documentViewer').FlexPaperViewer({
				config : {
					SWFFile : '${file.swfPath}?t='+new Date().getTime(),
					Scale : 0.5,
					ZoomTransition : 'easeOut',
					ZoomTime : 0.5,
					ZoomInterval : 0.2,
					FitPageOnLoad : true,
					FitWidthOnLoad : true,
					FullScreenAsMaxWindow : false,
					ProgressiveLoading : false,
					MinZoomSize : 0.2,
					MaxZoomSize : 5,
					SearchMatchAll : false,
					InitViewMode : 'Portrait',
					RenderingOrder : 'flash',
					StartAtPage : '',

					ViewModeToolsVisible : true,
					ZoomToolsVisible : true,
					NavToolsVisible : true,
					CursorToolsVisible : true,
					SearchToolsVisible : true,
					WMode : 'window',
					localeChain : 'en_US'
				}
			});
		</script>
	</div>
</body>
</html>