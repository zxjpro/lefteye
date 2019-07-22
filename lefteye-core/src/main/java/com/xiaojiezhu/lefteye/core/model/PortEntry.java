package com.xiaojiezhu.lefteye.core.model;

/**
 * @author xiaojie.zhu
 * time 2019-07-21 00:00
 */
public class PortEntry {

    private int socketPort;

    private int httpPort;


    public PortEntry(int socketPort, int httpPort) {
        this.socketPort = socketPort;
        this.httpPort = httpPort;
    }

    public PortEntry() {
    }

    public int getSocketPort() {
        return socketPort;
    }

    public void setSocketPort(int socketPort) {
        this.socketPort = socketPort;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }
}
