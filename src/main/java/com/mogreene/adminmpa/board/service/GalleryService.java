package com.mogreene.adminmpa.board.service;

import com.mogreene.adminmpa.board.dto.AttachedDTO;
import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.repository.BaseRepository;
import com.mogreene.adminmpa.board.repository.GalleryRepository;
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
import java.util.UUID;

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

    //파일 저장 경로
    @Value("${mogreene.upload.path}")
    private String uploadPath;

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

        String imageOriginalName = file.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();
        assert imageOriginalName != null;
        String extension = imageOriginalName.substring(imageOriginalName.lastIndexOf("."));

        String imageName = uuid + extension;

        String folderCategoryName = boardDTO.getCategoryBoard();
        String folderPath = makeFolder(folderCategoryName);

        String imagePath = uploadPath + folderPath + imageName;

        AttachedDTO attachedDTO = AttachedDTO.builder()
                .boardNo(boardDTO.getBoardNo())
                .attachedOriginalName(imageOriginalName)
                .attachedName(imageName)
                .attachedPath(imagePath)
                .build();

        file.transferTo(new File(imagePath));
        galleryRepository.postImage(attachedDTO);
    }

    /**
     * 파일 저장시 날짜별 폴더 만들어서 보관
     * @return
     */
    // TODO: 2023/04/19 공통 유틸로 뽑야야됨
    public String makeFolder(String category) {

        String categoryFolder = category + "/" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd/"));
        String folderPath = categoryFolder.replace("/", File.separator);
        File uploadPathFolder = new File(uploadPath, folderPath);

        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }
}
