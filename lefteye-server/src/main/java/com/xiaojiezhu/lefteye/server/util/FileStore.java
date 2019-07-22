package com.xiaojiezhu.lefteye.server.util;

import com.xiaojiezhu.lefteye.core.Constant;
import com.xiaojiezhu.lefteye.core.model.PortEntry;
import com.xiaojiezhu.lefteye.core.util.IOUtils;

import java.io.*;
import java.util.Properties;

/**
 * @author xiaojie.zhu
 * time 2019-07-20 23:52
 */
public class FileStore {

    private static final String baseDir = System.getProperty(Constant.WORK_DIR);
    private static final File file = new File(baseDir , "conf/monitor");


    /**
     * 清空存储
     * @throws IOException
     */
    public static void clear() throws IOException {
        synchronized (FileStore.class){

            storeProperties(new Properties());
        }
    }

    /**
     * 设置
     * @param pid
     * @param portEntry
     */
    public static void set(int pid , PortEntry portEntry) throws IOException {
        synchronized (FileStore.class){

            Properties properties = loadProperties();
            properties.setProperty(String.valueOf(pid) , portEntry.getSocketPort() + "," + portEntry.getHttpPort());

            storeProperties(properties);

        }

    }

    /**
     * 删除
     * @param pid
     * @throws IOException
     */
    public static void delete(int pid) throws IOException {
        synchronized (FileStore.class){

            Properties properties = loadProperties();
            properties.remove(pid);

            storeProperties(properties);
        }

    }

    /**
     * 获取
     * @param pid
     * @return
     * @throws IOException
     */
    public static PortEntry get(int pid) throws IOException {
        synchronized (FileStore.class){
            Properties properties = loadProperties();

            String text = (String) properties.get(String.valueOf(pid));
            if(text == null){
                return null;
            }
            String[] split = text.split(",");
            return new PortEntry(Integer.parseInt(split[0]) , Integer.parseInt(split[1]));

        }
    }


    /**
     * 从文件中读取
     * @return
     * @throws IOException
     */
    private static Properties loadProperties() throws IOException {
        if(!file.exists()){
            boolean newFile = file.createNewFile();
            if(!newFile){
                throw new RuntimeException(String.format("创建文件 %s 失败， 请检查文件权限" , file.getAbsoluteFile()));
            }
        }

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;

        } finally {
            IOUtils.close(inputStream);
        }
    }

    /**
     * 持久化
     * @param properties
     * @throws IOException
     */
    private static void storeProperties(Properties properties) throws IOException {
        OutputStream out = null;

        try {
            out = new FileOutputStream(file);
            properties.store(out , "");
        } finally {
            IOUtils.close(out);
        }
    }
}
