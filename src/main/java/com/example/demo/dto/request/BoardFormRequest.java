package com.example.demo.dto.request;

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

}
