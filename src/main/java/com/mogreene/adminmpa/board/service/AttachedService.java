package com.mogreene.adminmpa.board.service;

import com.mogreene.adminmpa.board.dto.AttachedDTO;
import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.repository.AttachedRepository;
import com.mogreene.adminmpa.board.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Value("${mogreene.upload.path}")
    private String uploadPath;

    /**
     * Base-Board 게시글 등록
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
}
