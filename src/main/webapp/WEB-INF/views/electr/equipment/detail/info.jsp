<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="table-responsive" id="table_panel">
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th colspan=4><center>
						基本信息
						<center></th>
				<th colspan=2><center>
						配件信息
						<center></th>
			</tr>
		</thead>
		<tbody>
			<tr class="page_report_table_tr">
				<td width="80">设备分类</td>
				<td width="120">${equipment.deviceClass.itemName}</td>
				<td width="80">设备种类</td>
				<td width="120">${equipment.deviceCategory.itemName}</td>
				<td colspan=2 width="200" rowspan=10>
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>配件型号</th>
								<th>配件编号</th>
								<th>出厂日期</th>
								<th>运输功率</th>
								<th>厂商</th>
								<th>传动比</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${equipment.accessories}" var="data" varStatus="status">
								<tr>
									<td>${data.accessoryModel }</td>
									<td>${data.accessoryNumber }</td>
									<td>${data.productionDate }</td>
									<td>${data.producer }</td>
									<td>${data.serviceRating }</td>
									<td>${data.transmissionRatio }</td>
									<td>${data.remark }</td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
				</td>
			</tr>
			<tr class="page_report_table_tr">
				<td>设备类型</td>
				<td>${equipment.deviceType.itemName}</td>
				<td>设备名称</td>
				<td>${equipment.deviceName}</td>
			</tr>
			<tr class="grid-row page_report_table_tr">
				<td>设备型号</td>
				<td>${equipment.deviceModel}</td>
				<td>使用环境</td>
				<td>${equipment.serviceEnvironment.itemName}</td>
			</tr>
			<tr class="grid-row page_report_table_tr">
				<td>所属区域</td>
				<td>${equipment.deviceArea.itemName}</td>
				<td>存放地点</td>
				<td>${equipment.stowedPosition.itemName}</td>
			</tr>
			<tr class="grid-row page_report_table_tr">
				<td>用途</td>
				<td>${equipment.usage}</td>
				<td>生产厂家</td>
				<td>${equipment.producer}</td>
			</tr>
			<tr class="grid-row page_report_table_tr">
				<td>设备编号</td>
				<td>${equipment.deviceNumber}</td>
				<td>出厂编号</td>
				<td>${equipment.factoryNumber}</td>
			</tr>
			<tr class="grid-row page_report_table_tr">
				<td>出厂日期</td>
				<td>${equipment.productionDate}</td>
				<td>包机人</td>
				<td>${equipment.chargePerson}</td>
			</tr>
			<tr class="grid-row page_report_table_tr">
				<td>班组长</td>
				<td>${equipment.monitor}</td>
				<td>三开一防锁</td>
				<td>${equipment.openLocker}</td>
			</tr>
			<tr class="grid-row page_report_table_tr">
				<td>数量</td>
				<td>${equipment.lockerNumber}</td>
				<td>速度</td>
				<td>${equipment.speed}</td>
			</tr>
			<tr class="grid-row page_report_table_tr">
				<td>运输量</td>
				<td>${equipment.deliveryValue}</td>
				<td>布置长度</td>
				<td>${equipment.layoutLength}</td>
			</tr>
		</tbody>
	</table>

</div>
