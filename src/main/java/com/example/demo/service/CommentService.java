package com.example.demo.service;


import com.example.demo.dao.CommentDAO;
import com.example.demo.dto.CommentDto;
import com.example.demo.dto.response.CommentResponse;
import com.example.demo.exception.BoardException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.model.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.runtime.Inner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDAO commentDAO;

    public List<CommentResponse> getComments(Integer boardId) {
        return commentDAO.comments(boardId).stream().map(CommentResponse::from).collect(Collectors.toList());
    }

    public void createComment(CommentDto commentDto) {
        Integer ref = commentDAO.refIfNull(commentDto.getBoardId());
        Comment comment = Comment.builder()
                .commentId(commentDto.getCommentId())
                .boardId(commentDto.getBoardId())
                .comment(commentDto.getComment())
                .commentUserName(commentDto.getCommentUserName())
                .commentPassword(commentDto.getCommentPassword())
                .ref(ref)
                .step(0)
                .refOrder(0)
                .answerNum(0)
                .parentNum(0)
                .build();

        commentDAO.createComment(comment);
    }

    public void updateComment(Integer commentId, CommentDto commentDto) {
        Comment comment = commentDAO.getComment(commentId).orElseThrow(() ->
                new BoardException(ErrorCode.COMMENT_NOT_FOUND, String.format("%s COMMENT_NOT_FOUND", commentId)));
        if (!comment.getCommentPassword().equals(commentDto.getCommentPassword())) {
            log.info("boardPassword {} ", comment.getCommentPassword());
            log.info("requestPassword {} ", commentDto.getCommentPassword());
            throw new BoardException(ErrorCode.INVALID_PASSWORD, String.format("%s은 틀린 패스워드 입니다.", commentDto.getCommentPassword()));
        }
        Comment saveComment = Comment.builder()
                .commentId(commentDto.getCommentId())
                .comment(commentDto.getComment())
                .build();
        commentDAO.updateComment(saveComment);
    }

    public List<Integer> deleteComment(Integer commentId, String password) {
        Comment comment = commentDAO.getComment(commentId).orElseThrow(() ->
                new BoardException(ErrorCode.COMMENT_NOT_FOUND, String.format("%s COMMENT_NOT_FOUND", commentId)));
        List<Integer> list = null;
        if (!comment.getCommentPassword().equals(password)) {
            log.info("boardPassword {} ", comment.getCommentPassword());
            log.info("requestPassword {} ", password);
            throw new BoardException(ErrorCode.INVALID_PASSWORD, String.format("%s은 틀린 패스워드 입니다.", password));
        }
        if (comment.getStep() == 0) {
            list = commentDAO.findByDeleteCommentId(comment.getBoardId(), comment.getRef());
            commentDAO.deleteCommentAndRef(comment.getBoardId(), comment.getRef());
        } else {
            commentDAO.deleteComment(comment);
            list.add(comment.getCommentId());
        }
        return list;
    }

    public void reComment(Integer commentId, CommentDto commentDto) {
        Comment comment = commentDAO.getComment(commentId).orElseThrow(() ->
                new BoardException(ErrorCode.COMMENT_NOT_FOUND, String.format("%s COMMENT_NOT_FOUND", commentId)));

        Integer refOrderResult = refOrderAndUpdate(comment);
        if (refOrderResult == null) {
            throw new BoardException(ErrorCode.INTERNAL_SERVER_ERROR, "답글을 달 수 없습니다.");
        }
        Comment saveComment = Comment.builder()
                .boardId(commentDto.getBoardId())
                .comment(commentDto.getComment())
                .commentUserName(commentDto.getCommentUserName())
                .commentPassword(commentDto.getCommentPassword())
                .ref(comment.getRef())
                .step(comment.getStep() + 1)
                .refOrder(refOrderResult)
                .answerNum(0)
                .parentNum(commentId)
                .build();
        commentDAO.reComment(saveComment);
        commentDAO.updateAnswerNum(comment.getCommentId(), comment.getAnswerNum());
    }

    private Integer refOrderAndUpdate(Comment comment) {

        Integer saveStep = comment.getStep() + 1;
        Integer refOrder = comment.getRefOrder();
        Integer answerNum = comment.getAnswerNum();
        Integer ref = comment.getRef();
        Integer boardId = comment.getBoardId();

        //부모 댓글그룹의 answerNum(자식수)
        Integer answerNumSum = commentDAO.findBySumAnswerNum(ref);
        //SELECT SUM(answerNum) FROM BOARD_COMMENTS WHERE ref = ?1
        //부모 댓글그룹의 최댓값 step
        Integer maxStep = commentDAO.findByNvlMaxStep(ref);
        //SELECT MAX(step) FROM BOARD_COMMENTS WHERE ref = ?1

        //저장할 대댓글 step과 그룹내의최댓값 step의 조건 비교
        /*
        step + 1 < 그룹리스트에서 max step값  AnswerNum sum + 1 * NO UPDATE
        step + 1 = 그룹리스트에서 max step값  refOrder + AnswerNum + 1 * UPDATE
        step + 1 > 그룹리스트에서 max step값  refOrder + 1 * UPDATE
        */
        if (saveStep < maxStep) {
            return answerNumSum + 1;
        } else if (saveStep == maxStep) {
            commentDAO.updateRefOrderPlus(ref, refOrder + answerNum, boardId);
            //UPDATE BOARD_COMMENTS SET refOrder = refOrder + 1 WHERE ref = ?1 AND refOrder > ?2
            return refOrder + answerNum + 1;
        } else if (saveStep > maxStep) {
            commentDAO.updateRefOrderPlus(ref, refOrder, boardId);
            //UPDATE BOARD_COMMENTS SET refOrder = refOrder + 1 WHERE ref = ?1 AND refOrder > ?2
            return refOrder + 1;
        }

        return null;
    }
}
