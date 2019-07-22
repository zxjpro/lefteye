package com.xiaojiezhu.lefteye.core.context;

import com.xiaojiezhu.lefteye.core.util.HttpKit;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaojie.zhu
 * time 2019-07-20 23:12
 */
public class ContextImpl implements Context {

    private final ConcurrentHashMap<HttpSession, MonitorResourcePool> monitors = new ConcurrentHashMap<>();


    private ContextImpl(){}



    @Override
    public MonitorResourcePool getPool(HttpSession session) {
        return this.monitors.get(session);
    }

    @Override
    public MonitorResourcePool getPool() {
        return getPool(HttpKit.getSession());
    }

    @Override
    public void putPool(HttpSession session, MonitorResourcePool resourcePool) {
        if(this.monitors.get(session) != null){
            throw new RuntimeException("会话池已存在");
        }
        this.monitors.put(session , resourcePool);
    }

    @Override
    public void deletePool(HttpSession session) {
        this.monitors.remove(session);
    }




    public static Context getInstance(){
        return Instance.INSTANCE;
    }

    private static class Instance{
        private static final Context INSTANCE = new ContextImpl();
    }

}
