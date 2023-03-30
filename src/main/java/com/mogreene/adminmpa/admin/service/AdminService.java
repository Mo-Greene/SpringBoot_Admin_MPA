package com.mogreene.adminmpa.admin.service;

import com.mogreene.adminmpa.admin.dto.AdminDTO;
import com.mogreene.adminmpa.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * admin Service
 * @author mogreene
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    /**
     * 관리자 로그인
     * @param adminDTO
     * @return AdminDTO
     */
    public AdminDTO loginAdmin(AdminDTO adminDTO) {

        return adminRepository.loginAdmin(adminDTO);
    }
}
