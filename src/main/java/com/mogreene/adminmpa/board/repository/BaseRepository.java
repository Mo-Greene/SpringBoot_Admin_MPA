package com.mogreene.adminmpa.board.repository;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 부모 Board repository
 * @author mogreene
 */
@Mapper
public interface BaseRepository {

    /**
     * 게시글 등록
     * @param boardDTO
     * @return boardNo
     */
    int postArticle(BoardDTO boardDTO);

    /**
     * 조회수 증가
     */
    void viewUpdate(Long boardNo);

    /**
     * 게시글 수정
     * @param boardDTO
     */
    int updateArticle(BoardDTO boardDTO);

    List<Map<String, Object>> newArticleByDate();

    List<BoardDTO> getMainFreeArticle();
    List<BoardDTO> getMainNoticeArticle();
    List<BoardDTO> getMainGalleryArticle();
    List<BoardDTO> getMainAttachedArticle();
}
