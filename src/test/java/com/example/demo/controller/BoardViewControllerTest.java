package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.request.SearchBoardRequest;
import com.example.demo.dto.response.BoardResponse;
import com.example.demo.dto.response.CommentResponse;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;
import com.example.demo.service.PaginationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("뷰 컨트롤러 테스트")
@WebMvcTest(BoardViewController.class)
class BoardViewControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BoardService boardService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private PaginationService paginationService;

    @DisplayName("[GET] - 인덱스 페이지 테스트")
    @Test
    void givenNothing_whenRequestingIndexPage_thenRedirectsToBoardListPage() throws Exception {
        // given

        // when & then
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/view/board"))
                .andExpect(forwardedUrl("/view/board"))
                .andDo(MockMvcResultHandlers.print());

    }

    @DisplayName("[GET] - 보드 리스트 페이지 테스트")
    @Test
    void givenNothing_whenRequestBoardListPage_thenReturnsBoardListPage() throws Exception {
        System.out.println("mvc = " + mvc);
        // given
        given(boardService.boards(any(SearchBoardRequest.class))).willReturn(List.of());
//        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4, 5));

        // when & then
        mvc.perform(get("/view/board"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/list"))
                .andExpect(model().attributeExists("boards"))
                .andExpect(model().attributeExists("search"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("paginationBarNumbers"))
                .andExpect(model().attributeExists("total"))
                .andDo(MockMvcResultHandlers.print());
        then(boardService).should().boards(any(SearchBoardRequest.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
        then(paginationService).should().getEndNumber(anyInt(), anyInt());
    }

    @DisplayName("[GET] - 게시글 작성 페이지")
    @Test
    void givenNothing_whenRequestingNewBoardPage_thenReturnsNewBoardPage() throws Exception {
        // given

        // when & then
        mvc.perform(get("/view/board/new")
                        .param("page", String.valueOf(1))
                )
                .andExpect(view().name("board/new-board"))
                .andExpect(model().attributeExists("page"))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("[GET] - 게시글 상세 페이지")
    @Test
    void givenNothing_whenRequestingBoardView_thenReturnsBoardView() throws Exception {
        // given
        Integer boardId = 1;
        BoardDto boardDto = createBoardDto();
        given(boardService.getBoard(boardId)).willReturn(boardDto);
        given(commentService.getComments(boardId)).willReturn(List.of());

        // when & then
        mvc.perform(get("/view/board/" + boardId)
                        .param("page", String.valueOf(1))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("board/detail"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(model().attributeExists("board"))
                .andExpect(model().attributeExists("page"))
                .andDo(MockMvcResultHandlers.print());
        then(boardService).should().getBoard(boardId);
        then(commentService).should().getComments(boardId);
    }

    @DisplayName("[GET] - 게시글 수정 페이지")
    @Test
    void givenNothing_whenRequesting_thenReturnsUpdateBoardView() throws Exception {
        // given
        Integer boardId = 1;
        BoardDto boardDto = createBoardDto();
        given(boardService.getBoard(boardId)).willReturn(boardDto);

        // when & then
        mvc.perform(get("/view/board/" + boardId)
                        .param("page", String.valueOf(1))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("board/detail"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(model().attributeExists("board"))
                .andExpect(model().attributeExists("page"))
                .andDo(MockMvcResultHandlers.print());
        then(boardService).should().getBoard(boardId);
    }


    private BoardDto createBoardDto() {
        return BoardDto.builder()
                .boardId(1)
                .boardTitle("제목")
                .boardContent("내용")
                .boardUserName("boardUserName")
                .boardPassword("1234")
                .createAt(LocalDateTime.now())
                .deleteYN("N")
                .build();
    }

    private BoardResponse createBoardResponse() {
        return BoardResponse.builder()
                .boardId(1)
                .boardTitle("제목")
                .boardContent("내용")
                .boardUserName("boardUserName")
                .createAt(LocalDateTime.now())
                .deleteYN("N")
                .build();
    }
}