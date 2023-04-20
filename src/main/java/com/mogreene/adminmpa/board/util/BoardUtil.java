package com.mogreene.adminmpa.board.util;

import com.mogreene.adminmpa.board.dto.AttachedDTO;
import com.mogreene.adminmpa.board.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * 게시판 공통 util
 * @author mogreene
 */
@Component
public class BoardUtil {

    //파일 저장 경로
    @Value("${mogreene.upload.path}")
    private String uploadPath;

    /**
     * 게시글 postmapping 작성자 추가
     * @param boardDTO
     * @param session
     */
    public void setBoardWriter(BoardDTO boardDTO, HttpSession session) {

        String admin = (String) session.getAttribute("admin");
        boardDTO.setBoardWriter(admin);
    }

    /**
     * 제목 40자 이상일시 요약
     * @param list
     */
    public void skipTitle(List<BoardDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getBoardTitle().length() > 40) {
                String title = list.get(i).getBoardTitle().substring(0, 40) + "...";
                list.get(i).setBoardTitle(title);
            }
        }
    }

    /**
     * 파일 업로드 메서드
     * @param boardDTO
     * @param file
     * @return
     */
    public AttachedDTO uploadFile(BoardDTO boardDTO, MultipartFile file) {
        String originalFileName = file.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();
        assert originalFileName != null;
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String saveFileName = uuid + extension;

        String folderCategoryName = boardDTO.getCategoryBoard();
        String folderPath = makeFolder(folderCategoryName);

        String filePath = uploadPath + folderPath + saveFileName;

        return AttachedDTO.builder()
                .boardNo(boardDTO.getBoardNo())
                .attachedOriginalName(originalFileName)
                .attachedName(saveFileName)
                .attachedPath(filePath)
                .build();
    }

    /**
     * 파일 저장시 날짜별 폴더 만들어서 보관
     * @return
     */
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
