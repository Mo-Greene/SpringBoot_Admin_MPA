package com.mogreene.adminmpa.board.controller;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 공지 게시판
 * @author mogreene
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 공지게시판 화면
     * @param model
     * @return
     */
    @GetMapping("/notice")
    public String getNotice(Model model) {

        List<BoardDTO> noticeBoardList = noticeService.getNoticeArticle();

        model.addAttribute("noticeBoardList", noticeBoardList);
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
        // TODO: 2023/04/03 세션 처리

        return "board/notice/noticeWrite";
    }

    /**
     * 공지게시판 게시글 등록
     * @param session
     * @param boardDTO
     * @return
     */
    @PostMapping("/notice/write")
    public String postNotice(BoardDTO boardDTO) {
        // TODO: 2023/04/03 세션처리

        noticeService.postNotice(boardDTO);
        return "redirect:/notice";
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

        // TODO: 2023/04/03 세션 처리

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
    public String modifyArticle(@PathVariable Long boardNo,
                                BoardDTO boardDTO) {
        // TODO: 2023/04/03 세션 처리

        boardDTO.setBoardNo(boardNo);

        try {
            noticeService.modifyNoticeArticle(boardDTO);

        } catch (IllegalArgumentException e) {

            log.error(e.getMessage());
            return "error";
        }

        return "board/notice/noticeList";
    }

    /**
     * 공지게시판 게시글 삭제
     * @param boardNo
     * @return
     */
    @DeleteMapping("/notice/delete/{boardNo}")
    public String deleteNotice(@PathVariable Long boardNo) {

        // TODO: 2023/04/03 세션처리
        try {
            noticeService.deleteNoticeArticle(boardNo);

        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());

            return "error";
        }

        return "board/notice/noticeList";
    }
}
