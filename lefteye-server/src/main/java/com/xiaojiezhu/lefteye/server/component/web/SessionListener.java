package com.xiaojiezhu.lefteye.server.component.web;

import com.xiaojiezhu.lefteye.core.context.Context;
import com.xiaojiezhu.lefteye.core.context.SimpleMonitorResourcePool;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author xiaojie.zhu
 * time 2019-07-20 23:19
 */
public class SessionListener implements HttpSessionListener {


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        Context context = Context.getInstance();
        context.putPool(se.getSession() , new SimpleMonitorResourcePool());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        Context context = Context.getInstance();
        context.deletePool(se.getSession());
    }
}
