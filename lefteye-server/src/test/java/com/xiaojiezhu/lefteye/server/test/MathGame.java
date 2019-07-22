package com.xiaojiezhu.lefteye.server.test;

import java.util.Random;

/**
 * @author xiaojie.zhu
 * time 2019-07-20 22:46
 */
public class MathGame {

    public static void main(String[] args) {

        while (true){
            run();
        }
    }

    private static void run(){

        Random r = new Random();
        int i = r.nextInt(2000);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(i);


    }
}
