package com.example.demo.dto.response;

import com.example.demo.model.Board;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardResponse {

    private final Integer boardId;

    private final String boardTitle;

    private final String boardContent;

    private final String boardUserName;

    private final LocalDateTime createAt;

    private final String deleteYN;

    public BoardResponse(Integer boardId, String boardTitle, String boardContent, String boardUserName, LocalDateTime createAt, String deleteYN) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardUserName = boardUserName;
        this.createAt = createAt;
        this.deleteYN = deleteYN;
    }

    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getBoardId(),
                board.getBoardTitle(),
                board.getBoardContent(),
                board.getBoardUserName(),
                board.getCreateAt(),
                board.getDeleteYN()
        );
    }
}
