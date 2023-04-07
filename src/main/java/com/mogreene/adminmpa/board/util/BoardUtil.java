package com.mogreene.adminmpa.board.util;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 게시판 공통 util
 * @author mogreene
 */
@Component
public class BoardUtil {

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

}
