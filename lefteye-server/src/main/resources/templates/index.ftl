<!DOCTYPE html>
<#assign path="${request.contextPath}" />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>左眼</title>
    <!-- 引入样式 -->
    <link rel="stylesheet" href="${path}/css/common.css">
    <style>
        [v-cloak] {
            display: none;
        }

        .container{margin-top: 50px;}

        .process{display: flex;    justify-content: space-around;margin-top: 1rem;}
        .left , .right{width: 48%;}
        .box{border:1px solid #ccc;min-height: 300px;}
        .box-header{height: 50px;    line-height: 50px;padding-left: 1rem;padding-right:1rem;font-size: 1rem;}
        .box-header span{color: #333333;}
        .box-header-all{background: #dfe8ec;}
        .box-header-monitor{background: #e6c6a4;display:flex;justify-content: space-between;}
        .remove-monitor{cursor: pointer;color: #0f6dbd !important;}
        .remove-monitor:hover{color: #0f6dbd !important;}
        .ip{margin-left: 1.4rem;font-size: 1.3rem;margin-top: 5rem;}
        .java-icon{width: 60px;height: 60px;}
        .process-item{display: flex;    background: #f7f7f7;cursor: pointer;
            padding: 0.8rem;margin-bottom: 0.4rem;
            border-left: 5px solid #2cb1d0;}
        .process-item:hover{background: #cee1f5;}
        .process-item .pid{font-size: 1.3rem;}
        .process-item .name{font-size: 1.3rem;margin-left: 1rem;}
        .process-item .desc{color: #666666;margin-top: 0.4rem;}
        .process-item .item-info{margin-left: 1rem;}
        .box-body{padding: 1rem;}
    </style>
</head>
<body>

    <div id="app" v-cloak>

        <#include "include/header.ftl" />

        <div class="container">

            <div class="ip">IP: 192.168.1.1</div>

            <div class="process">

                <!-- 显示所有的进程 -->
                <div class="left box">
                    <div class="box-header box-header-all">
                        <span>全部进程</span>
                    </div>
                    <div class="box-body">

                        <div class="process-item" v-for="item in processList" @click="clickProcess(item)">
                            <img class="java-icon" src="img/java.png">
                            <div class="item-info">
                                <div>
                                    <span class="pid">{{item.pid}}</span>
                                    <span class="name">{{item.name}}</span>
                                </div>
                                <div class="desc">
                                    <span class="">{{item.path}}</span>
                                </div>
                            </div>

                        </div>

                    </div>
                </div>


                <!-- 显示已监控的进程 -->
                <div class="right box">
                    <div class="box-header box-header-monitor">
                        <span>监控进程</span>
                        <a href="#" class="remove-monitor">清除所有监控</a>
                    </div>
                    <div class="box-body">

                    </div>
                </div>

            </div>

        </div>
    </div>

</body>
<script src="${path}/js/vue.min.js"></script>
<script src="${path}/js/axios.min.js"></script>
<script src="${path}/js/http.js"></script>
<script>
    new Vue({
        el: "#app",
        data: {
            processList: []
        },
        methods : {

            // 加载所有进程
            loadAllProcess : function () {
                var that = this
                Http.get('${path}/listProcess').then(function (data) {
                    that.processList = data
                })
            },

            clickProcess : function(item){
                window.open('${path}/process/process/' + item.pid)
            }

        },
        mounted: function(){
            // 加载所有进程
            this.loadAllProcess()
        }
    })
</script>
</html>