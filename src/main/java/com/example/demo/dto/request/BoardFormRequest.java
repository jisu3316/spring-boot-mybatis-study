package com.example.demo.dto.request;

import com.example.demo.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardFormRequest {

    private final Integer boardId;
    private final String boardTitle;
    private final String boardContent;
    private final String boardUserName;
    private final String boardPassword;

    public static BoardFormRequest of(Integer boardId, String boardTitle, String boardContent, String boardUserName, String boardPassword) {
        return new BoardFormRequest (boardId, boardTitle, boardContent, boardUserName, boardPassword);
    }

    public BoardDto toDto() {
        return BoardDto.builder()
                .boardId(this.boardId)
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardUserName(this.boardUserName)
                .boardPassword(this.boardPassword)
                .build();
    }
}
