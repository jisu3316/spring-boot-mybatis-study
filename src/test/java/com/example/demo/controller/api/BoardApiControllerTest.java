package com.example.demo.controller.api;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.request.BoardFormRequest;
import com.example.demo.model.Board;
import com.example.demo.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("뷰 컨트롤러 테스트")
@WebMvcTest(BoardApiController.class)
class BoardApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;

    @DisplayName("[POST] - 새 게시글 작성")
    @Test
    void 게시글작성() throws Exception {
        // given

        // when & then
        mvc.perform(post("/api/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createBoardFormRequest()))
        ).andDo(print())
         .andExpect(status().isOk());
    }

    @DisplayName("[PUT] - 게시글 수정")
    @Test
    void 게시글수정() throws Exception {
       //given
       Integer boardId = 1;
       BoardDto boardDto = createBoardDto();
       given(boardService.updateBoard(boardId, boardDto)).willReturn(boardId);

       // when & then
        mvc.perform(put("/api/board/" + boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createBoardFormRequest()))
                ).andDo(print())
                .andExpect(status().isOk());
        then(boardService).should().updateBoard(boardId, boardDto);
    }

    @DisplayName("게시글 삭제하기")
    @Test
    void 게시글삭제() throws Exception{
        //given
        Integer boardId = 1;
        BoardFormRequest boardFormRequest = createBoardFormRequest();
//        willDoNothing().given(boardService).deleteBoard(boardId, boardFormRequest.getBoardPassword());
        // when & then
        mvc.perform(delete("/api/board/" + boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(boardFormRequest))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    private Board createBoard() {
        return Board.builder()
                .boardId(1)
                .boardTitle("제목")
                .boardContent("내용")
                .boardUserName("boardUserName")
                .boardPassword("1234")
                .createAt(LocalDateTime.now())
                .deleteYN("N")
                .build();
    }

    private BoardDto createBoardDto() {
        BoardDto dto = BoardDto.builder()
                .boardId(1)
                .boardTitle("제목")
                .boardContent("내용")
                .boardUserName("김지수")
                .boardPassword("1234")
                .build();
        return dto;
    }
    private BoardFormRequest createBoardFormRequest() {
        return BoardFormRequest.of(
                1,
                "제목",
                "내용",
                "김지수",
                "1234"
        );
    }
}