package com.clou.photoshare.controller;
import com.clou.photoshare.services.PhotoDistributionService;
import com.clou.photoshare.errorHandler.PhotoIsNullException;
import com.clou.photoshare.errorHandler.PhotoNotFoundException;
import com.clou.photoshare.model.Photo;
import com.clou.photoshare.model.PhotoBuilder;
import com.clou.photoshare.repository.PhotosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.net.ResourceManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    final PhotosRepository repository;

    public boolean checkIsNull(Photo photo){
        if(photo.getId().isEmpty() || photo.getOwnerId().isEmpty()
                ||photo.getTripId().isEmpty() || photo.getPhotoKey().isEmpty() || photo.getBucketName().isEmpty()){
            return false;
        }
        return true;
    }

    @Autowired
    public PhotoController(PhotosRepository repo){
        this.repository = repo;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<?> addPhoto(@RequestBody Photo photo){
        try {
            if (!checkIsNull(photo)) {
                throw new PhotoIsNullException(photo);
            }
            Photo newPhoto = new PhotoBuilder()
                    .photoId(photo.getId())
                    .ownerId(photo.getOwnerId())
                    .tripId(photo.getTripId())
                    .photoKey(photo.getPhotoKey())
                    .bucketName(photo.getBucketName())
                    .buildPhoto();
            repository.save(newPhoto);

            //add trigger API

            return new ResponseEntity<>(newPhoto, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }


    @RequestMapping(value = "/findOne/{userId}/{photoId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPhoto(@PathVariable("userId") String userId,
                                      @PathVariable("photoId") String photoId) {
        try{
            Photo photo = repository.findById(photoId).orElseThrow(()-> new PhotoNotFoundException(photoId));

            return new ResponseEntity<>(photo, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }


    //photo/findAll?userId=XXX
    @RequestMapping(value = "/findAll/{userId}/{tripId}", method = RequestMethod.GET)
    public ResponseEntity<?> findAll(@PathVariable("userId") String userId,
                                     @PathVariable("tripId") String tripId){
        try {
            String res = "";
            Iterable<Photo> photos = repository.findAll();
            for(Photo photo:photos){
                res += photo.toString() + "<br>";
            }


            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @RequestMapping(value = "/delete{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePhoto(@PathVariable String id){
        try {
            if (repository.findById(id).isPresent()){
                repository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }

    }

    @RequestMapping(value = "/testface", method = RequestMethod.GET)
    public String testFace() {
        PhotoDistributionService photoService = new PhotoDistributionService();
        Photo testPhoto = new PhotoBuilder()
                                .ownerId("123")
                                .photoKey("123")
                                .photoId("123")
                                .buildPhoto();

        try {
            return photoService.getFaces(testPhoto);
        } catch (Exception e) {
            return "Error" + e.toString();
        }
    }


    //TODO: a test method for compareFace using FaceDistributionService
    @RequestMapping(value = "/compareface", method = RequestMethod.GET)
    public String compareFace() {
        return "";
    }
}
