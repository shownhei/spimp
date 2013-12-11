define(function (require, exports, module) {
    var $ = require('kjquery'), Grid = require('grid'), Utils = require('../../../common/utils');

    // 提示信息
    $('button[title]').tooltip({
        placement: 'bottom'
    });

    // 配置表格列
    var fields = [
        {
            header: '计划名称',
            name: 'name'
        },
        {
            header: '分类',
            name: 'category'
        },
        {
            header: '来源',
            name: 'origin'
        },
        {
            header: '下载',
            name: 'id',
            width: 50,
            align: 'center',
            render: function (value) {
                return '<i data-role="view" class="icon-list" style="cursor:pointer;"></i>';
            }
        }
    ];

    // 计算表格高度和行数
    var gridHeight = $(window).height() - ($('.navbar').height() + $('.page-toolbar').height() + 84);
    var pageSize = Math.floor(gridHeight / GRID_ROW_HEIGHT);

    /**
     * 修改/重置按钮状态
     */
    function changeButtonsStatus(selected, data) {
        if (selected) {
            Utils.button.enable([ 'edit', 'remove' ]);
        } else {
            Utils.button.disable([ 'edit', 'remove' ]);
        }
    }

    // 配置表格
    var defaultUrl = contextPath + '/spmi/daily/trainings?orderBy=id&order=desc&pageSize=' + pageSize;
    var grid = new Grid({
        parentNode: '#records',
        url: defaultUrl,
        model: {
            fields: fields,
            needOrder: true,
            orderWidth: 50,
            height: gridHeight
        },
        onClick: function (target, data) {
            changeButtonsStatus(this.selected, data);
        },
        onLoaded: function () {
            changeButtonsStatus();
        }
    }).render();

    // 新建
    $('#create').click(function () {
        Utils.modal.reset('create');
        Utils.modal.show('create');
    });

    // 保存
    $('#create-save').click(function () {
        var object = Utils.form.serialize('create');

        // 验证

        // 处理属性

        $.post(contextPath + '/spmi/daily/trainings', JSON.stringify(object), function (data) {
            if (data.success) {
                grid.refresh();
                Utils.modal.hide('create');
            } else {
                Utils.modal.message('create', data.errors);
            }
        });
    });

    // 编辑
    $('#edit').click(function () {
        if (Utils.button.isDisable('edit')) {
            return;
        }

        Utils.modal.reset('edit');

        var selectId = grid.selectedData('id');
        $.get(contextPath + '/spmi/daily/trainings/' + selectId, function (data) {
            var object = data.data;

            Utils.form.fill('edit', object);
            Utils.modal.show('edit');
        });
    });

    // 更新
    $('#edit-save').click(function () {
        var object = Utils.form.serialize('edit');

        // 处理属性
        var selectId = grid.selectedData('id');
        object.id = selectId;

        $.put(contextPath + '/spmi/daily/trainings/' + selectId, JSON.stringify(object), function (data) {
            if (data.success) {
                grid.refresh();
                Utils.modal.hide('edit');
            } else {
                Utils.modal.message('edit', data.errors);
            }
        });
    });

    // 删除
    $('#remove').click(function () {
        if (Utils.button.isDisable('remove')) {
            return;
        }

        Utils.modal.show('remove');
    });

    // 删除确认
    $('#remove-save').click(function () {
        var selectId = grid.selectedData('id');
        $.del(contextPath + '/spmi/daily/trainings/' + selectId, function (data) {
            grid.refresh();
            Utils.modal.hide('remove');
        });
    });

    // 搜索
    $('#nav-search-button').click(function () {
        grid.set('url', defaultUrl + Utils.form.buildParams('search-form'));
    });
});
