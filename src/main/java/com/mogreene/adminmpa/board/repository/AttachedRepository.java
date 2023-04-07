package com.mogreene.adminmpa.board.repository;

import com.mogreene.adminmpa.board.dto.AttachedDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 자료실 repository
 * @author mogreene
 */
@Mapper
public interface AttachedRepository {

    /**
     * 자료실 게시글 등록
     */
    void postAttachedArticle(AttachedDTO attachedDTO);
}
