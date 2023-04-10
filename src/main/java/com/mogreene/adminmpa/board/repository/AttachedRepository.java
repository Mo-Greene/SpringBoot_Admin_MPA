package com.mogreene.adminmpa.board.repository;

import com.mogreene.adminmpa.board.dto.AttachedDTO;
import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.dto.page.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 자료실 repository
 * @author mogreene
 */
@Mapper
public interface AttachedRepository {

    /**
     * 자료실 게시글 등록
     */
    void postAttachedArticle(AttachedDTO attachedDTO);

    /**
     * 게시글 개수
     * @param pageRequestDTO
     * @return
     */
    int totalAttachedCount(PageRequestDTO pageRequestDTO);

    /**
     * 자료실 전체조회 + 페이지네이션
     * @param pageRequestDTO
     * @return
     */
    List<BoardDTO> getAttachedArticle(PageRequestDTO pageRequestDTO);

    /**
     * 자료실 특정게시글 조회
     * @param boardNo
     * @return
     */
    BoardDTO getAttachedViewArticle(Long boardNo);

    /**
     * 자료실 게시글 삭제
     * @param boardNo
     * @return
     */
    int deleteAttached(Long boardNo);

    /**
     * 첨부파일 조회
     * @param boardNo
     * @return
     */
    List<AttachedDTO> getAttached(Long boardNo);

    /**
     * 첨부파일 다운로드
     * @param attachedNo
     * @return
     */
    AttachedDTO downloadAttached(Long attachedNo);
}
