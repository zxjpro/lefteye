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
    <link rel="stylesheet" type="text/css" href="${path}/js/highlight/styles/atom-one-dark.css"/>
    <style>
        [v-cloak] {
            display: none;
        }
        *{padding:0;margin:0;}
        html,body,#app{width:100%;height: 100%;background: #f6f7f9;}
        .halo-tree .inputCheck{display: none !important;}
        .package-img{width: 18px;height: 16px;}
        .tree-expand{position: relative;}
        .tree-expand ++ span{color: #0f6dbd;}
        .container{margin-top: 50px;height: 100%;}
        .title{font-size: 1.4rem;}
        .co{display: flex;height: 100%;}
        .package{width: 20rem;overflow-x: scroll;background: #fff;
            border: 1px solid #eee;}
        .dashboard{margin-left: 1rem;    width: 100%;}
        .input-dev{margin-top: 0.5rem;}
        .input{width: 100%;}
        .outdiv{margin-top: 1rem;height: 100%;}
        .output{width: 100%;height:82%;resize: none;    background: #f6f7f9;border: 1px solid #e4e2e2;
            font-size: 16px;}
        .output-color{color: #1985ab;}
        .active{background: #409EFF; color: #fff;}

    </style>
</head>
<body>

<div id="app" v-cloak>

        <#include "include/header.ftl" />

    <div class="container">
        <div style="height: 0.5rem;"></div>
        <div class="title">
            <p>
                <span>${name}</span>
            </p>
        </div>

        <div class="co">

            <div class="package">

                <div style="width: 150%;">
                    <v-tree ref='tree' :data='treeData' :multiple="true" :radio="true"
                            :halfcheck='false' :async="true" :loading="true" @node-click="nodeClick"
                            @async-load-nodes="loadChildNode"
                    ></v-tree>
                </div>


            </div>

            <div class="dashboard">

                <div>
                    <el-button-group>
                        <el-button :class="{active : 'dashboard' == activeBtn}">仪表</el-button>
                        <el-button :class="{active : 'jvm' == activeBtn}">启动参数</el-button>
                        <el-button :class="{active : 'gc' == activeBtn}">GC</el-button>
                        <el-button :class="{active : 'traceMethod' == activeBtn}" @click="traceMethod">方法监控</el-button>
                        <el-button :class="{active : 'deCompile' == activeBtn}" @click="deCompile">反编译</el-button>
                        <el-button :class="{active : 'heightCpu' == activeBtn}">CPU高耗线程</el-button>
                        <el-button @click="test">测试</el-button>
                    </el-button-group>
                </div>
                <div class="input-dev">
                    <el-input v-model="input" :disabled="true" class="input">
                </div>

                <div class="outdiv">
                    <textarea v-if="'traceMethod' == activeBtn" id="traceMethodArea" class="output output-color" disabled="disabled">
                    </textarea>


                    <div v-if="'deCompile' == activeBtn" id="deCompile" class="output">
                        <pre><code class="java" id="de-code"></code></pre>
                    </div>
                </div>

            </div>

        </div>
        

    </div>
</div>

</body>
<script src="${path}/js/vue.min.js"></script>
<!-- 引入组件库 -->
<script src="${path}/js/vue.tree.js"></script>
<script src="${path}/js/element.js"></script>
<script src="${path}/js/axios.min.js"></script>
<script src="${path}/js/http.js"></script>
<script src="${path}/js/highlight/highlight.pack.js"></script>
<script>
    Vue.component('v-tree' , VTree.VTree)
    new Vue({
        el: "#app",
        data: {

            treeData: [],
            input: '',
            activeBtn: 'dashboard'
        },
        methods : {

            test : function(){
                var cmd = "trace com.xiaojiezhu.lefteye.server.test.MathGame run"
                var that = this
                Http.get('${path}/process/trace/${pid}?method=' + encodeURIComponent(cmd))
                    .then(function (data) {
                        that.showStream()
                    })
            },

            /**
             * 反编译
             */
            deCompile : function(){
                if(!this.input.endsWith('.java')){
                    alert('请选择一个 java 类')
                    return
                }
                this.activeBtn = 'deCompile'
                var className = encodeURIComponent(this.input)
                Http.get('${path}/process/deCompile/${pid}?className=' + className).then(function(data){
                    var dom = document.getElementById('de-code');
                    dom.innerHTML = data

                    document.querySelectorAll('pre code').forEach((block) => {
                        hljs.highlightBlock(block);
                });
                })
            },
            /**
             * 监控方法
             */
            traceMethod : function(){
                if(!this.input.endsWith(")")){
                    alert("请选择一个方法")
                    return
                }
                this.activeBtn = 'traceMethod'

                var that = this
                Http.get('${path}/process/trace/${pid}?method=' + encodeURIComponent(this.input))
                    .then(function (data) {
                        that.showStream()
                    })
            },

            showStream : function(){
                setInterval(function(){
                    Http.get('${path}/process/showStream/${pid}').then(function(data){
                        var output = document.getElementById("traceMethodArea");
                        output.innerHTML = output.innerHTML + data
                        output.scrollTop = output.scrollHeight
                    })
                } , 1000)

            },

            nodeClick : function(node){
                this.input = node.id;
            },

            loadChildNode : function(node){
                var parent = node.id
                var that = this
                this.loadNode(node.id).then(function(data){

                    if (!node.hasOwnProperty('children')) {
                        that.$set(node, 'children', [])
                    }

                    var list = []
                    for(var i = 0 ; i < data.length ; i ++){
                        var item = data[i]
                        var id = item

                        var title = item;
                        if(!parent.endsWith(".java")){
                            title = title.substring(node.id.length)

                        }
                        title = that.renderPackage(title)

                        var async = false
                        if(item.endsWith(".") || item.endsWith(".java")){
                            async = true
                        }
                        if(item.endsWith(")")){
                            id = parent + " " + id;
                        }


                        list.push({
                            title: title,
                            id: id ,
                            async: async
                        })
                    }

                    for(var i = 0 ; i < list.length ; i ++){
                        node.children.push(list[i])
                    }

                })



            },

            renderPackage : function(title){
                if(title.endsWith(".")){
                    title = "<img class='package-img' src='${path}/img/package.svg'/> <span style='color:#d0881c'>"+title+"<span>"
                }else if(title.endsWith(".java")){
                    title = "<img class='package-img' src='${path}/img/class.svg'/> <span style='color:#13619e'>"+title+"<span>"
                }else{
                    title = "<img class='package-img' src='${path}/img/method.svg'/> <span style='color:#37940e'>"+title+"<span>"
                }
                return title;
            },

            loadRootNode : function(){
                var that = this
                this.loadNode("").then(function(data){
                    var list = []
                    for(var i = 0 ; i < data.length ; i ++){
                        var item = data[i]
                        var title = that.renderPackage(item)

                        list.push({
                            title: title,
                            id: item ,
                            async: item.endsWith("."),
                        })
                    }
                    that.treeData = list
                })
            },

            loadNode : function (parent) {
                parent = encodeURIComponent(parent)
                var p = new Promise(function (resolve, reject) {
                    Http.get('${path}/process/listPackage/${pid}?parent=' + parent).then(function(data){
                        resolve(data)
                    })
                })
                return p;

            }

        },
        mounted: function(){
            this.loadRootNode()
        }
    })
</script>
</html>