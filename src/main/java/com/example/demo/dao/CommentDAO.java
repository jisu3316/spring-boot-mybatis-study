package com.example.demo.dao;

import com.example.demo.dto.request.CommentRequest;
import com.example.demo.model.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentDAO {

    List<Comment> comments(Integer boardId);

    void createComment(CommentRequest commentRequest);

    void updateGroupId(Integer commentId);

    void updateComment(Map<String, Object> map);

    Comment getComment(Integer commentId);

    void deleteComment(Integer commentId);

    void reComment(CommentRequest commentRequest);
}
