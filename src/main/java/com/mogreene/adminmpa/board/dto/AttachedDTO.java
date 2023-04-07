package com.mogreene.adminmpa.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 파일 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachedDTO {

    /**
     * 게시글 pk
     */
    private Long boardNo;

    /**
     * 파일 실제 이름
     */
    private String attachedOriginalName;

    /**
     * 파일 암호화 이름
     */
    private String attachedName;

    /**
     * 파일 경로
     */
    private String attachedPath;
}
