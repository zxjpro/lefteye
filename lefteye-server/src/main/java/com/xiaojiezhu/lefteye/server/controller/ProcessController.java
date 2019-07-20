package com.xiaojiezhu.lefteye.server.controller;/**
 * @author xiaojie.zhu
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: lefteye
 * @description:
 * @author: zhu.xiaojie
 * @create: 2019-07-20 17:00
 */
@RequestMapping("/process")
@Controller
public class ProcessController {


    /**
     * 进入到一个进程的页面
     * @param pid
     * @return
     */
    @RequestMapping("/process/{pid}")
    public String process(@PathVariable("pid") int pid){
        return "process";
    }

}
