package com.mogreene.adminmpa.board.service;

import com.mogreene.adminmpa.board.dto.AttachedDTO;
import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.dto.page.PageRequestDTO;
import com.mogreene.adminmpa.board.dto.page.PageResponseDTO;
import com.mogreene.adminmpa.board.repository.BaseRepository;
import com.mogreene.adminmpa.board.repository.GalleryRepository;
import com.mogreene.adminmpa.board.util.BoardUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * 갤러리 service
 * @author mogreene
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GalleryService {

    private final BaseRepository baseRepository;
    private final GalleryRepository galleryRepository;
    private final BoardUtil boardUtil;

    /**
     * 갤러리 전체조회
     * @param pageRequestDTO
     * @return
     */
    public List<BoardDTO> getGalleryArticle(PageRequestDTO pageRequestDTO) {

        List<BoardDTO> list = galleryRepository.getGalleryArticle(pageRequestDTO);

        boardUtil.skipTitle(list);

        return list;
    }

    /**
     * 갤러리 페이지네이션
     * @param pageRequestDTO
     * @return
     */
    public PageResponseDTO pagination(PageRequestDTO pageRequestDTO) {

        int total = galleryRepository.totalGalleryCount(pageRequestDTO);

        return PageResponseDTO.pagination()
                .pageRequestDTO(pageRequestDTO)
                .total(total)
                .build();
    }

    /**
     * 갤러리 특정게시글 조회
     * @param boardNo
     * @return
     */
    public BoardDTO getGalleryViewArticle(Long boardNo) {

        //조회수 증가
        baseRepository.viewUpdate(boardNo);

        return galleryRepository.getGalleryViewArticle(boardNo);
    }

    /**
     * 갤러리 특정게시글 수정페이지 조회(조회수 증가 x)
     * @param boardNo
     * @return
     */
    public BoardDTO getGalleryModify(Long boardNo) {

        return galleryRepository.getGalleryViewArticle(boardNo);
    }

    /**
     * 이미지 파일
     * @param boardNo
     * @return
     */
    public AttachedDTO getImage(Long boardNo) {

        return galleryRepository.getImage(boardNo);
    }

    /**
     * 이미지 파일 반환
     * @param boardNo
     * @return
     * @throws MalformedURLException
     */
    public Resource getImageFile(Long boardNo) throws MalformedURLException {

        AttachedDTO attachedDTO = galleryRepository.getImage(boardNo);

        return new UrlResource("file:" + attachedDTO.getAttachedPath());
    }

    /**
     * 게시글 등록
     * @param boardDTO
     */
    public void postGalleryArticle(BoardDTO boardDTO) {

        baseRepository.postArticle(boardDTO);
    }

    /**
     * 이미지 파일 업로드
     * @param boardDTO
     * @param file
     */
    public void uploadImage(BoardDTO boardDTO, MultipartFile file) throws IOException {

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        assert extension != null;

        //갤러리 확장자 검증
        if (extension.equalsIgnoreCase("jpg") ||
                extension.equalsIgnoreCase("jpeg") ||
                    extension.equalsIgnoreCase("png")) {

            AttachedDTO attachedDTO = boardUtil.uploadFile(boardDTO, file);

            file.transferTo(new File(attachedDTO.getAttachedPath()));
            galleryRepository.postImage(attachedDTO);
        } else {
            throw new IllegalArgumentException("맞지 않는 확장자 입니다. 확인 후 재시도 해주세요.");
        }
    }

    /**
     * 갤러리 게시글 수정
     * @param boardDTO
     */
    public void modifyGalleryArticle(BoardDTO boardDTO) {

        int baseBoardModifyCheck = baseRepository.updateArticle(boardDTO);

        if (baseBoardModifyCheck == 0) {
            throw new IllegalArgumentException("갤러리 수정 실패");
        }
    }

    /**
     * 게시글 삭제
     * @param boardNo
     */
    public void deleteGalleryArticle(Long boardNo) {

        int deleteCheck = galleryRepository.deleteGalleryArticle(boardNo);

        if (deleteCheck == 0) {
            throw new IllegalArgumentException("갤러리 삭제 실패");
        }
    }
}
