package com.clou.photoshare.model;

import org.springframework.beans.factory.annotation.Value;

import java.util.*;


public class PhotoBuilder {

    private String _id;
    private String _photoKey;
    private String _owenerId;
    private String _tripsId;

    private String _bucketName = "cloudphoto-hay";

    private Set<String> _viewersId = new HashSet<>();

    public PhotoBuilder(){
    }

    public Photo buildPhoto() {
        Photo ph = new Photo(this._id, this._owenerId, this._photoKey, this._tripsId);
        if(this._viewersId.size() != 0){
            ph.setViewersId(this._viewersId);
        }
        ph.setBucketName(this._bucketName);
        return ph;
    }

    public PhotoBuilder photoId(String id){
        this._id = id;
        return this;
    }

    public PhotoBuilder photoKey(String photoKey){
        this._photoKey = photoKey;
        return this;
    }

    public PhotoBuilder ownerId(String ownerId){
        this._owenerId = ownerId;
        return this;
    }

    public PhotoBuilder addViewerId(Collection<String> viewersId){
        this._viewersId.addAll(viewersId);
        return this;
    }

    public PhotoBuilder addViewerId(String viewersId){
        this._viewersId.add(viewersId);
        return this;
    }

    public PhotoBuilder tripId(String tripId){
        this._tripsId = tripId;
        return this;
    }
}


