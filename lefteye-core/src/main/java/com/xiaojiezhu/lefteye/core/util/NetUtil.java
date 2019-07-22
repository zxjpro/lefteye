package com.xiaojiezhu.lefteye.core.util;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

/**
 * @author xiaojie.zhu
 * time 2019-07-20 23:38
 */
public class NetUtil {


    /**
     * 随机找一个可用的端口号
     * @return
     */
    public static int randomPort(){
        Random r = new Random();
        int port = 1500 + r.nextInt(20000);

        while (true){
            try {
                Socket socket = new Socket("localhost" , port);
                socket.close();
            } catch (IOException e) {
                return port;
            }
        }

    }

    public static boolean isBind(int port) {
        try {
            Socket socket = new Socket("localhost" , port);
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
