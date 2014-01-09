<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>图片管理 - 安全生产综合管理平台</title>
<%@ include file="../../../common/head.jsp"%>
<%@ include file="../../../common/template.jsp"%>
<style type="text/css">
	.imgContent{
	display:none;
	}
 	div.imgFrame:hover div{display:block};   
</style>
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
										<i class="green icon-home"></i> 图片
									</a>
								</li>
							</ul>
							<div id="tab-content" class="tab-content">
								<div id="basic" class="tab-pane in active">
								<ul id = "column1" class="span3" style="list-style-type:none">
								</ul>
								<ul id = "column2" class="span3" style="list-style-type:none">
								</ul>
								<ul id = "column3" class="span3" style="list-style-type:none">
								</ul>
								<ul id = "column4" class="span3" style="list-style-type:none">
								</ul>
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
							<div class="control-group" style="display: none;">
							<label class="control-label" for="credential">附件</label>
							<div class="controls">
								<input id="attachment" readonly name="attachment" type="text" class="span10" style="width: 350px;">
								<input value="删除" type="button" id="create-file-delete" class="btn btn-small btn-success span2" style="width: 48px;">
							</div>
						</div>
					</form>
						<form id="create-file-form" action="/simpleupload" class="form-horizontal" method="post" enctype="multipart/form-data" target="acceptFrame">
						<div class="control-group">
							<label class="control-label" for="credential">附件</label>
							<div class="controls">
								<input name="file" id="file" type="file">
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
			<!-- 查看 -->
	<div id="detail-modal" class="modal hide" >
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-list"></i> 查看
			</h5>
		</div>
		<div class="modal-body"  align="center">
			<div class="row-fluid">
				<div  class="span12">
					<img id="imgfile" src="" class='img-polaroid' style="height: 500px">
				</div>
			</div>
		</div>
		
	</div>
			<iframe name="acceptFrame" border="1" frameborder="1" width="100" height="100" style="display: none"></iframe>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/spmi/daily/picture/index');
	</script>
</body>
</html>
