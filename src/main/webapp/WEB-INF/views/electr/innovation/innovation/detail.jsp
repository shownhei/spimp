<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="table-responsive" id="table_panel">
	<table id="sample-table-1" data-id="${maintenance.id}" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th colspan=4><center>
						小改小革、小发明项目申报表
						<a href="javascript:void(0);" id="close_detail" style="float: right;">返回</a>
					</center></th>
			</tr>
			<tr>
				<th width=120>申报单位：</th>
				<th colspan=3>${innovation.recordGroup.name }</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td width=120>项目名称:</td>
				<td colspan=3>${innovation.projectName}</td>
			</tr>
			<tr>
				<td width=120>项目负责人</td>
				<td width=300>${innovation.chargePerson}</td>
				<td width=100>实施时间</td>
				<td>${innovation.implementationPeriod}</td>
			</tr>
			<tr>
				<td>实施地点</td>
				<td>${innovation.implementationAddress}</td>
				<td>申报日期</td>
				<td>${innovation.declarationDate}</td>
			</tr>
			<tr>
				<td>参加人员</td>
				<td colspan=3>${innovation.participant}</td>
			</tr>
			<tr>
				<td>目的</td>
				<td colspan=3>${innovation.inventionPurpose}</td>
			</tr>
			<tr>
				<td>主要内容或原理</td>
				<td colspan=3>${innovation.content}</td>
			</tr>
			<tr>
				<td>效果及经济社会效益分析</td>
				<td colspan=3>${innovation.analysis}</td>
			</tr>
			<tr>
				<td>相关图片</td>
				<td colspan=3>
					<c:forEach items="${pictures}" var="pic" varStatus="listIndex">
					<div class="span2" style="position:relative;height:100%;">
						<img src="${pic.imagePath }" data-type="detail-image" style="width: 400px;position:absolute;left:1px;top:1px;">
						<a href="javascript:void(0)" data-id="${pic.id }" data-type="removeImg" style="position:absolute;left:1px;top:1px;">删除</a>
					</div>
					</c:forEach>
				</td>
			</tr>
		</tbody>
	</table>
</div>
