package com.example.demo.dto.request;

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
    private final Integer groupId;
    private final Integer depth;
    private final Integer step;

}
