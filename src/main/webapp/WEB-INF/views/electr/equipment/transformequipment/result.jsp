<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<ul class="nav nav-tabs">
	<li class="active" id="ReducerDeviceTab"  elType="tab"><a data-toggle="tab" href="#ReducerDevice" elType="tab">
			<i class="green icon-home"  elType="tab"></i> 减速机
	</a></li>
	<li id="ElectromotorDeviceTab"><a data-toggle="tab" href="#ElectromotorDevice"  elType="tab"> <i
			class="green icon-list"  elType="tab"></i> 电动机
	</a></li>
	<li id="BrakeDeviceTab"><a data-toggle="tab" href="#BrakeDevice"  elType="tab"> <i
			class="green icon-list"  elType="tab"></i> 制动器
	</a></li>
	<li id="TensioningDeviceTab"  elType="tab"><a data-toggle="tab" href="#TensioningDevice"  elType="tab"> <i
			class="green icon-list"  elType="tab"></i> 拉紧装置
	</a></li>
</ul>
<div id="tab-content" class="tab-content">
	<div id="ReducerDevice" class="tab-pane in active">
		<div class="row-fluid">
			<div class="span6">
				<button id="reducer_create" class="btn btn-small btn-success">
					<i class="icon-plus-sign-alt"></i> <span class="hidden-phone">添加</span>
				</button>
			</div>
			<div class="row-fluid" style="overflow: auto;">
				<div class="span12">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>型号</th>
								<th>运行功率</th>
								<th>传动比</th>
								<th>出厂编号</th>
								<th>生产厂家</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${reducerDevices }" var="data">
								<tr class="grid-row page_report_table_tr">
									<td>${data.deviceModel}</td>
									<td>${data.runningPower}</td>
									<td>${data.transmissionRatio}</td>
									<td>${data.factoryNumber}</td>
									<td>${data.producer}</td>
									<td><div class="visible-md  btn-group">
											<button class="btn btn-small btn-danger" dataType="reducer" dataId="${data.id}" buttonType="delete">
												<i class="icon-trash bigger-120" dataType="reducer" dataId="${data.id}" buttonType="delete"></i>
											</button>
										</div></td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="ElectromotorDevice" class="tab-pane">
		<div class="row-fluid">
			<button id="electromotor_create" class="btn btn-small btn-success">
				<i class="icon-plus-sign-alt"></i> <span class="hidden-phone">添加</span>
			</button>
		</div>
		<div class="row-fluid" style="overflow: auto;">
			<div class="span12">

				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>型号</th>
							<th>编号</th>
							<th>出厂日期</th>
							<th>生厂产家</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${electromotorDevices }" var="data">
							<tr class="grid-row page_report_table_tr">
								<td>${data.deviceModel}</td>
								<td>${data.factoryNumber}</td>
								<td>${data.productionDate}</td>
								<td>${data.producer}</td>
								<td><div class="visible-md  btn-group">
											<button class="btn btn-small btn-danger" dataType="electromotor" dataId="${data.id}" buttonType="delete">
												<i class="icon-trash bigger-120" dataType="electromotor" dataId="${data.id}" buttonType="delete"></i>
											</button>
										</div></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div id="BrakeDevice" class="tab-pane">
		<div class="row-fluid">
			<button id="brake_create" class="btn btn-small btn-success">
				<i class="icon-plus-sign-alt"></i> <span class="hidden-phone">添加</span>
			</button>
		</div>
		<div class="row-fluid" style="overflow: auto;">
			<div class="span12">

				<table class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>型号</th>
							<th>编号</th>
							<th>出厂日期</th>
							<th>生厂产家</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${brakeDeviceDevices }" var="data">
							<tr class="grid-row page_report_table_tr">
								<td>${data.deviceModel}</td>
								<td>${data.factoryNumber}</td>
								<td>${data.productionDate}</td>
								<td>${data.producer}</td>
								<td><div class="visible-md  btn-group">
											<button class="btn btn-small btn-danger" dataType="brake" dataId="${data.id}" buttonType="delete">
												<i class="icon-trash bigger-120" dataType="brake" dataId="${data.id}" buttonType="delete"></i>
											</button>
										</div></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div id="TensioningDevice" class="tab-pane">
		<div class="row-fluid">
			<div class="span6">
				<button id="tensioning_create" class="btn btn-small btn-success">
					<i class="icon-plus-sign-alt"></i> <span class="hidden-phone">添加</span>
				</button>
			</div>
			<div class="row-fluid" style="overflow: auto;">
				<div class="span12">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>拉紧方式</th>
								<th>装置名称</th>
								<th>型号</th>
								<th>编号</th>
								<th>出厂日期</th>
								<th>生厂产家</th>
								<th>技术参数</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${tensioningDevices }" var="data">
								<tr class="grid-row page_report_table_tr">
									<td>${data.takeUp}</td>
									<td>${data.deviceName}</td>
									<td>${data.deviceModel}</td>
									<td>${data.deviceNumber}</td>
									<td>${data.productionDate}</td>
									<td>${data.producer}</td>
									<td dataType="showRemark">${data.techParameters}</td>
									<td><div class="visible-md  btn-group">
											<button class="btn btn-small btn-danger" dataType="tensioning" dataId="${data.id}" buttonType="delete">
												<i class="icon-trash bigger-120" dataType="tensioning" dataId="${data.id}" buttonType="delete"></i>
											</button>
										</div></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>