package com.sha.rabbitdemo.core.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ErrorMessage {

    private String message;
    private String errorCode;
    private Date timestamp;
}
