<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="table-responsive" id="table_panel">
	<table id="sample-table-1" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th colspan=${fn:length(carList)+2 }><center>${year }无轨胶轮车行程公里一览表</center></th>
			</tr>
			<tr>
				<th>月份/车辆</th>
				<c:forEach items="${carList }" var="row" varStatus="index">
					<th>${row}</th>
				</c:forEach>
				<th>合计</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${result}" var="data" varStatus="listIndex">
				<tr class="grid-row page-report-table-tr">
					<td>${listIndex.index+1}月份</td>
					<c:forEach begin="1" end="${fn:length(carList)+1}" step="1" var="index">
						<td>
							<c:if test="${data[index-1]>0}">${data[index-1]}</c:if>
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
