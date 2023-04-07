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
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(new AdminCheckInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login", "/js/**", "/css/**", "/assets/**");
//
//        // TODO: 2023/04/07 인터셉터로 session값을 보내줘서 화면에서 login, logout 구분하기
//    }
}
