package com.mogreene.adminmpa.board;

import com.mogreene.adminmpa.board.dto.BoardDTO;
import com.mogreene.adminmpa.board.repository.FreeRepository;
import com.mogreene.adminmpa.board.service.FreeService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class FreeServiceTests {

    @Autowired
    private FreeService freeService;

    @Test
    public void testRegister() {

        IntStream.rangeClosed(1,100).forEach(i -> {
            BoardDTO boardDTO = BoardDTO.builder()
                    .boardTitle("더미데이타.." + i)
                    .boardWriter("me")
                    .categoryBoard("FREE")
                    .boardContent("내용이용.." + i)
                    .build();

            freeService.postFree(boardDTO);
        });
    }
}
