<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>历史查询 - 山西王庄煤业数字矿山综合管理平台</title>
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
									<a data-toggle="tab" href="#tab1">报警明细</a>
								</li>
								<li>
									<a data-toggle="tab" href="#tab2">报警统计</a>
								</li>
								<li>
									<a data-toggle="tab" href="#tab3">密采查询</a>
								</li>
								<li>
									<a data-toggle="tab" href="#tab4">模拟量查询</a>
								</li>
								<li>
									<a data-toggle="tab" href="#tab5">开关量查询</a>
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
											<input name="timeLast" type="text" placeholder="时长大于" class="input-mini" autocomplete="off">
											<span>秒</span>
											<div class="input-append">
												<input name="startTime" type="datetime" placeholder="开始日期" class="input-small" autocomplete="off">
												<span class="add-on nav-add-on">
													<i class="icon-calendar"></i>
												</span>
											</div>
											<div class="input-append">
												<input name="endTime" type="datetime" placeholder="结束日期" class="input-small" autocomplete="off">
												<span class="add-on nav-add-on">
													<i class="icon-calendar"></i>
												</span>
											</div>
											<button id="query1" class="btn btn-small btn-primary">查询</button>
											<button id="export1" class="btn btn-small btn-primary">导出</button>
											<button id="reset1" type="reset" class="btn btn-primary btn-small">重置</button>
										</form>
									</div>
									<div id="grid1" class="row-fluid"></div>
								</div>
								<div id="tab2" class="tab-pane">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form2" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
											<select id="monitorSensorType2" name="monitorSensorType" style="width: 100px">
											</select>
											<select id="monitorState2" name="monitorState" style="width: 100px">
											</select>
											<div class="input-append">
												<input name="startTime" type="datetime" placeholder="开始日期" class="input-small" autocomplete="off">
												<span class="add-on nav-add-on">
													<i class="icon-calendar"></i>
												</span>
											</div>
											<div class="input-append">
												<input name="endTime" type="datetime" placeholder="结束日期" class="input-small" autocomplete="off">
												<span class="add-on nav-add-on">
													<i class="icon-calendar"></i>
												</span>
											</div>
											<button id="query2" class="btn btn-small btn-primary">查询</button>
											<button id="export2" class="btn btn-small btn-primary">导出</button>
											<button id="reset2" type="reset" class="btn btn-primary btn-small">重置</button>
										</form>
									</div>
									<div id="grid2" class="row-fluid"></div>
								</div>
								<div id="tab3" class="tab-pane">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form3" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
											<select id="monitorSensorType3" name="monitorSensorType" style="width: 100px">
											</select>
											<select id="nodePlace3" name="nodePlace" style="width: 100px" disabled title="先选择测点类型">
												<option value="">选择位置</option>
											</select>
											<div class="input-append">
												<input name="startTime" type="datetime" placeholder="开始日期" class="input-small" autocomplete="off">
												<span class="add-on nav-add-on">
													<i class="icon-calendar"></i>
												</span>
											</div>
											<div class="input-append">
												<input name="endTime" type="datetime" placeholder="结束日期" class="input-small" autocomplete="off">
												<span class="add-on nav-add-on">
													<i class="icon-calendar"></i>
												</span>
											</div>
											<button id="query3" class="btn btn-small btn-primary">查询</button>
											<button id="export3" class="btn btn-small btn-primary">导出</button>
											<button id="reset3" type="reset" class="btn btn-primary btn-small">重置</button>
										</form>
									</div>
									<div id="grid3" class="row-fluid"></div>
								</div>
								<div id="tab4" class="tab-pane">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form4" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
											<select id="monitorSensorType4" name="monitorSensorType" style="width: 100px">
											</select>
											<select id="nodePlace4" name="nodePlace" style="width: 100px" disabled title="先选择测点类型">
												<option value="">选择位置</option>
											</select>
											<div class="input-append">
												<input name="startTime" type="datetime" placeholder="开始日期" class="input-small" autocomplete="off">
												<span class="add-on nav-add-on">
													<i class="icon-calendar"></i>
												</span>
											</div>
											<div class="input-append">
												<input name="endTime" type="datetime" placeholder="结束日期" class="input-small" autocomplete="off">
												<span class="add-on nav-add-on">
													<i class="icon-calendar"></i>
												</span>
											</div>
											<button id="query4" class="btn btn-small btn-primary">查询</button>
											<button id="export4" class="btn btn-small btn-primary">导出</button>
											<button id="reset4" type="reset" class="btn btn-primary btn-small">重置</button>
										</form>
									</div>
									<div id="grid4" class="row-fluid"></div>
								</div>
								<div id="tab5" class="tab-pane">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form5" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
											<select id="monitorSensorType5" name="monitorSensorType" class="input-small" style="width: 100px">
											</select>
											<select id="nodePlace5" name="nodePlace" class="input-small" style="width: 100px" disabled title="先选择测点类型">
												<option value="">选择位置</option>
											</select>
											<div class="input-append">
												<input name="startTime" type="datetime" placeholder="开始日期" class="input-small" autocomplete="off">
												<span class="add-on nav-add-on">
													<i class="icon-calendar"></i>
												</span>
											</div>
											<div class="input-append">
												<input name="endTime" type="datetime" placeholder="结束日期" class="input-small" autocomplete="off">
												<span class="add-on nav-add-on">
													<i class="icon-calendar"></i>
												</span>
											</div>
											<button id="query5" class="btn btn-small btn-primary">查询</button>
											<button id="export5" class="btn btn-small btn-primary">导出</button>
											<button id="reset5" type="reset" class="btn btn-primary btn-small">重置</button>
										</form>
									</div>
									<div id="grid5" class="row-fluid"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/monitor/query/index');
	</script>
</body>
</html>
