<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
<head>
<title>三维综合管理 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="head.jsp"%>
<%@ include file="../common/template.jsp"%>

<script id="staffTraceList-template" type="text/x-handlebars-template">
<table class="table table-striped table-bordered table-hover" width="100%">
  <thead><tr>
    <th class="hidden-480" width="10%">id</th>
    <th class="hidden-480" width="40%">基站</th>
    <th class="hidden-480" width="32%">进入时间</th>
    <th class="hidden-480" width="18%">停留时间</th>
    </tr></thead>
    <tbody>
    {{#each result}}
      <tr>
          <td>{{stationId}}</td>
          <td>{{stationName}}</td>
          <td>{{enterCurTime}}</td>
          <td>{{indataTime}}</td>
      </tr>
    {{/each}}
     </tbody></table>
</script>
<script id="allCameraViews-template" type="text/x-handlebars-template">
    {{#each result}}
        <div class="row-fluid">
               {{#each children}}
        		<div class="span3"><a href="javascript:void(0);" onclick="WebMineSystem.GoToCamLocation('{{name}}');">
        			<img src="${resources}/images/3d/viewpoint/1.png" class="img-rounded" style="width: 100%">
	        		<span style="font-size: 11px">{{name}}</span></a>
	        	</div>
               {{/each}}
        </div>
    {{/each}}
</script>
<script id="multipleObjectsSelected-template" type="text/x-handlebars-template">
      <table class="table table-striped table-bordered table-hover">
              <thead><tr><th class="hidden-480">名称</th></tr></thead>
              <tbody>
              {{#each result}}
              <tr><td><a href="javascript:void(0)" onclick="WebMineSystem.PositonByName('{{value}}');">{{value}}</a></td></tr>
              {{/each}}
              </tbody></table>
</script>
<script id="objectinfo-template" type="text/x-handlebars-template">
<div id="objectinfo-template" class="accordion-style1">
{{#each result}}
   <div class="panel panel-default ">
        <div class="panel-heading ">
            <h4 class="panel-title">
                <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapse{{@index}}" data-type="object-info-group">{{groupName}}</a>
            </h4>
        </div>
        <div class="panel-collapse in" id="collapse{{@index}}" style="height: auto;">
           
              <table class="table table-striped table-bordered table-hover">
              <thead><tr><th >名称</th><th class="hidden-480">参数</th></tr></thead>
              <tbody>
              {{#each children}}
              <tr><td>{{childName}}</td><td>{{childValue}}</td></tr>
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
        <div class="panel-collapse in collapse" id="collapse{{@index}}" style="height: auto;">
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
			<div class="page-toolbar" id="page-toolbar">
				<div class="toolbar">
				
				    <button class="btn btn-small btn-info" data-image="信息统计.png" data-type="basicInfo">
						<i class="icon-info-sign"></i>
						<span>基本信息</span>
					</button>
					<button class="btn btn-small btn-info" data-image="信息统计.png" data-type="renJiHuan">
						<i class="icon-info-sign"></i>
						<span>生产动态</span>
					</button>
					<button class="btn btn-small btn-info" data-image="轨迹回放.png" data-type="traceReplay">
						<i class="icon-retweet"></i>
						<span>轨迹回放</span>
					</button>
					<button class="btn btn-small btn-info" data-image="路线飞行.png">
						<i class="icon-rocket"></i>
						<span>路线飞行</span>
					</button>
					<button class="btn btn-small btn-info" data-image="主界面.png" data-type="wind">
						<i class="icon-home"></i>
						<span>主通风</span>
					</button>
					<button class="btn btn-small btn-info" data-image="人员信息.png" data-type="water">
						<i class="icon-user"></i>
						<span>主排水</span>
					</button>
					<button class="btn btn-small btn-info" data-image="安全监控.png" data-type="conveyor">
						<i class="icon-desktop"></i>
						<span>皮带运输</span>
					</button>
					<button class="btn btn-small btn-info" data-image="" data-type="substation">
						<i class="icon-random"></i>
						<span>35KV变电站</span>
					</button>
					<button class="btn btn-small btn-info" data-image="应急资源.jpg" data-type="workshop">
						<i class="icon-puzzle-piece"></i>
						<span>动筛车间</span>
					</button>
					<button class="btn btn-small btn-info" data-image="避灾路线-火灾1.png" onclick="WebMineSystem.NoteRoute()">
						<i class="icon-road"></i>
						<span>避灾路线</span>
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
					<div class="span12" id="active_panel">
						<object id="WebMineSystem" classid="CLSID:481854E7-4443-4E9E-873B-05CDB7C070B8" height="600" width="400" codebase="${resources}/cab/publish.cab"></object>
					</div>
				</div>
			</div>
		</div>
		<div id="layer-control" class="ace-settings-container">
		    <a id="control-bar" href="javascript:void(0);" class="icon-double-angle-right" style="background:url(${resources}/images/control-bar.png);background-position:245px 0px;display:block;text-align:center;width:10px;height:40px;background-color:white;float:left;" ></a>
			<div id="layer-control-div" class="ace-settings-box open" style="width: 300px; border: 0; padding: 0">
				<div class="tabbable tabs-right" style="margin-top: 0; background-color: #c5d0dc">
					<ul class="nav nav-tabs tab-color-blue background-blue" style="min-width: 5px">
					    <li class="active" style="min-width: 4px">
							<a id="result-tab" data-toggle="tab" href="#result" style="min-width: 4px;width:4px;">查<br>询<br>结<br>果</a>
						</li>
						<li style="min-width: 4px">
							<a id="layer-tab" data-toggle="tab" href="#layer" style="min-width: 4px;width:4px;">图<br>层<br>管<br>理</a>
						</li>
						<li >
							<a data-toggle="tab" href="#viewpoint" style="min-width: 4px;width:4px;">视点导航</a>
						</li>
					</ul>
					<div id="rightPanel" class="tab-content" style="box-shadow: 0 2px 2px 1px rgba(0, 0, 0, 0.2); background-color: #fff">
					    <div id="result" data-level="first" class="tab-pane active">
					    	<div class="well">
								<h4 class="green smaller lighter">查询结果</h4>
								显示框选或者查询结果的列表信息。
							</div>
						</div>
						<div id="layer" data-level="first" class="tab-pane">
							<div id="layer-tree" class="ztree"></div>
						</div>
						<div id="viewpoint" data-level="first" class="tab-pane">
							<div class="row-fluid">
								<div class="span3"><a href="javascript:void(0);" onclick="WebMineSystem.GoToCamLocation('我的相机');">
									<img src="${resources}/images/3d/viewpoint/1.png" class="img-rounded" style="width: 100%">
									<span style="font-size: 11px">我的相机</span></a>
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
		seajs.use('${resources}/scripts/app/3d/realtime-module');
	</script>
	<SCRIPT FOR=WebMineSystem EVENT=ObjectSelected(id,name)>
	    var infos = WebMineSystem.GetObjProperty(id,"");
	    var toolstate = WebMineSystem.GetManipulatorMode();
	    if (toolstate != "人机环") {
	    	callbackClt.showObjectInfo(infos);
	    }
    </SCRIPT>
	<SCRIPT FOR=WebMineSystem EVENT=CommandFinished(evt)>
	    var jsonData = $.parseJSON(evt);
	    callbackClt.test(jsonData);
    </SCRIPT>
    <SCRIPT FOR=WebMineSystem EVENT=MultipleObjectsSelected(_SelectedObjs,_SelectedObjsCount)>
    	var toolstate = WebMineSystem.GetManipulatorMode();
    	if (toolstate != "人机环") {
			callbackClt.multipleObjectsSelected(_SelectedObjs);
    	}
    </SCRIPT>
    <SCRIPT FOR=WebMineSystem EVENT=Platform3DStarted()>
        //调用加载模型的方法 如果 此方法无效 js文件最后 有个定时监测机制可以再走一次检查然后执行
        setTimeout(" callbackClt.Platform3DStarted();",2000);
    </SCRIPT>
    <SCRIPT FOR=WebMineSystem EVENT=GetAreaNames(jsonAreas)>
        //{"AREA":[{"NAME":"3045工作面"},{"NAME":"3046工作面"}]}
        var areas=[];
        $.each($.parseJSON(jsonAreas).AREA,function(key,value){
        	areas.push(value.NAME);
        });
        rjhProcessInDialog(encodeURI('/3d/rjh?areas='+areas.join(',')));
    </SCRIPT>
    <SCRIPT FOR=WebMineSystem EVENT=RJHCommand(jsonData)>
       rjhProcess(jsonData);
    </SCRIPT>
    
</body>
</html>
