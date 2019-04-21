package com.clou.photoshare.model;


/*---- in case the bucket name is not hardcode ------*/

public class PhotoAdress {

    private String bucketName;

    private String photoKey;

    public String getBucketName() {
        return bucketName;
    }

    public String getPhotoKey() {
        return photoKey;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public void setPhotoKey(String photoKey) {
        this.photoKey = photoKey;
    }
}
