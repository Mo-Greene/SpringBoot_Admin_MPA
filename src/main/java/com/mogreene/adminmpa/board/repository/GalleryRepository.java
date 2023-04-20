package com.mogreene.adminmpa.board.repository;

import com.mogreene.adminmpa.board.dto.AttachedDTO;
import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.dto.page.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 갤러리 게시글 개수
     * @param pageRequestDTO
     * @return
     */
    int totalGalleryCount(PageRequestDTO pageRequestDTO);

    /**
     * 갤러리 전체조회
     * @param pageRequestDTO
     * @return
     */
    List<BoardDTO> getGalleryArticle(PageRequestDTO pageRequestDTO);

    /**
     * 갤러리 특정게시글
     * @param boardNo
     * @return
     */
    BoardDTO getGalleryViewArticle(Long boardNo);

    /**
     * 이미지 조회
     * @param boardNo
     * @return
     */
    AttachedDTO getImage(Long boardNo);

    /**
     * 게시글 삭제
     * @param boardNo
     */
    int deleteGalleryArticle(Long boardNo);
}
