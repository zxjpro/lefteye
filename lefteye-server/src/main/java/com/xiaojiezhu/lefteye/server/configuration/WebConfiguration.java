package com.xiaojiezhu.lefteye.server.configuration;/**
 * @author xiaojie.zhu
 */

import com.xiaojiezhu.lefteye.server.component.web.ResultMethodHandler;
import com.xiaojiezhu.lefteye.server.component.web.SessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @program: lefteye
 * @description:
 * @author: zhu.xiaojie
 * @create: 2019-07-20 16:08
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {


    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new ResultMethodHandler());
    }


    @Bean
    public ServletListenerRegistrationBean<SessionListener> sessionListener(){
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener(new SessionListener());
        return bean;
    }
}
