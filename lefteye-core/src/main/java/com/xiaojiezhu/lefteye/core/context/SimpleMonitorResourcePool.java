package com.xiaojiezhu.lefteye.core.context;

import com.xiaojiezhu.lefteye.core.cmd.CommandExecutor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaojie.zhu
 * time 2019-07-20 23:07
 */
public class SimpleMonitorResourcePool implements MonitorResourcePool {

    private final ConcurrentHashMap<String  , CommandExecutor> pool = new ConcurrentHashMap<>();

    @Override
    public synchronized void put(String pid, CommandExecutor commandExecutor) {
        if(pool.get(pid) != null){
            throw new RuntimeException(String.format("进程 [%s] 的命令执行器已存在" , pid));
        }
        this.pool.put(pid , commandExecutor);
    }

    @Override
    public synchronized CommandExecutor get(String pid) {
        return pool.get(pid);
    }

    @Override
    public void remove(String pid) {
        this.pool.remove(pid);
    }
}
