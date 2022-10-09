package com.example.demo.dto.response;

import com.example.demo.dto.BoardDto;
import com.example.demo.model.Board;
import lombok.Builder;
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

    @Builder
    public BoardResponse(Integer boardId, String boardTitle, String boardContent, String boardUserName, LocalDateTime createAt, String deleteYN) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardUserName = boardUserName;
        this.createAt = createAt;
        this.deleteYN = deleteYN;
    }

    public static BoardResponse of(Integer boardId, String boardTitle, String boardContent, String boardUserName, LocalDateTime createAt, String deleteYN) {
        return BoardResponse.builder()
                .boardId(boardId)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardUserName(boardUserName)
                .createAt(createAt)
                .deleteYN(deleteYN)
                .build();
    }

    public static BoardResponse from(BoardDto boardDto) {
        return new BoardResponse(
                boardDto.getBoardId(),
                boardDto.getBoardTitle(),
                boardDto.getBoardContent(),
                boardDto.getBoardUserName(),
                boardDto.getCreateAt(),
                boardDto.getDeleteYN()
        );
    }
}
