package com.clou.photoshare.errorHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TripIsNullAdvice {
    @ResponseBody
    @ExceptionHandler(TripNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String TripNotFoundHandler(TripIsNullException ex) {
        return ex.getMessage();
    }


}
