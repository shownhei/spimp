<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>三维综合管理 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="head.jsp"%>
<%@ include file="../common/template.jsp"%>
<script id="objectinfo-template-fordialog" type="text/x-handlebars-template">
<ul class="nav nav-tabs">
{{#each result}}<li ><a data-toggle="tab" id="firsttab" href="#tab{{@index}}"> <i class="green icon-th-list"></i> {{groupName}}</a></li>{{/each}}</ul>
<div id="tab-content" class="tab-content">
{{#each result}}
<div id="tab{{@index}}" class="tab-pane ">
<table class="table table-striped table-bordered table-hover">
<thead><tr class="page-report-table-tr"><td>名称</td><td>参数</td></tr></thead>
<tbody>{{#each children}}<tr class="page-report-table-tr"><td>{{childName}}</td><td>{{childValue}}</td>
</tr>{{/each}}
</tbody></table></div>
{{/each}}
</div>
</script>
</head>
<body class="navbar-fixed">
	<div class="container-fluid">
		<div class="navbar navbar-fixed-top" id="navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container-fluid">
					<a href="javascript:void(0);" class="brand" target="safe"> <small>
							<i class="icon-leaf"></i> 王庄煤业数字矿山综合管理平台
					</small>
					</a>
				</div>
			</div>
		</div>
		<div class="main-content">
			<div class="page-toolbar">
				<div class="toolbar"></div>
				<div class="nav-search">
					<form id="query-form" class="form-inline" onsubmit="return false;">
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid">
					<div class="tabbable tabs-left" id="content">
					</div>
				</div>
				<div class="row-fluid" id="stafflist-table"></div>
			</div>
		</div>
	</div>
	<SCRIPT type="text/javascript">
		define(function(require, exports, module) {
			var $ = require('kjquery');
			var h=require('gallery/handlebars/1.0.0/handlebars');
			var data=[];
			var groupIndex=0;
			var _jsonData=${content};
			for(var key in _jsonData){
				if(_jsonData.hasOwnProperty(key)) {
					var groupRaw={};
					groupRaw.groupCode=groupIndex++;
					groupRaw.groupName=key;
					var group=_jsonData[key];
					var childrenArray=[];
					var j=0;
					for(var keyj in group){
						if(group.hasOwnProperty(keyj)) {
							var child={};
							child.childCode=j++;
							child.childName=keyj;
							child.childValue=group[keyj];
							childrenArray.push(child);
						}
					}
					groupRaw.children=childrenArray;
					data.push(groupRaw);
				}
			 }
			
			var template = h.compile($('#objectinfo-template-fordialog').html());
			var html = template({"result":data});
			$('#content').html(html);
			$('#firsttab').trigger('click');
		})
	</SCRIPT>
</body>
</html>