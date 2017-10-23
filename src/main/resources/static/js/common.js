//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost/index.html?id=123
// T.p('id') --> 123;
let url = function (name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if (r !== null) {
        return decodeURI(r[2]);
    }
    return null;
};
T.p = url;

//请求前缀
//var baseURL = "http://demo.open.renren.io/renren-fast/";
let baseURL = "/renren-fast/";
// let baseURL = "/";

//登录token
let token = localStorage.getItem("token");
if (token === 'null') {
    parent.location.href = baseURL + 'login.html';
}

//jquery全局配置
$.ajaxSetup({
    dataType: "json",
    cache: false,
    headers: {
        "token": token
    },
    complete: function (xhr) {
        //token过期，则跳转到登录页面
        if (xhr.responseJSON.code === 401) {
            parent.location.href = baseURL + 'login.html';
        }
    }
});

//jqgrid全局配置
$.extend($.jgrid.defaults, {
    ajaxGridOptions: {
        headers: {
            "token": token
        }
    }
});

//权限判断
function hasPermission(permission) {
    return window.parent.permissions.indexOf(permission) > -1;
}

//重写alert
window.alert = function (msg, callback) {
    parent.layer.alert(msg, function (index) {
        parent.layer.close(index);
        if (typeof(callback) === "function") {
            callback("ok");
        }
    });
};

//重写confirm式样框
window.confirm = function (msg, callback) {
    parent.layer.confirm(msg, {btn: ['确定', '取消']},
        function () {//确定事件
            if (typeof(callback) === "function") {
                callback("ok");
            }
        });
};

//选择一条记录
function getSelectedRow() {
    let grid = $("#jqGrid");
    let rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    let selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        alert("只能选择一条记录");
        return;
    }

    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    let grid = $("#jqGrid");
    let rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    return grid.getGridParam("selarrrow");
}

/**
 * 判断参数是否为空,null或undefined
 * @param param
 * @returns {boolean}
 */
function isNull(param) {
    return param === null || param === "undefined" || param === "" || param === undefined;
}

/**
 * 判断参数是否不为空,null或undefined
 * @param param
 * @returns {boolean}
 */
function isNotNull(param) {
    return !isNull(param);
}