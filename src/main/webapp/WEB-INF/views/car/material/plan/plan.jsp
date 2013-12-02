<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${plan!=null}">
	<div id="table_panel">
		<table id="sample-table-1" data-id="${planId}" class="table table-striped table-bordered table-hover">
			<thead>
				<tr class="grid-row">
					<th align="center" colspan=9>${fn:substring(plan.planDate, 0,4)}年${fn:substring(plan.planDate, 5,7)}月份胶轮车材料申请计划</th>
				</tr>
				<tr class="grid-row">
					<th colspan=4>单位：${plan.planGroup.name}</th>
					<th colspan=5></th>
				</tr>
				<tr>
					<th style="width: 30px;">序号</th>
					<th>材料名称</th>
					<th>规格型号或备件号</th>
					<th>单位</th>
					<th>数量</th>
					<th>单价（元）</th>
					<th>金额（元）</th>
					<th>备注</th>
					<th>操作</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${details}" var="data" varStatus="status">
					<tr class="grid-row page_report_table_tr">
						<td>${status.index+1}</td>
						<td>${data.materialName }</td>
						<td>${data.model }</td>
						<td>${data.measureUnit }</td>
						<td>${data.price }</td>
						<td>${data.quantity }</td>
						<td>${data.sumMoney }</td>
						<td>${data.remark }</td>
						<td><div class="visible-md  btn-group">
								<button class="btn btn-small btn-info" detailId="${data.id}" buttonType="edit">
									<i class="icon-edit bigger-120" detailId="${data.id}" buttonType="edit"></i>
								</button>

								<button class="btn btn-small btn-danger" detailId="${data.id}" buttonType="delete">
									<i class="icon-trash bigger-120" detailId="${data.id}" buttonType="delete"></i>
								</button>
							</div></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</c:if>