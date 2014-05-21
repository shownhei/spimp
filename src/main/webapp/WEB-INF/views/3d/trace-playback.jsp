<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>三维综合管理 - 山西王庄煤业数字矿山综合管理平台</title>
<%@ include file="head.jsp"%>
</head>
<body class="navbar-fixed">
	<div class="main-container container-fluid">
		<div class="navbar navbar-fixed-top" id="navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container-fluid">
					<a href="/" class="brand" target="safe"> <small> <i
							class="icon-leaf"></i> 王庄煤业数字矿山综合管理平台
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
						<div style="display: block; margin-bottom: 5px;">
							<div class="input-append">
								<select name="department" id="trace_department"
									style="height: 25px; width: 150px; font-size: 12px;">
								</select>
							</div>
							<div class="input-append">
								<select name="staff" id="trace_staff"
									style="height: 25px; width: 150px; font-size: 12px;">
								</select>
							</div>
							<div class="input-append">
								<input name="startDateTime" type="datetime"
									id="trace_startDateTime" style="width: 119px;"
									placeholder="开始时间" class="input-small"> <span
									class="add-on nav-add-on"> <i class="icon-calendar"></i>
								</span>
							</div>
							<div class="input-append">
								<input name="endDateTime" type="datetime" id="trace_endDateTime"
									style="width: 119px;" placeholder="截止时间" class="input-small">
								<span class="add-on nav-add-on"> <i class="icon-calendar"></i>
								</span>
							</div>
							<div class="input-append ">
								<table width="150">
									<tr>
										<td>
											<button id="trace_query_btn"
												class="btn btn-small btn-success disabled">
												<i class="icon-search"></i>查询
											</button>
										</td>
										<td align="right">
											<button id="trace_playback_btn"
												class="btn btn-small btn-success disabled">
												<i class="icon-list"></i>轨迹回放
											</button>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="page-content">
				<div class="row-fluid" id="stafflist-table"></div>
			</div>
		</div>
	</div>
	<SCRIPT type="text/javascript">
		define(function(require, exports, module) {
			var $ = require('kjquery');
			var Grid = require('grid');
			// 配置表格列
			var fields = [ {
				header : 'id',
				width : 90,
				name : 'id',
				render:function(v){
					return v;
				}
			}, {
				header : '员工姓名',
				width:80,
				name : 'name'
			},{
				header : '工种',
				name : 'jobType'
			},{
				header : '班组',
				name : 'troopName'
			},{
				header : '基站',
				name : 'stationName'
			}, {
				header : '进入时间',
				name : 'enterCurTime'
			}, {
				header : '停留时间',
				name : 'indataTime'
			}];

			// 计算表格高度和行数
			var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
			var pageSize = Math.floor(gridHeight / 21);

			// 配置表格
			var defaultUrl = contextPath + '/location/location-tracks-query?orderBy=id&order=desc&pageSize=' + pageSize;
			var grid = new Grid({
				parentNode : '#stafflist-table',
				url : defaultUrl,
				model : {
					fields : fields,
					needOrder : true,
					orderWidth : 50,
					height : gridHeight
				},
				onClick : function(target, data) {
				},
				onLoaded : function() {}
			}).render();
			
			//部门选择发生变化  加载对应的员工信息
			$('#trace_department').change(function(){
				var _department=$('#trace_department').val();
				$("#trace_staff option").each(function(){ $(this).remove(); });
				if(!_department){
					return;
				}
				$.ajax({
					type : 'get',
					dataType : 'json',
					data:'department='+encodeURI(_department),
					url : '/location/location-staffs/departmentstaff',
					success : function(datas) {
						var _select=$("#trace_staff");
						$("<option value=''>请选择人员</option>").appendTo(_select);
						$.each(datas.data,function(key,value){
							console.log(value);
							$("<option value='"+value.id.staffId+"' data-param='"+value.id.mineId+"'>"+value.name+"</option>").appendTo(_select);
						});
					}
				});
			});//
			$('#trace_staff').change(function(){
				var staffVal=$(this).val();
				if(!staffVal){
					$('#trace_query_btn').addClass('disabled');
					$('#trace_playback_btn').addClass('disabled');
				}else{
					$('#trace_query_btn').removeClass('disabled');
					$('#trace_playback_btn').removeClass('disabled');
				}
			});
			//查询人员信息列表
			$('#trace_query_btn').click(function() {
				if($('#trace_query_btn').hasClass('disabled')){
					return;
				}
				var _param='department='+$("#trace_department").val();
				_param+='&staffId='+$('#trace_staff').val();
				_param+='&startTime='+$('#trace_startDateTime').val();
				_param+='&endTime='+$('#trace_endDateTime').val();
				_param=encodeURI(_param);
				
				grid.set('url', contextPath + '/location/location-tracks-query?'+_param);
				grid.refresh();
			});
			//轨迹回放
			$('#trace_playback_btn').click(function(){
				if($('#trace_query_btn').hasClass('disabled')){
					return;
				}
				var _param='department='+$("#trace_department").val();
				_param+='&staffId='+$('#trace_staff').val();
				_param+='&startTime='+$('#trace_startDateTime').val();
				_param+='&endTime='+$('#trace_endDateTime').val();
				$.get(contextPath + '/location/location-tracks-query?'+_param, function(data) {
					var _jsonResult={};
					_jsonResult.PEOPLE=$("#trace_staff").find("option:selected").text();
					var READCARD=[];
					$.each(data.data.result,function(key,value){
						READCARD.push({'DBID':'MineID:'+value.mineId+';StationID:'+value.stationId+';'});
					});
					_jsonResult.READCARD=READCARD;
					external._Rydw_SetMineWorkerTrajectory(JSON.stringify(_jsonResult));
				});
			});
			
			var loadDepartMent=function (){
				$.ajax({
					type : 'get',
					dataType : 'json',
					url : '/location/location-staffs/alldepartment',
					success : function(datas) {
						$("#trace_department option").each(function(){ $(this).remove(); });
						var _select=$("#trace_department");
						$("<option value=''>请选择部门</option>").appendTo(_select);
						$.each(datas.data,function(key,value){
							$("<option value='"+value+"'>"+value+"</option>").appendTo(_select);
						});
					}
				});
			};
			loadDepartMent();
			$('#trace_startDateTime').datetimepicker();
			$('#trace_endDateTime').datetimepicker();
		})
	</SCRIPT>
</body>
</html>