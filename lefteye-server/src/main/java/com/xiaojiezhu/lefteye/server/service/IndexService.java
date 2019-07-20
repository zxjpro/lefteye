package com.xiaojiezhu.lefteye.server.service;/**
 * @author xiaojie.zhu
 */

import com.xiaojiezhu.lefteye.core.model.Process;

import java.util.List;

/**
 * @program: lefteye
 * @description:
 * @author: zhu.xiaojie
 * @create: 2019-07-20 16:14
 */
public interface IndexService {

    /**
     * 列出所有的进程
     * @return
     */
    List<Process> listProcess();
}
