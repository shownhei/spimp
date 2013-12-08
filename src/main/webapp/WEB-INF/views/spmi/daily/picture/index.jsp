<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>图片管理 - 安全生产综合管理平台</title>
<%@ include file="../../../common/head.jsp"%>
<%@ include file="../../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar">
				<div class="toolbar">
					<button id="create" class="btn btn-small btn-success disabled" title="上传图片，先选择机构">
						<i class="icon-plus-sign-alt"></i>
						<span class="hidden-phone">上传</span>
					</button>
					<button id="remove" class="btn btn-small btn-danger disabled" title="">
						<i class="icon-trash"></i>
						<span class="hidden-phone">删除</span>
					</button>
				</div>
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<span class="input-icon">
							<input id="nav-search-input" name="search" type="text" placeholder="输入图片名称..." class="input-small nav-search-input nav-search-input210"
								autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid">
					<div class="span4">
						<div class="widget-box tree-widget-box">
							<div class="widget-body tree-widget-body">
								<div class="widget-main padding-8 tree-widget-main">
									<div id="groups-tree" class="ztree"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="span8">
						<div class="tabbable">
							<ul class="nav nav-tabs">
								<li class="active">
									<a data-toggle="tab" href="#basic">
										<i class="green icon-home"></i> 图片预览
									</a>
								</li>
							</ul>
							<div id="tab-content" class="tab-content">
								<div id="basic" class="tab-pane in active">
									<div class="row-fluid">
										<div class="span3">
											<img src="${resources}/images/demo/1.jpg" width="100%" class="img-polaroid">
										</div>
										<div class="span3">
											<img src="${resources}/images/demo/2.jpg" width="100%" class="img-polaroid">
										</div>
										<div class="span3">
											<img src="${resources}/images/demo/3.jpg" width="100%" class="img-polaroid">
										</div>
										<div class="span3">
											<img src="${resources}/images/demo/4.jpg" width="100%" class="img-polaroid">
										</div>
									</div>
									<div class="row-fluid" style="margin-top: 6px;">
										<div class="span3">
											<img src="${resources}/images/demo/5.jpg" width="100%" class="img-polaroid">
										</div>
										<div class="span3">
											<img src="${resources}/images/demo/6.jpg" width="100%" class="img-polaroid">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 新建
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal" onsubmit="return false;">
						<div class="control-group">
							<label class="control-label" for="name">图片名称</label>
							<div class="controls">
								<input name="name" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="number">选择文件</label>
							<div class="controls">
								<input name="file" type="file">
							</div>
						</div>
					</form>
				</div>
			</div>
			<div id="create-message-alert" class="row-fluid hide">
				<div class="span12">
					<div class="alert alert-error">
						<i class="icon-remove"></i>
						<span id="create-message-content"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="create-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 删除 -->
	<div id="remove-modal" class="modal modal-xs hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="red">
				<i class="icon-trash"></i> 删除
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<i class="icon-warning-sign"></i> 提示：确认删除选中的图片？
				</div>
			</div>
			<div id="remove-message-alert" class="row-fluid hide">
				<div class="span12">
					<div class="alert alert-error">
						<i class="icon-remove"></i>
						<span id="remove-message-content"></span>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="remove-save" class="btn btn-small btn-danger">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/spmi/daily/picture/index');
	</script>
</body>
</html>
