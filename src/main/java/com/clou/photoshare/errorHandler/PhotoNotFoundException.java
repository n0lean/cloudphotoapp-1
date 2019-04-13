package com.clou.photoshare.errorHandler;

public class PhotoNotFoundException extends RuntimeException{
    public PhotoNotFoundException(String id){
        super("Could not find Photo" + id);
    }
}
