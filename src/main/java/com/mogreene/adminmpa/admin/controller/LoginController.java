package com.mogreene.adminmpa.admin.controller;

import com.mogreene.adminmpa.admin.dto.AdminDTO;
import com.mogreene.adminmpa.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
     * @param request
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/login")
    public String login(AdminDTO adminDTO,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(1800);   //세션 30분

        AdminDTO admin = adminService.loginAdmin(adminDTO);

        if (admin == null) {
            redirectAttributes.addFlashAttribute("loginFail", "아이디 혹은 비밀번호가 맞지 않습니다.");
            return "redirect:/login";
        }

        session.setAttribute("loginAdmin", admin.getUsername());
        return "board/boardList";
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

        return "redirect:/login";
    }
}
