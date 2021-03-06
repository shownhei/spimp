<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="resources" value="${pageContext.request.contextPath}/static-resources" />
<c:set var="requestURI" value="${pageContext.request.requestURI}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="shortcut icon" type="image/x-icon" href="${resources}/favicon.ico">
<%-- <link href="${resources}/app.css" rel="stylesheet"> --%>
<link href="${resources}/styles/ui/bootstrap.min.css" rel="stylesheet">
<link href="${resources}/styles/ui/bootstrap-responsive.min.css" rel="stylesheet">
<link href="${resources}/styles/ui/font-awesome.min.css" rel="stylesheet">
<link href="${resources}/styles/ui/ace.min.css" rel="stylesheet">
<link href="${resources}/styles/ui/ace-responsive.min.css" rel="stylesheet">
<link href="${resources}/styles/ui/ace-skins.min.css" rel="stylesheet">
<link href="${resources}/styles/ui/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<link href="${resources}/styles/main.css" rel="stylesheet">
<link href="${resources}/ikj.css" rel="stylesheet">
<!--[if IE 7]>
<link href="${resources}/styles/ui/font-awesome-ie7.min.css" rel="stylesheet"/>
<![endif]-->
<!--[if lte IE 8]>
<link href="${resources}/ui/ace-ie.min.css" rel="stylesheet"/>
<![endif]-->
<%-- <script src="${resources}/app.js"></script> --%>
<script src="${resources}/scripts/jquery/jquery-1.10.2.js" type="text/javascript"></script>
<script src="${resources}/scripts/jquery/bootstrap.js" type="text/javascript"></script>
<script src="${resources}/scripts/jquery/bootbox.js" type="text/javascript"></script>
<script src="${resources}/scripts/jquery/handlebars.js" type="text/javascript"></script>
<script src="${resources}/scripts/jquery/jqrest-1.0.0.js" type="text/javascript"></script>
<script src="${resources}/scripts/jquery/jquery.serialize-object.js" type="text/javascript"></script>
<script src="${resources}/scripts/jquery/jquery.slimscroll.js" type="text/javascript"></script>
<script src="${resources}/scripts/jquery/jquery.ztree.all-3.5.js" type="text/javascript"></script>
<script src="${resources}/scripts/jquery/json2.js" type="text/javascript"></script>
<script src="${resources}/scripts/sea-modules/seajs/seajs/2.0.0/sea.js"></script>
<script type="text/javascript">
	var contextPath = "${contextPath}";
	var resources = "${resources}";
	var requestURI = "${requestURI}";
	seajs.config({
		plugins : [ 'text' ],
		alias : {
			$ : 'jquery/jquery/1.10.1/jquery',
			'$-debug' : 'jquery/jquery/1.10.1/jquery-debug',
			grid : 'ikj/grid/1.4.0/grid'
		}
	});
</script>
