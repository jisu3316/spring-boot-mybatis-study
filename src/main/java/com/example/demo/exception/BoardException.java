package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;

    public BoardException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }
}
