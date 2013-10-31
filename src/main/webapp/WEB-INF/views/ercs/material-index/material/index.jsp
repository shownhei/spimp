<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>应急资源管理 - 安全生产综合管理平台</title>
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
				</div>
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<span class="input-icon">
							<input id="nav-search-input" name="resourceName" type="text" placeholder="输入资源名称..." class="input-small nav-search-input" autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="material-table"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 添加应急资源
			</h5>
		</div>
		<div class="modal-body ">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal ">
						<div class="control-group">
							<label class="control-label " for="principal">资源名称</label>
							<div class="controls">
								<input id="resourceName" name="resourceName" type="text" >
							</div>
						</div>
                        <div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="resourceNo">编号</label>
								<div class="controls">
									<input id="resourceNo" name="resourceNo" type="text"   class="span12" >
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label span4" for="amount">数量</label>
								<div class="controls">
									<input id="amount" name="amount" type="text"  class="span11">
								</div>
							</div>
						</div>	        
	        			<div class="control-group">
							<label class="control-label" for="function">用途</label>
							<div class="controls">
								<input id="function" name="function" type="text" >
							</div>
						</div>	
					    <div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="model">型号</label>
								<div class="controls">
									<input id="model" name="model" type="text"  class="span12">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label span4" for="origin">原产地</label>
								<div class="controls">
									<input id="origin" name="origin" type="text"   class="span11">
								</div>
							</div>
						</div>	 	
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="butTime">购置时间</label>
								<div class="controls">
									<input id="butTime" name="butTime" type="datetime"   class="span12">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label span4" for="expiration">使用期限</label>
								<div class="controls">
									<input id="expiration" name="expiration" type="text"  class="span11">
								</div>
							</div>
						</div>	
						
						<div class="control-group">
							<label class="control-label" for="location">存放位置</label>
							<div class="controls">
								<input id="location" name="location" type="text" >
							</div>
						</div>	
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="manager">管理人员</label>
								<div class="controls">
									<input id="manager" name="manager" type="text"  class="span12">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label span4" for="telephone">手机</label>
								<div class="controls">
									<input id="telephone" name="telephone" type="text"  class="span11">
								</div>
							</div>
						</div>	
						<div class="control-group">
							<label class="control-label" for="remark">备注</label>
							<div class="controls">
								<input id="remark" name="remark" type="text" >
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
	<div id="edit-modal" class="modal modal-md  hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="blue">
				<i class="icon-edit"></i> 编辑应急资源
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="edit-form" class="form-horizontal">
						<input  name="id" type="hidden" >
					    <div class="control-group">
							<label class="control-label" for="principal">资源名称</label>
							<div class="controls">
								<input id="resourceName" name="resourceName" type="text" >
							</div>
						</div>
                        <div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="resourceNo">编号</label>
								<div class="controls">
									<input id="resourceNo" name="resourceNo" type="text"  class="span12">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label span4" for="amount">数量</label>
								<div class="controls">
									<input id="amount" name="amount" type="text" class="span11">
								</div>
							</div>
						</div>	        
	        			<div class="control-group">
							<label class="control-label" for="function">用途</label>
							<div class="controls">
								<input id="function" name="function" type="text" >
							</div>
						</div>	
					    <div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="model">型号</label>
								<div class="controls">
									<input id="model" name="model" type="text"  class="span12" >
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label span4" for="origin">原产地</label>
								<div class="controls">
									<input id="origin" name="origin" type="text"   class="span11">
								</div>
							</div>
						</div>	 	
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label " for="butTime">购置时间</label>
								<div class="controls">
									<input id="butTime" name="butTime" type="datetime"  class="span12">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label span4" for="expiration">使用期限</label>
								<div class="controls">
									<input id="expiration" name="expiration" type="text"  class="span11">
								</div>
							</div>
						</div>	
						
						<div class="control-group">
							<label class="control-label" for="location">存放位置</label>
							<div class="controls">
								<input id="location" name="location" type="text" >
							</div>
						</div>	
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="manager">管理人员</label>
								<div class="controls">
									<input id="manager" name="manager" type="text"  class="span12">
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label span4" for="telephone">手机</label>
								<div class="controls">
									<input id="telephone" name="telephone" type="text"  class="span11">
								</div>
							</div>
						</div>	
						<div class="control-group">
							<label class="control-label" for="remark">备注</label>
							<div class="controls">
								<input id="remark" name="remark" type="text" >
							</div>
						</div>	
	        
					</form>
				</div>
				<div id="edit-message-alert" class="row-fluid hide">
					<div class="span12">
						<div class="alert alert-error">s
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
		seajs.use('${resources}/scripts/app/ercs/material-index/material/index');
	</script>
</body>
</html>
