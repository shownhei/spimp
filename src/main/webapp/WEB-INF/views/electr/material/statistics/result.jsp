<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table id="sample-table-1" class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th colspan=12><center>${year }年${month }月份胶轮车配件进货使用剩余量明细表</center></th>
		</tr>
		<tr>
			<th colspan=4><center>胶轮车配件进货明细表(本月)</center></th>
			<th colspan=4><center>胶轮车材料使用明细表(本月)</center></th>
			<th colspan=4><center>胶轮车配件(当前)剩余量统计表</center></th>
		</tr>
		<tr>
			<th>物料名称</th>
			<th>数量</th>
			<th>单位</th>
			<th>备注</th>
			<th>物料名称</th>
			<th>数量</th>
			<th>单位</th>
			<th>备注</th>
			<th>物料名称</th>
			<th>数量</th>
			<th>单位</th>
			<th>备注</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${result}" var="data">
			<tr class="page_report_table_tr">
				<td>${data.materialName1 }</td>
				<td class="hidden-480">${data.quantity1 }</td>
				<td>${data.measureUnit1 }</td>
				<td></td>
				<td>${data.materialName2 }</td>
				<td class="hidden-480">${data.quantity2 }</td>
				<td>${data.measureUnit2 }</td>
				<td></td>
				<td>${data.materialName3 }</td>
				<td class="hidden-480">${data.quantity3 }</td>
				<td>${data.measureUnit3 }</td>
				<td></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
