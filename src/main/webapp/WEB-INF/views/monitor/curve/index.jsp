<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>历史曲线 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/template.jsp"%>
<script src="${resources}/scripts/app/common/echarts-plain.js" type="text/javascript"></script>
</head>
<body class="navbar-fixed">
	<%@ include file="../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar">
				<div class="nav-query">
					<form id="query-form" class="form-inline" onsubmit="return false;">
						<select id="monitorSensorType" name="monitorSensorType" style="width: 100px; float: left; margin-right: 5px">
						</select>
						<select id="nodePlace" name="nodePlace" style="width: 100px; float: left; margin-right: 5px" disabled title="先选择测点类型">
							<option value="">选择位置</option>
						</select>
						<div class="input-append" style="float: left; margin-right: 5px">
							<input name="date" type="datetime" placeholder="选择日期" class="input-small" autocomplete="off">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<div class="checkbox" style="float: left; margin-right: 5px">
							<label>
								<input id="avgValue" name="valueType" type="checkbox" class="ace" value="VT1" checked>
								<span class="lbl"> 平均值</span>
							</label>
						</div>
						<div class="checkbox" style="float: left; margin-right: 5px">
							<label>
								<input id="maxValue" name="valueType" type="checkbox" class="ace" value="VT2">
								<span class="lbl"> 最大值</span>
							</label>
						</div>
						<div class="checkbox" style="float: left; margin-right: 5px">
							<label>
								<input id="minValue" name="valueType" type="checkbox" class="ace" value="VT3">
								<span class="lbl"> 最小值</span>
							</label>
						</div>
						<button id="query" class="btn btn-small btn-primary">
							<i class="icon-search"></i> 查询
						</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid">
					<div class="span3 hide">
						<div class="widget-box tree-widget-box">
							<div class="widget-body tree-widget-body">
								<div class="widget-main padding-8 tree-widget-main">
									<div id="groupTree" class="ztree"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="span9 hide"></div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/monitor/curve/index');
	</script>
</body>
</html>
