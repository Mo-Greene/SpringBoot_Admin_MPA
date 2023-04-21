package com.mogreene.adminmpa.board.controller;

import com.mogreene.adminmpa.board.dto.AttachedDTO;
import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.dto.page.PageRequestDTO;
import com.mogreene.adminmpa.board.dto.page.PageResponseDTO;
import com.mogreene.adminmpa.board.service.GalleryService;
import com.mogreene.adminmpa.board.util.BoardUtil;
import com.mogreene.adminmpa.common.api.ApiResponseDTO;
import com.mogreene.adminmpa.reply.dto.ReplyDTO;
import com.mogreene.adminmpa.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * 이미지 게시판
 * @author mogreene
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;
    private final ReplyService replyService;
    private final BoardUtil boardUtil;

    /**
     * 갤러리 전체화면
     * @return
     */
    @GetMapping("/gallery")
    public String getGallery(@Valid PageRequestDTO pageRequestDTO,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            pageRequestDTO = PageRequestDTO.builder().build();
        }

        List<BoardDTO> galleryList = galleryService.getGalleryArticle(pageRequestDTO);
        PageResponseDTO pageResponseDTO = galleryService.pagination(pageRequestDTO);

        model.addAttribute("galleryList", galleryList);
        model.addAttribute("pagination", pageResponseDTO);
        return "board/gallery/galleryList";
    }

    /**
     * 갤러리 특정게시글 조회
     * @param boardNo
     * @param model
     * @return
     */
    @GetMapping("/gallery/{boardNo}")
    public String getGalleryView(@PathVariable Long boardNo, Model model) {

        BoardDTO dto = galleryService.getGalleryViewArticle(boardNo);
        AttachedDTO attachedDTO = galleryService.getImage(boardNo);
        List<ReplyDTO> replyDtoList = replyService.getReply(boardNo);

        model.addAttribute("dto", dto);
        model.addAttribute("attachedDto", attachedDTO);
        model.addAttribute("replyDto", replyDtoList);
        return "board/gallery/galleryView";
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
                              HttpSession session) throws IOException {

        if (file.isEmpty()) {
            throw new RuntimeException("파일 존재해야됨");
        }

        //boardWriter => 세션에서 "admin" 값으로
        boardUtil.setBoardWriter(boardDTO, session);

        galleryService.postGalleryArticle(boardDTO);

        galleryService.uploadImage(boardDTO, file);

        return "redirect:/gallery";
    }

    /**
     * 갤러리 수정화면
     * @param boardNo
     * @param model
     * @return
     */
    @GetMapping("/gallery/modify/{boardNo}")
    public String getGalleryModifyView(@PathVariable Long boardNo,
                                       Model model) {

        BoardDTO dto = galleryService.getGalleryModify(boardNo);
        AttachedDTO attachedDTO = galleryService.getImage(boardNo);

        model.addAttribute("dto", dto);
        model.addAttribute("attachedDTO", attachedDTO);
        return "board/gallery/galleryModify";
    }

    // TODO: 2023/04/20 갤러리 수정, 삭제 구현
    @ResponseBody
    @GetMapping("/view/{boardNo}")
    public Resource showImageFile(@PathVariable Long boardNo) throws IOException {

        return galleryService.getImageFile(boardNo);
    }

    /**
     * 갤러리 게시글 수정
     * @param boardNo
     * @param boardDTO
     * @param session
     * @return
     */
    @PutMapping("/gallery/modify/{boardNo}")
    public ResponseEntity<ApiResponseDTO<?>> modifyGalleryArticle(@PathVariable Long boardNo,
                                                                  @RequestBody BoardDTO boardDTO,
                                                                  HttpSession session) {
        log.info("boardDTO : " + boardDTO);

        boardUtil.setBoardWriter(boardDTO, session);
        boardDTO.setBoardNo(boardNo);

        try {
            galleryService.modifyGalleryArticle(boardDTO);

        } catch (IllegalArgumentException e) {

            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                .httpStatus(HttpStatus.OK)
                .data("Modify_OK")
                .build();
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    /**
     * 갤러리 게시글 삭제
     * @param boardNo
     * @return
     */
    @DeleteMapping("/gallery/delete/{boardNo}")
    public ResponseEntity<?> deleteGallery(@PathVariable Long boardNo) {

        try {

            galleryService.deleteGalleryArticle(boardNo);
        } catch (IllegalArgumentException e) {

            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
