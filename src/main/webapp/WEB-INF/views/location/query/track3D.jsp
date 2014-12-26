<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>历史查询 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="../../common/head3D.jsp"%>
<%@ include file="../../common/template.jsp"%>
</head>
<body>
	<div class="main-container container-fluid">
		<div>
			<div>
				<div class="row-fluid">
					<div class="span12">
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
							</ul>
							<div class="tab-content" style="padding: 10px">
				
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
