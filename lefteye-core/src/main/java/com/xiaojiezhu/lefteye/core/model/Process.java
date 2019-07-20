package com.xiaojiezhu.lefteye.core.model;/**
 * @author xiaojie.zhu
 */

/**
 * @program: lefteye
 * @description:
 * @author: zhu.xiaojie
 * @create: 2019-07-20 16:11
 */
public class Process {

    private int pid;

    /**
     * 进程名
     */
    private String name;


    /**
     * 程序路径
     */
    private String path;

    /**
     * 所在主机
     */
    private String host;

    /**
     * 监控信息端口
     */
    private int port;


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
