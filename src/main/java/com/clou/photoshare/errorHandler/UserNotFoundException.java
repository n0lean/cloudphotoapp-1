package com.clou.photoshare.errorHandler;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super("UserID: " + id + " could not be found");
    }
}
