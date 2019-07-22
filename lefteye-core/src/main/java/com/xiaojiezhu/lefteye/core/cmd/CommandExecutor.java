package com.xiaojiezhu.lefteye.core.cmd;/**
 * @author xiaojie.zhu
 */

import com.xiaojiezhu.lefteye.core.exception.CommandException;

import java.io.IOException;
import java.util.List;

/**
 * @program: lefteye
 * @description: 运行命令
 * @author: zhu.xiaojie
 * @create: 2019-07-20 10:48
 */
public interface CommandExecutor {

    /**
     * 清除字节码增强的功能
     */
    void reset() throws CommandException;

    /**
     * 关闭服务端
     */
    void shutdown() throws CommandException;

    /**
     * 退出客户端
     */
    void quit() throws CommandException;

    /**
     * 中断当前操作
     */
    void breakc() throws CommandException;

    /**
     * 输出一个回车键
     */
    void enter() throws CommandException;


    /**
     * 隐出包名
     * @param parent 父包，如果为空字符，则列根包
     * @return
     */
    List<String> listPackage(String parent) throws CommandException;

    /**
     * 进行方法的监控，这是一个流返回，所以返回的是一个任务ID，任借任务ID，可以获取该流的输出结果
     * @param method 需要监控的方法
     * @return
     */
    String trace(String method) throws CommandException;

    /**
     * 加载 stream 的 buffer
     * @return
     */
    String loadStreamBuffer();

    /**
     * 反编译类名
     * @param className 全类名
     * @return
     */
    String deCompile(String className) throws CommandException;
}
