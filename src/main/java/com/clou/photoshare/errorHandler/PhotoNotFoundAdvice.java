package com.clou.photoshare.errorHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PhotoNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(PhotoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)     // render an http 404
    public String photoNotFoundHandler(PhotoNotFoundException ex){
        return ex.getMessage();
    }
}