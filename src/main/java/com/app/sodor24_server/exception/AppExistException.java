package com.app.sodor24_server.exception;

public class AppExistException extends RuntimeException {
    public AppExistException(String message) {
        super(message);
    }
}
