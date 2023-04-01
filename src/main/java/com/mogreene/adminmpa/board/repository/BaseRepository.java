package com.mogreene.adminmpa.board.repository;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 부모 Board repository
 */
@Mapper
public interface BaseRepository {

    /**
     * 게시글 등록
     * @param boardDTO
     * @return boardNo
     */
    void postArticle(BoardDTO boardDTO);

    /**
     * 조회수 증가
     */
    void viewUpdate(Long boardNo);

    /**
     * 게시글 수정
     * @param boardDTO
     */
    int updateArticle(BoardDTO boardDTO);
}
