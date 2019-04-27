package com.clou.photoshare.services;

import com.clou.photoshare.model.*;
import com.clou.photoshare.errorHandler.PhotoNotFoundException;
import com.clou.photoshare.model.PhotoSearchBuilder;
import com.clou.photoshare.repository.PhotoSearchRepository;
import com.clou.photoshare.repository.PhotosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


// @Hu Xin
@Service
public class PhotoService {

    private PhotosRepository photosRepository;
    private PhotoSearchRepository photoSearchRepository;
    private PhotoDistributionService photoDistributionService;

    @Autowired
    public PhotoService(PhotosRepository photoRepo, PhotoSearchRepository photoSearchRepo, PhotoDistributionService distSer) {
        this.photosRepository= photoRepo;
        this.photoSearchRepository = photoSearchRepo;
        this.photoDistributionService = distSer;
    }


    // TODO: assign new viewer id to to a photo
    public void assignViewerOnPhotoById(Photo photo, String newViewerId) {
        try {
            String photoId = photo.getId();
            String tripId = photo.getTripId();
            Set<String> photolist = photoSearchRepository.findByUserIdAndTripId(newViewerId, tripId).getPhotoId();
            if (photolist.isEmpty()) {
                return;
            }
            photolist.add(photoId);
            PhotoSearch photoSearch = new PhotoSearchBuilder()
                    .set_photosId(photolist)
                    .set_tripId(photo.getTripId())
                    .set_userId(newViewerId)
                    .builder();
            photoSearchRepository.save(photoSearch);
        }catch (Exception e){
            System.out.println(e);
        }


    }

    public Set<S3Address> getAllPhotoByQuery(String userId, String tripID){
        try {
            if (photoSearchRepository.findByUserIdAndTripId(userId, tripID).getPhotoId().isEmpty()) {   // to do check no result
                return null;
            }
            Set<String> photolist = photoSearchRepository.findByUserIdAndTripId(userId, tripID).getPhotoId();
            Set<S3Address> res = new HashSet<>();
            for(String photoId:photolist){
                if(photosRepository.findById(photoId).isPresent()){
                    res.add(photosRepository.findById(photoId).get().getPhotoAddress());
                }
            }
            return res;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    //TODO: Give a user entity, return a photo entity contains user profile photo
    /**
     *
     * @param user
     * @return A photo object
     */
    public Photo getProfilePhoto(User user) {
        return new Photo();
    }

    //TODO: Give a trip entity, return a List<Photo> that are already belongs to this Trip

    /**
     *
     * @param trip
     * @return A list of photo in a trip
     */
    public List<Photo> getPhotosByTrip(Trip trip) {
        List<Photo> photos = photosRepository.findByTripId(trip.getId());

        return photos;
    }

}
