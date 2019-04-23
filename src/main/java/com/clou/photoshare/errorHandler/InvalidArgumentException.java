package com.clou.photoshare.errorHandler;

public class InvalidArgumentException extends RuntimeException{
    public InvalidArgumentException(String info) { super(info); }
}
