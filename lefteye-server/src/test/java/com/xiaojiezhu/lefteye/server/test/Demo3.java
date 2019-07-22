package com.xiaojiezhu.lefteye.server.test;

import java.util.Arrays;

/**
 * @author xiaojie.zhu
 * time 2019-07-21 09:12
 */
public class Demo3 {


    public static void main(String[] args) {
        String text = "jad com.xiaojiezhu.lefteye.server.test.MathGame\n" +
                "\n" +
                "ClassLoader:                                                                    \n" +
                "+-sun.misc.Launcher$AppClassLoader@18b4aac2                                     \n" +
                "  +-sun.misc.Launcher$ExtClassLoader@44b1116a                                   \n" +
                "\n" +
                "Location:                                                                       \n" +
                "/Users/zxj/data/code/java/lefteye/lefteye-server/target/test-classes/           \n" +
                "\n" +
                "/*\n" +
                " * Decompiled with CFR 0_132.\n" +
                " */\n" +
                "package com.xiaojiezhu.lefteye.server.test;\n" +
                "\n" +
                "import java.io.PrintStream;\n" +
                "import java.util.Random;\n" +
                "\n" +
                "public class MathGame {\n" +
                "    public static void main(String[] args) {\n" +
                "        do {\n" +
                "            MathGame.run();\n" +
                "        } while (true);\n" +
                "    }\n" +
                "\n" +
                "    private static void run() {\n" +
                "        Random r = new Random();\n" +
                "        int i = r.nextInt(2000);\n" +
                "        try {\n" +
                "            Thread.sleep(i);\n" +
                "        }\n" +
                "        catch (InterruptedException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "        System.out.println(i);\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "Affect(row-cnt:1) cost in 201 ms.\n";


        System.out.println(text);
    }
}
