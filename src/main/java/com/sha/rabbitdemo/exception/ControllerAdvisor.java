package com.sha.rabbitdemo.exception;

import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAdvisor{

//    @AfterThrowing(pointcut = "execution(* com.sha.rabbitdemo.Tut6Server.fibonacci(..))", throwing = "e")
//    public ResponseEntity<Object> handleApiException(ApiException e) {
//        HttpStatus badRequest = HttpStatus.INTERNAL_SERVER_ERROR;
//        return new ResponseEntity<>(e.getMessage(), badRequest);
//    }


    @AfterThrowing (pointcut = "execution(* com.sha.rabbitdemo.Tut6Server.*(..)))", throwing = "e")
    public void logAfterThrowingAllMethods(ApiException e) throws Throwable  {
        System.out.println("****LoggingAspect.logAfterThrowingAllMethods() ");
    }

}
