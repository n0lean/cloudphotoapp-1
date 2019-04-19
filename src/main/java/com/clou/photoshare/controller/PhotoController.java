package com.clou.photoshare.controller;

import com.clou.photoshare.errorHandler.PhotoIsNullException;
import com.clou.photoshare.errorHandler.PhotoNotFoundException;
import com.clou.photoshare.model.Photo;
import com.clou.photoshare.model.PhotoBuilder;
import com.clou.photoshare.repository.PhotosRepository;
import com.clou.photoshare.services.PhotoDistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/photos")
public class PhotoController {


    final PhotosRepository repository;


    public boolean checkIsNull(Photo photo){
        if(photo.getId().isEmpty() || photo.getOwnerId().isEmpty()){
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
                    .photoAddress(photo.getAddress())
                    .addTripId(photo.getTripsId())
                    .addViewerId(photo.getViewersId())
                    .buildPhoto();

            repository.save(newPhoto);
            return new ResponseEntity<>(newPhoto, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPhoto(@PathVariable String id) {
        try{
            Photo photo = repository.findById(id).orElseThrow(()-> new PhotoNotFoundException(id));
            return new ResponseEntity<>(photo, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.toString());
        }
    }


    @RequestMapping(value = "/testsave",method = RequestMethod.GET)
    public String testsave(){
        Set<String> viewIds = new HashSet<>( Arrays.asList("anDa","huxin"));
        Set<String> tripsId = new HashSet<>(Arrays.asList("LA","NYC"));
        Photo test = new Photo("ss", "10101");
        test.setTripsId(tripsId);
        test.setViewersId(viewIds);
        repository.save(test);

        return "test Done";
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> findAll(){
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
                                .photoAddress("123")
                                .photoId("123")
                                .buildPhoto();

        try {
            return photoService.getFaces(testPhoto);
        } catch (Exception e) {
            return "Error" + e.toString();
        }
    }
}
