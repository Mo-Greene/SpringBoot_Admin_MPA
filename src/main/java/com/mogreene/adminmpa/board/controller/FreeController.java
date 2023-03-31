package com.mogreene.adminmpa.board.controller;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.service.FreeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 자유 게시판
 * @author mogreene
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FreeController {

    private final FreeService freeService;

    /**
     * 자유게시판 화면
     * @return
     */
    @GetMapping("/free")
    public String getFree(Model model) {

        List<BoardDTO> boardList = freeService.getFreeArticle();

        model.addAttribute("listDto", boardList);

        log.info("model : " + model);
        return "board/free/freeList";
    }

    /**
     * 자유게시판 게시글 등록 화면
     */
    @GetMapping("/free/write")
    public String getFreeWrite() {

        return "board/free/freeWrite";
    }

    /**
     * 자유게시판 게시글 등록
     * @param boardDTO
     */
    @PostMapping("/free/write")
    public String postFree(BoardDTO boardDTO) {

        freeService.postFree(boardDTO);

        return "redirect:/free";
    }
}
