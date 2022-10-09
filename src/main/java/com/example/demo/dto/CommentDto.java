package com.example.demo.dto;

import com.example.demo.model.Comment;
import lombok.Builder;
import lombok.Data;

@Data
public class CommentDto {
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

    @Builder
    public CommentDto(Integer commentId, Integer boardId, String comment, String commentUserName, String commentPassword, Integer ref, Integer step, Integer refOrder, Integer answerNum, Integer parentNum) {
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

    public Comment toEntity() {
        return Comment.builder()
                .commentId(this.commentId)
                .boardId(this.boardId)
                .commentUserName(this.commentUserName)
                .commentPassword(this.commentPassword)
                .ref(this.ref)
                .step(this.step)
                .refOrder(this.refOrder)
                .answerNum(this.answerNum)
                .parentNum(this.parentNum)
                .build();
    }
}
