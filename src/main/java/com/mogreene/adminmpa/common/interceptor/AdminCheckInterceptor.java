package com.mogreene.adminmpa.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 로그인 인터셉터
 * @author mogreene
 */
@Slf4j
public class AdminCheckInterceptor implements HandlerInterceptor {

    /**
     * 로그인 "admin" 세션 확인
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        if (session.getAttribute("admin") == null) {

            log.error("관리자 권한이 아닙니다.");
            return false;
        }
        return true;
    }
}
