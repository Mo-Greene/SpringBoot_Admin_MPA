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
     * 자유 게시판 특정게시글 수정조회
     * @param boardNo
     * @return
     */
    public BoardDTO getFreeModify(Long boardNo) {

        return freeRepository.getFreeViewArticle(boardNo);
    }

    public void modifyFreeArticle(BoardDTO boardDTO) {

        // TODO: 2023/04/02 해결 방안있는지 생각
        int baseBoardModifyCheck = baseRepository.updateArticle(boardDTO);
        int freeBoardModifyCheck = freeRepository.updateFreeArticle(boardDTO);

        if (baseBoardModifyCheck == 0 || freeBoardModifyCheck == 0) {
            throw new IllegalArgumentException("게시글 수정 실패");
        }
    }

    /**
     * 자유 게시판 특정게시글 삭제
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
