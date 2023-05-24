package com.mogreene.adminmpa.board.service;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 메인페이지 Service
 *
 * @author mogreene
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BaseRepository baseRepository;

    /**
     * 메인페이지 자유게시판 게시글
     * @return
     */
    public List<BoardDTO> getMainFreeArticle() {

        return baseRepository.getMainFreeArticle();
    }

    public List<BoardDTO> getMainNoticeArticle() {

        return baseRepository.getMainNoticeArticle();
    }

    public List<BoardDTO> getMainGalleryArticle() {

        return baseRepository.getMainGalleryArticle();
    }

    public List<BoardDTO> getMainAttachedArticle() {

        return baseRepository.getMainAttachedArticle();
    }
}

