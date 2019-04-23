package com.clou.photoshare.errorHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TripNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(TripNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String tripNorFoundHandler(TripNotFoundException ex) {
        return ex.getMessage();
    }

}
