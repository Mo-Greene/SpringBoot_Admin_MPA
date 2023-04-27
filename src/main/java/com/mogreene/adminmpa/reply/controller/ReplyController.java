package com.mogreene.adminmpa.reply.controller;

import com.mogreene.adminmpa.common.api.ApiResponseDTO;
import com.mogreene.adminmpa.reply.dto.ReplyDTO;
import com.mogreene.adminmpa.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 댓글 비동기 RestController
 * @author mogreene
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    /**
     * 댓글 등록
     * @param boardNo
     * @param replyDTO
     * @return
     */
    @PostMapping("/reply/{boardNo}")
    public ResponseEntity<ApiResponseDTO<?>> postReply(@PathVariable Long boardNo,
                                                       @RequestBody ReplyDTO replyDTO) {

        int success = replyService.postReply(boardNo, replyDTO);

        if (success != 1) {
            throw new IllegalArgumentException("댓글 등록 실패!");
        }

        ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                .httpStatus(HttpStatus.CREATED)
                .data("Reply Create!")
                .build();
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }

    /**
     * 댓글 조회
     * @param boardNo
     * @return
     */
    @GetMapping("/reply/{boardNo}")
    public ResponseEntity<ApiResponseDTO<?>> getReplyList(@PathVariable Long boardNo) {

        List<ReplyDTO> dtoList = replyService.getReply(boardNo);

        ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                .httpStatus(HttpStatus.CREATED)
                .data(dtoList)
                .build();
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }
}
