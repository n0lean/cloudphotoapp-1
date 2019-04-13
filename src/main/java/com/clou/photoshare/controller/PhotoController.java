package com.clou.photoshare.controller;

import com.clou.photoshare.errorHandler.PhotoIsNullException;
import com.clou.photoshare.errorHandler.PhotoNotFoundException;
import com.clou.photoshare.model.Photo;
import com.clou.photoshare.repository.PhotosRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String addPhoto(@RequestBody Photo photo){
        if(!checkIsNull(photo)){
            throw new PhotoIsNullException(photo);
        }
        repository.save(photo);
        return "add ph oto success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Photo getPhoto(@PathVariable String id) {
        return repository.findById(id).orElseThrow(()-> new PhotoNotFoundException(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String updatePhoto(@RequestBody Photo photo){
        if(!checkIsNull(photo)){
            throw new PhotoIsNullException(photo);
        }
        repository.save(photo);
        return "update photo success";
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
    public String findAll(){
        String res = "";
        Iterable<Photo> photos = repository.findAll();

        for(Photo photo:photos){
            return res += photo.toString() + "<br>";
        }

        return res;
    }

    @RequestMapping(value = "/delete{id}", method = RequestMethod.DELETE)
    public String deletePhoto(@PathVariable String id){
        repository.deleteById(id);
        return "delete success";
    }
}
