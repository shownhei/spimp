<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>避难场所管理 - 安全生产综合管理平台</title>
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
							<select id="refugeTypeSelect" name="refugeType" class="input-small span2"></select>
						</span>
						<span class="input-icon">
							<input id="nav-search-input" name="refugeName" type="text" placeholder="输入文件号或文件名称..." class="input-small nav-search-input" autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="refuge-table"></div>
			</div>
		</div>
	</div>
	<!-- 新建 -->
	<div id="create-modal" class="modal modal-md hide">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h5 class="green">
				<i class="icon-plus-sign-alt"></i> 新增避难场所
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="create-form" class="form-horizontal">
						<div class="control-group">
							<label class="control-label span2" for="principal">名称</label>
							<div class="controls">
								<input id="refugeName" name="refugeName" type="text">
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="principal">种类</label>
								<div class="controls">
									<select id="create-refugeType" name="refugeType[id]" class="span12"></select>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label span4" for="principal">数量</label>
								<div class="controls">
									<input id="quantity" name="quantity" type="text" value="1" class="span11">
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">位置</label>
							<div class="controls">
								<input id="position" name="position" type="text" >
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">基本情况</label>
							<div class="controls">
								<input id="basicInfomation" name="basicInfomation" type="text">
							</div>
						</div>
						
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="principal">面积</label>
								<div class="controls">
									<input id="refugeArea" type="text"  name="refugeArea"  class="span12">
								</div>
							</div>
							<div class="control-group  span6">
								<label class="control-label span4" for="principal">可容纳人数</label>
								<div class="controls">
									<input id="capacity" name="capacity" type="text" value="10" class="span11">
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">基础设施</label>
							<div class="controls">
								<input id="infrastructure" name="infrastructure" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">防护功能</label>
							<div class="controls">
								<input id="protection" name="protection" type="text" >
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="principal">隶属单位</label>
							<div class="controls">
								<input name="department"  id="create_department" type="text" class="span10">
								<input type="button" value="选择" id="create_selectGroup" class="btn btn-small btn-success span2" >
							</div>
						</div>
						
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="principal">管理人</label>
								<div class="controls">
									<input id="manager" type="text" name="manager"  class="span12">
								</div>
							</div>
							<div class="control-group  span6">
								<label class="control-label span4" for="principal">联系方式</label>
								<div class="controls">
									<input id="telepone" name="telepone" type="text" class="span11">
								</div>
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
					</form>
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
				<i class="icon-edit"></i> 编辑
			</h5>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<form id="edit-form" class="form-horizontal">
						<input name="id" type="hidden" >
						<div class="control-group">
							<label class="control-label" for="principal">名称</label>
							<div class="controls">
								<input name="refugeName" type="text">
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="principal">种类</label>
								<div class="controls">
									<select id="edit-refugeType" name="refugeType[id]" class="span12"></select>
								</div>
							</div>
							<div class="control-group span6">
								<label class="control-label span4" for="principal">数量</label>
								<div class="controls">
									<input name="quantity" type="text" value="1" class="span11">
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="principal">位置</label>
							<div class="controls">
								<input name="position" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="principal">基本情况</label>
							<div class="controls">
								<input name="basicInfomation" type="text">
							</div>
						</div>
						
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="principal">面积</label>
								<div class="controls">
									<input type="text"  name="refugeArea" class="span12">
								</div>
							</div>
							<div class="control-group  span6">
								<label class="control-label span4" for="principal">可容纳人数</label>
								<div class="controls">
									<input name="capacity" type="text" value="10" class="span11">
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="principal">基础设施</label>
							<div class="controls">
								<input  name="infrastructure" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="principal">防护功能</label>
							<div class="controls">
								<input name="protection" type="text">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label " for="principal">隶属单位</label>
							<div class="controls">
								<input name="department"  id="edit_department" type="text" class="span10">
								<input type="button" value="选择1" id="edit_selectGroup" class="btn btn-small btn-success span2" >
							</div>
						</div>
						<div class="row-fluid ">
							<div class="control-group span6">
								<label class="control-label span4" for="principal">管理人</label>
								<div class="controls">
									<input  name="manager" type="text"  class="span12">
								</div>
							</div>
							<div class="control-group  span6">
								<label class="control-label span4" for="principal">联系方式</label>
								<div class="controls">
									<input name="telepone" type="text" class="span11">
								</div>
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
	<div id="remove-modal" class="modal  modal-xs  hide">
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
		seajs.use('${resources}/scripts/app/ercs/place/index');
	</script>
	
</body>
</html>
