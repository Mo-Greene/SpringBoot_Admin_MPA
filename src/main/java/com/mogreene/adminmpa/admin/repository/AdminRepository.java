package com.mogreene.adminmpa.admin.repository;

import com.mogreene.adminmpa.admin.dto.AdminDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * admin repository
 * @author mogreene
 */
@Mapper
public interface AdminRepository {

    /**
     * 관리자 로그인
     * @param adminDTO
     * @return AdminDTO
     */
    AdminDTO loginAdmin (AdminDTO adminDTO);

}
