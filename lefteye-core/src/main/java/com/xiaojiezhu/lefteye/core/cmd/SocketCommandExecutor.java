package com.xiaojiezhu.lefteye.core.cmd;

import com.xiaojiezhu.lefteye.core.cmd.net.SocketCommand;
import com.xiaojiezhu.lefteye.core.context.Context;
import com.xiaojiezhu.lefteye.core.context.MonitorResourcePool;
import com.xiaojiezhu.lefteye.core.exception.CommandException;
import com.xiaojiezhu.lefteye.core.util.HttpKit;
import com.xiaojiezhu.lefteye.core.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author xiaojie.zhu
 * time 2019-07-21 00:25
 */
public class SocketCommandExecutor implements CommandExecutor {

    private String host;

    private int port;

    private int pid;

    /**
     * 是否进入了流式状态
     */
    private boolean stream = false;


    private CommandRunner commandRunner;

    /**
     * 进入 stream 时的返回缓冲区
     */
    private StringBuffer streamBuffer = new StringBuffer();


    public SocketCommandExecutor(int pid , String host, int port) throws IOException {
        this.pid = pid;
        this.host = host;
        this.port = port;

        this.commandRunner = new SocketCommand(host , port);
    }

    @Override
    public  void reset() throws CommandException {
        this.checkStream();
        this.commandRunner.execute("reset");
        this.commandRunner.clear();
    }

    @Override
    public  void shutdown() throws CommandException {
        this.checkStream();
        this.reset();
        this.commandRunner.execute("shutdown");
        this.releaseContext();
    }

    @Override
    public  void quit() throws CommandException {
        this.checkStream();
        this.reset();
        this.commandRunner.close();

        this.releaseContext();

    }


    /**
     * 释放当前连接，在context中的缓存，一般用于退出客户端的情况
     */
    private void releaseContext(){
        Context context = Context.getInstance();

        MonitorResourcePool resourcePool = context.getPool(HttpKit.getSession());
        resourcePool.remove(String.valueOf(this.pid));
    }

    @Override
    public void breakc() throws CommandException {
        this.commandRunner.write("q");
        this.commandRunner.clear();
    }

    @Override
    public void enter() throws CommandException {
        String enter = this.commandRunner.enter();
    }

    @Override
    public List<String> listPackage(String parent) throws CommandException {
        this.checkStream();
        if(parent == null){
            parent = "";
        }
        boolean showMethod = false;
        if(parent.endsWith(".java")){
            parent = parent.substring(0 , parent.length() - 5) + " ";
            showMethod = true;
        }
        this.enter();
        String cmd = "trace " + parent;
        String suggest = commandRunner.suggest(cmd, 200);
        suggest = suggest.replaceAll("\r" , "");

        List<String> linesf = StringUtil.lines(suggest);
        List<String> lines = new ArrayList<>();
        Iterator<String> iterator = linesf.iterator();
        while (iterator.hasNext()){
            String line = iterator.next();
            if(line.trim().equals("trace")){
                iterator.remove();
                continue;
            }
            if(line.trim().startsWith("$ trace")){
                iterator.remove();
                continue;
            }
            if(StringUtil.isEmpty(line)){
                iterator.remove();
                continue;
            }
            if(line.equals("trace " + parent)){
                iterator.remove();
                continue;
            }
            if(line.startsWith("trace ")){
                line = line.replace("trace " , "");
            }
            lines.add(line);
        }
        List<String> childPackage = new ArrayList<>();
        if(lines.size() > 0){
            for (String line : lines) {
                line = line.replaceAll("\\s+" , "\t");
                String[] split = line.split("\t");
                for (String s : split) {
                    if(!s.endsWith(".")){
                        if(cmd.endsWith(" ")){
                            s += "()";
                        }else{
                            s+= ".java";
                        }
                    }
                    childPackage.add(s);
                }
            }
        }

        return childPackage;
    }

    @Override
    public String trace(String method) throws CommandException {
        this.checkStream();

        String[] split = method.split(" ");
        String className = split[0].substring(0 , split[0].length() - 5);
        String methodName = split[1].substring(0 , split[1].length() - 2);
        String cmd = "trace " + className + " " + methodName;
        this.stream = true;
        commandRunner.stream(cmd, new MessageListener() {
            @Override
            public void onMessage(String msg) {
                streamBuffer.append(msg);
            }
        });
        return null;
    }

    @Override
    public String loadStreamBuffer() {
        String s = streamBuffer.toString();
        streamBuffer = new StringBuffer();
        return s;
    }

    @Override
    public String deCompile(String className) throws CommandException {
        this.checkStream();
        String source = this.commandRunner.execute("jad " + className);
        source = source.replaceAll("\u001B\\[\\d+m", "");
        source = source.replaceAll("\u001B\\[\\d;\\d+m" , "");
        return source;
    }

    /**
     * 每个方法都需要调用这个方法，用于检测当前通信的状态是否为 stream 模式，
     * 如果是的话，需要把该模式退出，并且清空缓冲区
     */
    private void checkStream() throws CommandException {
        if(this.stream){
            this.breakc();

            this.streamBuffer = new StringBuffer();
            this.stream = false;
        }

        this.enter();
    }
}
