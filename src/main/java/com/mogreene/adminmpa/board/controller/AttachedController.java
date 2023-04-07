package com.mogreene.adminmpa.board.controller;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.service.AttachedService;
import com.mogreene.adminmpa.board.util.BoardUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

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

        //게시글 등록 (제목 + 내용)
        attachedService.postAttached(boardDTO);
        attachedService.uploadFile(boardDTO, files);

        // TODO: 2023/04/06 url 임시방편 고쳐야됨
        return "redirect:/attached/write";
    }
}
