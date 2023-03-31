package com.mogreene.adminmpa.board.service;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.repository.BaseRepository;
import com.mogreene.adminmpa.board.repository.FreeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 자유게시판 service
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FreeService {

    private final BaseRepository baseRepository;
    private final FreeRepository freeRepository;

    /**
     * 자유 게시판 등록
     * @param boardDTO
     */
    public void postFree(BoardDTO boardDTO) {

        //base_board 등록
        baseRepository.postArticle(boardDTO);
        //board_free 등록
        freeRepository.postFreeArticle(boardDTO);
    }

    /**
     * 자유 게시판 전체조회
     *
     * @return
     */
    public List<BoardDTO> getFreeArticle() {

        return freeRepository.getFreeArticle();
    }
}
