package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Comment {

    private Integer commentId;

    private Integer boardId;

    private String comment;

    private String commentUserName;

    private String commentPassword;

    private Integer groupId;

    private Integer depth;

    private Integer step;

}
