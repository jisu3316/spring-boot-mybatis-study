package com.example.demo.dao;

import com.example.demo.model.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentDAO {

    List<Comment> comments(Integer boardId);

    void createComment(Comment comment);

    void updateComment(Comment comment);

    Optional<Comment> getComment(Integer commentId);

    void deleteComment(Comment comment);

    void deleteCommentAndRef(Integer boardId, Integer ref);

    List<Integer> findByDeleteCommentId(Integer boardId, Integer ref);

    void reComment(Comment comment);

    Integer refIfNull(Integer boardId);

    Integer findBySumAnswerNum(Integer ref);

    Integer findByNvlMaxStep(Integer ref);

    void updateRefOrderPlus(Integer ref, Integer refOrder, Integer boardId);

    void updateAnswerNum(Integer commentId, Integer answerNum);
}
