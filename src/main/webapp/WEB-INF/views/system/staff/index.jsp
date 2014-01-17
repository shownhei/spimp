<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>人员管理 - 安全生产综合管理平台</title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../common/sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar">
				<div class="toolbar">
					<button id="create" class="btn btn-small btn-success">
						<i class="icon-plus-sign-alt"></i>
						<span class="hidden-phone">新建</span>
					</button>
					<button id="edit" class="btn btn-small btn-primary disabled">
						<i class="icon-edit"></i>
						<span class="hidden-phone">编辑</span>
					</button>
					<button id="remove" class="btn btn-small btn-danger disabled">
						<i class="icon-trash"></i>
						<span class="hidden-phone">删除</span>
					</button>
				</div>
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<span class="input-icon">
							<input id="nav-search-input" name="search" type="text" placeholder="输入姓名或身份证号..." class="input-small nav-search-input" style="width: 180px"
								autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="staff-table"></div>
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
						<table style="width: 100%">
							<tbody>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">姓名</td>
									<td>
										<input name="name" type="text" style="width: 140px">
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">性别</td>
									<td>
										<select name="gender" style="width: 140px">
											<option value="男">男</option>
											<option value="女">女</option>
										</select>
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">用工类别</td>
									<td>
										<select name="category" style="width: 140px">
											<option value="" class="light-grey">请选择用工类别</option>
											<option value="正">正</option>
											<option value="协">协</option>
											<option value="临">临</option>
										</select>
									</td>
								</tr>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">文化程度</td>
									<td>
										<select id="create-education" name="education" style="width: 140px">
										</select>
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">职务职称</td>
									<td>
										<select id="create-duty" name="duty" style="width: 140px">
										</select>
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">所属机构</td>
									<td>
										<select id="create-groupEntity" name="groupEntity[id]" style="width: 140px"></select>
									</td>
								</tr>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">确定岗位</td>
									<td>
										<select id="create-post" name="post" style="width: 140px">
										</select>
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">兼职岗位</td>
									<td>
										<select id="create-partTime" name="partTime" style="width: 140px">
										</select>
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">定岗日期</td>
									<td>
										<input name="postDate" type="datetime" style="width: 140px">
									</td>
								</tr>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">资格证号</td>
									<td>
										<input name="qualification" type="text" style="width: 140px">
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">身份证</td>
									<td>
										<input name="identityCard" type="text" style="width: 140px">
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px"></td>
									<td></td>
								</tr>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">备注</td>
									<td colspan="5">
										<textarea name="remark" class="xheditor {skin:'nostyle',tools:'simple'}" style="width: 100%"></textarea>
									</td>
								</tr>
							</tbody>
						</table>
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
						<table style="width: 100%">
							<tbody>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">姓名</td>
									<td>
										<input name="name" type="text" style="width: 140px">
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">性别</td>
									<td>
										<select name="gender" style="width: 140px">
											<option value="男">男</option>
											<option value="女">女</option>
										</select>
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">用工类别</td>
									<td>
										<select name="category" style="width: 140px">
											<option value="" class="light-grey">请选择用工类别</option>
											<option value="正">正</option>
											<option value="协">协</option>
											<option value="临">临</option>
										</select>
									</td>
								</tr>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">文化程度</td>
									<td>
										<select id="edit-education" name="education" style="width: 140px">
										</select>
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">职务职称</td>
									<td>
										<select id="edit-duty" name="duty" style="width: 140px">
										</select>
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">所属机构</td>
									<td>
										<select id="edit-groupEntity" name="groupEntity[id]" style="width: 140px"></select>
									</td>
								</tr>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">确定岗位</td>
									<td>
										<select id="edit-post" name="post" style="width: 140px">
										</select>
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">兼职岗位</td>
									<td>
										<select id="edit-partTime" name="partTime" style="width: 140px">
										</select>
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">定岗日期</td>
									<td>
										<input name="postDate" type="datetime" style="width: 140px">
									</td>
								</tr>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">资格证号</td>
									<td>
										<input name="qualification" type="text" style="width: 140px">
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">身份证</td>
									<td>
										<input name="identityCard" type="text" style="width: 140px">
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px"></td>
									<td></td>
								</tr>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">备注</td>
									<td colspan="5">
										<textarea id="edit-remark" name="remark" style="width: 100%"></textarea>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
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
					<i class="icon-warning-sign"></i> 提示：删除人员将删除与此人员关联的所有数据，确认删除选中的人员？
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
	<!-- 变更记录 -->
	<div id="record-modal" class="modal modal-xl hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-list-alt"></i> 变更记录
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<div id="record-no-records" class="alert alert-info hide">
						<i class="icon-exclamation-sign"></i>
						<span>
							<strong id="record-staff-name"></strong> 无变更记录。
						</span>
					</div>
					<div id="alteration-records" class="hide" style="max-height: 400px; overflow: auto;">
						<table class="grade-table">
							<thead>
								<tr>
									<th width="80px">变更日期 <i class="icon-caret-up"></i></th>
									<th>变更内容</th>
								</tr>
							</thead>
							<tbody id="alteration-records-tbody">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 关闭
			</button>
		</div>
	</div>
	<!-- 查看 -->
	<div id="view-modal" class="modal modal-lg hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-list"></i> 查看
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="view-form" class="form-horizontal" onsubmit="return false;">
						<table style="width: 100%">
							<tbody>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">姓名</td>
									<td>
										<input name="name" type="text" style="width: 140px" readonly="readonly">
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">性别</td>
									<td>
										<input name="gender" type="text" style="width: 140px" readonly="readonly">
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">用工类别</td>
									<td>
										<input name="category" type="text" style="width: 140px" readonly="readonly">
									</td>
								</tr>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">文化程度</td>
									<td>
										<input name="education" type="text" style="width: 140px" readonly="readonly">
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">职务职称</td>
									<td>
										<input name="duty" type="text" style="width: 140px" readonly="readonly">
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">所属机构</td>
									<td>
										<input id="view-groupEntity" name="groupEntity.name" type="text" style="width: 140px" readonly="readonly">
									</td>
								</tr>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">确定岗位</td>
									<td>
										<input name="post" type="text" style="width: 140px" readonly="readonly">
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">兼职岗位</td>
									<td>
										<input name="partTime" type="text" style="width: 140px" readonly="readonly">
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">定岗日期</td>
									<td>
										<input name="postDate" type="text" style="width: 140px" readonly="readonly">
									</td>
								</tr>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">资格证号</td>
									<td>
										<input name="qualification" type="text" style="width: 140px" readonly="readonly">
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px">身份证</td>
									<td>
										<input name="identityCard" type="text" style="width: 140px" readonly="readonly">
									</td>
									<td style="width: 80px; text-align: right; padding-right: 10px"></td>
									<td></td>
								</tr>
								<tr>
									<td style="width: 80px; text-align: right; padding-right: 10px">备注</td>
									<td colspan="5" id="view-remark"></td>
								</tr>
							</tbody>
						</table>
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
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/system/staff/index');
	</script>
</body>
</html>
