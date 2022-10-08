package com.example.demo.dto.response;

import com.example.demo.model.Comment;
import lombok.Data;

@Data
public class CommentResponse {

    private final Integer commentId;

    private final Integer boardId;

    private final String comment;

    private final String commentUserName;

    private final String commentPassword;

    private final Integer groupId;

    private final Integer depth;

    private final Integer step;


    public CommentResponse(Integer commentId, Integer boardId, String comment, String commentUserName, String commentPassword, Integer groupId, Integer depth, Integer step) {
        this.commentId = commentId;
        this.boardId = boardId;
        this.comment = comment;
        this.commentUserName = commentUserName;
        this.commentPassword = commentPassword;
        this.groupId = groupId;
        this.depth = depth;
        this.step = step;
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getBoardId(),
                comment.getComment(),
                comment.getCommentUserName(),
                comment.getCommentPassword(),
                comment.getGroupId(),
                comment.getDepth(),
                comment.getStep()
        );
    }
}
