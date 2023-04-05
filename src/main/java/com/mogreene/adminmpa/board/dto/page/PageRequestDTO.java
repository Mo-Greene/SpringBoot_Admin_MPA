package com.mogreene.adminmpa.board.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * 페이지네이션 + 검색조건
 * @author mogreene
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO {

    /**
     * 현재 페이지
     */
    @Builder.Default
    @Min(value = 1)
    @Positive
    private int page = 1;

    /**
     * 페이지당 조회 게시글 수
     */
    @Builder.Default
    @Positive
    private int size = 10;

    /**
     * 게시글 몇번째 부터 보여줄리
     */
    public int getSkip() {
        return (page - 1) * 10;
    }

    /* 검색조건 */
    /**
     * 날짜 시작
     */
    private String startDate;

    /**
     * 날짜 종료
     */
    private String endDate;

    /**
     * 카테고리 검색
     */
    private String categoryBoard;

    /**
     * 검색어
     */
    private String keyword;
}
