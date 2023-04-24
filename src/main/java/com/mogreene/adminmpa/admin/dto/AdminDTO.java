package com.mogreene.adminmpa.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
    @NotBlank
    private String username;

    /**
     * 관리자 비밀번호
     */
    @NotBlank
    private String password;

    /**
     * 자동로그인
     */
    private boolean rememberMe;
}
