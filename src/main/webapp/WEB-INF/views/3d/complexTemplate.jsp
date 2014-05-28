<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="tabbable" id="content">
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#tab1">生产信息</a></li>
		<li><a data-toggle="tab" href="#tab2">规程图纸措施</a></li>
	</ul>
	<div class="tab-content" id="tab-content">
	<div id="tab1" class="tab-pane in active">
		<div class="row-fluid" id="row1">
			<div class="span6" id="col1_1">
				<div class="widget-header widget-header-flat widget-header-small">
					<i class="green icon-th-list"></i>视频信息<i class="blue icon-resize-full complex-button-resize"></i>
				</div>
				<div class="widget-body complex-widget-body">
					<div class="widget-main" style="overflow-y: auto;">
					<c:forEach items="${CAMERA}" var="data">
						<div class="complex-video-wrapper">
							<object classid="clsid:ab1408a0-10f6-40ba-984d-074d7bdc3126" width="200" height="150" id="${data.ID}"></object>
							<div>${data.LOCATION}</div>
						</div>
					</c:forEach>
					</div>
				</div>
			</div>
			<div class="span6" id="col1_2">
				<div class="widget-header widget-header-flat widget-header-small">
					<i class="green icon-th-list"></i>人员情况<i class="blue icon-resize-full complex-button-resize"></i>
				</div>
				<div class="widget-body complex-widget-body">
					<div class="widget-main complex-widget-main">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>姓名</th>
								<th>部门</th>
								<th>岗位</th>
								<th>班组</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${PERSON}" var="data" varStatus="status">
								<tr>
									<td>${ data.name}</td>
									<td>${ data.department}</td>
									<td>${ data.jobName}</td>
									<td>${ data.troopName}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid" id="row2" style="margin-top: 10px;">
			<div class="span6" id="col2_1">
				<div class="widget-header widget-header-flat widget-header-small">
					<i class="green icon-th-list"></i>设备情况<i class="blue icon-resize-full complex-button-resize"></i>
				</div>
				<div class="widget-body complex-widget-body">
					<div class="widget-main complex-widget-main">
						<table class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th>名称</th>
									<th>编号</th>
									<th>类型</th>
									<th>位置</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${EQIPMENT.electr_equipments}" var="data"
									varStatus="status">
									<tr>
										<td>${ data.deviceName}</td>
										<td>${ data.deviceNumber}</td>
										<td>${ data.deviceType}</td>
										<td>${ data.deviceArea}</td>
									</tr>
								</c:forEach>
				
								<c:forEach items="${EQIPMENT.electr_transform_equipments}"
									var="data" varStatus="status">
									<tr>
										<td>${ data.deviceName}</td>
										<td>${ data.equipmentNumber}</td>
										<td>${ data.deviceModel}</td>
										<td>${ data.location}</td>
									</tr>
								</c:forEach>
								<c:forEach items="${EQIPMENT.electr_wind_water_equipments}"
									var="data" varStatus="status">
									<tr>
										<td>${ data.windAmount}</td>
										<td>${ data.equipmentCode}</td>
										<td>${ data.windCycle}</td>
										<td>${ data.location}</td>
									</tr>
								</c:forEach>
								<c:forEach items="${EQIPMENT.electr_fire_fighting_equipments}"
									var="data" varStatus="status">
									<tr>
										<td>${ data.sandBoxCapacity}</td>
										<td>${ data.equipmentCode}</td>
										<td>消防斧${ data.fireAxe}/消防钩${data.fireHook}</td>
										<td>${ data.location}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="span6" id="col2_2">
				<div class="widget-header widget-header-flat widget-header-small">
					<i class="green icon-th-list"></i>环境信息<i class="blue icon-resize-full complex-button-resize"></i>
				</div>
				<div class="widget-body complex-widget-body">
					<div class="widget-main complex-widget-main">
						<table class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th>编号</th>
									<th>位置</th>
									<th>所属分站</th>
									<th>名称</th>
									<th>当前值</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${ENVIROMENT}" var="data" varStatus="status">
									<tr>
										<td>${ data[0]}</td>
										<td>${ data[1]}</td>
										<td>${ data[2]}</td>
										<td>${ data[3]}</td>
										<td>${ data[8]}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="tab-pane" id="tab2">
		<div class="row-fluid">1111
		</div>
	</div>
</div>