package com.mogreene.adminmpa.board.service;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.dto.page.PageRequestDTO;
import com.mogreene.adminmpa.board.dto.page.PageResponseDTO;
import com.mogreene.adminmpa.board.repository.BaseRepository;
import com.mogreene.adminmpa.board.repository.FreeRepository;
import com.mogreene.adminmpa.board.util.BoardUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 자유게시판 service
 * @author mogreene
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FreeService {

    private final BaseRepository baseRepository;
    private final FreeRepository freeRepository;
    private final BoardUtil boardUtil;

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
     * @return
     */
    public List<BoardDTO> getFreeArticle(PageRequestDTO pageRequestDTO) {

        List<BoardDTO> list = freeRepository.getFreeArticle(pageRequestDTO);
        boardUtil.skipTitle(list);

        return list;
    }

    /**
     * 자유 게시판 페이지네이션
     * @param pageRequestDTO
     * @return
     */
    public PageResponseDTO pagination(PageRequestDTO pageRequestDTO) {

        int total = freeRepository.totalFreeCount(pageRequestDTO);

        return PageResponseDTO.pagination()
                .pageRequestDTO(pageRequestDTO)
                .total(total)
                .build();
    }

    /**
     * 자유 게시판 특정게시글 조회
     * @param boardNo
     * @return
     */
    public BoardDTO getFreeViewArticle(Long boardNo) {

        //조회수 증가
        baseRepository.viewUpdate(boardNo);

        return freeRepository.getFreeViewArticle(boardNo);
    }

    /**
     * 자유 게시판 특정게시글 수정조회(조회수 증가x)
     * @param boardNo
     * @return
     */
    public BoardDTO getFreeModify(Long boardNo) {

        return freeRepository.getFreeViewArticle(boardNo);
    }

    /**
     * 자유게시판 게시글 수정
     * @param boardDTO
     */
    public void modifyFreeArticle(BoardDTO boardDTO) throws IllegalArgumentException {

        int baseBoardModifyCheck = baseRepository.updateArticle(boardDTO);

        // TODO: 2023/04/07 예외처리 필
        if (baseBoardModifyCheck == 0) {
            throw new IllegalArgumentException("게시글 수정 실패");
        }
    }

    /**
     * 자유 게시판 게시글 삭제
     * @param boardNo
     */
    // TODO: 2023/04/01 free_board에서만 삭제가 되고 base_board에선 삭제가 안되었음, 결과적으론 성공?
    public void deleteFreeArticle(Long boardNo) throws IllegalArgumentException {

        int deleteCheck = freeRepository.deleteFree(boardNo);

        if (deleteCheck == 0) {
            throw new IllegalArgumentException("게시글 삭제 실패");
        }
    }
}
