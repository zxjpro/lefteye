package com.xiaojiezhu.lefteye.server;

import com.xiaojiezhu.lefteye.core.util.ProcessUtils;

import java.util.ArrayList;
import java.util.List;

/**

/**
 * @program lefteye
 * @description 测试
 * @author zxj
 * @create 2019-07-20 10:17
 */
public class Demo {

    private static int targetPid = 11408;

    private static String coreJar = "G:\\code\\lefteye\\lefteye-server\\lib\\arthas-core.jar";
    private static String agentJar = "G:\\code\\lefteye\\lefteye-server\\lib\\arthas-agent.jar";

    public static void main(String[] args) {
        List<String> attachArgs = new ArrayList<String>();
        attachArgs.add("-jar");
        attachArgs.add(coreJar);
        attachArgs.add("-pid");
        attachArgs.add("" + targetPid);
        attachArgs.add("-target-ip");
        attachArgs.add("localhost");
        attachArgs.add("-telnet-port");
        attachArgs.add("3658");
        attachArgs.add("-http-port");
        attachArgs.add("8563");
        attachArgs.add("-core");
        attachArgs.add(coreJar);
        attachArgs.add("-agent");
        attachArgs.add(agentJar);

        ProcessUtils.startArthasCore(targetPid, attachArgs);

    }
}
