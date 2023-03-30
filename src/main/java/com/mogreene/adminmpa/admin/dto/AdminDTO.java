package com.mogreene.adminmpa.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * admin DTO
 * @author mogreene
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {

    /**
     * 관리자 아이디
     */
    private String username;

    /**
     * 관리자 비밀번호
     */
    private String password;

}
