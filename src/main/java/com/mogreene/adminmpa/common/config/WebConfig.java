package com.mogreene.adminmpa.common.config;

import com.mogreene.adminmpa.common.interceptor.HandleInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfiguration
 * @author mogreene
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final HandleInterceptor handleInterceptor;

    /**
     * config 세션 인터셉터 적용
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(handleInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns();
    }
}
