<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>培训计划 - 安全生产综合管理平台</title>
    <%@ include file="../../../common/head.jsp" %>
    <%@ include file="../../../common/template.jsp" %>
</head>
<body class="navbar-fixed">
<%@ include file="../../../common/navbar.jsp" %>
<div class="main-container container-fluid">
    <%@ include file="../../../common/sidebar.jsp" %>
    <div class="main-content">
        <div class="page-toolbar">
            <div class="toolbar">
                <button id="create" class="btn btn-small btn-success" title="上传培训计划">
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
							<input id="nav-search-input" name="search" type="text" placeholder="输入计划名称..."
                                   class="input-small nav-search-input" autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
                    <button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
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
                        <label class="control-label">计划名称</label>

                        <div class="controls">
                            <input name="name" type="text">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">培训计划分类</label>

                        <div class="controls">
                            <select name="category">
                                <option value="年度">年度</option>
                                <option value="月度">月度</option>
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">来源</label>

                        <div class="controls">
                            <select name="origin">
                                <option value="内培计划">内培计划</option>
                                <option value="委外培训计划">委外培训计划</option>
                            </select>
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
        <h5 class="green">
            <i class="icon-plus-sign-alt"></i> 编辑
        </h5>
    </div>
    <div class="modal-body">
        <div class="row-fluid">
            <div class="span12">
                <form id="edit-form" class="form-horizontal" onsubmit="return false;">
                  
                    <div class="control-group">
                        <label class="control-label">计划名称</label>

                        <div class="controls">
                            <input name="name" type="text">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">培训计划分类</label>

                        <div class="controls">
                            <select name="category">
                                <option value="年度">年度</option>
                                <option value="月度">月度</option>
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">来源</label>

                        <div class="controls">
                            <select name="origin">
                                <option value="内培计划">内培计划</option>
                                <option value="委外培训计划">委外培训计划</option>
                            </select>
                        </div>
                    </div>
              		 <div class="control-group">
							<label class="control-label" for="credential">附件</label>
							<div class="controls">
								<input name="attachment" id="edit_attachment" readonly type="text">
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
        <button id="edit-save" class="btn btn-small btn-success">
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
					<i class="icon-warning-sign"></i> 提示：确认删除选中的数据？
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
	<iframe name="acceptFrame" border="1" frameborder="1" width="100" height="100" style="display: none"></iframe>
<script type="text/javascript">
    seajs.use('${resources}/scripts/app/spmi/daily/training/index');
</script>
</body>
</html>
