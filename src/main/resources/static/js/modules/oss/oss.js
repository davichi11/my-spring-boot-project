$(() => {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/oss/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', width: 20, key: true},
            {label: 'URL地址', name: 'url', width: 160},
            {label: '创建时间', name: 'createDate', width: 40}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.pageNum",
            total: "page.pageSize",
            records: "page.total"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: () => {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    new AjaxUpload('#upload', {
        action: baseURL + 'sys/oss/upload?token=' + token,
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: (file, extension) => {
            if (vm.config.type === null) {
                alert("云存储配置未配置");
                return false;
            }
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、png、gif格式的图片！');
                return false;
            }
        },
        onComplete: (file, r) => {
            if (r.code === 0) {
                alert(r.url);
                vm.reload();
            } else {
                alert(r.msg);
            }
        }
    });

});

let vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        config: {}
    },
    created: () => {
        this.getConfig();
    },
    methods: {
        query: () => {
            vm.reload();
        },
        getConfig: () => {
            $.getJSON(baseURL + "sys/oss/config", function (r) {
                vm.config = r.config;
            });
        },
        addConfig: () => {
            vm.showList = false;
            vm.title = "云存储配置";
        },
        saveOrUpdate: () => {
            let url = baseURL + "sys/oss/saveConfig";
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/json",
                data: JSON.stringify(vm.config),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: () => {
            let ossIds = getSelectedRows();
            if (ossIds === null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/oss/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ossIds),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function () {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        reload: () => {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
});