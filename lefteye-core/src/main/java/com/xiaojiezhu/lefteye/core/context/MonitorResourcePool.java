package com.xiaojiezhu.lefteye.core.context;

import com.xiaojiezhu.lefteye.core.cmd.CommandExecutor;

/**
 * 监控池
 * @author xiaojie.zhu
 * time 2019-07-20 23:04
 */
public interface MonitorResourcePool {


    /**
     * put 存入一个 进程 的命令执行器 到资源池中
     * @param pid 进程的PID
     * @param commandExecutor 执行器
     */
    void put(String pid , CommandExecutor commandExecutor);

    /**
     * 根据 进程 Pid 获取一个命令执行器
     * @param pid
     * @return
     */
    CommandExecutor get(String pid);

    /**
     * 移除
     * @param pid
     */
    void remove(String pid);
}
