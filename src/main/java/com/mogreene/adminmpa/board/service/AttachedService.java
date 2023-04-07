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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

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

    @Value("${mogreene.upload.path}")
    private String uploadPath;

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
     * @return
     */
    public void postAttached(BoardDTO boardDTO) {

        //base_board 등록
        baseRepository.postArticle(boardDTO);
    }

    /**
     * 다중 파일 업로드
     * @param boardDTO
     */
    public void uploadFile(BoardDTO boardDTO, MultipartFile[] files) throws IOException {

        baseRepository.postArticle(boardDTO);

        for (MultipartFile file : files) {
            //파일이 존재 하지 않을 경우 넘어감
            // TODO: 2023/04/07 파일 확장자명 구분 추가해야됨
            if (file.isEmpty()) {
                continue;
            }

            //첨부파일 원본 이름
            String attachedOriginalName = file.getOriginalFilename();

            String uuid = UUID.randomUUID().toString();
            assert attachedOriginalName != null;
            String extension = attachedOriginalName.substring(attachedOriginalName.lastIndexOf("."));

            //첨부파일 수정된 이름
            String attachedName = uuid + extension;

            String folderCategoryName = boardDTO.getCategoryBoard();
            String folderPath = makeFolder(folderCategoryName);

            //첨부파일 저장 경로
            String attachedPath = uploadPath + folderPath + attachedName;

            AttachedDTO attachedDTO = AttachedDTO.builder()
                    .boardNo(boardDTO.getBoardNo())
                    .attachedOriginalName(attachedOriginalName)
                    .attachedName(attachedName)
                    .attachedPath(attachedPath)
                    .build();

            file.transferTo(new File(attachedPath));
            attachedRepository.postAttachedArticle(attachedDTO);
        }
    }

    /**
     * 파일 저장시 날짜별 폴더 만들어서 보관
     * @return
     */
    private String makeFolder(String category) {

        String categoryFolder = category + "/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd/"));
        String folderPath = categoryFolder.replace("/", File.separator);
        File uploadPathFolder = new File(uploadPath, folderPath);

        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    /**
     * 자료실 게시글 수정
     * @param boardDTO
     */
    public void modifyAttachedArticle(BoardDTO boardDTO) {

        int baseBoardModifyCheck = baseRepository.updateArticle(boardDTO);

        // TODO: 2023/04/07 예외처리
        if (baseBoardModifyCheck == 0) {
            throw new IllegalArgumentException("자료실 수정 실패");
        }
    }

    /**
     * 자료실 게시글 삭제
     * @param boardNo
     * @throws IllegalArgumentException
     */
    public void deleteAttachedArticle(Long boardNo) throws IllegalArgumentException {

        int deleteCheck = attachedRepository.deleteAttached(boardNo);

        // TODO: 2023/04/07 예외처리 필
        if (deleteCheck == 0) {
            throw new IllegalArgumentException("자료실 게시글 삭제 실패");
        }
    }
}
