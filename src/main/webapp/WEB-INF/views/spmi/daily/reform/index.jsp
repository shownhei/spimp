<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>整改通知单 - 安全生产综合管理平台</title>
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
					<button id="create" class="btn btn-small btn-success" title="填写整改通知单">
						<i class="icon-plus-sign-alt"></i>
						<span class="hidden-phone">新建</span>
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled" title="只能编辑已下发状态的整改通知单">
						<i class="icon-edit"></i>
						<span class="hidden-phone">编辑</span>
					</button>
					<button id="check" class="btn btn-small btn-primary disabled" title="审核已执行的整改通知单">
						<i class="icon-check"></i>
						<span class="hidden-phone">审核</span>
					</button>
					<button id="remove" class="btn btn-small btn-danger disabled" title="只能删除已下发状态的整改通知单">
						<i class="icon-trash"></i>
						<span class="hidden-phone">删除</span>
					</button>
				</div>
				<div class="nav-search">
					<form id="query-form" class="form-inline" onsubmit="return false;">
						<div class="input-append">
							<input id="query-startDate" name="startDate" type="datetime" placeholder="检查开始日期" class="input-small" autocomplete="off">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<div class="input-append">
							<input id="query-endDate" name="endDate" type="datetime" placeholder="检查结束日期" class="input-small" autocomplete="off">
							<span class="add-on nav-add-on">
								<i class="icon-calendar"></i>
							</span>
						</div>
						<select id="query-testGroupIdSelect" name="testGroupId" class="input-small" style="width: 100px">
						</select>
						<select id="query-statusSelect" name="status" class="input-small">
							<option value="" class="light-grey">选择状态</option>
							<option value="已下发" class="text-error">已下发</option>
							<option value="已指派" class="text-warning">已指派</option>
							<option value="已执行" class="text-success">已执行</option>
							<option value="已审核" class="text-info">已审核</option>
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
							<label class="control-label">安全问题或隐患</label>
							<div class="controls">
								<input name="issue" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">整改期限</label>
							<div class="controls">
								<input id="create-deadlineDate" name="deadlineDate" type="datetime">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">整改标准或要求</label>
							<div class="controls">
								<input name="standard" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">整改措施</label>
							<div class="controls">
								<textarea name="measure" class="xheditor {skin:'nostyle',tools:'simple'}"></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">整改责任人</label>
							<div class="controls">
								<input id="create-principalId" name="principalId" type="text" style="width: 560px;">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">检查日期</label>
							<div class="controls">
								<input id="create-checkDate" name="checkDate" type="datetime">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">检查人员</label>
							<div class="controls">
								<input name="checker" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">被检单位</label>
							<div class="controls">
								<input id="create-testGroupId" name="testGroupId" type="text" style="width: 560px;">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">隐患下发部门</label>
							<div class="controls">
								<input id="create-sendGroupId" name="sendGroupId" type="text" style="width: 560px;">
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
							<label class="control-label">安全问题或隐患</label>
							<div class="controls">
								<input name="issue" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">整改期限</label>
							<div class="controls">
								<input id="edit-deadlineDate" name="deadlineDate" type="datetime">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">整改标准或要求</label>
							<div class="controls">
								<input name="standard" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">整改措施</label>
							<div class="controls">
								<textarea id="edit-measure" name="measure" style="width: 560px;"></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">整改责任人</label>
							<div class="controls">
								<input id="edit-principalId" name="principalId" type="text" style="width: 560px;">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">检查日期</label>
							<div class="controls">
								<input id="edit-checkDate" name="checkDate" type="datetime">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">检查人员</label>
							<div class="controls">
								<input name="checker" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">被检单位</label>
							<div class="controls">
								<input id="edit-testGroupId" name="testGroupId" type="text" style="width: 560px;">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">隐患下发部门</label>
							<div class="controls">
								<input id="edit-sendGroupId" name="sendGroupId" type="text" style="width: 560px;">
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
	<!-- 审核 -->
	<div id="check-modal" class="modal modal-lg hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-check"></i> 审核
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="check-form" class="form-horizontal" onsubmit="return false;">
						<div class="control-group">
							<label class="control-label">安全问题或隐患</label>
							<div class="controls">
								<input name="issue" type="text" readonly="readonly">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">整改责任人</label>
							<div class="controls">
								<input name="principal" type="text" readonly="readonly">
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
				<div id="check-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="check-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button id="check-save" class="btn btn-small btn-primary">
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
					<i class="icon-warning-sign"></i> 提示：删除整改通知单将同时删除对应的工作安排，确认删除？
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
			<div style="height: 450px; overflow: auto;">
				<table style="width: 100%" class="reform-table">
					<tbody>
						<tr>
							<td colspan="6" style="text-align: center; background-color: #dfe3e8;">
								<h4>整改通知单</h4>
							</td>
						</tr>
						<tr>
							<td colspan="4"></td>
							<td>编号</td>
							<td id="view-reform-number"></td>
						</tr>
						<tr>
							<td>存在安全问题或隐患</td>
							<td id="view-reform-issue" colspan="5"></td>
						</tr>
						<tr>
							<td>整改期限</td>
							<td id="view-reform-deadlineDate"></td>
							<td>整改责任人</td>
							<td id="view-reform-principal"></td>
							<td>被检单位</td>
							<td id="view-reform-testGroup"></td>
						</tr>
						<tr>
							<td>检查人员</td>
							<td id="view-reform-checker"></td>
							<td>检查日期</td>
							<td id="view-reform-checkDate"></td>
							<td>隐患下发部门</td>
							<td id="view-reform-sendGroup"></td>
						</tr>
						<tr>
							<td>整改标准或要求</td>
							<td id="view-reform-standard" colspan="5"></td>
						</tr>
						<tr>
							<td>整改措施</td>
							<td id="view-reform-measure" colspan="5"></td>
						</tr>
						<tr>
							<td>审核意见</td>
							<td id="view-reform-feedback" colspan="5"></td>
						</tr>
						<tr>
							<td>审核时间</td>
							<td id="view-reform-feedbackTime"></td>
							<td>状态</td>
							<td id="view-reform-status"></td>
							<td>完成日期</td>
							<td id="view-reform-doneDate"></td>
						</tr>
						<tr>
							<td>创建人</td>
							<td id="view-reform-creater"></td>
							<td>创建时间</td>
							<td id="view-reform-createTime"></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="6" style="text-align: center; background-color: #dfe3e8;">
								<h4>整改安排</h4>
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
							<td></td>
							<td></td>
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
					</tbody>
				</table>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 关闭
			</button>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/spmi/daily/reform/index');
	</script>
</body>
</html>
