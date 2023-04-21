package com.mogreene.adminmpa.common.config;

import com.mogreene.adminmpa.common.interceptor.AdminCheckInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfiguration
 * @author mogreene
 */
@Component
public class WebConfig implements WebMvcConfigurer {

    /**
     * 세션 인터셉터 적용
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(new AdminCheckInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/login", "/js/**", "/css/**", "/assets/**");
    }
}
