package com.mogreene.adminmpa.admin.controller;

import com.mogreene.adminmpa.admin.dto.AdminDTO;
import com.mogreene.adminmpa.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipalNotFoundException;

/**
 * 로그인 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AdminService adminService;

    /**
     * 로그인 페이지 이동
     * @return login.html
     */
    @GetMapping("/login")
    public String getLogin() {
        return "login/login";
    }

    /**
     * 관리자 로그인 세션 생성 30분
     * @param adminDTO
     * @return
     */
    @PostMapping("/login")
    public String login(@Valid AdminDTO adminDTO,
                        HttpSession session,
                        HttpServletResponse response) {

        // TODO: 2023/04/01 예외처리 생각하고 session 공부
        try {
            AdminDTO admin = adminService.loginAdmin(adminDTO);

            Cookie cookie = new Cookie("admin", admin.getUsername());
            cookie.setMaxAge(1800); //30분
            cookie.setPath("/");
            response.addCookie(cookie);

            session.setAttribute("admin", admin.getUsername());

            return "board/boardList";
        } catch (UserPrincipalNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 로그아웃
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession();

        session.invalidate();

        // TODO: 2023/04/02 로그아웃 후 보통 전체게시글을 보여주지 않나?
        return "redirect:/login";
    }
}
