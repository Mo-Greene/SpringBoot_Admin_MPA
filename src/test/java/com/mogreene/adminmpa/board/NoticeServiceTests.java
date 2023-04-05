package com.mogreene.adminmpa.board;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.service.NoticeService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
public class NoticeServiceTests {

    @Autowired
    private NoticeService noticeService;

    @Test
    public void testRegister() {

        IntStream.rangeClosed(1,100).forEach(i -> {
            BoardDTO boardDTO = BoardDTO.builder()
                    .boardTitle("더미데이타.." + i)
                    .boardWriter("me")
                    .categoryBoard("NOTICE")
                    .boardContent("내용이용.." + i)
                    .build();

            noticeService.postNotice(boardDTO);
        });
    }
}
