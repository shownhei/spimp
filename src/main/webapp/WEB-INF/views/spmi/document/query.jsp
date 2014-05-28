<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>文档综合查询 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-content">
				<div id="search_title" class="header blue" style="margin-top: 0px;">
					<img src="${resources}/images/icons/document_search.png"
						style="height: 30px;" />
					<h4 style="display: inline-block;">文档检索</h4>
				</div>
				<div class="row-fluid  ">
					<div class="span3 ">

						<div class="widget-box tree-widget-box">
							<div class="widget-body tree-widget-body">
								<div class="widget-main padding-8 tree-widget-main">
									<div id="folders-tree" class="ztree" style="height: 520px;">
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="span9">
						<div id="search_content">
							<form id="query-form" class="form-inline"
								onsubmit="return false;">
								<div style="display: block; margin-bottom: 5px;">
									<div class="input-append">
										<input name="documentName" id="search_documentName" type="text"
											style="height: 15px; width: 150px; font-size: 12px;"
											placeholder="文档名称">
									</div>
									<div class="input-append">
										<input name="keyWord" type="text" id="search_keyWord"
											style="height: 15px; width: 150px; font-size: 12px;"
											placeholder="查询关键字">
									</div>
									<div class="input-append">
										<input name="startDate" type="datetime"  id="search_startDate" style="width: 119px;"
											placeholder="开始时间" class="input-small"> <span
											class="add-on nav-add-on"> <i class="icon-calendar"></i>
										</span>
									</div>
									<div class="input-append">
									    <input type="hidden" name="folderId" id="search_folder_id"/>
										<input name="endDate" type="datetime" id="search_endDate" style="width: 119px;"
											placeholder="结束时间" class="input-small"> <span
											class="add-on nav-add-on"> <i class="icon-calendar"></i>
										</span>
									</div>
									<div class="input-append">
										<button id="submit" type="button"
											class="btn btn-primary btn-small ">查询</button>
									</div>
									<div class="input-append" style="margin-left: 10px;">
										<button id="reset" type="button"
											class="btn btn-primary btn-small ">重置</button>
									</div>
								</div>
							</form>
						</div>
						<div id="plan-table"></div>
					</div>
				</div>

			</div>
		</div>
	</div>
	<div id="view-modal" class="modal modal-xl hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-list"></i> 查看
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<iframe id="showDocument" src="" width="100%" height=355></iframe>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 关闭
			</button>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/spmi/document/query/index');
	</script>
</body>
</html>
