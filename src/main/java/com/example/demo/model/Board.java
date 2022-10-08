package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Board {

    private Integer boardId;

    private String boardTitle;

    private String boardContent;

    private String boardUserName;

    private String boardPassword;

    private LocalDateTime createAt;

    private String deleteYN;

}
