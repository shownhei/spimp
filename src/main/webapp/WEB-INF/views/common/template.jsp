<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script id="menu-template" type="text/x-handlebars-template">
{{#each resourceEntities}}
<li>
	<a href="{{uri}}" {{#if resourceEntities}} class="dropdown-toggle" {{/if}}>
		<i class="{{iconCls}}"></i>
		<span class="menu-text"> {{name}} </span>
		{{#if resourceEntities}}
		<b class="arrow icon-angle-down"></b>
		{{/if}}
	</a>
	{{#if resourceEntities}}
	<ul class="submenu">
		{{#each resourceEntities}}
		<li>
			<a href="{{uri}}" {{#if resourceEntities}} class="dropdown-toggle" {{/if}}>
				<i class="icon-double-angle-right"></i> <i class="{{iconCls}}"></i> {{name}}
				{{#if resourceEntities}}
				<b class="arrow icon-angle-down"></b>
				{{/if}}
			</a>
			{{#if resourceEntities}}
			<ul class="submenu">
				{{#each resourceEntities}}
				<li>
					<a href="{{uri}}">
						<i class="{{iconCls}}"></i> {{name}}
					</a>
				</li>
				{{/each}}
			</ul>
			{{/if}}
		</li>
		{{/each}}
	</ul>
	{{/if}}
</li>
{{/each}}
</script>
