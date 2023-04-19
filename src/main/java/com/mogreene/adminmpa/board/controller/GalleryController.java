package com.mogreene.adminmpa.board.controller;

import com.mogreene.adminmpa.board.dto.BoardDTO;
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

/**
 * 이미지 게시판
 * @author mogreene
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class GalleryController {

    private final BoardUtil boardUtil;

    /**
     * 갤러리 전체화면
     * @return
     */
    @GetMapping("/gallery")
    public String getGallery() {

        return "board/gallery/galleryList";
    }

    /**
     * 갤러리 등록화면
     * @return
     */
    @GetMapping("/gallery/write")
    public String getGalleryWrite() {

        return "board/gallery/galleryWrite";
    }

    /**
     * 갤러리 등록
     * @param boardDTO
     * @param file
     * @param session
     * @return
     */
    @PostMapping("/gallery/write")
    public String postGallery(@Valid BoardDTO boardDTO,
                              @RequestParam MultipartFile file,
                              HttpSession session) {

        if (file.isEmpty()) {
            throw new RuntimeException("파일 존재해야됨");
        }

        //boardWriter => 세션에서 "admin" 값으로
        boardUtil.setBoardWriter(boardDTO, session);

        return "redirect:/gallery";
    }
}
