define(function(require, exports, module) {
	var $ = require('kjquery');
	var Grid = require('grid');
	// 配置表格列
	var fields = [ {
		header : 'id',
		width : 90,
		name : 'id',
		render : function(v) {
			return v;
		}
	}, {
		header : '员工姓名',
		width : 80,
		name : 'name'
	}, {
		header : '工种',
		name : 'jobType'
	}, {
		header : '班组',
		name : 'troopName'
	}, {
		header : '基站',
		name : 'stationName'
	}, {
		header : '进入时间',
		name : 'enterCurTime'
	}, {
		header : '停留时间',
		name : 'indataTime'
	} ];

	// 计算表格高度和行数
	var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
	var pageSize = Math.floor(gridHeight / 21);

	// 配置表格
	var defaultUrl = contextPath + '/location/location-tracks-query?orderBy=id&order=desc&pageSize=' + pageSize;
	var grid = new Grid({
		parentNode : '#stafflist-table',
		url : defaultUrl,
		model : {
			fields : fields,
			needOrder : true,
			orderWidth : 50,
			height : gridHeight
		},
		onClick : function(target, data) {
		},
		onLoaded : function() {
		}
	}).render();

	// 部门选择发生变化 加载对应的员工信息
	$('#trace_department').change(function() {
		var _department = $('#trace_department').val();
		$("#trace_staff option").each(function() {
			$(this).remove();
		});
		if (!_department) {
			return;
		}
		$.ajax({
			type : 'get',
			dataType : 'json',
			data : 'department=' + encodeURI(_department),
			url : '/location/location-staffs/departmentstaff',
			success : function(datas) {
				var _select = $("#trace_staff");
				$("<option value=''>请选择人员</option>").appendTo(_select);
				$.each(datas.data, function(key, value) {
					$("<option value='" + value.id.staffId + "' data-param='" + value.id.mineId + "'>" + value.name + "</option>").appendTo(_select);
				});
			}
		});
	});//
	$('#trace_staff').change(function() {
		var staffVal = $(this).val();
		if (!staffVal) {
			$('#trace_query_btn').addClass('disabled');
			$('#trace_playback_btn').addClass('disabled');
		} else {
			$('#trace_query_btn').removeClass('disabled');
			$('#trace_playback_btn').removeClass('disabled');
		}
	});
	// 查询人员信息列表
	$('#trace_query_btn').click(function() {
		if ($('#trace_query_btn').hasClass('disabled')) {
			return;
		}
		var _param = 'department=' + $("#trace_department").val();
		_param += '&staffId=' + $('#trace_staff').val();
		_param += '&startTime=' + $('#trace_startDateTime').val();
		_param += '&endTime=' + $('#trace_endDateTime').val();
		_param = encodeURI(_param);

		grid.set('url', contextPath + '/location/location-tracks-query?' + _param);
		grid.refresh();
	});
	// 轨迹回放
	$('#trace_playback_btn').click(function() {
		if ($('#trace_query_btn').hasClass('disabled')) {
			return;
		}
		var _param = 'department=' + $("#trace_department").val();
		_param += '&staffId=' + $('#trace_staff').val();
		_param += '&startTime=' + $('#trace_startDateTime').val();
		_param += '&endTime=' + $('#trace_endDateTime').val();
		$.get(contextPath + '/location/location-tracks-query?' + _param, function(data) {
			var _jsonResult = {};
			_jsonResult.PEOPLE = $("#trace_staff").find("option:selected").text();
			var READCARD = [];
			$.each(data.data.result, function(key, value) {
				READCARD.push({
					'DBID' : 'MineID:' + value.mineId + ';StationID:' + value.stationId + ';'
				});
			});
			_jsonResult.READCARD = READCARD;
			external._Rydw_SetMineWorkerTrajectory(JSON.stringify(_jsonResult));
		});
	});

	function loadDepartMent() {
		$.ajax({
			type : 'get',
			dataType : 'json',
			url : '/location/location-staffs/alldepartment',
			success : function(datas) {
				$("#trace_department option").each(function() {
					$(this).remove();
				});
				var _select = $("#trace_department");
				$("<option value=''>请选择部门</option>").appendTo(_select);
				$.each(datas.data, function(key, value) {
					$("<option value='" + value + "'>" + value + "</option>").appendTo(_select);
				});
			}
		});
	}
	$('#trace_startDateTime').datetimepicker();
	$('#trace_endDateTime').datetimepicker();
	setTimeout(loadDepartMent, 100);
});