package com.mogreene.adminmpa.board.controller;

import com.mogreene.adminmpa.board.dto.AttachedDTO;
import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.dto.page.PageRequestDTO;
import com.mogreene.adminmpa.board.dto.page.PageResponseDTO;
import com.mogreene.adminmpa.board.service.AttachedService;
import com.mogreene.adminmpa.board.util.BoardUtil;
import com.mogreene.adminmpa.reply.dto.ReplyDTO;
import com.mogreene.adminmpa.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 자료실 게시판
 * @author mogreene
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class AttachedController {

    private final AttachedService attachedService;
    private final ReplyService replyService;
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

    /**
     * 자료실 특정게시글 조회
     * @param boardNo
     * @param model
     * @return
     */
    @GetMapping("/attached/{boardNo}")
    public String getAttachedView(@PathVariable Long boardNo,
                                  PageRequestDTO pageRequestDTO,
                                  Model model) {

        BoardDTO dto = attachedService.getAttachedViewArticle(boardNo);
        List<AttachedDTO> attachedDtoList = attachedService.getAttached(boardNo);
        List<ReplyDTO> replyDtoList = replyService.getReply(boardNo);

        model.addAttribute("dto", dto);
        model.addAttribute("attachedDto", attachedDtoList);
        model.addAttribute("replyDto", replyDtoList);
        return "board/attached/attachedView";
    }

    /**
     * 파일 다운로드
     * @param attachedNo
     * @return
     * @throws IOException
     */
    @GetMapping("/download/{attachedNo}")
    public ResponseEntity<Resource> fileDown(@PathVariable Long attachedNo) throws IOException {

        AttachedDTO attachedDTO = attachedService.downloadAttached(attachedNo);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, attachedDTO.getContentDisposition())
                .body(attachedDTO.getResource());
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

        //파일이 없을 경우 throw
        if (files[0].isEmpty() && files[1].isEmpty() && files[2].isEmpty()) {
            throw new RuntimeException("파일은 하나라도 존재 해야함");
        }

        //boardWriter => 세션에서 "admin" 값으로
        boardUtil.setBoardWriter(boardDTO, session);

        //게시글 등록
        attachedService.postAttachedArticle(boardDTO);

        //첨부파일 등록 (제목 + 내용)
        attachedService.uploadAttached(boardDTO, files);

        return "redirect:/attached";
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
        List<AttachedDTO> attachedDtoList = attachedService.getAttached(boardNo);
        int attachedCount = attachedService.attachedCount(boardNo);

        Map<String, Object> response = new HashMap<>();
        response.put("attachedDto", attachedDtoList);
        response.put("attachedCount", attachedCount);

        model.addAttribute("dto", dto);
        model.addAttribute("response", response);
        return "board/attached/attachedModify";
    }

    /**
     * 자료실 수정
     * @param boardDTO
     * @param files
     */
    @PostMapping("/attached/modify")
    public String modifyAttachedArticle(BoardDTO boardDTO,
                                        @RequestPart MultipartFile[] files) throws IOException {

        //파일이 없을 경우 throw
        if (files[0].isEmpty() && files[1].isEmpty() && files[2].isEmpty()) {
            throw new RuntimeException("파일은 하나라도 존재 해야함");
        }

        attachedService.modifyAttachedArticle(boardDTO);
        attachedService.uploadAttached(boardDTO, files);

        Long boardUrl = boardDTO.getBoardNo();

        return "redirect:/attached/" + boardUrl;
    }

    /**
     * 파일삭제
     */
    @GetMapping("/attached/delete/{attachedNo}")
    public String deleteAttached(@PathVariable Long attachedNo) {

        Long boardNo = attachedService.deleteAttached(attachedNo);

        return "redirect:/attached/modify/" + boardNo;
    }

    /**
     * 자료실 게시글 삭제
     * @param boardNo
     * @return
     */
    @DeleteMapping("/attached/delete/{boardNo}")
    public ResponseEntity<?> deleteAttachedArticle(@PathVariable Long boardNo) {

        attachedService.deleteAttachedArticle(boardNo);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
