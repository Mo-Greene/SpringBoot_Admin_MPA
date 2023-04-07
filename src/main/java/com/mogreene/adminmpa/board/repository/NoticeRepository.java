package com.mogreene.adminmpa.board.repository;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.dto.page.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 공지 게시판 repository
 * @author mogreene
 */
@Mapper
public interface NoticeRepository {

    /**
     * 공지게시판 등록
     * @param boardDTO
     */
    void postNoticeArticle(BoardDTO boardDTO);

    /**
     * 게시글 개수
     * @param pageRequestDTO
     * @return
     */
    int totalNoticeCount(PageRequestDTO pageRequestDTO);

    /**
     * 공지게시판 전체조회
     * @return
     */
    List<BoardDTO> getNoticeArticle(PageRequestDTO pageRequestDTO);

    /**
     * 공지게시판 특정게시글 조회
     * @param boardNo
     * @return
     */
    BoardDTO getNoticeViewArticle(Long boardNo);

    /**
     * 공지게시판 게시글 삭제
     * @param boardNo
     * @return
     */
    int deleteNotice(Long boardNo);
}
