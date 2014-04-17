define(function(require, exports, module) {
	var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../common/utils');

	// 启用日期控件
	Utils.input.date('input[type=datetime]');

	// 获取测点类型
	Utils.select.remote([ 'monitorSensorType' ], contextPath + '/monitor/monitor-sensor-types', 'sensorTypeId', 'sensorTypeName', true, '选择测点类型');

	// 根据是否显示机构，控制页面显示、机构树加载、表格列等
	var groupTree;
	if (showGroup) {
		$('.page-content .row-fluid div').removeClass('hide');

		// 控制显示机构树
		groupTree = Utils.tree.group('groupTree', contextPath + '/system/groups/2?label=mine', function(event, treeId, treeNode, clickFlag) {
			console.log(treeNode.number);
		});
	} else {
		$('.page-content .row-fluid .span3').remove();
		$('.page-content .row-fluid .span9').removeClass('span9 hide').addClass('span12');
	}

	// 级联下拉列表
	function change(selectId, cascadeId, url, value, display, blank, blankText) {
		$('#' + selectId).change(function() {
			if ($(this).val() === '') {
				$('#' + cascadeId).attr('disabled', 'disabled');
			} else {
				Utils.select.remote([ cascadeId ], encodeURI(url + $(this).val()), value, display, blank, blankText);
				$('#' + cascadeId).removeAttr('disabled');
			}
		});
	}

	change('monitorSensorType', 'nodePlace', contextPath + '/monitor/monitor-node-places?sensorTypeId=', 'nodeId', 'nodePlace', true, '选择位置');
});
