package com.mogreene.adminmpa.board.service;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // TODO: 2023/05/24 주석 체크!!! 
    public Map<LocalDate, Long> newArticleByDate() {
        List<Map<String, Object>> articleCounts = baseRepository.newArticleByDate();

        Map<LocalDate, Long> result = new HashMap<>();
        for (Map<String, Object> articleCount : articleCounts) {
            LocalDate date = ((java.sql.Date) articleCount.get("date")).toLocalDate();
            long count = (long) articleCount.get("count");
            result.put(date, count);
        }

        return result;
    }

    /**
     * 메인페이지 게시글
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

