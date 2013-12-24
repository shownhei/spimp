<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>小改小革管理 - 安全生产综合管理平台</title>
<%@ include file="../../../common/head.jsp"%>
<%@ include file="../../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar">
				<div class="toolbar" id="button_bar">
					<button id="create" class="btn btn-small btn-success">
						<i class="icon-plus-sign-alt"></i> 新建
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled">
						<i class="icon-edit"></i> 编辑
					</button>
					<button id="upload" class="btn btn-small btn-primary disabled">
						<i class="icon-upload"></i> 图片
					</button>
					<button id="remove" class="btn btn-small btn-danger disabled">
						<i class="icon-trash"></i> 删除
					</button>
					<button id="export" class="btn btn-small btn-pink disabled">
						<i class="icon-download-alt"></i> 导出
					</button>
				</div>

				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<input name="search" type="text" style="height: 15px; width: 130px; font-size: 12px;" placeholder="输入项目名称...">
						<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
						<button id="reset" type="reset" class="btn btn-primary btn-small">重置</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="material-table"></div>
				<div class="row-fluid" id="detail-panel" style="display: none;"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 新建
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="projectName">项目名称</label>
							<div class="controls">
								<input id="create_projectName" name="projectName" type="text">
							</div>
						</div>

						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="refugeType">负责人</label>
								<div class="controls">
									<input id="create_chargePerson" name="chargePerson" type="text" class="span2" style="width: 130px;">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="quantity">申报日期</label>
								<div class="controls">
									<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="create_declarationDate" name="declarationDate" class="span2"
										style="width: 122px;">
								</div>
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="refugeType">实施地点</label>
								<div class="controls">
									<input id="create_implementationAddress" name="implementationAddress" type="text" class="span2" style="width: 130px;">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label " for="quantity">实施时间</label>
								<div class="controls">
									<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="create_implementationPeriod" name="implementationPeriod"
										class="span2" style="width: 122px;">
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="participant">参与人</label>
							<div class="controls">
								<input id="create_participant" name="participant" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inventionPurpose">目的</label>
							<div class="controls">
								<input id="create_inventionPurpose" name="inventionPurpose" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="content">主要内容或原理</label>
							<div class="controls">
								<textarea id="create_content" name="content" class="xheditor {skin:'nostyle',tools:'simple'}" style="height: 100px;"></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="analysis">效果及经济社会效益分析</label>
							<div class="controls">
								<textarea id="create_analysis" name="analysis" class="xheditor {skin:'nostyle',tools:'simple'}" style="height: 100px;"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div id="create-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i> <span id="create-message-content"></span>
						</div>
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
	<!-- 编辑 -->
	<div id="edit-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-edit"></i> 编辑
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="edit-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="projectName">项目名称</label>
							<div class="controls">
								<input id="edit_projectName" name="projectName" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="chargePerson">负责人</label>
							<div class="controls">
								<input id="edit_chargePerson" name="chargePerson" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="declarationDate">申报日期</label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="edit_declarationDate" name="declarationDate">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="implementationAddress">实施地点</label>
							<div class="controls">
								<input id="edit_implementationAddress" name="implementationAddress" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="implementationPeriod">实施时间</label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="edit_implementationPeriod" name="implementationPeriod">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="participant">参与人</label>
							<div class="controls">
								<input id="edit_participant" name="participant" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inventionPurpose">目的</label>
							<div class="controls">
								<input id="edit_inventionPurpose" name="inventionPurpose" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="content">主要内容或原理</label>
							<div class="controls">
								<input id="edit_content" name="content" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="analysis">效果及经济社会效益分析</label>
							<div class="controls">
								<input id="edit_analysis" name="analysis" type="text">
							</div>
						</div>
					</form>
				</div>
				<div id="edit-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i> <span id="edit-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="edit-save" class="btn btn-small btn-primary">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 详细信息 -->
	<div id="detail-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-list"></i> 详细信息
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="detail-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="projectName">项目名称</label>
							<div class="controls">
								<input id="detail_projectName" name="projectName" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="chargePerson">负责人</label>
							<div class="controls">
								<input id="detail_chargePerson" name="chargePerson" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="declarationDate">申报日期</label>
							<div class="controls">
								<input id="detail_declarationDate" name="declarationDate" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="implementationAddress">实施地点</label>
							<div class="controls">
								<input id="detail_implementationAddress" name="implementationAddress" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="implementationPeriod">实施时间</label>
							<div class="controls">
								<input id="detail_implementationPeriod" name="implementationPeriod" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="participant">参与人</label>
							<div class="controls">
								<input id="detail_participant" name="participant" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="inventionPurpose">目的</label>
							<div class="controls">
								<input id="detail_inventionPurpose" name="inventionPurpose" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="content">主要内容或原理</label>
							<div class="controls">
								<input id="detail_content" name="content" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="analysis">效果及经济社会效益分析</label>
							<div class="controls">
								<input id="detail_analysis" name="analysis" type="text" readonly="readonly">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 关闭
			</button>
		</div>
	</div>
	<!-- 删除 -->
	<div id="remove-modal" class="modal hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="red">
				<i class="icon-trash"></i> 删除
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<i class="icon-warning-sign"></i> 提示：删除选中的数据？
				</div>
				<div id="remove-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i> <span id="remove-message-content"></span>
						</div>
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
	<!-- 上传-->
	<div id="upload-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 上传图片
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="upload-form" class="form-horizontal" style="margin-bottom: 0px;"></form>
					<form id="create-file-form" action="/simpleupload" class="form-horizontal" style="margin-bottom: 0px;" method="post" enctype="multipart/form-data"
						target="acceptFrame">
						<div class="control-group">
							<label class="control-label span2" for="credential">处置方案</label>
							<div class="controls">
								<input name="file" id="file" type="file" class="span11">
							</div>
						</div>
					</form>
				</div>
				<div id="upload-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i> <span id="upload-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="upload-save" class="btn btn-small btn-success">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/electr/innovation/innovation/index');
	</script>
	<iframe name="acceptFrame" border="1" frameborder="1" width="100" height="100" style="display: none"></iframe>
	<div id="view-modal" class="modal hide" style="width: 800px;">
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