package com.mogreene.adminmpa.board.service;

import com.mogreene.adminmpa.board.dto.AttachedDTO;
import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.dto.page.PageRequestDTO;
import com.mogreene.adminmpa.board.dto.page.PageResponseDTO;
import com.mogreene.adminmpa.board.repository.AttachedRepository;
import com.mogreene.adminmpa.board.repository.BaseRepository;
import com.mogreene.adminmpa.board.util.BoardUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 자료실 service
 * @author mogreene
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AttachedService {

    private final BaseRepository baseRepository;
    private final AttachedRepository attachedRepository;
    private final BoardUtil boardUtil;

    /**
     * 자료실 전체조회
     * @param pageRequestDTO
     * @return
     */
    public List<BoardDTO> getAttachedArticle(PageRequestDTO pageRequestDTO) {

        List<BoardDTO> list = attachedRepository.getAttachedArticle(pageRequestDTO);

        boardUtil.skipTitle(list);

        return list;
    }

    /**
     * 자료실 페이지네이션
     * @param pageRequestDTO
     * @return
     */
    public PageResponseDTO pagination(PageRequestDTO pageRequestDTO) {

        int total = attachedRepository.totalAttachedCount(pageRequestDTO);

        return PageResponseDTO.pagination()
                .pageRequestDTO(pageRequestDTO)
                .total(total)
                .build();
    }

    /**
     * 자료실 특정게시글 조회
     * @param boardNo
     * @return
     */
    public BoardDTO getAttachedViewArticle(Long boardNo) {

        //조회수 증가
        baseRepository.viewUpdate(boardNo);

        return attachedRepository.getAttachedViewArticle(boardNo);
    }

    /**
     * 첨부파일 리스트
     * @param boardNo
     * @return
     */
    public List<AttachedDTO> getAttached(Long boardNo) {

        return attachedRepository.getAttached(boardNo);
    }

    /**
     * 첨부파일 개수
     * @param boardNo
     * @return
     */
    public int attachedCount(Long boardNo) {

        return attachedRepository.attachedCount(boardNo);
    }

    /**
     * 자료실 특정게시글 수정조회(조회수 증가x)
     * @param boardNo
     * @return
     */
    public BoardDTO getAttachedModify(Long boardNo) {

        return attachedRepository.getAttachedViewArticle(boardNo);
    }

    /**
     * 자료실 게시글 등록
     * @param boardDTO
     */
    @Transactional
    public void postAttachedArticle(BoardDTO boardDTO, MultipartFile[] files) throws IOException {

        int baseBoardPostCheck = baseRepository.postArticle(boardDTO);

        if (baseBoardPostCheck == 0) {
            throw new IllegalArgumentException("자료실 등록 실패");
        }

        //첨부파일 핸들러
        handleAttached(boardDTO, files);
    }

    /**
     * 자료실 게시글 수정
     * @param boardDTO
     */
    @Transactional
    public void modifyAttachedArticle(BoardDTO boardDTO, MultipartFile[] files) throws IOException {

        int baseBoardModifyCheck = baseRepository.updateArticle(boardDTO);

        if (baseBoardModifyCheck == 0) {
            throw new IllegalArgumentException("자료실 수정 실패");
        }

        if (files != null) {
            //첨부파일 핸들러
            handleAttached(boardDTO, files);
        }
    }

    /**
     * 다중 파일 업로드
     * @param boardDTO
     */
    private void handleAttached(BoardDTO boardDTO, MultipartFile[] files) throws IOException {

        for (MultipartFile file : files) {
            //파일이 존재 하지 않을 경우 넘어감
            if (file.isEmpty()) {
                continue;
            }

            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

            //자료실 파일 확장자 구분
            assert extension != null;
            if (extension.equalsIgnoreCase("hwp") ||
                    extension.equalsIgnoreCase("pdf") ||
                        extension.equalsIgnoreCase("docs")) {

                AttachedDTO attachedDTO = boardUtil.uploadFile(boardDTO, file);
                file.transferTo(new File(attachedDTO.getAttachedPath()));
                attachedRepository.postAttached(attachedDTO);
            } else {
                throw new IllegalArgumentException("맞지 않는 확장자 입니다. 확인 후 재시도 해주세요.");
            }
        }
    }

    /**
     * 첨부파일 다운로드
     * @param attachedNo
     * @return
     */
    public AttachedDTO downloadAttached(Long attachedNo) throws IOException {

        AttachedDTO attachedDTO = attachedRepository.getSingleAttached(attachedNo);
        UrlResource resource = new UrlResource("file:" + attachedDTO.getAttachedPath());
        String encodeName = UriUtils.encode(attachedDTO.getAttachedOriginalName(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeName + "\"";

        attachedDTO.setResource(resource);
        attachedDTO.setContentDisposition(contentDisposition);

        return attachedDTO;
    }

    /**
     * 자료실 게시글 삭제
     * @param boardNo
     * @throws IllegalArgumentException
     */
    public void deleteAttachedArticle(Long boardNo) throws IllegalArgumentException {

        int deleteCheck = attachedRepository.deleteAttachedArticle(boardNo);

        if (deleteCheck == 0) {
            throw new IllegalArgumentException("자료실 게시글 삭제 실패");
        }
    }

    /**
     * 첨부파일 삭제
     * @param attachedNo
     * @return
     */
    @Transactional
    public Long deleteAttached(Long attachedNo) {

        //첨부파일 조회
        AttachedDTO attachedDTO = attachedRepository.getSingleAttached(attachedNo);
        Long boardNo = attachedDTO.getBoardNo();

        //첨부파일 삭제
        File file = new File(attachedDTO.getAttachedPath());
        attachedRepository.deleteSingleAttached(attachedNo);
        boolean isFileDeleted = file.delete();

        if (!isFileDeleted) {
            throw new RuntimeException("파일삭제 오류");
        }

        return boardNo;
    }
}
