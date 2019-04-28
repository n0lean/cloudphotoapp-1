package com.clou.photoshare.model;

import java.util.HashSet;
import java.util.Set;

public class PhotoSearchBuilder {

    private String _userId;
    private String _tripId;
    private Set<String> _photosId;

    public PhotoSearchBuilder(){

    }

    public PhotoSearch builder(){
        PhotoSearch pS = new PhotoSearch();
        pS.setUserId(_userId);
        pS.setTripId(_tripId);
        pS.setPhotoId(this._photosId);
        return pS;
    }


    public PhotoSearchBuilder set_userId(String _userId) {
        this._userId = _userId;
        return this;
    }


    public PhotoSearchBuilder set_tripId(String _tripId) {
        this._tripId = _tripId;
        return this;
    }


    public PhotoSearchBuilder set_photosId(Set<String> _photosId) {
        this._photosId = _photosId;
        return this;
    }

    public PhotoSearchBuilder add_photosId(String _photosId) {
        if(this._photosId == null){
            this._photosId = new HashSet<>();
        }
        this._photosId.add(_photosId);
        return this;
    }


}
