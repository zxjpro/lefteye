package com.xiaojiezhu.lefteye.core.cmd;/**
 * @author xiaojie.zhu
 */

import com.xiaojiezhu.lefteye.core.exception.CommandException;

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
    String enter();

    /**
     * 建议
     * @return
     */
    String suggest(String cmd) throws CommandException;
}
