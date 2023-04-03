package com.mogreene.adminmpa.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 전체 게시판
 * @author mogreene
 */
@Slf4j
@Controller
public class BoardController {

    /**
     * 전체 게시글 페이지
     * @return
     */
    @GetMapping("/")
    public String listPage() {

        return "board/boardList";
    }
}
