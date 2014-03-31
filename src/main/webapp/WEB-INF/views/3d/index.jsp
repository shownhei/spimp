<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
<head>
<title>三维综合管理 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="head.jsp"%>
<%@ include file="../common/template.jsp"%>
</head>
<body class="navbar-fixed">
	<%@ include file="navbar.jsp"%>
	<div class="main-container container-fluid">
		<%@ include file="sidebar.jsp"%>
		<div class="main-content">
			<div class="page-toolbar">
				<div class="toolbar">
					<button class="btn btn-small btn-info" data-image="主界面.png" data-type="main">
						<i class="icon-home"></i>
						<span>主界面</span>
					</button>
					<button class="btn btn-small btn-info" data-image="轨迹回放.png">
						<i class="icon-retweet"></i>
						<span>轨迹回放</span>
					</button>
					<button class="btn btn-small btn-info" data-image="人员信息.png">
						<i class="icon-user"></i>
						<span>人员信息</span>
					</button>
					<button class="btn btn-small btn-info" data-image="安全监控.png">
						<i class="icon-desktop"></i>
						<span>安全监控</span>
					</button>
					<button class="btn btn-small btn-info">
						<i class="icon-random"></i>
						<span>连接力控</span>
					</button>
					<button class="btn btn-small btn-info" data-image="信息统计.png" data-type="info">
						<i class="icon-info-sign"></i>
						<span>信息统计</span>
					</button>
					<button class="btn btn-small btn-info" data-image="路线飞行.png">
						<i class="icon-rocket"></i>
						<span>路线飞行</span>
					</button>
					<button class="btn btn-small btn-info" data-image="避灾路线-火灾1.png">
						<i class="icon-road"></i>
						<span>避灾路线</span>
					</button>
					<button class="btn btn-small btn-info" data-image="应急资源.jpg">
						<i class="icon-puzzle-piece"></i>
						<span>应急资源</span>
					</button>
					<button class="btn btn-small btn-info" data-image="应急资源.jpg" id="water">
						<i class="icon-puzzle-piece"></i>
						<span>主水仓</span>
					</button>
					<button class="btn btn-small btn-info" data-image="应急资源.jpg" id="sendMessage">
						<i class="icon-puzzle-piece"></i>
						<span>发消息</span>
					</button>
				</div>
				<div class="nav-search">
					<form id="search-form" class="form-search" onsubmit="return false;">
						<span class="input-icon">
							<input id="nav-search-input" name="search" type="text" placeholder="输入..." class="input-small nav-search-input" autocomplete="off">
							<i class="icon-search nav-search-icon"></i>
						</span>
						<button id="nav-search-button" class="btn btn-small btn-primary">搜索</button>
					</form>
				</div>
			</div>
			<div class="page-content" style="padding: 0">
				<div class="row-fluid">
					<div class="span12">
						<object id="WebMineSystem" classid="CLSID:95EE964E-4A33-423A-9DED-3D81BBE20D66" height="500" width="500"></object>
						<!--img id="wgl" src="${resources}/images/3d/capture/主界面.png" style="width: 100%"-->
					</div>
				</div>
			</div>
		</div>
		<div id="layer-control" class="ace-settings-container">
		    <a id="control-bar" href="javascript:void(0);" style="display:block;width:15px;height:40px;background-color:white;float:left;" >a</a>
			<div id="layer-control-div" class="ace-settings-box open" style="width: 260px; border: 0; padding: 0">
				<div class="tabbable" style="margin-top: 0; background-color: #c5d0dc">
					<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
						<li class="active">
							<a id="layer-tab" data-toggle="tab" href="#layer">图层管理</a>
						</li>
						<li>
							<a id="info-tab" data-toggle="tab" href="#object">对象信息</a>
						</li>
						<li class="dropdown">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								更多 <i class="icon-caret-down bigger-110 width-auto"></i>
							</a>
							<ul class="dropdown-menu dropdown-info" style="min-width: 80px">
								<li>
									<a data-toggle="tab" href="#viewpoint">视点导航</a>
								</li>
							</ul>
						</li>
					</ul>
					<div id="rightPanel" class="tab-content" style="box-shadow: 0 2px 2px 1px rgba(0, 0, 0, 0.2); background-color: #fff">
					 
						<div id="layer" data-level="first" class="tab-pane active">
							<div id="layer-tree" class="ztree"></div>
						</div>
						<div id="object" data-level="first" class="tab-pane">
							<div class="well">
								<h4 class="green smaller lighter">对象信息</h4>
								返回在三维场景中选中的设备信息。
							</div>
							<div class="profile-user-info profile-user-info-striped" style="margin: 0">
								<div class="profile-info-row">
									<div class="profile-info-name">ID</div>
									<div class="profile-info-value">
										<span class="editable editable-click">1028097</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">位置</div>
									<div class="profile-info-value">
										<i class="icon-map-marker light-orange bigger-110"></i>
										<span class="editable editable-click">变电站</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">名称</div>
									<div class="profile-info-value">
										<span class="editable editable-click">2号主变B相</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">类型ID</div>
									<div class="profile-info-value">
										<span class="editable editable-click">1</span>
									</div>
								</div>
							</div>
							<img id="info-image-1" src="${resources}/images/3d/capture/信息统计-右侧属性栏.jpg" style="width: 100%; display: none;">
						</div>
						<div id="viewpoint" data-level="first" class="tab-pane">
							<div class="row-fluid">
								<div class="span3">
									<img src="${resources}/images/3d/viewpoint/1.png" class="img-rounded" style="width: 100%">
									<span style="font-size: 11px">工业广场</span>
								</div>
								<div class="span3">
									<img src="${resources}/images/3d/viewpoint/2.png" class="img-rounded" style="width: 100%">
									<span style="font-size: 11px">工作面</span>
								</div>
								<div class="span3">
									<img src="${resources}/images/3d/viewpoint/3.png" class="img-rounded" style="width: 100%">
									<span style="font-size: 11px">水泵房</span>
								</div>
								<div class="span3">
									<img src="${resources}/images/3d/viewpoint/4.png" class="img-rounded" style="width: 100%">
									<span style="font-size: 11px">变电所</span>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span3">
									<img src="${resources}/images/3d/viewpoint/5.png" class="img-rounded" style="width: 100%">
									<span style="font-size: 11px">通风机房</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		seajs.use('${resources}/scripts/app/3d/index');
	</script>
</body>
</html>
