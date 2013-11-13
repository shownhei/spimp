<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>文档综合查询 - 安全生产综合管理平台</title>
<%@ include file="../../../common/head.jsp"%>
<%@ include file="../../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-content">
				<div id="search_title" class="header blue" style="margin-top: 0px;">
					<img src="${resources}/images/icons/document_search.png" />
					<h4 style="display: inline-block;">文档检索</h4>
				</div>
				<div id="search_content" style="margin-left: 80px;">
					<form id="query-form" class="form-inline" onsubmit="return false;">
						<div>
							<span style="display: block; margin-bottom: 5px;">
								<div class="input-append">
									<input name="documentName" type="text" style="height: 18px; width: 290px; font-size: 12px;" placeholder="文档名称">
								</div>
							</span>
							<span style="display: block; margin-bottom: 5px;">
								<div class="input-append">
									<input name="keyWord" type="text" style="height: 18px; width: 290px; font-size: 12px;" placeholder="查询关键字">
								</div>
							</span>
							<span style="display: block; margin-bottom: 5px;">
								<div class="input-append">
									<select id="query_projectType" name="projectType" style="height: 25px; width: 150px; font-size: 12px;"></select>
								</div>
								<div class="input-append">
									<select id="query_office" name="office" style="height: 25px; width: 150px; font-size: 12px;">
										<option value="">选择科室</option>
										<option value="调度室">调度室</option>
										<option value="安全科">安全科</option>
										<option value="机电科">机电科</option>
										<option value="通风科">通风科</option>
										<option value="生产技术科">生产技术科</option>
										<option value="防治水科">防治水科</option>
										<option value="综掘队">综掘队</option>
										<option value="综采队">综采队</option>
										<option value="开拓队">开拓队</option>
									</select>
								</div>
							</span>
							<span style="display: block; margin-bottom: 5px;">
								<div class="input-append">
									<input name="startDate" type="datetime" style="width: 119px;" placeholder="开始时间" class="input-small">
									<span class="add-on nav-add-on">
										<i class="icon-calendar"></i>
									</span>
								</div>
								<div class="input-append">
									<input name="endDate" type="datetime" style="width: 119px;" placeholder="结束时间" class="input-small">
									<span class="add-on nav-add-on">
										<i class="icon-calendar"></i>
									</span>
								</div>
							</span>
							<span style="display: block; margin-left: 90px;">
								<div class="input-append">
									<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
								</div>
								<div class="input-append" style="margin-left: 40px;">
									<button id="reset" type="reset" class="btn btn-primary btn-small">重置</button>
								</div>
							</span>
						</div>
					</form>
				</div>
				<div class="row-fluid" id="plan-table"></div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/spmi/document/query/index');
	</script>
	<div id="view-modal" class="modal modal-xl hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="red">
				<i class="icon-trash"></i> 查看
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<iframe id="showDocument" src="" width="100%" height=355 border=0 margin=0 frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 关闭
			</button>
		</div>
	</div>
</body>
</html>
