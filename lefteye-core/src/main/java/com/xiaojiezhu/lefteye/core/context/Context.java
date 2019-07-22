package com.xiaojiezhu.lefteye.core.context;

import javax.servlet.http.HttpSession;

/**
 * @author xiaojie.zhu
 * time 2019-07-20 23:00
 */
public interface Context {


    /**
     * 获取一个用户的资源池
     * @param session
     * @return
     */
    MonitorResourcePool getPool(HttpSession session);


    /**
     * 获取当前 session 的资源池
     * @return
     */
    MonitorResourcePool getPool();


    /**
     * 存入一个连接池
     * @param session
     * @param resourcePool
     */
    void putPool(HttpSession session , MonitorResourcePool resourcePool);


    /**
     * 删除一个池
     * @param session
     */
    void deletePool(HttpSession session);


    Context CONTEXT = ContextImpl.getInstance();

    static Context getInstance(){
        return CONTEXT;
    }

}
