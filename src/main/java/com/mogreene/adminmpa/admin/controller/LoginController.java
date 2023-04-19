package com.mogreene.adminmpa.admin.controller;

import com.mogreene.adminmpa.admin.dto.AdminDTO;
import com.mogreene.adminmpa.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getLogin(HttpSession session,
                           Model model) {

        String redirect = (String) session.getAttribute("redirect");

        model.addAttribute("redirect", redirect);
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
                        HttpServletResponse response,
                        @RequestParam String redirect) {

        try {
            adminService.loginAdmin(adminDTO);

            Cookie cookie = new Cookie("admin", "ADMIN");
            cookie.setMaxAge(60 * 60); //1시간
            cookie.setPath("/");
            response.addCookie(cookie);

            session.setAttribute("admin", "관리자");

            // TODO: 2023/04/19 수정 필요
            if (redirect.equals("logout")) {
                return "redirect:/";
            }
            return "redirect:/" + redirect;
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

        session.removeAttribute("admin");
        session.invalidate();

        return "redirect:/login";
    }
}
