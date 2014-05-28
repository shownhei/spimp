<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>实时监测 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../common/sidebar.jsp"%>
		<div class="main-content">
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
					<div class="span9 hide">
						<div class="tabbable">
							<ul class="nav nav-tabs">
								<li class="active">
									<a data-toggle="tab" href="#tab1">全部测点</a>
								</li>
								<li>
									<a data-toggle="tab" href="#tab2">模拟量</a>
								</li>
							
								<li>
									<a data-toggle="tab" href="#tab3">开关量</a>
								</li>
<!--  
								<li>
									<a data-toggle="tab" href="#tab4">累积量</a>
								</li>
-->
								<li>
									<a data-toggle="tab" href="#tab5">瓦斯分站</a>
								</li>
								<li style="float: right;">
									<div style="padding-top: 8px">
										[今日瓦斯报警:
										<span class="CH4">0</span>
										][今日一氧化碳超限:
										<span class="CO">0</span>
										]
									</div>
								</li>
							</ul>
							<div class="tab-content" style="padding: 10px">
								<div id="tab1" class="tab-pane active">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form1" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
											<select id="monitorSensorType1" name="monitorSensorType" style="width: 100px">
											</select>
											<select id="monitorState1" name="monitorState" style="width: 100px">
											</select>
											<select id="refresh1" title="当前刷新周期" style="width: 60px">
												<option value="5000" selected>5秒</option>
												<option value="10000">10秒</option>
												<option value="30000">30秒</option>
												<option value="60000">1分钟</option>
												<option value="300000">5分钟</option>
											</select>
										</form>
									</div>
									<div id="grid1" class="row-fluid"></div>
									<div id="statistic1" style="margin-top: 4px">统计数据加载中，请稍侯...</div>
								</div>
								<div id="tab2" class="tab-pane">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form2" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
											<select id="monitorSensorType2" name="monitorSensorType" style="width: 100px">
											</select>
											<select id="monitorState2" name="monitorState" style="width: 100px">
											</select>
											<select id="refresh2" title="当前刷新周期" style="width: 60px">
												<option value="5000" selected>5秒</option>
												<option value="10000">10秒</option>
												<option value="30000">30秒</option>
												<option value="60000">1分钟</option>
												<option value="300000">5分钟</option>
											</select>
										</form>
									</div>
									<div id="grid2" class="row-fluid"></div>
									<div id="statistic2" style="margin-top: 4px">统计数据加载中，请稍侯...</div>
								</div>
								<div id="tab3" class="tab-pane">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form3" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
											<select id="monitorSensorType3" name="monitorSensorType" style="width: 100px">
											</select>
											<select id="monitorState3" name="monitorState" style="width: 100px">
											</select>
											<select id="refresh3" title="当前刷新周期" style="width: 60px">
												<option value="5000" selected>5秒</option>
												<option value="10000">10秒</option>
												<option value="30000">30秒</option>
												<option value="60000">1分钟</option>
												<option value="300000">5分钟</option>
											</select>
										</form>
									</div>
									<div id="grid3" class="row-fluid"></div>
									<div id="statistic3" style="margin-top: 4px">统计数据加载中，请稍侯...</div>
								</div>
								<div id="tab4" class="tab-pane" >
									<div class="nav-query" style="position: inherit;">
										<form id="query-form4" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
											<select id="monitorSensorType4" name="monitorSensorType" style="width: 100px">
											</select>
											<select id="monitorState4" name="monitorState" style="width: 100px">
											</select>
											<select id="refresh4" title="当前刷新周期" style="width: 60px">
												<option value="5000" selected>5秒</option>
												<option value="10000">10秒</option>
												<option value="30000">30秒</option>
												<option value="60000">1分钟</option>
												<option value="300000">5分钟</option>
											</select>
										</form>
									</div>
									<div id="grid4" class="row-fluid"></div>
									<div id="statistic4" style="margin-top: 4px">统计数据加载中，请稍侯...</div>
								</div>
								<div id="tab5" class="tab-pane">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form5" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
											<select name="state" style="width: 100px">
												<option value="" class="light-grey">选择分站状态</option>
												<option value="true">正常</option>
												<option value="false">故障</option>
											</select>
											<select id="refresh5" title="当前刷新周期" style="width: 60px">
												<option value="5000" selected>5秒</option>
												<option value="10000">10秒</option>
												<option value="30000">30秒</option>
												<option value="60000">1分钟</option>
												<option value="300000">5分钟</option>
											</select>
										</form>
									</div>
									<div id="grid5" class="row-fluid"></div>
									<div id="statistic5" style="margin-top: 4px">统计数据加载中，请稍侯...</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/monitor/realtime/index');
	</script>
</body>
</html>
