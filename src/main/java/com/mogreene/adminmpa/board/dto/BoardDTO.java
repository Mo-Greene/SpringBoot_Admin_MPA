package com.mogreene.adminmpa.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 자유게시판 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    /**
     * pk 게시글
     */
    private Long boardNo;

    /**
     * 게시글 제목
     */
    private String boardTitle;

    /**
     * 게시글 내용
     */
    private String boardContent;

    /**
     * 게시글 작성자
     */
    private String boardWriter;

    /**
     * 게시글 조회수
     */
    private int boardView;

    /**
     * 게시글 생성일
     */
    private String boardRegDate;

    /**
     * 게시글 수정일
     */
    private String boardModDate;

    /**
     * 게시글 분류 카테고리
     */
    private String categoryBoard;
}
