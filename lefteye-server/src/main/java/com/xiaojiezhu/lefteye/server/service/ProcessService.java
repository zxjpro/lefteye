package com.xiaojiezhu.lefteye.server.service;

import com.xiaojiezhu.lefteye.core.exception.CommandException;

import java.io.IOException;
import java.util.List;

/**
 * @author xiaojie.zhu
 * time 2019-07-20 22:57
 */
public interface ProcessService {

    /**
     * 列出该包下面所有的子包
     * @param parent
     * @return
     */
    List<String> queryPackage(String pid , String parent) throws CommandException;

    /**
     * 对一个进程，进行字节码增加
     * @param pid
     */
    void attachProcess(int pid) throws IOException;

    /**
     * 方法监控
     * @param method
     * @return
     */
    String trace(int pid , String method) throws CommandException;

    /**
     * 显示当前 stream 的缓冲流
     * @param pid
     * @return
     */
    String showStream(String pid);

    /**
     * 反编译类
     * @param pid
     * @param className 全类名
     * @return
     */
    String deCompile(int pid, String className) throws CommandException;
}
