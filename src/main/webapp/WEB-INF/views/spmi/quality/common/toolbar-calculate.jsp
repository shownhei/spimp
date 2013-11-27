<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="page-toolbar">
	<div class="toolbar">
		<button id="create" class="btn btn-small btn-success" title="新建评分表">
			<i class="icon-plus-sign-alt"></i>
			<span class="hidden-phone">评分</span>
		</button>
		<button id="calculate" class="btn btn-small btn-success" title="统计月度平均得分">
			<i class="icon-table"></i>
			<span class="hidden-phone">统计</span>
		</button>
		<button id="edit" class="btn btn-small btn-primary disabled">
			<i class="icon-edit"></i>
			<span class="hidden-phone">编辑</span>
		</button>
		<button id="remove" class="btn btn-small btn-danger disabled">
			<i class="icon-trash"></i>
			<span class="hidden-phone">删除</span>
		</button>
	</div>
	<div class="nav-search">
		<form id="query-form" class="form-inline" onsubmit="return false;">
			<select id="query-yearSelect" name="year" class="input-small">
				<option value="" class="light-grey">选择年份</option>
			</select>
			<select id="query-monthSelect" name="month" class="input-small">
				<option value="" class="light-grey">选择月份</option>
			</select>
		</form>
	</div>
</div>