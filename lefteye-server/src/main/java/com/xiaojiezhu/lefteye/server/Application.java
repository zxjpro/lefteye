package com.xiaojiezhu.lefteye.server;

import com.xiaojiezhu.lefteye.core.Constant;
import com.xiaojiezhu.lefteye.core.util.StringUtil;
import com.xiaojiezhu.lefteye.server.util.FileStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.io.IOException;

/**
 * @author xiaojie.zhu
 */
@ServletComponentScan
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        checkParam();
        SpringApplication.run(Application.class , args);
    }


    private static void checkParam(){
        String property = System.getProperty(Constant.WORK_DIR);
        if(property == null){
            System.err.println(String.format("启动参数 %s 不存在" , Constant.WORK_DIR));
            System.exit(1);
        }

    }
}
