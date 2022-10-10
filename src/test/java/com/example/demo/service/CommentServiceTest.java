package com.example.demo.service;

import com.example.demo.dao.CommentDAO;
import com.example.demo.dto.BoardDto;
import com.example.demo.dto.CommentDto;
import com.example.demo.model.Board;
import com.example.demo.model.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("댓글 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService sut;

    @Mock
    private CommentDAO commentDAO;

    @DisplayName("댓글 정보를 입력하면, 댓글을 생성한다.")
    @Test
    void givenCommentInfo_whenCreateComment_thenCreateComment() {
        //given
        CommentDto commentDto = createCommentDto();
        willDoNothing().given(commentDAO).createComment(any(Comment.class));
        //when
        sut.createComment(commentDto);
        //then
        then(commentDAO).should().createComment(any(Comment.class));
    }

    @DisplayName("댓글 수정 정보를 입력하면, 댓글을 수정한다.")
    @Test
    void givenUpdateCommentInfo_whenUpdatingComment_thenUpdateComment() {
        //given
        Integer commentId = 1;
        Comment comment = createComment();
        CommentDto commentDto = createCommentDto();
        given(commentDAO.getComment(commentId)).willReturn(Optional.of(comment));
        willDoNothing().given(commentDAO).updateComment(any(Comment.class));

        //when
        sut.updateComment(commentId, commentDto);

        //then
        assertThat(comment)
                .hasFieldOrPropertyWithValue("commentId", commentDto.getCommentId())
                .hasFieldOrPropertyWithValue("boardId", commentDto.getBoardId())
                .hasFieldOrPropertyWithValue("comment", commentDto.getComment());
        then(commentDAO).should().getComment(commentId);
        then(commentDAO).should().updateComment(any(Comment.class));
    }

    @DisplayName("댓글 ID와 비밀번호를 입력하면,  댓글을 삭제한다.")
    @Test
    void givenCommentIdAndPassword_whenDeletingComment_thenDeleteComment() {
        //given
        Integer commentId = 1;
        String password = "1234";
        Comment comment = createComment();
        given(commentDAO.getComment(commentId)).willReturn(Optional.of(comment));
        given(commentDAO.findByDeleteCommentId(comment.getCommentId(), comment.getRef())).willReturn(List.of());
        willDoNothing().given(commentDAO).deleteCommentAndRef(comment.getBoardId(), comment.getRef());
        //when
        List<Integer> list = sut.deleteComment(commentId, password);

        //then
        assertThat(list).isEmpty();
        then(commentDAO).should().deleteCommentAndRef(comment.getBoardId(), comment.getRef());
    }

    @DisplayName("대댓글 ID와 비밀번호를 입력하면,  대댓글을 삭제한다.")
    @Test
    void givenReCommentIdAndPassword_whenDeletingReComment_thenDeleteReComment() {
        //given
        Integer commentId = 2;
        String password = "1234";
        Comment reComment = createReComment();
        given(commentDAO.getComment(commentId)).willReturn(Optional.of(reComment));
        willDoNothing().given(commentDAO).deleteComment(reComment.getCommentId());

        //when
        List<Integer> list = sut.deleteComment(commentId, password);

        //then
        assertThat(list.get(0)).isEqualTo(commentId);
        then(commentDAO).should().deleteComment(commentId);
    }

    @DisplayName("첫번째 대댓글 정보를 입력하면, 댓글을 생성한다.")
    @Test
    void givenReCommentInfo_whenCreateReComment_thenCreateReComment() {
        //given
        Integer commentId = 1;
        CommentDto commentDto = createCommentDto();
        Comment comment = createComment();
        given(commentDAO.getComment(commentId)).willReturn(Optional.of(comment));
        given(commentDAO.findBySumAnswerNum(comment.getRef(), comment.getBoardId())).willReturn(comment.getAnswerNum());
        given(commentDAO.findByNvlMaxStep(comment.getRef(), comment.getBoardId())).willReturn(comment.getStep());
//        willDoNothing().given(commentDAO).updateRefOrderPlus(comment.getRef(), comment.getRefOrder()+ comment.getAnswerNum(), comment.getBoardId());
        willDoNothing().given(commentDAO).updateRefOrderPlus(comment.getRef(), comment.getRefOrder(), comment.getBoardId());

        willDoNothing().given(commentDAO).reComment(any(Comment.class));
        willDoNothing().given(commentDAO).updateAnswerNum(commentId, comment.getAnswerNum());

        //when
        sut.reComment(commentId, commentDto);

        //then
        then(commentDAO).should().reComment(any(Comment.class));
        then(commentDAO).should().updateRefOrderPlus(comment.getRef(), comment.getRefOrder(), comment.getBoardId());
        then(commentDAO).should().updateAnswerNum(commentId, comment.getAnswerNum());
    }
    private Comment createComment() {
        return Comment.builder()
                .commentId(1)
                .boardId(1)
                .comment("내용")
                .commentUserName("김지수")
                .commentPassword("1234")
                .ref(1)
                .refOrder(0)
                .step(0)
                .parentNum(0)
                .answerNum(1)
                .build();
    }

    private Comment createReComment() {
        return Comment.builder()
                .commentId(2)
                .boardId(1)
                .comment("내용")
                .commentUserName("김지수")
                .commentPassword("1234")
                .ref(1)
                .refOrder(1)
                .step(1)
                .parentNum(0)
                .answerNum(0)
                .build();
    }

    private CommentDto createCommentDto() {
        return CommentDto.builder()
                .commentId(1)
                .boardId(1)
                .comment("내용")
                .commentUserName("김지수")
                .commentPassword("1234")
                .ref(1)
                .refOrder(0)
                .step(0)
                .parentNum(0)
                .answerNum(0)
                .build();
    }
}