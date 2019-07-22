package com.xiaojiezhu.lefteye.server.controller;/**
 * @author xiaojie.zhu
 */

import com.xiaojiezhu.lefteye.core.exception.CommandException;
import com.xiaojiezhu.lefteye.server.component.web.ResultResponseBody;
import com.xiaojiezhu.lefteye.server.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @program: lefteye
 * @description:
 * @author: zhu.xiaojie
 * @create: 2019-07-20 17:00
 */
@RequestMapping("/process")
@Controller
public class ProcessController {

    @Autowired
    private ProcessService processService;



    /**
     * 进入到一个进程的页面
     * @param pid
     * @return
     */
    @RequestMapping("/process/{pid}")
    public String process(@PathVariable("pid") int pid , @RequestParam("name") String name , Map<String , Object> data) throws IOException {
        processService.attachProcess(pid);
        data.put("pid" , pid);
        data.put("name" , name);
        return "process";
    }


    /**
     * 列出 java 下的包
     *
     * @param parent 父包
     * @return
     */
    @ResultResponseBody
    @RequestMapping("/listPackage/{pid}")
    public List<String> listPackage(@PathVariable("pid") String pid, @RequestParam("parent") String parent, Map<String, Object> data) throws CommandException {
        return processService.queryPackage(pid, parent);
    }


    /**
     * 进行方法跟踪
     * @return
     */
    @ResultResponseBody
    @RequestMapping("/trace/{pid}")
    public String trace(@PathVariable("pid") int pid , @RequestParam("method") String method) throws CommandException {
        return processService.trace(pid , method);
    }


    /**
     * 反编译
     * @param pid
     * @param className
     * @return
     */
    @ResultResponseBody
    @RequestMapping("/deCompile/{pid}")
    public String deCompile(@PathVariable("pid") int pid , @RequestParam("className") String className) throws CommandException {
        return processService.deCompile(pid ,className);
    }


    /**
     * 显示缓冲流信息
     * @param pid
     * @return
     */
    @ResultResponseBody
    @RequestMapping("/showStream/{pid}")
    public String showStream(@PathVariable("pid") String pid){
        return processService.showStream(pid);
    }

}
