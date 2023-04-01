package com.mogreene.adminmpa.board.repository;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 자유게시판 repository
 */
@Mapper
public interface FreeRepository {

    /**
     * 자유 게시판 등록
     * @param boardDTO
     */
    void postFreeArticle(BoardDTO boardDTO);

    /**
     * 자유 게시판 전체조회
     * @return
     */
    List<BoardDTO> getFreeArticle();

    /**
     * 자유 게시판 특정게시글 조회
     * @param boardNo
     * @return
     */
    BoardDTO getFreeViewArticle(Long boardNo);

    /**
     * 자유 게시판 게시글 삭제
     * @param boardNo
     */
    int deleteFree(Long boardNo);

    /**
     * 자유 게시판 게시글 수정
     * @param boardDTO
     * @return
     */
    int updateFreeArticle(BoardDTO boardDTO);
}
