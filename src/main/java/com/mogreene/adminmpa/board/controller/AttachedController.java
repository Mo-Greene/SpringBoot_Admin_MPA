package com.mogreene.adminmpa.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 자료실 게시판
 * @author mogreene
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AttachedController {

    /**
     * 자료실 게시글 등록 화면
     * @return
     */
    @GetMapping("/attached/write")
    public String getAttachedWrite() {

        return "board/attached/attachedWrite";
    }
}
