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
     * Get 제외 "admin" 세션 확인
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        // TODO: 2023/04/05 안됨
//        if (request.getMethod().equals("POST") ||
//                request.getMethod().equals("DELETE") ||
//                request.getMethod().equals("PUT")) {
//            HttpSession session = request.getSession();
//            String admin = (String) session.getAttribute("admin");
//
//            if (admin == null) {
//                response.sendRedirect("/login");
//                return false;
//            }
//        }
//        return true;
//    }
}
