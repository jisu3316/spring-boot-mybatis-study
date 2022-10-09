package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Board {

    private Integer boardId;

    private String boardTitle;

    private String boardContent;

    private String boardUserName;

    private String boardPassword;

    private LocalDateTime createAt;

    private String deleteYN;

    @Builder
    public Board(Integer boardId, String boardTitle, String boardContent, String boardUserName, String boardPassword, LocalDateTime createAt, String deleteYN) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardUserName = boardUserName;
        this.boardPassword = boardPassword;
        this.createAt = createAt;
        this.deleteYN = deleteYN;
    }
}
