<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${fn:length(carList)>0}">
	<c:set var="carListSize" value="${fn:length(carList)}" />
	<div class="table-responsive" id="table_panel">
		<table id="sample-table-1" <c:if test="${carListSize>1}">style="width:${carListSize*500+50 }px;" </c:if>
			class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th colspan=${carListSize*10+1 }><center>${year }年${month }月份运行情况统计表</center></th>
				</tr>
				<tr>
					<th></th>
					<c:forEach items="${carList }" var="row" varStatus="index">
						<th colspan=10 style="border-left-color: black;"><center>${row}</center></th>
					</c:forEach>
				</tr>
				<tr>
					<th>班次</th>
					<c:forEach items="${carList }" var="row" varStatus="index">
						<th colspan=3 width=60 style="border-left-color: black;"><center>零点</center></th>
						<th colspan=3 width=60><center>八点</center></th>
						<th colspan=3 width=60><center>四点</center></th>
						<th width=20></th>
					</c:forEach>
				</tr>
				<tr>
					<th>日期</th>
					<c:forEach items="${carList }" var="row" varStatus="index">
						<th style="border-left-color: black;">车次</th>
						<th>路程</th>
						<th>加油</th>
						<th>车次</th>
						<th>路程</th>
						<th>加油</th>
						<th>车次</th>
						<th>路程</th>
						<th>加油</th>
						<th width=20></th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${result}" var="data" varStatus="listIndex">
					<tr class="grid-row page-report-table-tr">
						<td>${listIndex.index+1}日</td>
						<c:forEach begin="1" end="${fn:length(data)}" step="1" var="index">
							<td <c:if test="${(index-1)%10==0}">style="border-left-color:black;"</c:if>>
								<c:if test="${data[index-1]>0}">${data[index-1]}</c:if>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
				<tr class="grid-row page-report-table-tr">
					<td>合计</td>
					<c:forEach items="${sumList}" var="data" varStatus="index">
						<td <c:if test="${(index.index)%10==0}">style="border-left-color:black;"</c:if>>
							<c:if test="${data>0}">${data}</c:if>
						</td>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
</c:if>
