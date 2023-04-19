package com.mogreene.adminmpa.board.repository;

import com.mogreene.adminmpa.board.dto.AttachedDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 갤러리
 * @author mogreene
 */
@Mapper
public interface GalleryRepository {

    /**
     * 이미지 파일 저장
     * @param attachedDTO
     */
    void postImage(AttachedDTO attachedDTO);
}
