package com.clou.photoshare.model;


public class PhotoBuilder {

    private String _id;
    private String _ownerId;
    private String _tripsId;

    private S3Address _photoAddress = new S3Address();

    public PhotoBuilder(){
    }

    public Photo buildPhoto() {
        Photo ph = new Photo(this._id, this._ownerId, this._tripsId, this._photoAddress);
        return ph;
    }

    public PhotoBuilder photoId(String id){
        this._id = id;
        return this;
    }


    public PhotoBuilder ownerId(String ownerId){
        this._ownerId = ownerId;
        return this;
    }

    public PhotoBuilder tripId(String tripId){
        this._tripsId = tripId;
        return this;
    }

    public PhotoBuilder photoKey(String photoKey){
        this._photoAddress.setAddressKey(photoKey);
        return this;
    }

    public PhotoBuilder bucketName(String bucketName){
        this._photoAddress.setAddressBucket(bucketName);
        return this;
    }

    public PhotoBuilder photoAddress(S3Address address){
        this._photoAddress = address;
        return this;
    }
}


