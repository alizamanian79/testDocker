package com.app.sodor24_server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    // Not found exception
    @ExceptionHandler(AppNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> appNotFoundException(AppNotFoundException e) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(e.getMessage())
                .details("رکورد مورد نظر پیدا نشد")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Exist exception
    @ExceptionHandler(AppExistException.class)
    public ResponseEntity<ErrorResponseDto> appExistException(AppExistException e) {
        ErrorResponseDto error = ErrorResponseDto.builder()
                .message(e.getMessage())
                .details("رکورد مورد نظر در پایگاه داده وجود دارد")
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
