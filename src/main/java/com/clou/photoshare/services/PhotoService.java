package com.clou.photoshare.services;

import com.clou.photoshare.model.*;
import com.clou.photoshare.errorHandler.PhotoNotFoundException;
import com.clou.photoshare.model.PhotoSearchBuilder;
import com.clou.photoshare.repository.PhotoSearchRepository;
import com.clou.photoshare.repository.PhotosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


// @Hu Xin
@Service
public class PhotoService {

    @Autowired
    private PhotosRepository photosRepository;

    @Autowired
    private PhotoSearchRepository photoSearchRepository;

    @Autowired
    private PhotoDistributionService photoDistributionService;



    //TODO: trigger function for start assignViewerId
    public void triggerAssignViewers(Photo photo) {
        photoDistributionService.assignViewer(photo);

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
}
