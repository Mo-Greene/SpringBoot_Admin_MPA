package com.mogreene.adminmpa.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
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
     * "admin" 세션 확인
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        String redirectUrl = request.getRequestURI().substring(1);

        if (admin == null) {
            response.sendRedirect("/login?redirect=" + redirectUrl);
            return false;
        }

        return true;
    }
}
