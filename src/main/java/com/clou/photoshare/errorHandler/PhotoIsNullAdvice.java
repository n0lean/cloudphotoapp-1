package com.clou.photoshare.errorHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PhotoIsNullAdvice {
    @ResponseBody
    @ExceptionHandler(PhotoIsNullException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String photoNotFoundHandler(PhotoIsNullException ex){
        return ex.getMessage();
    }
}
