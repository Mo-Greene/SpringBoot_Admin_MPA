package com.mogreene.adminmpa.board.controller;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.service.FreeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    // TODO: 2023/04/03 페이지네이션
    @GetMapping("/free")
    public String getFree(Model model) {

        List<BoardDTO> freeBoardList = freeService.getFreeArticle();

        model.addAttribute("freeBoardList", freeBoardList);
        return "board/free/freeList";
    }

    /**
     * 자유게시판 특정게시글 조회
     * @param boardNo
     * @param model
     * @return
     */
    @GetMapping("/free/{boardNo}")
    public String getFreeView(@PathVariable Long boardNo, Model model) {

        BoardDTO dto = freeService.getFreeViewArticle(boardNo);

        model.addAttribute("dto", dto);
        return "board/free/freeView";
    }

    /**
     * 자유게시판 게시글 등록 화면
     */
    @GetMapping("/free/write")
    public String getFreeWrite(HttpSession session) {

        // TODO: 2023/04/01 예외처리 생각해보자
        String admin = (String) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/login";
        }
        return "board/free/freeWrite";
    }

    /**
     * 자유게시판 게시글 등록
     * @param boardDTO
     */
    @PostMapping("/free/write")
    public String postFree(HttpSession session,
                           @RequestBody BoardDTO boardDTO) {

        String admin = (String) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/login";
        }

        freeService.postFree(boardDTO);

        return "redirect:/free";
    }

    /**
     * 자유게시판 수정페이지 이동
     * @param boardNo
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/free/modify/{boardNo}")
    public String getFreeModifyView(@PathVariable Long boardNo,
                                    HttpSession session,
                                    Model model) {

        String admin = (String) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/login";
        }

        BoardDTO dto = freeService.getFreeModify(boardNo);

        model.addAttribute("dto", dto);
        return "board/free/freeModify";
    }

    /**
     * 자유게시판 게시글 수정
     * @param boardNo
     * @param boardDTO
     * @param session
     * @return
     */
    @PutMapping("/free/modify/{boardNo}")
    public String modifyArticle(@PathVariable Long boardNo,
                                @RequestBody BoardDTO boardDTO,
                                HttpSession session) {

        String admin = (String) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/login";
        }

        boardDTO.setBoardNo(boardNo);

        try {
            freeService.modifyFreeArticle(boardDTO);

        } catch (IllegalArgumentException e) {

            // TODO: 2023/04/02 글로벌 예외처리 봅아내기
            log.error(e.getMessage());
            return "error";
        }

        return "board/free/freeList";
    }

    /**
     * 자유게시판 게시글 삭제
     * @param boardNo
     * @return
     */
    @DeleteMapping("/free/delete/{boardNo}")
    public String deleteFree(@PathVariable Long boardNo) {

        // TODO: 2023/04/03 세션 처리
        try {
            freeService.deleteFreeArticle(boardNo);

        } catch (IllegalArgumentException e) {

            log.error(e.getMessage());
            // TODO: 2023/04/01 에러페이지가 아닌 메세지를 담고 프론트로 보내자
            return "error";
        }

        return "board/free/freeList";
    }
}
