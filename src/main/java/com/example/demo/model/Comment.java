package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment {

    private Integer commentId;

    private Integer boardId;

    private String comment;

    private String commentUserName;

    private String commentPassword;

    private Integer ref;

    private Integer step;

    private Integer refOrder;

    private Integer answerNum;

    private Integer parentNum;

    @Builder
    public Comment(Integer commentId, Integer boardId, String comment, String commentUserName, String commentPassword, Integer ref, Integer step, Integer refOrder, Integer answerNum, Integer parentNum) {
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

}
