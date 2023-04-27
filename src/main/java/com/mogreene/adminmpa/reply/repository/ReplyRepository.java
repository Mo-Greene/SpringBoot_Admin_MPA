package com.mogreene.adminmpa.reply.repository;

import com.mogreene.adminmpa.reply.dto.ReplyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 댓글 repository
 * @author mogreene
 */
@Mapper
public interface ReplyRepository {

    /**
     * 댓글 등록
     * @param replyDTO
     */
    int postReply(ReplyDTO replyDTO);

    /**
     * 댓글 조회
     * @param boardNo
     * @return
     */
    List<ReplyDTO> getReply(Long boardNo);
}
