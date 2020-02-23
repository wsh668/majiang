package com.example.majiang.majiang.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//注意这里由于配置了EnableWebMvc会导致静态资源文件无法加载
@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private SessionInterceptor sessionInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
    }
}
