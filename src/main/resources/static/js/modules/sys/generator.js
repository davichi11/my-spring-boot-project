$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/generator/list',
        datatype: "json",
        colModel: [
            {label: '表名', name: 'tableName', width: 100, key: true},
            {label: 'Engine', name: 'engine', width: 70},
            {label: '表备注', name: 'tableComment', width: 100},
            {label: '创建时间', name: 'createTime', width: 100}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50, 100, 200],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.pageNum",
            total: "page.pages",
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
});

let vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            tableName: null
        }
    },
    methods: {
        query: () => {
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'tableName': vm.q.tableName},
                page: 1
            }).trigger("reloadGrid");
        },
        generator: () => {
            let tableNames = getSelectedRows();
            if (isNull(tableNames)) {
                alert("请选择一项");
                return;
            }
            $.ajax({
                type: "GET",
                url: baseURL + "sys/generator/code",
                contentType: "application/json",
                data: {'tables': JSON.stringify(tableNames)},
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', () => vm.reload());
                    } else {
                        alert(r.msg);
                    }
                }
            })
        }
    }
});

