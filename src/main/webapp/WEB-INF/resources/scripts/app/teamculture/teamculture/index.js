define(function(require, exports, module) {
    var $ = require('kjquery'),
    Grid = require('grid'),
    Utils = require('../../common/utils');
    var operateUri = '/electr/team-cultures';

    // 提示信息
    $('button[title]').tooltip({
        placement: 'bottom'
    });
    new Utils.form.groupTree('create_groupSelectTree', 'create_grouptree', 'create_groupEntity', 'create_groupEntity');
	var editMeasure = $('#create_content').xheditor({
		skin : 'nostyle',
		tools : 'simple'
	});
    // 启用日期控件
    Utils.input.date('input[type=datetime]');

    // 配置表格列
    var fields = [{
        header: '主题',
        name: 'topic'
    },
    {
        header: '内容',
        name: 'content'
    },
    {
        header: '班组',
        width:100,
        name: 'groupEntity',
        render :function(v){
        	return v.name;
        }
    },
    {
        header: '日期',
        width: 90,
        name: 'recordDate'
    },
    {
        header: '附件',
        width: 90,
        name: 'attachment',
        render :function(v){
        	if(v){
        		return '<a href="'+v+'" target="_blank">附件</a>';
        	}
        	return '--';
        }
    },
    {
        header: '参与人员',
        width: 150,
        name: 'participants'
    },
    {
        header: '备注',
        width: 90,
        name: 'remark'
    },
    {
        header: '查看',
        name: 'id',
        width: 50,
        align: 'center',
        render: function(value) {
            return '<i data-role="detail" class="icon-list" style="cursor:pointer;"></i>';
        }
    }];

    // 计算表格高度和行数
    var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + $('.page-header').height() + 100);
    var pageSize = Math.floor(gridHeight / 21);

    /**
	 * 修改/重置按钮状态
	 */
    function changeButtonsStatus(selected, data) {
        if (selected) {
            Utils.button.enable(['edit', 'remove']);
        } else {
            Utils.button.disable(['edit', 'remove']);
        }
    }

    // 配置表格
    var defaultUrl = contextPath + operateUri + '?orderBy=id&order=desc&pageSize=' + pageSize;
    var grid = new Grid({
        parentNode: '#material-table',
        url: defaultUrl,
        model: {
            fields: fields,
            needOrder: true,
            orderWidth: 50,
            height: gridHeight
        },
        onClick: function(target, data) {
            changeButtonsStatus(this.selected, data);

            if (target.attr('data-role') === 'detail') {
                showDetail(data);
            }
        },
        onLoaded: function() {
            changeButtonsStatus();

            // 改变导出按钮状态
            if (this.data.totalCount > 0) {
                Utils.button.enable(['export']);
            } else {
                Utils.button.disable(['export']);
            }
        }
    }).render();

    // 新建
    $('#create').click(function() {
        Utils.modal.reset('create');
        Utils.modal.show('create');
    });

    // 验证
    function validate(showType, model) {
        var errorMsg = [];

        if (model.topic === '') {
            errorMsg.push('请输入主题');
        }

        if (model.content === '') {
            errorMsg.push('请输入内容');
        }

        if (model.groupEntity === '') {
            errorMsg.push('请输入班组');
        }

        if (model.participants === '') {
            errorMsg.push('请输入参与人员');
        }

        if (errorMsg.length > 0) {
            Utils.modal.message(showType, [errorMsg.join(',')]);
            return false;
        }

        return true;
    }

    // 查看
    function showDetail(data) {
        Utils.modal.reset('detail');

        var object = $.extend({}, data);
        var group=object.groupEntity;
        object.groupEntity = object.groupEntity.name;
        object.attachment = object.attachment.title;

        Utils.form.fill('detail', object);
        $('#detail_groupEntity').attr('data-id',group.id);
        $('#detail_groupEntity').val(group.name);
        Utils.modal.show('detail');
    }

    // 保存
    $('#create-save').click(function() {
        var object = Utils.form.serialize('create');
        var create_groupEntity = {id:$('#create_groupEntity').attr('data-id')};
        delete  object.groupEntity;
        object.groupEntity=create_groupEntity;
        // 验证
        if (!validate('create', object)) {
            return false;
        }

        $.post(operateUri, JSON.stringify(object),
        function(data) {
            if (data.success) {
                grid.refresh();
                Utils.modal.hide('create');
            } else {
                Utils.modal.message('create', data.errors);
            }
        });
    });

    // 编辑
    $('#edit').click(function() {
        if (Utils.button.isDisable('edit')) {
            return;
        }

        Utils.modal.reset('edit');

        var selectId = grid.selectedData('id');
        $.get(operateUri + '/' + selectId,
        function(data) {
            var object = data.data;

            var group=object.groupEntity;
            Utils.form.fill('edit', object);
            $('#edit_groupEntity').attr('data-id',group.id);
            $('#edit_groupEntity').val(group.name);
            Utils.modal.show('edit');
        });
    });

    // 更新
    $('#edit-save').click(function() {
        var object = Utils.form.serialize('edit');

        // 验证
        if (!validate('edit', object)) {
            return false;
        }

        // 处理属性
        var selectId = grid.selectedData('id');
        object.id = selectId;
        var edit_groupEntity = {id:$('#edit_groupEntity').attr('data-id')};
        delete  object.groupEntity;
        object.groupEntity=edit_groupEntity;
        $.put(operateUri + '/' + selectId, JSON.stringify(object),
        function(data) {
            if (data.success) {
                grid.refresh();
                Utils.modal.hide('edit');
            } else {
                Utils.modal.message('edit', data.errors);
            }
        });
    });

    // 删除
    $('#remove').click(function() {
        if (Utils.button.isDisable('remove')) {
            return;
        }
        Utils.modal.show('remove');
    });

    // 删除确认
    $('#remove-save').click(function() {
        var selectId = grid.selectedData('id');
        $.del(operateUri + '/' + selectId,
        function(data) {
            grid.refresh();
            Utils.modal.hide('remove');
        });
    });

    // 导出
    $('#export').click(function() {
        if (Utils.button.isDisable('export')) {
            return;
        }

        window.location.href = operateUri + '/export-excel?' + Utils.form.buildParams('search-form');
    });

    // 搜索
    $('#submit').click(function() {
        grid.set({
            url: defaultUrl + Utils.form.buildParams('search-form')
        });
    });

    // 查询条件重置
    $('#reset').click(function() {
        grid.set('url', defaultUrl);
        grid.refresh();
    });
    
    
    $('#create_attachment').click(function(){
    	Utils.modal.showUpload('/simpleupload',function(data){
    		$('#create_attachment').val(data.data);
    	},'附件上传');
    });
});