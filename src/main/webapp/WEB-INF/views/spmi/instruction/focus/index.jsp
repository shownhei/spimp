<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>重点工作 - 安全生产综合管理平台</title>
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
					<button id="create" class="btn btn-small btn-success">
						<i class="icon-plus-sign-alt"></i> 新建
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled">
						<i class="icon-edit"></i> 编辑
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
						<div class="input-append">
							<input name="startDate" type="datetime" placeholder="开始日期" class="input-small">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<div class="input-append">
							<input name="endDate" type="datetime" placeholder="结束日期" class="input-small">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<input name="search" type="text" style="height:15px;width:130px;font-size:12px;" placeholder="输入工作名称/记录人...">
						<button id="submit" type="button" class="btn btn-primary btn-small">查询</button>
						<button id="reset" type="reset" class="btn btn-primary btn-small">重置</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="material-table"></div>
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
					<form id="create-form" class="form-horizontal" style="margin-bottom: 0px;">
						<div class="control-group">
							<label class="control-label" for="name">工作名称</label>
							<div class="controls">
								<input id="name" name="name" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="startTime">开始时间</label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="startTime" name="startTime">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="endTime">结束时间</label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="endTime" name="endTime">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="positon">地点</label>
							<div class="controls">
								<input id="positon" name="positon" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="responser">现场负责人</label>
							<div class="controls">
								<input id="responser" name="responser" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="workers">工作人员</label>
							<div class="controls">
								<input id="workers" name="workers" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="process">工作进度</label>
							<div class="controls">
								<input id="process" name="process" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="summary">工作总结情况</label>
							<div class="controls">
								<input id="summary" name="summary" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="description">工作简述</label>
							<div class="controls">
								<textarea id="description" name="description" rows=3></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="recorder">记录人</label>
							<div class="controls">
								<input id="recorder" name="recorder" type="text">
							</div>
						</div>
					</form>
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
	<div id="edit-modal" class="modal modal-sm hide">
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
							<label class="control-label" for="name">工作名称</label>
							<div class="controls">
								<input id="name" name="name" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="startTime">开始时间</label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="startTime" name="startTime">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="endTime">结束时间</label>
							<div class="controls">
								<input type="datetime" placeholder="请选择" class="input-small" autocomplete="off" id="endTime" name="endTime">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="positon">地点</label>
							<div class="controls">
								<input id="positon" name="positon" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="responser">现场负责人</label>
							<div class="controls">
								<input id="responser" name="responser" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="workers">工作人员</label>
							<div class="controls">
								<input id="workers" name="workers" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="process">工作进度</label>
							<div class="controls">
								<input id="process" name="process" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="summary">工作总结情况</label>
							<div class="controls">
								<input id="summary" name="summary" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="description">工作简述</label>
							<div class="controls">
								<textarea id="description" name="description" rows=3></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="recorder">记录人</label>
							<div class="controls">
								<input id="recorder" name="recorder" type="text">
							</div>
						</div>
					</form>
				</div>
				<div id="edit-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="edit-message-content"></span>
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
							<i class="icon-remove"></i>
							<span id="remove-message-content"></span>
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
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/spmi/instruction/focus/index');
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