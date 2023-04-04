package com.mogreene.adminmpa.board.dto.page;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 페이지네이션 반환
 */
@Getter
@ToString
public class PageResponseDTO {

    /**
     * 현재 페이지
     */
    private int page;

    /**
     * 페이지당 게시글 수
     */
    private int size;

    /**
     * 데이터 개수
     */
    private int total;

    /**
     * 시작 페이지
     */
    private int startPage;

    /**
     * 마지막 페이지
     */
    private int endPage;

    /**
     * 이전페이지 존재 여부
     */
    private boolean prev;

    /**
     * 다음페이지 존재 여부
     */
    private boolean next;

    @Builder(builderMethodName = "pagination")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, int total) {
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.endPage = (int)(Math.ceil((double) this.page / 10)) * 10;
        this.startPage = endPage - 9;
        /* 총 게시글이 endPage 보다 적을 경우 */
        int last = (int)(Math.ceil((total / (double) size)));
        this.endPage = Math.min(endPage, last);
        this.prev = this.startPage > 1;
        this.next = total > this.endPage * this.size;
    }
}
