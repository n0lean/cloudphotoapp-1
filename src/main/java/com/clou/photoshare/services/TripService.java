package com.clou.photoshare.services;


import com.amazonaws.services.rekognition.model.Image;
import com.clou.photoshare.errorHandler.PhotoNotFoundException;
import com.clou.photoshare.errorHandler.TripNotFoundException;
import com.clou.photoshare.errorHandler.UserNotFoundException;
import com.clou.photoshare.model.Photo;
import com.clou.photoshare.model.TripBuilder;
import com.clou.photoshare.model.User;
import com.clou.photoshare.repository.PhotosRepository;
import com.clou.photoshare.repository.TripRepository;
import com.clou.photoshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.clou.photoshare.model.Trip;
import com.clou.photoshare.model.TripBuilder;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final PhotosRepository photosRepository;

    @Autowired
    public TripService(TripRepository tRepo, UserRepository uRepo, PhotosRepository pRepo)  {
        this.tripRepository = tRepo;
        this.userRepository = uRepo;
        this.photosRepository = pRepo;
    }

    // get profile photos of user in a trip
    public List<Photo> getProfilePhotosByTripId(String tripId) {
        List<Photo> photoList = new ArrayList<>();
        Trip trip = tripRepository.findById(tripId).orElseThrow(()-> new TripNotFoundException(tripId));
        Set<String> tripMember = trip.getTripMember();

        for (String memberId : tripMember) {
            User member = userRepository.findById(memberId).orElseThrow(()-> new UserNotFoundException(memberId));
            Photo photo = photosRepository
                    .findById(member.getProfilePhotoAddress())
                    .orElseThrow(()-> new PhotoNotFoundException(member.getProfilePhotoAddress()));
            photoList.add(photo);
        }
        return photoList;
    }

    // get users is a trip
    public List<User> getUsersByTripId(String tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(()-> new TripNotFoundException(tripId));
        Set<String> tripMember = trip.getTripMember();
        List<User> userList = new ArrayList<>();
        for (String memberId : tripMember) {
            User member = userRepository.findById(memberId).orElseThrow(()-> new UserNotFoundException(memberId));
            userList.add(member);
        }
        return userList;
    }
}
