package com.mogreene.adminmpa.reply.service;

import com.mogreene.adminmpa.reply.dto.ReplyDTO;
import com.mogreene.adminmpa.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 댓글 service
 * @author mogreene
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    /**
     * 댓글 저장 후 댓글 목록 return
     * @param boardNo
     * @param replyDTO
     * @return
     */
    public List<ReplyDTO> postReply(Long boardNo, ReplyDTO replyDTO) {

        replyDTO.setBoardNo(boardNo);
        replyRepository.postReply(replyDTO);

        return replyRepository.getReply(replyDTO);
    }
}
