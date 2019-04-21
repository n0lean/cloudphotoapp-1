package com.clou.photoshare.services;


import com.clou.photoshare.model.Photo;
import com.clou.photoshare.repository.PhotosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// @Hu Xin
@Service
public class PhotoService {

    @Autowired
    private PhotosRepository photosRepository;

    //TODO: trigger function for start assignViewerId
    public void triggerAssignViewers() {

    }

    // TODO: assign new viewer id to to a photo
    public void assignViewerOnPhotoById(Photo photo, String newViewerId) {

    }
}
