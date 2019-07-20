package com.xiaojiezhu.lefteye.core.cmd.net;/**
 * @author xiaojie.zhu
 */

import com.xiaojiezhu.lefteye.core.cmd.CommandRunner;
import com.xiaojiezhu.lefteye.core.exception.CommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

/**
 * @program: lefteye
 * @description:
 * @author: zhu.xiaojie
 * @create: 2019-07-20 10:50
 */
public class SocketCommand implements CommandRunner {
    public final static Logger LOG = LoggerFactory.getLogger(SocketCommand.class);

    private static final List<String> END_START_WITH = Arrays.asList("$" , "Affect(row-cnt");
    private static final List<String> END_EQUALS = Arrays.asList("" , "\n");

    /**
     * 跳过初始行
     */
    public static final int SKIP_LINE = 13;

    private String host;

    private int port;

    private Socket socket;
    private OutputStream outputStream;
    private BufferedReader reader;


    public SocketCommand(String host, int port) throws IOException {
        this.host = host;
        this.port = port;

        this.init();
    }


    private void init() throws IOException {
        try {
            this.socket = new Socket(this.host , this.port);

            this.outputStream = this.socket.getOutputStream();
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            for (int i = 0; i < SKIP_LINE; i++) {
                reader.readLine();
            }

        } catch (IOException e) {
            LOG.error("连接进程失败" , e);
            throw e;
        }


    }

    @Override
    public synchronized String execute(String cmd) throws CommandException {
        if(cmd == null || cmd.length() == 0){
            throw new CommandException("命令不能为空");
        }
        if(cmd.endsWith("\n")){
            cmd = cmd.substring(0 , cmd.length() - 1);
        }


        try {
            cmd = cmd + "\n";
            this.outputStream.write(cmd.getBytes());
            this.outputStream.flush();
        } catch (IOException e) {
            LOG.error("SOCKET通信异常，命令输出异常" , e);
            throw new CommandException("SOCKET 通信异常" , e);
        }


        return read();
    }


    private String read(){
        return read(null);
    }


    private String readTimeout(int timems) throws CommandException {
        long start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();

        try{
            while (true){

                while (this.reader.ready()){
                    char c = (char) this.reader.read();
                    sb.append(c);
                }
                long now = System.currentTimeMillis();
                if((now - start) > timems){
                    break;
                }
            }

        }catch (IOException e){
            throw new CommandException(e);
        }

        return sb.toString();
    }


    private String read(List<String> endEquals) {

        boolean first = true;
        StringBuilder sb = new StringBuilder();
        try {
            String line = null;

            reader.mark(2);
            char[] b = new char[2];
            int read = reader.read(b);
            if(read != -1){
                if("$ ".equals(new String(b))){
                    //return null;
                }
            }
            reader.reset();
            while ((line = reader.readLine()) != null){

                sb.append(line);
                sb.append("\n");
                if(line.startsWith("$")){
                    if(!first){
                        break;
                    }
                    first = false;
                }
                if(line.startsWith("Affect(row-cnt")){
                    break;
                }

                if(endEquals != null && endEquals.size() > 0){
                    boolean bk = false;
                    for (String s : endEquals) {
                        if(line.equals(s)){
                            bk = true;
                            break;
                        }
                    }
                    if(bk){
                        break;
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return sb.toString();
    }

    @Override
    public String write(String cmd) throws CommandException {
        try {
            this.outputStream.write(cmd.getBytes());
            this.outputStream.flush();
        } catch (IOException e) {
            throw new CommandException(e);
        }
        return read();
    }

    @Override
    public String enter() {
        return null;
    }

    @Override
    public String suggest(String cmd) throws CommandException {
        if(cmd == null){
            cmd = "";
        }
        cmd += "\t";
        try {
            this.outputStream.write(cmd.getBytes());
        } catch (IOException e) {
            throw new CommandException(e);
        }
        return readTimeout(500);
    }
}
