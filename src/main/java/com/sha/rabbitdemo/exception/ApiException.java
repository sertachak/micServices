package com.sha.rabbitdemo.exception;

import com.sha.rabbitdemo.enums.TutException;

public class ApiException extends RuntimeException{

    private final String code;

    public ApiException(TutException tut){
        super(tut.getMessage());
        this.code = tut.getCode();
    }

    public String getCode() {
        return code;
    }
}
