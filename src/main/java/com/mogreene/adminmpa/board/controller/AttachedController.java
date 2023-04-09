package com.mogreene.adminmpa.board.controller;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.dto.page.PageRequestDTO;
import com.mogreene.adminmpa.board.dto.page.PageResponseDTO;
import com.mogreene.adminmpa.board.service.AttachedService;
import com.mogreene.adminmpa.board.util.BoardUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 자료실 게시판
 * @author mogreene
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AttachedController {

    private final AttachedService attachedService;
    private final BoardUtil boardUtil;

    /**
     * 자료실 전체화면
     * @param pageRequestDTO
     * @param bindingResult
     * @param model
     * @return
     */
    @GetMapping("/attached")
    public String getAttached(@Valid PageRequestDTO pageRequestDTO,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            pageRequestDTO = PageRequestDTO.builder().build();
        }

        List<BoardDTO> attachedList = attachedService.getAttachedArticle(pageRequestDTO);
        PageResponseDTO pageResponseDTO = attachedService.pagination(pageRequestDTO);

        model.addAttribute("attachedList", attachedList);
        model.addAttribute("pagination", pageResponseDTO);
        return "board/attached/attachedList";
    }

    @GetMapping("/attached/{boardNo}")
    public String getAttachedView(@PathVariable Long boardNo, Model model) {

        BoardDTO dto = attachedService.getAttachedViewArticle(boardNo);

        model.addAttribute("dto", dto);
        return "board/attached/attachedView";
    }

    /**
     * 자료실 게시글 등록 화면
     * @return
     */
    @GetMapping("/attached/write")
    public String getAttachedWrite() {

        return "board/attached/attachedWrite";
    }

    /**
     * 게시글 + 다중 파일 업로드
     * @param boardDTO
     * @param files
     * @return
     */
    @PostMapping("/attached/write")
    public String postAttached(@Valid BoardDTO boardDTO,
                               @RequestParam MultipartFile[] files,
                               HttpSession session) throws IOException {

        // TODO: 2023/04/07 예외처리 필요
        //파일이 없을 경우 throw
        if (files[0].isEmpty() && files[1].isEmpty() && files[2].isEmpty()) {
            throw new RuntimeException("파일은 하나라도 존재 해야함");
        }

        //boardWriter => 세션에서 "admin" 값으로
        boardUtil.setBoardWriter(boardDTO, session);

        //게시글 + 첨부파일 등록 (제목 + 내용)
        attachedService.uploadArticle(boardDTO, files);

        return "board/attached/attachedList";
    }

    /**
     * 자료실 수정페이지 이동
     * @param boardNo
     * @param model
     * @return
     */
    @GetMapping("/attached/modify/{boardNo}")
    public String getAttachedModifyView(@PathVariable Long boardNo,
                                        Model model) {

        BoardDTO dto = attachedService.getAttachedModify(boardNo);

        model.addAttribute("dto", dto);
        return "board/attached/attachedModify";
    }

    // TODO: 2023/04/07 수정, 삭제는 생각 후 구현
}
