$(() => {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/role/list',
        datatype: "json",
        colModel: [
            {label: '角色ID', name: 'roleId', index: "role_id", width: 45, key: true},
            {label: '角色名称', name: 'roleName', index: "role_name", width: 75},
            {label: '备注', name: 'remark', width: 100},
            {label: '创建时间', name: 'createTime', index: "create_time", width: 80}
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

let setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "menuId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    },
    check: {
        enable: true,
        nocheckInherit: true
    }
};
let ztree;

let vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            roleName: null
        },
        showList: true,
        title: null,
        role: {}
    },
    methods: {
        query: () => vm.reload(),
        reset: () => vm.q.roleName = "",
        add: () => {
            vm.showList = false;
            vm.title = "新增";
            vm.role = {};
            vm.getMenuTree(null);
        },
        update: () => {
            let roleId = getSelectedRow();
            if (roleId === null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";
            vm.getMenuTree(roleId);
        },
        del: event => {
            let roleIds = getSelectedRows();
            if (roleIds === null) {
                return;
            }

            confirm('确定要删除选中的记录？', () => {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/role/delete",
                    contentType: "application/json",
                    data: JSON.stringify(roleIds),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', index => {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getRole: roleId => {
            $.get(baseURL + "sys/role/info/" + roleId, function (r) {
                vm.role = r.role;

                //勾选角色所拥有的菜单
                let menuIds = vm.role.menuIdList;
                for (let i = 0; i < menuIds.length; i++) {
                    let node = ztree.getNodeByParam("menuId", menuIds[i]);
                    ztree.checkNode(node, true, false);
                }
            });
        },
        saveOrUpdate: () => {
            //获取选择的菜单
            let nodes = ztree.getCheckedNodes(true);
            let menuIdList = [];
            for (let i = 0; i < nodes.length; i++) {
                menuIdList.push(nodes[i].menuId);
            }
            vm.role.menuIdList = menuIdList;

            let url = vm.role.roleId === null ? "sys/role/save" : "sys/role/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.role),
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
        getMenuTree: roleId => {
            //加载菜单树
            $.get(baseURL + "sys/menu/perms", function (r) {
                ztree = $.fn.zTree.init($("#menuTree"), setting, r.menuList);
                //展开所有节点
                ztree.expandAll(true);

                if (roleId !== null) {
                    vm.getRole(roleId);
                }
            });
        },
        reload: () => {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'roleName': vm.q.roleName},
                page: page
            }).trigger("reloadGrid");
        }
    }
});