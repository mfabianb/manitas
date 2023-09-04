package com.manitas.infrastructure.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@Configuration
@Log4j2
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private RequestInterceptor requestInterceptor;

    @PostConstruct
    public void init(){ log.info("Init configuration {}", this.getClass().getName()); }

    @Override
    public void addInterceptors(InterceptorRegistry registry){ registry.addInterceptor(requestInterceptor); }
}
