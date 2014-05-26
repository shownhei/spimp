define(function(require, exports, module) {
	var $ = require('kjquery');
	window.$=$;
	function resize() {
		var toolBar=42;//1
		var controlBarWidth=$('#control-bar').width();//2
		var controlDiv=$('#layer-control-div');
		var controlWidth=controlDiv.is(":hidden")?0:controlDiv.width();//3
		var tabHeight = $(window).height() - 170;
		$('div[class*="tab-pane"]').filter('div[data-level="first"]').css({
			'max-height' : tabHeight + 'px',
			'height' : tabHeight + 'px'
		});
		var mainHeight=$(window).height() - 92;
		$('#WebMineSystem').height(mainHeight);
		var newWidth=$(window).width() -toolBar-controlBarWidth-controlWidth;
		$('#WebMineSystem').width(newWidth);
		$('#control-bar').height(mainHeight);
		var paddingTop=($(window).height() - 90)/2;
		$('#control-bar').height(mainHeight);
		$('#rightPanel').height(mainHeight-40);
		$('#traceReplayInfo').height(mainHeight-280);
	}
	$('.ace-settings-container').css({'top':'41px'});
	window.onresize = resize;

	/**
	 * 处理图标路径
	 */
	function handleIcon(childrens) {
		if (childrens) {
			$.each(childrens, function(index, item) {
				if (item.groupEntities) {
					handleIcon(item.groupEntities);
				}
				switch (item.queryLabel) {
					case '1':
						item.icon = resources + '/images/icons/building.png';
						break;
					case '2':
						item.icon = resources + '/images/icons/monitor.png';
						break;
					case '3':
						item.icon = resources + '/images/icons/plugin_disabled.png';
						break;
					case '4':
						item.icon = resources + '/images/icons/house.png';
						break;
					default:
						item.icon = resources + '/images/icons/page_white.png';
						break;
				}
			});
		}
	}

	var initLayerTree=function(zNodes){
		var array=$.parseJSON(zNodes);
		var children=[];
		var id=1;
		for(var key in array){
			if(array.hasOwnProperty(key)) {
				var raw=array[key];
				raw.id=id++;
				raw.iconOpen = resources + "/images/icons/chart_organisation.png";
				raw.iconClose = resources + "/images/icons/chart_organisation.png";
				raw.checked=null;
				raw.name=raw.Name;
				raw.pId=-1;
				children.push(raw);
			}
		 }
		var result={id:"-1",name:"王庄煤业","pId":0,open:true,children:children};
		
		var setting = {
				check : {
					enable : true,
					autoCheckTrigger: true,
					chkStyle : "checkbox"
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback :{
					onClick :  function(event, treeId, treeNode, clickFlag) {
					   var groupTree = $.fn.zTree.getZTreeObj(treeId);
					},
					onCheck:function(event, treeId, treeNode){
						if(!(treeNode.children)){
							if(treeNode.checked){
								WebMineSystem.SetLayerOn(treeNode.name);
							}else{
								WebMineSystem.SetLayerOFF(treeNode.name);
							}
						}
					}
				}
		};
		var tree=$.fn.zTree.init($("#layer-tree"), setting, result);
		var rootNode=tree.getNodeByParam("id",-1);
		tree.checkNode(rootNode, true, true);
	};
	window.initLayerTree=initLayerTree;
    
	$('button[data-image],a[data-image]').click(function() {
		var AUTO_URL_BASE = 'http://192.168.20.104/';
		var AUTO_SUB_URL = {
			'wind': '2.htm',
			'water': '5.htm',
			'conveyor': '3.htm',
			'substation': '6.htm',
			'workshop': '7.htm'
		};
		var AUTO_NAME = 'automation';
		if ($(this).data('type') !== undefined) {
			switch ($(this).data('type')) {
				case 'fullscreen':
					$('#layer-tab').trigger('click');
					$('#map-image').attr('src', resources + '/images/3d/capture/' + $(this).data('image'));

					$('#layer-control-div').removeClass('open');
					$('#layer-control-icon').removeClass('icon-chevron-right').addClass('icon-chevron-left');
					$('#sidebar').removeClass('fixed');
					$('body').removeClass('navbar-fixed');
					$('.navbar-fixed-top').hide();
					$('#map-image').height($(window).height() - 45);
					break;
				case 'main':
					$('#layer-tab').trigger('click');
					$('#map-image').attr('src', resources + '/images/3d/capture/' + $(this).data('image'));

					$('#layer-control-div').addClass('open');
					$('#layer-control-icon').addClass('icon-chevron-right').removeClass('icon-chevron-left');
					$('#sidebar').addClass('fixed');
					$('body').addClass('navbar-fixed');
					$('.navbar-fixed-top').show();
					resize();
					break;
				case 'zoom-in':
					$('#layer-tab').trigger('click');
					var pic1 = $(this).data('image').split(',')[0];
					var pic2 = $(this).data('image').split(',')[1];

					$('#map-image').attr('src', resources + '/images/3d/capture/' + pic1);
					setTimeout(function() {
						$('#map-image').attr('src', resources + '/images/3d/capture/' + pic2);
					}, 800);
					break;
				case 'switch':
					pic1 = $(this).data('image').split(',')[0];
					pic2 = $(this).data('image').split(',')[1];

					setInterval(function() {
						$('#map-image').attr('src', resources + '/images/3d/capture/' + pic1);
					}, 500);
					setInterval(function() {
						$('#map-image').attr('src', resources + '/images/3d/capture/' + pic2);
					}, 600);
					break;
				case 'renJiHuan':
					//切换到人机环界面
					//加载所有的区域信息
					WebMineSystem.DoCommand('工具 人机环');
					break;
				case 'traceReplay':
					//轨迹回放
					var traceReplayUrl='http://' + location.hostname + ":" + location.port +'/3d/trace-playback';
					WebMineSystem.WebInterface('{"URL":"'+traceReplayUrl+'","ISWEB":1,"WIDTH":820,"HEIGHT":600}');
					WebMineSystem.DoCommand('设置 网页 开');
					break;
				case 'wind':
					//主通风
					window.open(AUTO_URL_BASE + AUTO_SUB_URL.wind, AUTO_NAME);
					break;
				case 'water':
					//主排水
					window.open(AUTO_URL_BASE + AUTO_SUB_URL.water, AUTO_NAME);
					break;
				case 'conveyor':
					//皮带运输
					window.open(AUTO_URL_BASE + AUTO_SUB_URL.conveyor, AUTO_NAME);
					break;
				case 'substation':
					//35KV变电站
					window.open(AUTO_URL_BASE + AUTO_SUB_URL.substation, AUTO_NAME);
					break;
				case 'workshop':
					//动筛车间
					window.open(AUTO_URL_BASE + AUTO_SUB_URL.workshop, AUTO_NAME);
					break;
				default:
					break;
			}
		} else {
			$('#layer-tab').trigger('click');
			$('#map-image').attr('src', resources + '/images/3d/capture/' + $(this).data('image'));
		}
	});
	$('#control-bar').height($(window).height() - 95);
	$('#control-bar').click(function() {
		$('#layer-control-div').toggleClass('open');
		resize();
	});
	$('#nav-search-button').click(function(){
		var resultData=null;
		if($('#nav-search-input').val()){
			resultData=WebMineSystem.FuzzyQuery($('#nav-search-input').val());
	    }else{
			resultData=WebMineSystem.FuzzyQuery('巷道');
	    }
		var temp=$.parseJSON( resultData );
		window.callbackClt.test(temp);
	});
	resize();
	var callbackClt={};
	//平台加载完毕回调
	callbackClt.Platform3DStarted=function(){
		$.get(contextPath + '/update?prefix=sywz&suffix=MDocSegment', function(data) {
			var paths = data.data.split('/');
			WebMineSystem.SetSysParam("资源地址", 'http://' + location.hostname + ':' + location.port + '/' + paths[1] + '/' + paths[2] + '/');
			window.projectLoaded=true;
			WebMineSystem.UpdateProjectFile(paths[3]);
			WebMineSystem.LoadProjectFile(paths[3]);
			var result=WebMineSystem.GetAllLayers();
			initLayerTree(result);
			callbackClt.onGetAllCameraViews(WebMineSystem.GetAllCameraViews());
		});
	};
	//所有的试点相机
	callbackClt.onGetAllCameraViews=function(_jsonData){
		var array=$.parseJSON(_jsonData);
		var result=[];
		var group=[];
		var index=0;
		for(var key in array){
			if(array.hasOwnProperty(key)) {
				if(index%4===0){
					group=[];
					result.push({children:group});
				}
				index+=1;
				var raw=array[key];
				raw.id=raw.ID;
				raw.name=raw['名称'];
				group.push(raw);
			}
		}
		var template = Handlebars.compile($('#allCameraViews-template').html());
		var html = template({"result":result});
		$('#viewpoint').html(html);
	};
	callbackClt.control={};//操作
	callbackClt.control.doCommand=function(cmd){
		WebMineSystem.DoCommand(cmd);
	};
	//多个物体选中
	callbackClt.multipleObjectsSelected=function(_SelectedObjs,_SelectedObjsCount){
		var jsonData=$.parseJSON(_SelectedObjs);
		var result=[];
		for(var key in jsonData){
			if(jsonData[key]){
				result.push({'key':key,'value':jsonData[key]});
			}
		 }
		var template = Handlebars.compile($('#multipleObjectsSelected-template').html());
		var html = template({"result":result});
		$('#result').html(html);
		$('#result-tab').trigger('click');
	};//查询
	callbackClt.showObjectInfo=function(_jsonData){
		var objInfoUrl='http://' + location.hostname + ":" + location.port +'/3d/single-click';
		WebMineSystem.WebInterface('{"URL":"'+objInfoUrl+'","ISWEB":1,"POST":"'+encodeURI(_jsonData)+'","WIDTH":750,"HEIGHT":500}');
		WebMineSystem.DoCommand('设置 网页 开');	
	};
	callbackClt.test=function(_jsonData){
		 var i=0;
		 var data=[];
		 for(var key in _jsonData){
			 if(_jsonData.hasOwnProperty(key)) {
				 var raw={};
				 raw.typeCode=i++;
				 raw.typeName=key;
				 raw.children=_jsonData[key];
				 raw.count=raw.children.length;
				 data.push(raw);
			 }
		 }
		var template = Handlebars.compile($('#queryresult-template').html());
		var html = template({"result":data});
		$('#result').html(html);
		$('#result-tab').trigger('click');
	};
	window.callbackClt=callbackClt;
	setTimeout(function(){
		if(!window.projectLoaded){
			callbackClt.Platform3DStarted();
		}
	},5000);
	
	
	$(document).click(function(event) {
		var el = $(event.target);
		var elType = el.attr('data-type');
		if(elType==='doCommand'){
			var aEl=null;
			if(el.is('i')){
				aEl=el.parent();
			}else{
				aEl=el;
			}
			callbackClt.control.doCommand(aEl.attr('data-param'));
			return;
		}
		if(elType==='fullscreen'){
			toggleFullScreen();
		}
	});
	/**切换全屏效果*/
	var fullscreen={};
	fullscreen.goFullScreen=function(){
		$('.navbar-fixed').css({'padding-top':'0px'});
		$('#page-toolbar').hide();
		$('#navbar-fixed-top').hide();
		$('#menu-toggler').hide();
		$('#layer-control').hide();
		$('#sidebar').hide();
		setTimeout(function(){
			var panel=$('#active_panel');
			panel.css({'position':'absolute','left':'0px','top':'0px;'});
			$('#WebMineSystem').width($(window).width());
			$('#WebMineSystem').height($(window).height());
		},2000);
	};
	fullscreen.exitFullScreen=function(){
		$('.navbar-fixed').css({'padding-top':'45px'});
		$('#page-toolbar').show();
		$('#navbar-fixed-top').show();
		$('#menu-toggler').show();
		$('#layer-control').show();
		$('#sidebar').show();
		setTimeout(function(){
			var panel=$('#active_panel');
			panel.css({'position':'relative'});
			resize();
		},2000);
	};
	var toggleFullScreen=function(){
		if($("#layer-control").is(":hidden")){
			fullscreen.exitFullScreen();
		}else{
			fullscreen.goFullScreen();
		}
	};
	$(document).keydown(function(event){
		switch(event.keyCode){
		      // f11 全屏toggle
              case 122:toggleFullScreen();
		         break;
		      // 退出全屏
              case 27:fullscreen.exitFullScreen();
		         break;
		}
	});
});
