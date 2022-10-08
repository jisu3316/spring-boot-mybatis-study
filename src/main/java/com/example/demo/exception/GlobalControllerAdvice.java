package com.example.demo.exception;

import com.example.demo.dto.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

//    @ExceptionHandler(BoardException.class)
//    public ResponseEntity<?> applicationHandler(BoardException e) {
//        log.info("Error occurs {}", e.toString());
//        return ResponseEntity.status(e.getErrorCode().getStatus())
//                .body(Response.error(e.getErrorCode().name()));
//    }

    @ExceptionHandler(BoardException.class)
    public ResponseEntity<?> applicationHandler(BoardException e) {
        log.info("Error occurs {}", e.toString());
        log.info("Error occurs {}", e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.getErrorCode().name(), e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> applicationHandler(RuntimeException e) {
        log.info("Error occurs {}", e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(ErrorCode.INTERNAL_SERVER_ERROR.name(), ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}
