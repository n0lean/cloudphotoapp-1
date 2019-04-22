package com.clou.photoshare.services;


import com.amazonaws.services.rekognition.model.Image;
import com.clou.photoshare.model.Photo;
import com.clou.photoshare.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {

    // get profile photos of user in a trip
    public List<Photo> getProfilePhotosByTripId(String tripId) { return new ArrayList<>(); }

    // get users is a trip
    public List<User> getUsersByTripId(String tripId) { return new ArrayList<>(); }
}
