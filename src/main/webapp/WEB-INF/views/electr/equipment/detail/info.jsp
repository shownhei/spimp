<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="table-responsive" id="table_panel">
	<ul class="nav nav-tabs">
		<li class="active" id="BaseTab" elType="tab">
			<a data-toggle="tab" href="#BasePanel" elType="tab">
				<i class="green icon-home" elType="tab"></i> 设备信息
			</a>
		</li>
		<li id="AccessoryTab" elType="tab">
			<a data-toggle="tab" href="#AccessoryPanel" elType="tab">
				<i class="green icon-list" elType="tab"></i>配件信息
			</a>
		</li>
	</ul>
	<div id="tab-content" class="tab-content">
		<div id="BasePanel" class="tab-pane in active">
			<table class="table table-striped table-bordered table-hover">
				<tbody>
					<tr class="page_report_table_tr">
						<td width="70">设备分类</td>
						<td width="180">${equipment.deviceClass.itemName}</td>
						<td width="70">设备种类</td>
						<td width="180">${equipment.deviceCategory.itemName}</td>
						<td width="70">设备类型</td>
						<td>${equipment.deviceType.itemName}</td>
						<td width="70">设备名称</td>
						<td>${equipment.deviceName}</td>
					</tr>
					<tr class="page_report_table_tr">
						<td>设备型号</td>
						<td>${equipment.deviceModel}</td>
						<td>使用环境</td>
						<td>${equipment.serviceEnvironment.itemName}</td>
						<td>所属区域</td>
						<td>${equipment.deviceArea.itemName}</td>
						<td>存放地点</td>
						<td>${equipment.stowedPosition.itemName}</td>
					</tr>
					<tr class="page_report_table_tr">
						<td>用途</td>
						<td>${equipment.usage}</td>
						<td>生产厂家</td>
						<td>${equipment.producer}</td>
						<td>设备编号</td>
						<td>${equipment.deviceNumber}</td>
						<td>出厂编号</td>
						<td>${equipment.factoryNumber}</td>
					</tr>
					<tr class="page_report_table_tr">
						<td>出厂日期</td>
						<td>${equipment.productionDate}</td>
						<td>包机人</td>
						<td>${equipment.chargePerson}</td>
						<td>班组长</td>
						<td>${equipment.monitor}</td>
						<td>三开一防锁</td>
						<td>${equipment.openLocker}</td>
					</tr>
					<tr class="page_report_table_tr">
						<td>数量</td>
						<td>${equipment.lockerNumber}</td>
						<td></td>
						<td></td>
						<td colspan=4></td>
					</tr>
					<tr class="page_report_table_tr">
						<td>图片</td>
						<td colspan="3">
							<c:if test="${!empty equipment.pictureURL}">
								<img src="${equipment.pictureURL}">
							</c:if>
						</td>
						<td>说明书</td>
						<td>
							<c:if test="${!empty equipment.pictureURL}">
								<a elType="showDocument" data-id="${equipment.specificationURL.id}" href="javascript:void(0);" target="_blank">${equipment.specificationURL.simpleName}</a>
							</c:if>
						</td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="AccessoryPanel" class="tab-pane">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th width=80>配件名称</th>
						<th width=80>配件型号</th>
						<th width=80>配件编号</th>
						<th width=80>出厂日期</th>
						<th>厂商</th>
						<th>存放位置</th>
						<th>备注</th>
						<th width=80>图片</th>
						<th width=80>说明书</th>
						<th width=50>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${accessories}" var="data" varStatus="status">
						<tr class="page_report_table_tr">
							<td>${data.accessoryName }</td>
							<td>${data.accessoryModel }</td>
							<td>${data.accessoryNumber }</td>
							<td>${data.productionDate }</td>
							<td>${data.producer }</td>
							<td>${data.producer }</td>
							<td>${data.remark }</td>
							<c:if test="${empty data.pictureURL}">
								<td buttonType="upload">
									<button class="btn btn-small btn-success" data-id="${data.id}" buttonType="upload">
										<i class="icon-upload bigger-120" data-id="${data.id}" buttonType="upload"></i>
									</button>
							</c:if>
							<c:if test="${!empty data.pictureURL}">
								<td dataType="showRemark">
									<div style="width: 100px; height: 20px; overflow: hidden; white-space: nowrap;">${data.pictureURL}</div>
							</c:if>
							</td>
							<c:if test="${empty data.instructions}">
								<td buttonType="upload-instructions">
									<button class="btn btn-small btn-success" data-id="${data.id}" buttonType="upload-instructions">
										<i class="icon-upload bigger-120" data-id="${data.id}" buttonType="upload-instructions"></i>
									</button>
							</c:if>
							<c:if test="${!empty data.instructions}">
								<td>
									<div style="width: 100px; height: 20px; overflow: hidden; white-space: nowrap;">
										<a href="javascript:void(0)" elType="showDocument" data-id="${data.instructions.id}">${data.instructions.simpleName}</a>
									</div>
							</c:if>
							</td>
							<td>
								<button class="btn btn-small btn-danger" data-id="${data.id}" buttonType="delete">
									<i class="icon-trash bigger-120" data-id="${data.id}" buttonType="delete"></i>
								</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
