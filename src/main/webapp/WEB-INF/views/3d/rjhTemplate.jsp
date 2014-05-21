<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="tabbable tabs-left" id="content">
	<ul class="nav nav-tabs">
		<li class="active"><a data-toggle="tab" href="#tab1"><i
				class="green icon-th-list"></i>人员情况
		</a></li>
		<li><a data-toggle="tab" id="firsttab" href="#tab2"><i
				class="green icon-th-list"></i>设备情况
		</a></li>
		<li><a data-toggle="tab" id="firsttab" href="#tab3"><i
				class="green icon-th-list"></i>环境信息
		</a></li>
	</ul>
	<div id="tab-content" class="tab-content">
		<div id="tab1" class="tab-pane in active">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>姓名</th>
						<th>部门</th>
						<th>岗位</th>
						<th>工种</th>
						<th>班组</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${PERSON}" var="data" varStatus="status">
						<tr>
							<td>${ data.name}</td>
							<td>${ data.department}</td>
							<td>${ data.jobName}</td>
							<td>${ data.jobType}</td>
							<td>${ data.troopName}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="tab2" class="tab-pane ">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>名称</th>
						<th>设备编号</th>
						<th>设备类型</th>
						<th>安装位置</th>
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
		<div id="tab3" class="tab-pane ">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>设备编号</th>
						<th>设备类型</th>
						<th>检测值</th>
						<th>是否超限</th>
						<th>安放地点</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ENRIROMENT}" var="data" varStatus="status">
						<tr>
							<td>${ data[0]}</td>
							<td>${ data[1]}</td>
							<td>${ data[2]}</td>
							<td>${ data[3]}</td>
							<td>${ data[4]}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>