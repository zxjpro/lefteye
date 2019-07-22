package com.xiaojiezhu.lefteye.server.test;

import com.xiaojiezhu.lefteye.core.cmd.CommandExecutor;
import com.xiaojiezhu.lefteye.core.cmd.CommandRunner;
import com.xiaojiezhu.lefteye.core.cmd.SocketCommandExecutor;
import com.xiaojiezhu.lefteye.core.cmd.net.SocketCommand;
import com.xiaojiezhu.lefteye.core.exception.CommandException;

import java.io.IOException;
import java.util.List;

/**
 * @author xiaojie.zhu
 * time 2019-07-21 20:52
 */
public class Demo4 {


    public static void main(String[] args) throws IOException, CommandException, InterruptedException {
        CommandExecutor executor = new SocketCommandExecutor(35036, "localhost", 18884);

        List<String> strings = executor.listPackage("");
        System.out.println(strings);

        String s = executor.deCompile("com.xiaojiezhu.lefteye.server.test.MathGame");
        System.out.println(s);

        //String trace = executor.trace("com.xiaojiezhu.lefteye.server.test.MathGame.java run()");


    }
}
