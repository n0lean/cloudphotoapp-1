package com.clou.photoshare.model;

import java.util.*;


public class PhotoBuilder {
    private String _id;
    private String _address;
    private String _owenerId;
    private Set<String> _viewersId = new HashSet<>();
    private Set<String> _tripsId = new HashSet<>();

    public PhotoBuilder(){
        this._tripsId.add("NUll");
        this._tripsId.add("NUll");
    }

    public Photo buildPhoto() {
        Photo ph = new Photo(this._id, this._owenerId,this._address);
        ph.setViewersId(this._viewersId);
        ph.setTripsId(this._tripsId);
        return ph;
    }

    public PhotoBuilder photoId(String id){
        this._id = id;
        return this;
    }

    public PhotoBuilder photoAddress(String addr){
        this._address = addr;
        return this;
    }

    public PhotoBuilder addOwnerId(String ownerId){
        this._owenerId = ownerId;
        return this;
    }

    public PhotoBuilder addViewerId(Collection<String> viewersId){
        if(this._viewersId.contains("NULL")){
            this._viewersId.remove("NULL");
        }
        this._viewersId.addAll(viewersId);
        return this;
    }

    public PhotoBuilder addOneViewerId(String viewersId){
        if(this._viewersId.contains("NULL")){
            this._viewersId.remove("NULL");
        }
        this._viewersId.add(viewersId);
        return this;
    }

    public PhotoBuilder addTripId(Collection<String> tripsId){
        if(this._tripsId.contains("NULL")){
            this._tripsId.remove("NULL");
        }
        this._tripsId.addAll(tripsId);
        return this;
    }

    public PhotoBuilder addOneTripId(String tripsId){
        if(this._tripsId.contains("NULL")){
            this._tripsId.remove("NULL");
        }
        this._tripsId.add(tripsId);
        return this;
    }
}


