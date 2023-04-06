package com.mogreene.adminmpa.board.service;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.dto.page.PageRequestDTO;
import com.mogreene.adminmpa.board.dto.page.PageResponseDTO;
import com.mogreene.adminmpa.board.repository.BaseRepository;
import com.mogreene.adminmpa.board.repository.NoticeRepository;
import com.mogreene.adminmpa.board.util.BoardUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 공지게시판 Service
 * @author mogreene
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final BaseRepository baseRepository;
    private final NoticeRepository noticeRepository;
    private final BoardUtil utilMethod;

    /**
     * 공지게시판 등록
     * @param boardDTO
     */
    public void postNotice(BoardDTO boardDTO) {

        //base_board 등록
        baseRepository.postArticle(boardDTO);
        //board_notice 등록
        noticeRepository.postNoticeArticle(boardDTO);
    }

    /**
     * 공지게시판 전체조회
     */
    public List<BoardDTO> getNoticeArticle(PageRequestDTO pageRequestDTO) {

        List<BoardDTO> list = noticeRepository.getNoticeArticle(pageRequestDTO);
        utilMethod.skipTitle(list);

        return list;
    }

    /**
     * 공지게시판 페이지네이션
     * @param pageRequestDTO
     * @return
     */
    public PageResponseDTO pagination(PageRequestDTO pageRequestDTO) {

        int total = noticeRepository.totalNoticeCount(pageRequestDTO);

        return PageResponseDTO.pagination()
                .pageRequestDTO(pageRequestDTO)
                .total(total)
                .build();
    }

    /**
     * 공지게시판 특정게시글 조회
     * @param boardNo
     * @return
     */
    public BoardDTO getNoticeViewArticle(Long boardNo) {

        //조회수 증가
        baseRepository.viewUpdate(boardNo);

        return noticeRepository.getNoticeViewArticle(boardNo);
    }

    /**
     * 공지게시판 특정게시글 수정조회(조회수 증가x)
     * @param boardNo
     * @return
     */
    public BoardDTO getNoticeModify(Long boardNo) {

        return noticeRepository.getNoticeViewArticle(boardNo);
    }

    /**
     * 공지게시판 게시글 수정
     * @param boardDTO
     */
    public void modifyNoticeArticle(BoardDTO boardDTO) throws IllegalArgumentException {

        int baseBoardModifyCheck = baseRepository.updateArticle(boardDTO);
        int noticeBoardModifyCheck = noticeRepository.updateNoticeArticle(boardDTO);

        if (baseBoardModifyCheck == 0 || noticeBoardModifyCheck == 0) {
            throw new IllegalArgumentException("게시글 수정 실패");
        }
    }

    /**
     * 공지게시판 게시글 삭제
     * @param boardNo
     * @throws IllegalArgumentException
     */
    public void deleteNoticeArticle(Long boardNo) throws IllegalArgumentException {

        int deleteCheck = noticeRepository.deleteNotice(boardNo);

        if (deleteCheck == 0) {
            throw new IllegalArgumentException("게시글 삭제 실패");
        }
    }

}
