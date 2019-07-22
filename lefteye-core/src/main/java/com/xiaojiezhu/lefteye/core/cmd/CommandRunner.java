package com.xiaojiezhu.lefteye.core.cmd;/**
 * @author xiaojie.zhu
 */

import com.xiaojiezhu.lefteye.core.exception.CommandException;

import java.io.IOException;

/**
 * @program: lefteye
 * @description: 命令执行
 * @author: zhu.xiaojie
 * @create: 2019-07-20 10:51
 */
public interface CommandRunner {


    /**
     * 执行命令，一般情况下是带有回车的执行
     * @param cmd 这里不需要包含回车符
     * @return
     */
    String execute(String cmd) throws CommandException;


    /**
     * 输入命令，但是不会带有回车符
     * @param cmd 命令
     * @return
     */
    String write(String cmd) throws CommandException;

    /**
     * 输入回车符
     * @return
     */
    String enter() throws CommandException;

    /**
     * 建议
     * @return
     */
    String suggest(String cmd) throws CommandException;

    /**
     * 读取建议，
     * @param cmd 基本命令
     * @param timeoutms 超时时间
     * @return
     */
    String suggest(String cmd , int timeoutms) throws CommandException;

    /**
     * 进入 stream , stream 模式最长可保持5分钟
     * @param cmd
     * @param messageListener
     */
    void stream(String cmd , MessageListener messageListener) throws CommandException;

    /**
     * 清除 socket 缓冲区未读取的内容
     */
    void clear() throws CommandException;

    /**
     * 关掉客户端，释放本地资源
     */
    void close();
}
