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
    private final Integer ref;
    private final Integer step;
    private final Integer refOrder;
    private final Integer answerNum;
    private final Integer parentNum;


    public CommentResponse(Integer commentId, Integer boardId, String comment, String commentUserName, String commentPassword, Integer ref, Integer step, Integer refOrder, Integer answerNum, Integer parentNum) {
        this.commentId = commentId;
        this.boardId = boardId;
        this.comment = comment;
        this.commentUserName = commentUserName;
        this.commentPassword = commentPassword;
        this.ref = ref;
        this.step = step;
        this.refOrder = refOrder;
        this.answerNum = answerNum;
        this.parentNum = parentNum;
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getBoardId(),
                comment.getComment(),
                comment.getCommentUserName(),
                comment.getCommentPassword(),
                comment.getRef(),
                comment.getStep(),
                comment.getRefOrder(),
                comment.getCommentId(),
                comment.getParentNum()
        );
    }
}
