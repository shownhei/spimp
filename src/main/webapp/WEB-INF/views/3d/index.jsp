<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
<head>
<title>三维综合管理 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="head.jsp"%>
<%@ include file="../common/template.jsp"%>
<script id="objectinfo-template" type="text/x-handlebars-template">
<div id="objectinfo-template" class="accordion-style1">
{{#each result}}
   <div class="panel panel-default ">
        <div class="panel-heading ">
            <h4 class="panel-title">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapse{{@index}}">{{groupName}}</a>
            </h4>
        </div>
        <div class="panel-collapse in" id="collapse{{@index}}" style="height: auto;">
           
              <table class="table table-striped table-bordered table-hover">
              <thead><tr><th style="width:40px;">名称</th><th class="hidden-480">参数</th></tr></thead>
              <tbody>
              {{#each children}}
              <tr><td><a href="#">{{childName}}</a></td><td>{{childValue}}</td></tr>
              {{/each}}
              </tbody></table>
            
        </div>
    </div>
{{/each}}
</div>

</script>
<script id="queryresult-template" type="text/x-handlebars-template">
<div id="queryresult-template" class="accordion-style1">
{{#each result}}
   <div class="panel panel-default ">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapse{{@index}}">{{typeName}}({{count}})</a>
            </h4>
        </div>
        <div class="panel-collapse in" id="collapse{{@index}}" style="height: auto;">
            {{#each children}}
            <div class="panel-body">
              <a href="javascript:void(0);" onclick="WebMineSystem.PositonByName('{{this}}');">{{this}}</a>
             </div>
            {{/each}}
        </div>
    </div>
{{/each}}
</div>
</script>
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
						<object id="WebMineSystem" classid="CLSID:481854E7-4443-4E9E-873B-05CDB7C070B8" height="200" width="200"></object>
					</div>
				</div>
			</div>
		</div>
		<div id="layer-control" class="ace-settings-container">
		    <a id="control-bar" href="javascript:void(0);" class="icon-double-angle-right" style="background:url(${resources}/images/control-bar.png);background-position:245px 0px;display:block;text-align:center;width:10px;height:40px;background-color:white;float:left;" ></a>
			<div id="layer-control-div" class="ace-settings-box open" style="width: 260px; border: 0; padding: 0">
				<div class="tabbable tabs-right" style="margin-top: 0; background-color: #c5d0dc">
					<ul class="nav nav-tabs tab-color-blue background-blue" style="min-width: 5px">
					    <li class="active" style="min-width: 4px">
							<a id="result-tab" data-toggle="tab" href="#result" style="min-width: 4px;width:4px;">查<br>询<br>结<br>果</a>
						</li>
						<li style="min-width: 4px">
							<a id="info-tab" data-toggle="tab" href="#object" style="min-width: 4px;width:4px;">对<br>象<br>信<br>息</a>
						</li>
						<li style="min-width: 4px">
							<a id="layer-tab" data-toggle="tab" href="#layer" style="min-width: 4px;width:4px;">图<br>层<br>管<br>理</a>
						</li>
						<li  >
							<a data-toggle="tab" href="#viewpoint" style="min-width: 4px;width:4px;">视点导航</a>
						</li>
					</ul>
					<div id="rightPanel" class="tab-content" style="box-shadow: 0 2px 2px 1px rgba(0, 0, 0, 0.2); background-color: #fff">
					    <div id="result" data-level="first" class="tab-pane active">
						</div>
						<div id="layer" data-level="first" class="tab-pane">
							<div id="layer-tree" class="ztree"></div>
						</div>
						<div id="object" data-level="first" class="tab-pane col-sm-6 accordion-style1">
							<div class="well">
								<h4 class="green smaller lighter">对象信息</h4>
								返回在三维场景中选中的设备信息。
							</div>
							<div id="object-infopanel" class="profile-user-info profile-user-info-striped accordion-style1" style="margin: 0">
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
								<div class="span3"><a href="javascript:void(0);" onclick="WebMineSystem.GoToCamLocation('我的相机');">
									<img src="${resources}/images/3d/viewpoint/1.png" class="img-rounded" style="width: 100%">
									<span style="font-size: 11px">我的相机</span></a>
								</div>
								<div class="span3"><a href="javascript:void(0);" onclick="WebMineSystem.GoToCamLocation('工作面');">
									<img src="${resources}/images/3d/viewpoint/2.png" class="img-rounded" style="width: 100%">
									<span style="font-size: 11px">工作面</span></a>
								</div>
								<div class="span3"><a href="javascript:void(0);" onclick="WebMineSystem.GoToCamLocation('水泵房');">
									<img src="${resources}/images/3d/viewpoint/3.png" class="img-rounded" style="width: 100%">
									<span style="font-size: 11px">水泵房</span></a>
								</div>
								<div class="span3"><a href="javascript:void(0);" onclick="WebMineSystem.GoToCamLocation('变电所');">
									<img src="${resources}/images/3d/viewpoint/4.png" class="img-rounded" style="width: 100%">
									<span style="font-size: 11px">变电所</span></a>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span3"><a href="javascript:void(0);" onclick="WebMineSystem.GoToCamLocation('通风机房');">
									<img src="${resources}/images/3d/viewpoint/5.png" class="img-rounded" style="width: 100%">
									<span style="font-size: 11px">通风机房</span></a>
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
	<SCRIPT FOR=WebMineSystem EVENT=ObjectSelected(id,name)>
	    var infos=WebMineSystem.GetObjProperty(id,"");
	    var temp=$.parseJSON(infos);
	    callbackClt.showObjectInfo(temp);
    </SCRIPT>
	<SCRIPT FOR=WebMineSystem EVENT=CommandFinished(evt)>
	    var jsonData=$.parseJSON(evt);
	    callbackClt.test(jsonData);
    </SCRIPT>
    <SCRIPT FOR=WebMineSystem EVENT=MultipleObjectsSelected(_SelectedObjs,_SelectedObjsCount)>
        var objInfo=_SelectedObjs;
        for(var key in objInfo){
			 console.log(key+":"+objInfo[key]);
		 }
        console.log(_SelectedObjs);
        console.log(_SelectedObjsCount);
    </SCRIPT>
    <SCRIPT FOR=WebMineSystem EVENT=Platform3DStarted()>
	    define(function(require, exports, module) {
			var $ = require('kjquery');
			/*$.get(contextPath + '/update?prefix=sywz&suffix=MDocSegment', function(data) {
				var paths = data.data.split('/');
				WebMineSystem.SetSysParam("资源地址", 'http://' + location.hostname + ':' + location.port + '/' + paths[1] + '/' + paths[2] + '/');
				WebMineSystem.LoadProjectFile(paths[3]);
			});*/
	        
		});
	    WebMineSystem.LoadProjectFile('sywz-0.MDocSegment');
    </SCRIPT>
</body>
</html>
