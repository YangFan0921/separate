package com.example.separate.exception;

import com.example.separate.common.Result;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException serviceException){
        return Result.error(serviceException.getCode(),serviceException.getMessage());
    }



}
