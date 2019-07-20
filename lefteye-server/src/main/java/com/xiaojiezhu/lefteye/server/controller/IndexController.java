package com.xiaojiezhu.lefteye.server.controller;

import com.xiaojiezhu.lefteye.core.model.Process;
import com.xiaojiezhu.lefteye.core.util.ProcessUtils;
import com.xiaojiezhu.lefteye.server.component.web.ResultResponseBody;
import com.xiaojiezhu.lefteye.server.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author xiaojie.zhu
 */
@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 进入首页
     * @return
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }


    /**
     * 列出所有的进程
     * @return
     */
    @ResultResponseBody
    @RequestMapping("/listProcess")
    public List<Process> listProcess(){
        return indexService.listProcess();
    }
}
