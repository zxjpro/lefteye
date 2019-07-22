package com.xiaojiezhu.lefteye.server.service.impl;

import com.xiaojiezhu.lefteye.core.Constant;
import com.xiaojiezhu.lefteye.core.cmd.CommandExecutor;
import com.xiaojiezhu.lefteye.core.cmd.SocketCommandExecutor;
import com.xiaojiezhu.lefteye.core.exception.CommandException;
import com.xiaojiezhu.lefteye.core.model.PortEntry;
import com.xiaojiezhu.lefteye.core.util.NetUtil;
import com.xiaojiezhu.lefteye.core.util.ProcessUtils;
import com.xiaojiezhu.lefteye.core.context.Context;
import com.xiaojiezhu.lefteye.core.context.MonitorResourcePool;
import com.xiaojiezhu.lefteye.core.util.StringUtil;
import com.xiaojiezhu.lefteye.server.service.ProcessService;
import com.xiaojiezhu.lefteye.server.util.FileStore;
import com.xiaojiezhu.lefteye.core.util.HttpKit;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaojie.zhu
 * time 2019-07-20 22:57
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    private Context context = Context.getInstance();

    @Override
    public List<String> queryPackage(String pid, String parent) throws CommandException {
        MonitorResourcePool pool = context.getPool();
        CommandExecutor commandExecutor = pool.get(pid);
        List<String> child = commandExecutor.listPackage(parent);
        return child;
    }

    @Override
    public void attachProcess(int pid) throws IOException {
        synchronized (ProcessServiceImpl.class){
            MonitorResourcePool pool = context.getPool();

            CommandExecutor commandExecutor = pool.get(String.valueOf(pid));
            if(commandExecutor == null){
                // 进行字节码增强
                int socketPort = -1;
                int httpPort = -1;

                PortEntry portEntry = FileStore.get(pid);
                if(portEntry == null){
                    socketPort = NetUtil.randomPort();
                    httpPort = NetUtil.randomPort();
                    attach(pid , socketPort , httpPort);

                    FileStore.set(pid , new PortEntry(socketPort , httpPort));
                }else{
                    boolean isBind = NetUtil.isBind(portEntry.getSocketPort());
                    if(isBind){
                        socketPort = portEntry.getSocketPort();
                        httpPort = portEntry.getHttpPort();
                    }else{
                        socketPort = NetUtil.randomPort();
                        httpPort = NetUtil.randomPort();
                        attach(pid , socketPort , httpPort);
                        FileStore.set(pid , new PortEntry(socketPort , httpPort));
                    }

                }

                commandExecutor = new SocketCommandExecutor(pid , "localhost" , socketPort);
                pool.put(String.valueOf(pid) ,commandExecutor);

            }
        }


    }

    @Override
    public String trace(int pid , String method) throws CommandException {
        MonitorResourcePool pool = context.getPool();
        CommandExecutor commandExecutor = pool.get(String.valueOf(pid));
        String taskId = commandExecutor.trace(method);
        return taskId;
    }

    @Override
    public String showStream(String pid) {
        MonitorResourcePool pool = context.getPool();
        CommandExecutor commandExecutor = pool.get(pid);
        String buffer = commandExecutor.loadStreamBuffer();
        return buffer;
    }

    @Override
    public String deCompile(int pid, String className) throws CommandException {
        MonitorResourcePool pool = context.getPool();
        CommandExecutor commandExecutor = pool.get(String.valueOf(pid));

        if(StringUtil.isEmpty(className)){
            throw new NullPointerException("类名不能为空");
        }
        if(className.endsWith(".java")){
            className = className.substring(0 , className.length() - 5);
        }

        return commandExecutor.deCompile(className);
    }


    private void attach(int pid , int socketPort , int httpPort){
        String baseDir = System.getProperty(Constant.WORK_DIR);
        if(!baseDir.endsWith("/")){
            baseDir += "/";
        }
        String coreJar = new File(baseDir , "lib/arthas-core.jar").getAbsolutePath();
        String agentJar = new File(baseDir , "lib/arthas-agent.jar").getAbsolutePath();

        List<String> attachArgs = new ArrayList<String>();
        attachArgs.add("-jar");
        attachArgs.add(coreJar);
        attachArgs.add("-pid");
        attachArgs.add(String.valueOf(pid));
        attachArgs.add("-target-ip");
        attachArgs.add("localhost");
        attachArgs.add("-telnet-port");
        attachArgs.add(String.valueOf(socketPort));
        attachArgs.add("-http-port");
        attachArgs.add(String.valueOf(httpPort));
        attachArgs.add("-core");
        attachArgs.add(coreJar);
        attachArgs.add("-agent");
        attachArgs.add(agentJar);

        ProcessUtils.startArthasCore(pid, attachArgs);
    }
}
