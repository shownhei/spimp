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
							<!--  
								<li>
									<a data-toggle="tab" href="#tab1">下井考勤查询</a>
								</li>
							-->
								<li  class="active">
									<a data-toggle="tab" href="#tab2">人员轨迹查询</a>
								</li>
								<li>
									<a data-toggle="tab" href="#tab3">报警查询</a>
								</li>
							</ul>
							<div class="tab-content" style="padding: 10px">
								<div id="tab1" class="tab-pane">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form1" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
											<select id="department1"  name="department" style="width: 100px">
											</select>
											<select id="staffId1" name="staffId" style="width: 100px" disabled title="先选择部门">
												<option value="">选择人员</option>
											</select>
											<div class="input-append">
												<input id="startTime1" name="startTime" type="datetime" placeholder="开始日期" class="input-small" autocomplete="off">
												<span class="add-on nav-add-on">
													<i class="icon-calendar"></i>
												</span>
											</div>
											<div class="input-append">
												<input id="endTime1" name="endTime" type="datetime" placeholder="结束日期" class="input-small" autocomplete="off">
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
								<div id="tab2" class="tab-pane active">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form2" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
											<select id="department2" name="department" style="width: 100px">
											</select>
											<select id="staffId2" name="staffId" style="width: 100px" disabled title="先选择部门">
												<option value="">选择人员</option>
											</select>
											<div class="input-append">
												<input id ="startTime2" name="startTime" type="datetime" placeholder="开始日期" class="input-small" autocomplete="off">
												<span class="add-on nav-add-on">
													<i class="icon-calendar"></i>
												</span>
											</div>
											<div class="input-append">
												<input id="endTime2"  name="endTime" type="datetime" placeholder="结束日期" class="input-small" autocomplete="off">
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
											<select id="department3" name="department" style="width: 100px">
											</select>
											<select id="staffId3" name="staffId" style="width: 80px" disabled title="先选择部门">
												<option value="">选择人员</option>
											</select>
											<select id="type3" name="type" style="width: 80px">
												<option value="">所有报警</option>
												<option value="7">超时报警</option>
												<option value="6">求救报警</option>
												<option value="5">禁区报警</option>
											</select>
											<input name="warnQueryIn" type="text" placeholder="姓名/部门/工种查询" class="input-medium" style="width: 110px" autocomplete="off">
											<div class="input-append">
												<input id="startTime3" name="startTime" type="datetime" placeholder="开始日期" class="input-small" style="width: 70px" autocomplete="off">
												<span class="add-on nav-add-on">
													<i class="icon-calendar"></i>
												</span>
											</div>
											<div class="input-append">
												<input id= "endTime3" name="endTime" type="datetime" placeholder="结束日期" class="input-small" style="width: 70px" autocomplete="off">
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
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/location/query/index');
	</script>
</body>
</html>
