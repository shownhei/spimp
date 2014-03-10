<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应急报警管理 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/template.jsp"%>
<script id="viewwindow-template" type="text/x-handlebars-template">
	<div id="view-modal" class="modal hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-th-list"></i> 查看
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="view-form" class="form-horizontal">
						<input  name="id" type="hidden" class="span11">
						
					    <div class="control-group">
							<label class="control-label span2" for="accidentLocation">事故地点</label>
							<div class="controls">
								<span  name="accidentLocation"   disabled class="span11">{{accidentLocation}}</span>
							</div>
						</div>
	        
					    <div class="control-group">
							<label class="control-label span2" for="-accidentType">事故类型</label>
							<div class="controls">
                                <span   class="span11">{{accidentType.itemName}}</span>
							</div>
						</div>
	        
					    <div class="control-group">
							<label class="control-label span2" for="accidentLevel">严重程度</label>
							<div class="controls">
								<span   class="span11">{{accidentLevel.itemName}}</span>
							</div>
						</div>
	        
					    <div class="control-group">
							<label class="control-label span2" for="alarmPeople">报警人</label>
							<div class="controls">
								<span  name="alarmPeople" type="text" disabled  class="span11">{{alarmPeople}}</span>
							</div>
						</div>
	        
	        
					    <div class="control-group">
							<label class="control-label span2" for="detail">事故描述</label>
							<div class="controls">
                                 <span  type="text" disabled  class="span11">{{detail}}</span>
							</div>
						</div>
					    <div class="control-group">
							<label class="control-label span2" for="alarmTime">报警时间</label>
							<div class="controls">
								<span  type="text" disabled  class="span11">{{alarmTime}}</span>
							</div>
						</div>
						<input  name="dealFlag" type="hidden" class="span11">
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
</script>
<script id="alarmwindow-template" type="text/x-handlebars-template">
	<div id="edit{{id}}-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-edit"></i> 报警处理
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="edit{{id}}-form" class="form-horizontal">
						<input  name="id" type="hidden" >
					    <div class="control-group">
							<label class="control-label " for="accidentLocation">事故地点</label>
							<div class="controls">
								<input  name="accidentLocation" type="text">
							</div>
						</div>
	        
					    <div class="control-group">
							<label class="control-label " for="principal">事故类型</label>
							<div class="controls">
                                <select id="edit{{id}}-accidentType" name="accidentType[id]"  ></select>
							</div>
						</div>
	        
					    <div class="control-group">
							<label class="control-label " for="edit{{id}}-accidentLevel">严重程度</label>
							<div class="controls">
                                <select id="edit{{id}}-accidentLevel" name="accidentLevel[id]" ></select>
							</div>
						</div>
	        
					    <div class="control-group">
							<label class="control-label " for="alarmPeople">报警人</label>
							<div class="controls">
								<input  name="alarmPeople" type="text" >
							</div>
						</div>
	                    <div class="control-group">
							<label class="control-label " for="detail">事故描述</label>
							<div class="controls">
							   <textarea  name="detail"  rows=5 ></textarea>
							</div>
						</div>
					    <div class="control-group">
							<label class="control-label " for="alarmTime">报警时间</label>
							<div class="controls">
								<input  name="alarmTime" readOnly=true type="text" value="{{alarmTime}}" >
							</div>
						</div>
						<input  name="dealFlag" readOnly=true type="hidden" value="{{dealFlag}}">
					</form>
				</div>
				<div id="edit{{id}}-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="edit{{id}}-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
            <button id="edit{{id}}-miss" class="btn btn-small btn-primary">
				<i class="icon-ok"></i> 误报
			</button>
			<button id="edit{{id}}-save" class="btn btn-small btn-primary">
				<i class="icon-ok"></i> 确定
			</button>
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 取消
			</button>
		</div>
	</div>
</script>
</head>
<body class="navbar-fixed">
	<%@ include file="../../common/navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="../../common/sidebar.jsp"%>
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
					<button id="view" class="btn btn-small  disabled">
						<i class="icon-th-list"></i> 查看
					</button>
				</div>
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<span class="input-icon">
							<select id="accidentTypeSelect" name="accidentType" class="input-small" style="width: 150px;"></select>
						</span>
						<span class="input-icon">
							<select id="accidentLevelSelect" name="accidentLevel" class="input-small" style="width: 150px;">
							</select>
						</span>
						<span class="input-icon">
							<select id="dealFlagSelect" name="dealFlag" class="input-small" style="width: 150px;">
								<option value=''>请选择事故状态</option>
								<option value='0'>未处理</option>
								<option value='1'>已处理</option>
								<option value='2'>误报</option>
							</select>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
						<button id="nav-reset-button" class="btn btn-small btn-primary">重置</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="alarm-table"></div>
			</div>
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
					<form id="edit-form" class="form-horizontal">
						<input name="id" type="hidden">
						<div class="control-group">
							<label class="control-label " for="principal">事故地点</label>
							<div class="controls">
								<input name="accidentLocation" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="edit-accidentType">事故类型</label>
							<div class="controls">
								<select id="edit-accidentType" name="accidentType[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="accidentLevel">严重程度</label>
							<div class="controls">
								<select id="edit-accidentLevel" name="accidentLevel[id]"></select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="alarmPeople">报警人</label>
							<div class="controls">
								<input name="alarmPeople" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="detail">事故描述</label>
							<div class="controls">
								<textarea name="detail" rows=5></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="alarmTime">报警时间</label>
							<div class="controls">
								<input name="alarmTime" readOnly disabled type="text">
							</div>
						</div>
						<input name="dealFlag" type="hidden">
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
	<!-- 编辑end -->
	<!-- 删除 -->
	<div id="remove-modal" class="modal modal-md hide">
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
	<div id="warning-modal" class="modal modal-xs hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="red">
				<i class="icon-trash"></i> 删除
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<i class="icon-warning-sign"></i> 提示：未处理的报警记录不能删除？
				</div>
				<div id="warning-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">
							<i class="icon-remove"></i>
							<span id="warning-message-content"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button class="btn btn-small" data-dismiss="modal">
				<i class="icon-remove"></i> 确定
			</button>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/ercs/alarm/index');
	</script>
</body>
</html>
