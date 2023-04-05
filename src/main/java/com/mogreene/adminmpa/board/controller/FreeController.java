package com.mogreene.adminmpa.board.controller;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.dto.page.PageRequestDTO;
import com.mogreene.adminmpa.board.dto.page.PageResponseDTO;
import com.mogreene.adminmpa.board.service.FreeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String getFree(@Valid PageRequestDTO pageRequestDTO,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            pageRequestDTO = PageRequestDTO.builder().build();
        }

        List<BoardDTO> freeBoardList = freeService.getFreeArticle(pageRequestDTO);
        PageResponseDTO pageResponseDTO = freeService.pagination(pageRequestDTO);

        model.addAttribute("freeBoardList", freeBoardList);
        model.addAttribute("pagination", pageResponseDTO);
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
    public String getFreeWrite() {

        return "board/free/freeWrite";
    }

    /**
     * 자유게시판 게시글 등록
     * @param boardDTO
     */
    @PostMapping("/free/write")
    public String postFree(@RequestBody BoardDTO boardDTO) {

        freeService.postFree(boardDTO);

        return "redirect:/free";
    }

    /**
     * 자유게시판 수정페이지 이동
     * @param boardNo\
     * @param model
     * @return
     */
    @GetMapping("/free/modify/{boardNo}")
    public String getFreeModifyView(@PathVariable Long boardNo,
                                    Model model) {

        BoardDTO dto = freeService.getFreeModify(boardNo);

        model.addAttribute("dto", dto);
        return "board/free/freeModify";
    }

    /**
     * 자유게시판 게시글 수정
     * @param boardNo
     * @param boardDTO
     * @return
     */
    @PutMapping("/free/modify/{boardNo}")
    public String modifyArticle(@PathVariable Long boardNo,
                                @RequestBody BoardDTO boardDTO) {

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
