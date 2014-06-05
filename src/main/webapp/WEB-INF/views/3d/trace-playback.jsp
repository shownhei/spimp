<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>三维综合管理 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="head.jsp"%>
</head>
<body class="navbar-fixed" style="min-width: 820px;">
	<div class="main-container container-fluid">
		<div class="navbar navbar-fixed-top" id="navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container-fluid">
					<a href="/" class="brand" target="safe">
					<small>
						<i class="icon-leaf"></i> 王庄煤业数字矿山综合管理平台
					</small>
					</a>
				</div>
			</div>
		</div>
		<div class="main-content" style="margin-left: 0;">
			<div class="page-toolbar">
				<div class="toolbar"></div>
				<div class="nav-search">
					<form id="query-form" class="form-inline" onsubmit="return false;">
						<div style="display: block; margin-bottom: 5px;">
							<div class="input-append">
								<select name="department" id="trace_department"
									style="height: 25px; width: 150px; font-size: 12px;">
								</select>
							</div>
							<div class="input-append">
								<select name="staff" id="trace_staff"
									style="height: 25px; width: 150px; font-size: 12px;">
								</select>
							</div>
							<div class="input-append">
								<input name="startDateTime" type="datetime"
									id="trace_startDateTime" style="width: 119px;"
									placeholder="开始时间" class="input-small"> <span
									class="add-on nav-add-on"> <i class="icon-calendar"></i>
								</span>
							</div>
							<div class="input-append">
								<input name="endDateTime" type="datetime" id="trace_endDateTime"
									style="width: 119px;" placeholder="截止时间" class="input-small">
								<span class="add-on nav-add-on"> <i class="icon-calendar"></i>
								</span>
							</div>
							<div class="input-append ">
								<table width="150">
									<tr>
										<td>
											<button id="trace_query_btn"
												class="btn btn-small btn-success disabled">
												<i class="icon-search"></i>查询
											</button>
										</td>
										<td align="right">
											<button id="trace_playback_btn"
												class="btn btn-small btn-success disabled">
												<i class="icon-list"></i>轨迹回放
											</button>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="stafflist-table"></div>
			</div>
		</div>
	</div>
	<SCRIPT type="text/javascript">
	   seajs.use('${resources}/scripts/app/3d/trace-playback');
	</SCRIPT>
</body>
</html>