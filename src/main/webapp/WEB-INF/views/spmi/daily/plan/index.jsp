<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>工作安排 - 安全生产综合管理平台</title>
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
						<i class="icon-plus-sign-alt"></i>
						<span class="hidden-phone">新建</span>
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled" title="整改安排不能编辑">
						<i class="icon-edit"></i>
						<span class="hidden-phone">编辑</span>
					</button>
					<button id="designate" class="btn btn-small btn-primary disabled" title="将工作安排指派给具体执行人">
						<i class="icon-share-alt"></i>
						<span class="hidden-phone">指派</span>
					</button>
					<button id="execute" class="btn btn-small btn-primary disabled" title="填写反馈，并将工作安排标记为已执行">
						<i class="icon-check"></i>
						<span class="hidden-phone">已执行</span>
					</button>
					<button id="remove" class="btn btn-small btn-danger disabled" title="整改安排不能删除">
						<i class="icon-trash"></i>
						<span class="hidden-phone">删除</span>
					</button>
				</div>
				<div class="nav-search">
					<form id="query-form" class="form-inline" onsubmit="return false;">
						<div class="input-append">
							<input id="query-startDate" name="startDate" type="datetime" placeholder="截止开始日期" class="input-small" autocomplete="off">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<div class="input-append">
							<input id="query-endDate" name="endDate" type="datetime" placeholder="截止结束日期" class="input-small" autocomplete="off">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<select id="query-categorySelect" name="category" class="input-small">
							<option value="" class="light-grey">选择分类</option>
							<option value="整改安排">整改安排</option>
							<option value="日常工作">日常工作</option>
							<option value="其他工作">其他工作</option>
						</select>
						<select id="query-statusSelect" name="status" class="input-small">
							<option value="" class="light-grey">选择状态</option>
							<option value="未指派" class="text-error">未指派</option>
							<option value="已指派" class="text-warning">已指派</option>
							<option value="已执行" class="text-success">已执行</option>
							<option value="已完成" class="text-info">已完成</option>
						</select>
						<button id="query-button" class="btn btn-small btn-primary">查询</button>
						<button id="reset-button" type="reset" class="btn btn-primary btn-small">重置</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="records"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-lg hide">
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
							<label class="control-label">主题</label>
							<div class="controls">
								<input name="title" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">分类</label>
							<div class="controls">
								<select name="category">
									<option value="日常工作">日常工作</option>
									<option value="其他工作">其他工作</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">内容</label>
							<div class="controls">
								<textarea name="content" class="xheditor {skin:'nostyle',tools:'simple'}"></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">截止日期</label>
							<div class="controls">
								<input id="create-cutoffDate" name="cutoffDate" type="datetime">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">执行人</label>
							<div class="controls">
								<input name="executor" type="text">
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
	<!-- 指派 -->
	<div id="designate-modal" class="modal modal-lg hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-share-alt"></i> 指派
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="designate-form" class="form-horizontal" onsubmit="return false;">
						<div class="control-group">
							<label class="control-label">主题</label>
							<div class="controls">
								<input name="title" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">分类</label>
							<div class="controls">
								<input name="category" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">内容</label>
							<div class="controls">
								<textarea id="designate-content" name="content" style="width: 560px;"></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">截止日期</label>
							<div class="controls">
								<input name="cutoffDate" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">执行人</label>
							<div class="controls">
								<input name="executor" type="text">
							</div>
						</div>
					</form>
				</div>
				<div id="designate-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="designate-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="designate-save" class="btn btn-small btn-primary">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 已执行确认 -->
	<div id="execute-modal" class="modal modal-lg hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-check"></i> 已执行
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="execute-form" class="form-horizontal" onsubmit="return false;">
						<div class="control-group">
							<label class="control-label">主题</label>
							<div class="controls">
								<input name="title" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">执行人</label>
							<div class="controls">
								<input name="executor" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">截止日期</label>
							<div class="controls">
								<input name="cutoffDate" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">反馈</label>
							<div class="controls">
								<textarea name="feedback" class="xheditor {skin:'nostyle',tools:'simple'}"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div id="execute-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="execute-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="execute-save" class="btn btn-small btn-primary">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
	<!-- 编辑 -->
	<div id="edit-modal" class="modal modal-lg hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-edit"></i> 编辑
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="edit-form" class="form-horizontal" onsubmit="return false;">
						<div class="control-group">
							<label class="control-label">主题</label>
							<div class="controls">
								<input name="title" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">分类</label>
							<div class="controls">
								<select name="category">
									<option value="日常工作">日常工作</option>
									<option value="其他工作">其他工作</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">内容</label>
							<div class="controls">
								<textarea id="edit-content" name="content" style="width: 560px;"></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">截止日期</label>
							<div class="controls">
								<input id="edit-cutoffDate" name="cutoffDate" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">执行人</label>
							<div class="controls">
								<input name="executor" type="text">
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
	<div id="remove-modal" class="modal modal-sm hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="red">
				<i class="icon-trash"></i> 删除
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<i class="icon-warning-sign"></i> 提示：确认删除选择的工作安排？
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
	<div id="view-modal" class="modal modal-xl hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-list"></i> 查看
			</h5>
		</div>
		<div class="modal-body">
			<table style="width: 100%" class="reform-table">
				<tbody>
					<tr>
						<td colspan="6" style="text-align: center; background-color: #dfe3e8;">
							<h4>工作安排</h4>
						</td>
					</tr>
					<tr>
						<td>主题</td>
						<td id="view-plan-title" colspan="5"></td>
					</tr>
					<tr>
						<td>内容</td>
						<td id="view-plan-content" colspan="5"></td>
					</tr>
					<tr>
						<td>截止日期</td>
						<td id="view-plan-cutoffDate"></td>
						<td>执行人</td>
						<td id="view-plan-executor"></td>
						<td>分类</td>
						<td id="view-plan-category"></td>
					</tr>
					<tr>
						<td>反馈</td>
						<td id="view-plan-feedback" colspan="5"></td>
					</tr>
					<tr>
						<td>反馈时间</td>
						<td id="view-plan-feedbackTime"></td>
						<td>状态</td>
						<td id="view-plan-status"></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>创建时间</td>
						<td id="view-plan-createTime"></td>
						<td>创建人</td>
						<td id="view-plan-creater"></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="modal-footer">
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 关闭
			</button>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/spmi/daily/plan/index');
	</script>
</body>
</html>
