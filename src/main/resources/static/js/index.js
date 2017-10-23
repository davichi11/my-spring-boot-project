//生成菜单
let menuItem = Vue.extend({
    name: 'menu-item',
    props: {item: {}},
    template: [
    `<li> 
       <a v-if="item.type === 0" href="javascript:;"> 
            <i v-if="item.icon != null" :class="item.icon"></i>
            <span>{{item.name}}</span>
            <i class="fa fa-angle-left pull-right"></i>
       </a>
       <ul v-if="item.type === 0" class="treeview-menu">
           <menu-item :item="item" v-for="item in item.list"></menu-item>
       </ul>
       <a v-if="item.type === 1" :href="'#'+item.url">
           <i v-if="item.icon != null" :class="item.icon"></i>
           <i v-else class="fa fa-circle-o"></i> {{item.name}}
       </a>
    </li>`
    ].join('')
});

//iframe自适应
$(window).on('resize', () => {
    let $content = $('.content');
    $content.height($(this).height() - 120);
    $content.find('iframe').each(function () {
        $(this).height($content.height());
    });
}).resize();

//注册菜单组件
Vue.component('menuItem', menuItem);

let vm = new Vue({
    el: '#rrapp',
    data: {
        user: {},
        menuList: {},
        main: "main.html",
        password: '',
        newPassword: '',
        navTitle: "欢迎页"
    },
    methods: {
        getMenuList: () => {
            $.getJSON(baseURL + "sys/menu/user", function (r) {
                vm.menuList = r.menuList;
                window.permissions = r.permissions;
            });
        },
        getUser: () => {
            $.getJSON(baseURL + "sys/user/info", function (r) {
                vm.user = r.user;
            });
        },
        updatePassword: () => {
            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "修改密码",
                area: ['550px', '270px'],
                shadeClose: false,
                content: jQuery("#passwordLayer"),
                btn: ['修改', '取消'],
                btn1: function (index) {
                    let data = "password=" + vm.password + "&newPassword=" + vm.newPassword;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "sys/user/password",
                        data: data,
                        dataType: "json",
                        success: function (r) {
                            if (r.code === 0) {
                                layer.close(index);
                                layer.alert('修改成功', function () {
                                    location.reload();
                                });
                            } else {
                                layer.alert(r.msg);
                            }
                        }
                    });
                }
            });
        },
        logout: () => {
            //删除本地token
            localStorage.removeItem("token");
            //跳转到登录页面
            location.href = baseURL + 'login.html';
        }
    },
    created: function () {
        this.getMenuList();
        this.getUser();
    },
    updated: function () {
        //路由
        let router = new Router();
        routerList(router, vm.menuList);
        router.start();
    }
});


function routerList(router, menuList) {
    for (let key in menuList) {
        let menu = menuList[key];
        if (menu.type === 0) {
            routerList(router, menu.list);
        } else if (menu.type === 1) {
            router.add('#' + menu.url, function () {
                let url = window.location.hash;

                //替换iframe的url
                vm.main = url.replace('#', '');

                //导航菜单展开
                $(".treeview-menu li").removeClass("active");
                $("a[href='" + url + "']").parents("li").addClass("active");

                vm.navTitle = $("a[href='" + url + "']").text();
            });
        }
    }
}
