require.config({
    baseUrl: 'static/js/',
    path: {
        jquery: 'libs/jquery.min',
        vue: 'libs/vue.min',
        jqGrid: 'plugins/jqgrid/jquery.jqGrid.min',
        router: 'libs/router',
        bootstrap3: 'libs/bootstrap.min',
        bootstrapTable: 'libs/bootstrap-table.min',
        app: 'libs/app',
        layer: 'plugins/layer/layer',
        zTree: 'plugins/ztree/jquery.ztree.all.min',
        locale: 'plugins/jqgrid/grid.locale-cn',
        treeGrid: 'plugins/treegrid/jquery.treegrid.min',
        treegrid_bootstrap3: 'plugins/treegrid/jquery.treegrid.bootstrap3',
        treegrid_extension: 'plugins/treegrid/jquery.treegrid.extension',
        tree_table: 'plugins/treegrid/tree.table',
        AjaxUpload: 'libs/ajaxupload',
        FastClick: 'libs/fastclick.min',
        slimScroll:'libs/jquery.slimscroll.min',
        common:'js/common.js',
        index:'js/index'
    },
    shim:{
        'slimScroll':{
            deps:['jquery'],
            exports:'slimScroll'
        },
        'app':{
            deps:['jquery'],
            exports:'app'
        },
        'layer':{
            deps:['jquery'],
            exports:'layer'
        },
        'treeGrid':{
            deps:['jquery'],
            exports:'treeGrid'
        },
        'zTree':{
            deps:['jquery'],
            exports:'zTree'
        },
        'common':{
            deps:['jquery','vue','jqGrid','layer'],
            exports:'common'
        },
        'index':{
            deps:['jquery','vue','jqGrid','layer'],
            exports:'index'
        }

    }
});

