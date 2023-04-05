package com.mogreene.adminmpa.board.util;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoardUtilMethod {

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
