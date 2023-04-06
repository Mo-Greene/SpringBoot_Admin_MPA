package com.mogreene.adminmpa.board.controller;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.dto.page.PageRequestDTO;
import com.mogreene.adminmpa.board.dto.page.PageResponseDTO;
import com.mogreene.adminmpa.board.service.NoticeService;
import com.mogreene.adminmpa.board.util.BoardUtil;
import com.mogreene.adminmpa.common.api.ApiResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * 공지 게시판
 * @author mogreene
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class NoticeController {

    // TODO: 2023/04/05 N+1 문제 해결해야됨!
    private final NoticeService noticeService;
    private final BoardUtil boardUtil;

    /**
     * 공지게시판 화면
     * @param model
     * @return
     */
    @GetMapping("/notice")
    public String getNotice(@Valid PageRequestDTO pageRequestDTO,
                            BindingResult bindingResult,
                            Model model) {

        if (bindingResult.hasErrors()) {
            pageRequestDTO = PageRequestDTO.builder().build();
        }

        List<BoardDTO> noticeBoardList = noticeService.getNoticeArticle(pageRequestDTO);
        PageResponseDTO pageResponseDTO = noticeService.pagination(pageRequestDTO);

        model.addAttribute("noticeBoardList", noticeBoardList);
        model.addAttribute("pagination", pageResponseDTO);
        return "board/notice/noticeList";
    }

    /**
     * 공지게시판 특정게시글 조회
     * @param boardNo
     * @param model
     * @return
     */
    @GetMapping("/notice/{boardNo}")
    public String getNoticeView(@PathVariable Long boardNo, Model model) {

        BoardDTO dto = noticeService.getNoticeViewArticle(boardNo);

        model.addAttribute("dto", dto);
        return "board/notice/noticeView";
    }

    /**
     * 공지게시판 게시글 등록 화면
     * @return
     */
    @GetMapping("/notice/write")
    public String getNoticeWrite() {

        return "board/notice/noticeWrite";
    }

    /**
     * 공지게시판 게시글 등록
     * @param boardDTO
     * @return
     */
    @PostMapping("/notice/write")
    public ResponseEntity<ApiResponseDTO<?>> postNotice(@RequestBody BoardDTO boardDTO,
                                                        HttpSession session) {

        boardUtil.addBoardWriter(boardDTO, session);
        noticeService.postNotice(boardDTO);

        ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                .httpStatus(HttpStatus.CREATED)
                .data("Success NoticeBoard Posting")
                .build();
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }

    /**
     * 공지게시판 수정페이지 이동
     * @param boardNo
     * @param model
     * @return
     */
    @GetMapping("/notice/modify/{boardNo}")
    public String getNoticeModifyView(@PathVariable Long boardNo,
                                      Model model) {

        BoardDTO dto = noticeService.getNoticeModify(boardNo);

        model.addAttribute("dto", dto);
        return "board/notice/noticeModify";
    }

    /**
     * 공지게시판 게시글 수정
     * @param boardNo
     * @param boardDTO
     * @return
     */
    @PutMapping("/notice/modify/{boardNo}")
    public ResponseEntity<ApiResponseDTO<?>> modifyArticle(@PathVariable Long boardNo,
                                @RequestBody BoardDTO boardDTO) {
        // TODO: 2023/04/03 세션 처리

        boardDTO.setBoardNo(boardNo);

        try {
            noticeService.modifyNoticeArticle(boardDTO);

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
     * 공지게시판 게시글 삭제
     * @param boardNo
     * @return
     */
    @DeleteMapping("/notice/delete/{boardNo}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long boardNo) {

        // TODO: 2023/04/03 세션처리
        try {
            noticeService.deleteNoticeArticle(boardNo);

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
