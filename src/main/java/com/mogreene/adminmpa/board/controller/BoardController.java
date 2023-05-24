package com.mogreene.adminmpa.board.controller;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * DashBoard
 * @author mogreene
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 전체 게시글 페이지
     * @return
     */
    @GetMapping("/")
    public String listPage(Model model) {

        List<BoardDTO> freeList = boardService.getMainFreeArticle();
        List<BoardDTO> noticeList = boardService.getMainNoticeArticle();
        List<BoardDTO> galleryList = boardService.getMainGalleryArticle();
        List<BoardDTO> attachedList = boardService.getMainAttachedArticle();

        model.addAttribute("freeList", freeList);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("galleryList", galleryList);
        model.addAttribute("attachedList", attachedList);

        return "board/boardList";
    }
}
