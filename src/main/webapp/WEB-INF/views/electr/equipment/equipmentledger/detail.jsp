<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<div class="row-fluid">
		<div class="span12">
			<form class="form-horizontal"
				style="margin-bottom: 0px;">
				<table class="table table-striped table-bordered table-hover">
					<tbody>
						<tr class="page-report-table-tr">
							<td style="width: 15%;">设备名称</td>
							<td style="width: 20%;" readonly>${equipment.deviceName }</td>
							<td style="width: 10%;">设备编号</td>
							<td style="width: 15%;" readonly>${equipment.equipmentID }</td>
							<td style="width: 10%;">规格型号</td>
							<td style="width: 20%;" readonly>${equipment.deviceModel }</td>
						</tr>

						<tr class="page-report-table-tr">
							<td>生产厂家</td>
							<td>${equipment.producer }</td>
							<td>技术特征</td>
							<td>${equipment.technology }</td>
							<td>单位</td>
							<td>${equipment.measureUnit }</td>
						</tr>

						<tr class="page-report-table-tr">
							<td>数量</td>
							<td>${equipment.amount }</td>
							<td>出厂日期</td>
							<td>${equipment.productionDate }</td>
							<td>出厂编号</td>
							<td>${equipment.factoryNumber }</td>
						</tr>

						<tr class="page-report-table-tr">
							<td>购买日期</td>
							<td>${equipment.buyDate }</td>
							<td>使用日期</td>
							<td>${equipment.useDate }</td>
							<td>使用年限</td>
							<td>${equipment.serviceLife }</td>
						</tr>
						<tr class="page-report-table-tr">
							<td>在籍</td>
							<td>${equipment.inMembership }</td>
							<td>使用</td>
							<td>${equipment.inUse }</td>
							<td>备用</td>
							<td>${equipment.isSpare }</td>
						</tr>
						<tr class="page-report-table-tr">
							<td>待修</td>
							<td>${equipment.needsRepair }</td>
							<td>待报废</td>
							<td>${equipment.prepareScrapped }</td>
							<td>已报废</td>
							<td>${equipment.scrapped }</td>
						</tr>
						<tr class="page-report-table-tr">
							<td>借入</td>
							<td>${equipment.borrowed }</td>
							<td>借出</td>
							<td>${equipment.isLoan }</td>
							<td>主要附机</td>
							<td>${equipment.attachedDevice }</td>
						</tr>
						<tr class="page-report-table-tr">
							<td>原值</td>
							<td>${equipment.originalValue }</td>
							<td>净值</td>
							<td>${equipment.netWorth }</td>
							<td>使用地点</td>
							<td>${equipment.usePlace }</td>
						</tr>
						<tr class="page-report-table-tr">
							<td>是否防爆</td>
							<td>${equipment.explosionProof }</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
