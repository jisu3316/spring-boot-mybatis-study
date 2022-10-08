package com.example.demo.service;


import com.example.demo.dao.CommentDAO;
import com.example.demo.dto.request.CommentRequest;
import com.example.demo.dto.response.CommentResponse;
import com.example.demo.exception.BoardException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.model.Board;
import com.example.demo.model.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDAO commentDAO;

    public List<CommentResponse> getComments(Integer boardId) {
        return commentDAO.comments(boardId).stream().map(CommentResponse::from).collect(Collectors.toList());
    }

    public void createComment(CommentRequest commentRequest) {
        commentDAO.createComment(commentRequest);
    }

    public void updateGroupId(Integer commentId) {
        commentDAO.updateGroupId(commentId);
    }

    public void updateComment(Integer commentId, CommentRequest commentRequest) {
        Comment comment = commentDAO.getComment(commentId);
        if (!comment.getCommentPassword().equals(commentRequest.getCommentPassword())) {
            log.info("boardPassword {} ", comment.getCommentPassword());
            log.info("requestPassword {} ", commentRequest.getCommentPassword());
//            throw new BoardException(ErrorCode.INVALID_PASSWORD, String.format("%s INVALID_PASSWORD", commentRequest.getCommentPassword()));
            throw new BoardException(ErrorCode.INVALID_PASSWORD, String.format("%s은 틀린 패스워드 입니다.", commentRequest.getCommentPassword()));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("commentId", commentId);
        map.put("comment", commentRequest.getComment());
        commentDAO.updateComment(map);
    }

    public void deleteComment(Integer commentId, CommentRequest commentRequest) {
        Comment comment = commentDAO.getComment(commentId);
        if (!comment.getCommentPassword().equals(commentRequest.getCommentPassword())) {
            log.info("boardPassword {} ", comment.getCommentPassword());
            log.info("requestPassword {} ", commentRequest.getCommentPassword());
//            throw new BoardException(ErrorCode.INVALID_PASSWORD, String.format("%s INVALID_PASSWORD", commentRequest.getCommentPassword()));
            throw new BoardException(ErrorCode.INVALID_PASSWORD, String.format("%s은 틀린 패스워드 입니다.", commentRequest.getCommentPassword()));
        }
        commentDAO.deleteComment(commentRequest.getCommentId());
    }

    public void reComment(Integer commentId, CommentRequest commentRequest) {
        Comment comment = commentDAO.getComment(commentId);
        if (comment == null) {
            throw new BoardException(ErrorCode.COMMENT_NOT_FOUND, String.format("%s COMMENT_NOT_FOUND", commentId));
        }
        commentDAO.reComment(commentRequest);
    }
}
