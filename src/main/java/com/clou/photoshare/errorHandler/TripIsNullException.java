package com.clou.photoshare.errorHandler;

import com.clou.photoshare.model.Trip;

public class TripIsNullException extends RuntimeException{
    public TripIsNullException(Trip trip) { super("Trip id is null" + trip); }
}
