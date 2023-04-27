package com.mogreene.adminmpa.board.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

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
     * 게시글 몇번째 부터 보여줄지(mybatis에서 사용)
     */
    public int getSkip() {
        return (page - 1) * 10;
    }

    /* 검색조건 */
    /**
     * 날짜 시작
     */
    private Timestamp startDate;

    /**
     * 날짜 종료
     */
    private Timestamp endDate;

    /**
     * 카테고리 검색
     */
    private String categoryBoard;

    /**
     * 검색어
     */
    private String keyword;

    /**
     * 페이지 이동 link
     */
    private String link;

    /**
     * link 값 파싱 후 전달
     * @return
     */
    public String getLink() {
        StringBuilder builder = new StringBuilder();
        builder.append("page=" + this.page);

        if (categoryBoard != null) {
            builder.append("&categoryBoard=" + URLEncoder.encode(categoryBoard, StandardCharsets.UTF_8));
        }

        if (startDate != null) {
            builder.append("&startDate=" + startDate);
        }

        if (endDate != null) {
            builder.append("&endDate=" + endDate);
        }

        if (keyword != null) {
            builder.append("&keyword=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8));
        }

        return builder.toString();
    }
}
