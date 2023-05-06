package com.mogreene.adminmpa.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * 자유게시판 DTO
 * @author mogreene
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
    @NotBlank(message = "제목을 적어주세요.")
    @Size(min = 3, max = 100, message = "제목은 3자 이상 100자 이하입니다.")
    private String boardTitle;

    /**
     * 게시글 내용
     */
    @NotBlank(message = "내용을 적어주세요.")
    @Size(min = 3, max = 2000, message = "내용은 3자 이상 2000자 이하입니다.")
    private String boardContent;

    /**
     * 게시글 작성자
     */
    private String boardWriter;

    /**
     * 게시글 비밀번호
     */
    private String boardPassword;

    /**
     * 게시글 비밀번호 확인
     */
    private String boardPasswordCheck;

    /**
     * 게시글 조회수
     */
    private int boardView;

    /**
     * 게시글 생성일
     */
    private Timestamp boardRegDate;

    /**
     * 게시글 수정일
     */
    private Timestamp boardModDate;

    /**
     * 게시글 분류 카테고리
     */
    private String categoryBoard;

    /**
     * 파일 존재 유무
     */
    public boolean isExistAttached = false;
}
