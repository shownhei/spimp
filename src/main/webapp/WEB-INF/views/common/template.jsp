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
				<i class="icon-double-angle-right"></i> <i class="icon-reorder"></i> {{name}}
				{{#if resourceEntities}}
				<b class="arrow icon-angle-down"></b>
				{{/if}}
			</a>
			{{#if resourceEntities}}
			<ul class="submenu" style="background-color: #f5f5f5;">
				{{#each resourceEntities}}
				<li>
					<a href="{{uri}}" {{#if resourceEntities}} class="dropdown-toggle" {{/if}}>
						<i class="icon-angle-right"></i>{{name}}
						{{#if resourceEntities}}
						<b class="arrow icon-angle-down"></b>
						{{/if}}
					</a>
					{{#if resourceEntities}}
					<ul class="submenu">
						{{#each resourceEntities}}
						<li>
							<a href="{{uri}}">
								<i class="icon-angle-right"></i>{{name}}
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
	</ul>
	{{/if}}
</li>
{{/each}}
</script>
<script id="infobox-template" type="text/x-handlebars-template">
{{#each data}}
<div class="infobox {{color}}" style="width: 45%">
	<div class="infobox-icon">
		<i class="{{icon}}"></i>
	</div>
	<div class="infobox-data">
		<span class="infobox-data-number">{{value}}</span>
		<div class="infobox-content">{{content}} <a href="{{link}}"><i class="icon-chevron-right"></i><i class="icon-chevron-right"></i></a></div>
	</div>
</div>
{{/each}}
</script>
