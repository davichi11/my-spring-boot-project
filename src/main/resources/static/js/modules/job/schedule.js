$(() => {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/schedule/list',
        datatype: "json",
        colModel: [
            {label: '任务ID', name: 'jobId', width: 60, key: true},
            {label: 'bean名称', name: 'beanName', width: 100},
            {label: '方法名称', name: 'methodName', width: 100},
            {label: '参数', name: 'params', width: 100},
            {label: 'cron表达式 ', name: 'cronExpression', width: 100},
            {label: '备注 ', name: 'remark', width: 100},
            {
                label: '状态', name: 'status', width: 60, formatter: (value, options, row) => value === 0 ?
                '<span class="label label-success">正常</span>' :
                '<span class="label label-danger">暂停</span>'
            }
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
});

let vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            beanName: null
        },
        showList: true,
        title: null,
        schedule: {}
    },
    methods: {
        query: () => vm.reload(),
        reset: () => vm.q.beanName = "",
        add: () => {
            vm.showList = false;
            vm.title = "新增";
            vm.schedule = {};
        },
        update: () => {
            let jobId = getSelectedRow();
            if (isNull(jobId)) {
                alert("请选择一个任务");
                return;
            }

            $.get(baseURL + "sys/schedule/info/" + jobId, r => {
                vm.showList = false;
                vm.title = "修改";
                vm.schedule = r.schedule;
            });
        },
        saveOrUpdate: event => {
            let url = isNull(vm.schedule.jobId) ? "sys/schedule/save" : "sys/schedule/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.schedule),
                success: r => {
                    if (r.code === 0) {
                        alert('操作成功', () => vm.reload());
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function () {
            let jobIds = getSelectedRows();
            if (isNull(jobIds)) {
                return;
            }

            confirm('确定要删除选中的记录？', () => {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/schedule/delete",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
                    success: r => {
                        if (r.code === 0) {
                            alert('操作成功', () => vm.reload());
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        pause: () => {
            let jobIds = getSelectedRows();
            if (isNull(jobIds)) {
                alert("请选择一个任务");
                return;
            }

            confirm('确定要暂停选中的记录？', () => {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/schedule/pause",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
                    success: r => {
                        if (r.code === 0) {
                            alert('操作成功', () => {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        resume: () => {
            let jobIds = getSelectedRows();
            if (isNull(jobIds)) {
                alert("请选择一个任务");
                return;
            }

            confirm('确定要恢复选中的记录？', () => {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/schedule/resume",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
                    success: r => {
                        if (r.code === 0) {
                            alert('操作成功', () => vm.reload());
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        runOnce: () => {
            let jobIds = getSelectedRows();
            if (isNull(jobIds)) {
                alert("请选择一个任务");
                return;
            }

            confirm('确定要立即执行选中的记录？', () => {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/schedule/run",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
                    success: r => {
                        if (r.code === 0) {
                            alert('操作成功', () => vm.reload());
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        reload: event => {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'beanName': vm.q.beanName},
                page: page
            }).trigger("reloadGrid");
        },
        cron: () => {
            layer.open({
                skin: 'layui-layer-molv',
                title: "在线cron表达式",
                area: ['750px', '570px'],
                type: 2,
                shadeClose: true,
                shade: 0.8,
                content: 'http://cron.qqe2.com'

            })
        }
    }
});