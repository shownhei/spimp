<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${maintenance!=null }">
	<div class="table-responsive" id="table_panel">
		<table id="sample-table-1" data-id="${maintenance.id}" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th colspan=6><center>${maintenance.car.carNo }无轨胶轮车日常保养记录</center></th>
				</tr>
				<tr>
					<th colspan=2 width=100>车牌号：${maintenance.car.carNo }</th>
					<th colspan=2>保养类别：<c:choose>
							<c:when test="${maintenance.maintenanceLevel=='0'}">日常保养</c:when>
							<c:when test="${maintenance.maintenanceLevel=='1'}">一级保养（走合期后每隔1500km进行一次）</c:when>
							<c:when test="${maintenance.maintenanceLevel=='2'}">二级保养（走合期后每隔5000km进行一次） </c:when>
							<c:when test="${maintenance.maintenanceLevel=='3'}">三级保养（走合期后每隔15000km进行一次）</c:when>
						</c:choose></th>
					<th colspan=2>${fn:substring(maintenance.maintenanceDate, 0,10)}</th>
				</tr>
				<tr>
					<th width=40>序号</th>
					<th>检查维修项目</th>
					<th>保养方式</th>
					<th>检修处理情况</th>
					<th>备注</th>
					<th>操作</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${details}" var="data" varStatus="status">
					<tr class="grid-row page_report_table_tr">
						<td>${status.index+1}</td>
						<td>${data.checkItem }</td>
						<td class="hidden-480">${data.maintenanceWay }</td>
						<td>${data.treatment }</td>
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
				<tr>
					<td></td>
					<td colspan=3>保养人：${maintenance.maintenancePeople }</td>
					<td colspan=2>验收人：${maintenance.accepter }</td>
				</tr>
			</tbody>
		</table>
	</div>
</c:if>
