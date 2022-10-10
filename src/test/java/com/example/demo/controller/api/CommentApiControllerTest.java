package com.example.demo.controller.api;

import com.example.demo.dto.CommentDto;
import com.example.demo.dto.request.CommentRequest;
import com.example.demo.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("댓글 API 컨트롤러 테스트")
@WebMvcTest(CommentApiController.class)
class CommentApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    @DisplayName("댓글 작성")
    @Test
    void givenCommentInfo_whenRequesting_thenCreateNewComment() throws Exception {
        // given
        Integer boardId = 1;
        CommentRequest commentRequest = createCommentRequest();
        willDoNothing().given(commentService).createComment(any(CommentDto.class));

        // when & then
        mvc.perform(post("/api/comment")
                        .content(objectMapper.writeValueAsBytes(commentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
        then(commentService).should().createComment(any(CommentDto.class));
    }

    @DisplayName("댓글 수정")
    @Test
    void givenUpdateCommentInfo_whenUpdatingComment_thenComment() throws Exception {
        // given
        Integer commentId = 1;
        CommentRequest commentRequest = createCommentRequest();
        CommentDto commentDto = createCommentDto();
        willDoNothing().given(commentService).updateComment(commentId, commentDto);

        // when & then
        mvc.perform(put("/api/comment/" + commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(commentRequest))
                ).andDo(print())
                .andExpect(status().isOk());
        then(commentService).should().updateComment(commentId, commentDto);
    }

    @DisplayName("댓글 삭제")
    @Test
    void givenCommentIdAndPassword_whenRequesting_thenCommentDelete() throws Exception {
        // given
        Integer commentId = 1;
        String password = "1234";
        CommentRequest commentRequest = createCommentRequest();
        given(commentService.deleteComment(commentId, password)).willReturn(List.of());
        // when & then
        mvc.perform(
                        delete("/api/comment/" + commentId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(commentId))
                                .content(objectMapper.writeValueAsBytes(commentRequest))
                ).andDo(print())
                .andExpect(status().isOk());
        then(commentService).should().deleteComment(commentId, commentRequest.getCommentPassword());
    }

    @DisplayName("답글 작성")
    @Test
    void givenReCommentInfo_whenRequesting_thenCreateNewReComment() throws Exception {
        // given
        Integer commentId = 1;
        CommentRequest commentRequest = createCommentRequest();
        willDoNothing().given(commentService).createComment(any(CommentDto.class));

        // when & then
        mvc.perform(post("/api/comment/" + commentId)
                        .content(objectMapper.writeValueAsBytes(commentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
        then(commentService).should().reComment(commentId, createCommentDto());
    }

    private CommentDto createCommentDto() {
        return CommentDto.builder()
                .commentId(1)
                .boardId(1)
                .comment("내용")
                .commentUserName("김지수")
                .commentPassword("1234")
                .ref(1)
                .step(0)
                .refOrder(0)
                .answerNum(0)
                .parentNum(0)
                .build();
    }

    private CommentRequest createCommentRequest() {
        return new CommentRequest(
                1,
                1,
                "내용",
                "김지수",
                "1234",
                1,
                0,
                0,
                0,
                0
        );
    }
}