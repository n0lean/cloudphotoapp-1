package com.clou.photoshare.model;

import java.util.ArrayList;
import java.util.List;

public class PhotoStream {

    private List<Photo> photos;

    public PhotoStream(){
    }

    public PhotoStream(List<Photo> photos){
        this.photos = photos;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

//    public void setPhotos(Photo photos) {
//        if(this.photos == null){
//            this.photos = new ArrayList<>();
//        }
//        this.photos.add(photos);
//    }
}
