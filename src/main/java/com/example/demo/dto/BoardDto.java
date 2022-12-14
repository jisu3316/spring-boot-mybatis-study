package com.example.demo.dto;

import com.example.demo.model.Board;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {

    private final Integer boardId;

    private final String boardTitle;

    private final String boardContent;

    private final String boardUserName;

    private final String boardPassword;

    private final LocalDateTime createAt;

    private final String deleteYN;

    @Builder
    public BoardDto(Integer boardId, String boardTitle, String boardContent, String boardUserName, String boardPassword, LocalDateTime createAt, String deleteYN) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardUserName = boardUserName;
        this.boardPassword = boardPassword;
        this.createAt = createAt;
        this.deleteYN = deleteYN;
    }

    public static BoardDto from(Board board) {
        return new BoardDto(
                board.getBoardId(),
                board.getBoardTitle(),
                board.getBoardContent(),
                board.getBoardUserName(),
                board.getBoardPassword(),
                board.getCreateAt(),
                board.getDeleteYN()
        );
    }

    public Board toEntity() {
        return Board.builder()
                .boardId(this.boardId)
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardUserName(this.boardUserName)
                .boardPassword(this.boardPassword)
                .build();
    }
}
