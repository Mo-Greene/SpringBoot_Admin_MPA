package com.mogreene.adminmpa.reply.controller;

import com.mogreene.adminmpa.common.api.ApiResponseDTO;
import com.mogreene.adminmpa.reply.dto.ReplyDTO;
import com.mogreene.adminmpa.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
     * 댓글 등록 후 댓글 목록 return
     * @param boardNo
     * @param replyDTO
     * @return
     */
    @PostMapping("/reply/{boardNo}")
    public ResponseEntity<ApiResponseDTO<?>> postReply(@PathVariable Long boardNo,
                                                       @RequestBody ReplyDTO replyDTO) {

        List<ReplyDTO> dtoList = replyService.postReply(boardNo, replyDTO);

        ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                .httpStatus(HttpStatus.CREATED)
                .data(dtoList)
                .build();
        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }
}
