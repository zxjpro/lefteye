<!DOCTYPE html>
<#assign path="${request.contextPath}" />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>左眼</title>
    <!-- 引入样式 -->
    <link rel="stylesheet" href="${path}/css/element.css">
    <link rel="stylesheet" href="${path}/css/common.css">
    <link rel="stylesheet" href="${path}/css/vue.tree.css">
    <style>
        [v-cloak] {
            display: none;
        }

        .container{margin-top: 50px;}

    </style>
</head>
<body>

<div id="app" v-cloak>

        <#include "include/header.ftl" />

    <div class="container">
        <h1>hello</h1>
        <v-tree ref='tree' :data='treeData' :multiple="true"
                :halfcheck='true' :async="true" :loading="true"
                @async-load-nodes="loadChildNode"
        ></v-tree>


        <el-button>12</el-button>
    </div>
</div>

</body>
<script src="${path}/js/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="${path}/js/vue.tree.js"></script>
<script src="${path}/js/element.js"></script>
<script src="${path}/js/axios.min.js"></script>
<script src="${path}/js/http.js"></script>
<script>
    Vue.component('v-tree' , VTree.VTree)
    new Vue({
        el: "#app",
        data: {

            treeData: [{
                title: '一级节点',
                expanded: true,
                children: [{
                    title: '二级节点1',
                    expanded: true,
                    children: [{
                        title: '三级节点1-1',
                        id: 'xxx',
                        async: true
                    }, {
                        title: '三级节点1-2'
                    }, {
                        title: '三级节点1-3'
                    }]
                }, {
                    title: '二级节点2',
                    children: [{
                        title: "<span style='color: red'>三级节点2-1</span>"
                    }, {
                        title: "<span style='color: red'>三级节点2-2</span>"
                    }]
                }]
            }]
        },
        methods : {

            loadChildNode : function(node){
                var list = [{
                    title: 'xxx',
                    id: 'aaa',
                    async: true
                }, {
                    title: '999'
                }]

                if (!node.hasOwnProperty('children')) {
                    this.$set(node, 'children', [])
                }

                for(var i = 0 ; i < list.length ; i ++){
                    node.children.push(list[i])
                }

            }

        },
        mounted: function(){
        }
    })
</script>
</html>