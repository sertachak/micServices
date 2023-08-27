package com.sha.rabbitdemo.enums;

public enum TutException implements ExceptionBase{
    FILE_NOT_FOUND("File not found", "404");

    private String message;
    private String code;

    TutException(String message, String code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return code;
    }
}
