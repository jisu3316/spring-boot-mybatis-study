package com.example.demo.dto.request;

import com.example.demo.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentRequest {

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

    public CommentDto toDto() {
        return CommentDto.builder()
                .commentId(this.commentId)
                .boardId(this.boardId)
                .comment(this.comment)
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
