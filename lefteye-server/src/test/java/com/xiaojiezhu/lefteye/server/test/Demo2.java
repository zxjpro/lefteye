package com.xiaojiezhu.lefteye.server.test;/**
 * @author xiaojie.zhu
 */

import com.xiaojiezhu.lefteye.core.cmd.CommandRunner;
import com.xiaojiezhu.lefteye.core.cmd.MessageListener;
import com.xiaojiezhu.lefteye.core.cmd.net.SocketCommand;
import com.xiaojiezhu.lefteye.core.exception.CommandException;
import com.xiaojiezhu.lefteye.core.util.ProcessUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @program: lefteye
 * @description:
 * @author: zhu.xiaojie
 * @create: 2019-07-20 11:02
 */
public class Demo2 {

    public static void main(String[] args) throws IOException, CommandException {

        Map<Integer, String> integerStringMap = ProcessUtils.listProcessByJps(false);
        System.out.println(integerStringMap);

        CommandRunner commandRunner = new SocketCommand("localhost" , 3197);
        String trace_ = commandRunner.execute("trace \t");
        System.out.println(trace_);
        commandRunner.stream("trace com.xiaojiezhu.lefteye.server.test.MathGame run", new MessageListener() {
            @Override
            public void onMessage(String msg) {
                System.out.println(msg);
            }
        });

    }
}
