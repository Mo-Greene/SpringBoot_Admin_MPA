package com.mogreene.adminmpa.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

/**
 * 댓글 DTO
 * @author mogreene
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {

    /**
     * 댓글 pk
     */
    private Long replyNo;

    /**
     * 댓글 내용
     */
    @Min(value = 3)
    private String replyContent;

    /**
     * 게시글 번호 fk
     */
    private Long boardNo;

    /**
     * 댓글 생성일자
     */
    private String replyRegDate;
}
