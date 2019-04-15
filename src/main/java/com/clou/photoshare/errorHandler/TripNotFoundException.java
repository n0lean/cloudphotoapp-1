package com.clou.photoshare.errorHandler;

public class TripNotFoundException extends RuntimeException{
    public TripNotFoundException(String id) { super("Could not find Trip" + id); }
}
