package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {

    private final String resultCode;
    private final String resultMessage;
    private final T result;

    public static Response<Void> error(String errorCode, String errorMessage) {
        return new Response<>(errorCode, errorMessage,null);
    }

    public static Response<Void> success() {
        return new Response<Void>("SUCCESS", null,null);
    }

    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", null, result);
    }
}
