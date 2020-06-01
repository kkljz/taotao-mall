package com.qtu.portal.config;

import com.qtu.portal.interceptor.OrderInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
拦截器配置类
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    /*
    springboot自定义拦截器时在debug发现@Autowired下的Bean都为null
    问题原因：
    拦截器加载的时间点在springcontext之前，所以在拦截器中注入自然为null
    解决办法：
    使用bean注解提前加载，即可成功。
    */
    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new OrderInterceptor();
    }


    /*
     多个拦截器组成一个拦截器链
     addPathPatterns 用于添加拦截规则
     excludePathPatterns 用户排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMyInterceptor())//添加拦截器
                .addPathPatterns("/order/**"); //拦截所有请求
//                .excludePathPatterns("/UserCon/**", "/Doctor/**", "/SMS/**");//对应的不拦截的请求
    }

}