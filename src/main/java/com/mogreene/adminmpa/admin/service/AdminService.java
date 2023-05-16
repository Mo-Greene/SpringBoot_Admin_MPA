package com.mogreene.adminmpa.admin.service;

import com.mogreene.adminmpa.admin.dto.AdminDTO;
import com.mogreene.adminmpa.admin.repository.AdminRepository;
import com.mogreene.adminmpa.common.config.SHA512;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.NoSuchAlgorithmException;

/**
 * admin Service
 * @author mogreene
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final SHA512 sha512;

    /**
     * 관리자 로그인
     * @param adminDTO
     * @return AdminDTO
     */
    public void loginAdmin(AdminDTO adminDTO) throws UserPrincipalNotFoundException, NoSuchAlgorithmException {

        //암호 복호화
        adminDTO.setPassword(sha512.encrypt(adminDTO.getPassword()));

        AdminDTO admin = adminRepository.loginAdmin(adminDTO);

        if (admin == null) {
            log.error("관리자 아이디 혹은 비밀번호가 아닙니다.");
            throw new UserPrincipalNotFoundException("관리자 아이디 혹은 비밀번호가 아닙니다.");
        }
    }
}
