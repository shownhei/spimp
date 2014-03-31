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
									<a data-toggle="tab" href="#tab1">区域人数</a>
								</li>
								<li>
									<a data-toggle="tab" href="#tab2">分站人数</a>
								</li>
								<li>
									<a data-toggle="tab" href="#tab3">井下人数</a>
								</li>
							</ul>
							<div class="tab-content" style="padding: 10px">
								<div id="tab1" class="tab-pane active">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form1" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
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
								</div>
								<div id="tab2" class="tab-pane">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form2" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
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
								</div>
								<div id="tab3" class="tab-pane">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form3" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
											<select id="refresh3" title="当前刷新周期" style="width: 60px">
												<option value="5000" selected>5秒</option>
												<option value="10000">10秒</option>
												<option value="30000">30秒</option>
												<option value="60000">1分钟</option>
												<option value="300000">5分钟</option>
											</select>
											<input name="staffQueryIn" type="text" placeholder="姓名/部门/工种/职务查询" class="input-medium" autocomplete="off">
											<button id="query3" class="btn btn-small btn-primary">查询</button>
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
		seajs.use('${resources}/scripts/app/location/realtime/index');
	</script>
</body>
</html>
