<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="table-responsive" id="table_panel">
	<table id="sample-table-1" data-id="${maintenance.id}" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th colspan=8><center>${year }年${month }月无轨胶轮车百公里油耗计算表</center></th>
			</tr>
			<tr>
				<th>车号</th>
				<th>运行次数（次）</th>
				<th>行程公里数（公里）</th>
				<th>加油数（升）</th>
				<th>日平均运行 次数（次）</th>
				<th>百公里油耗（升）</th>
				<th>平均油耗（升）</th>
				<th>备注</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${category}" var="list" varStatus="status">
				<c:forEach items="${list}" var="data" varStatus="listIndex">
					<tr class="grid-row page_report_table_tr">
						<td>${data.carNo}</td>
						<td>${data.trainNumber}</td>
						<td>${data.distance}</td>
						<td class="hidden-480">${data.refuelNumber}</td>
						<c:if test="${listIndex.index==0 }">
							<td rowspan="${fn:length(list) }"  style="text-align:center;vertical-align:middle"></td>
						</c:if>
						<td>${data.oilDistanceDisplay}</td>
						<c:if test="${listIndex.index==0 }">
							<td rowspan="${fn:length(list) }"  style="text-align:center;vertical-align:middle">${data.avgOilDistance }</td>
						</c:if>
						<c:if test="${listIndex.index==0 }">
							<td rowspan="${fn:length(list) }"  style="text-align:center;vertical-align:middle">${data.carCategory }</td>
						</c:if>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>
</div>
