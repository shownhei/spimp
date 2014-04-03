<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>基础信息 - 山西王庄煤业数字矿山综合管理平台</title>
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
									<a data-toggle="tab" href="#tab1">区域信息</a>
								</li>
								<li>
									<a data-toggle="tab" href="#tab2">分站信息</a>
								</li>
								<li>
									<a data-toggle="tab" href="#tab3">人员信息</a>
								</li>
							</ul>
							<div class="tab-content" style="padding: 10px">
								<div id="tab1" class="tab-pane active">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form1" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px"></form>
									</div>
									<div id="grid1" class="row-fluid"></div>
								</div>
								<div id="tab2" class="tab-pane">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form2" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px"></form>
									</div>
									<div id="grid2" class="row-fluid"></div>
								</div>
								<div id="tab3" class="tab-pane">
									<div class="nav-query" style="position: inherit;">
										<form id="query-form3" class="form-inline" onsubmit="return false;" style="margin-bottom: 10px">
											<input name="staffQueryIn" type="text" placeholder="姓名/部门/工种查询" class="input-medium" autocomplete="off">
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
		seajs.use('${resources}/scripts/app/location/info/index');
	</script>
</body>
</html>
