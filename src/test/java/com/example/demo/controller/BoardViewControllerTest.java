package com.example.demo.controller;

import com.example.demo.dto.request.SearchBoardRequest;
import com.example.demo.dto.response.BoardResponse;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;
import com.example.demo.service.PaginationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


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

    @DisplayName("보드 리스트 테스트")
    @Test
    void givenNothing_whenRequestIndexPage_thenReturnsIndexPage() throws Exception {
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

}