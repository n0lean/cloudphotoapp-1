package com.clou.photoshare.model;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@DynamoDBTable(tableName = "PhotoSearch")
@PropertySource("classpath:application.properties")
public class PhotoSearch {
    @Id
    private PhotoSearchId photoSearchId;

    private Set<String> photosId;

    public PhotoSearch(){
    }

    public PhotoSearch(String userId, String tripId){
        this.photosId = new HashSet<>();
        this.photoSearchId = new PhotoSearchId();
        this.photoSearchId.setTripId(tripId);
        this.photoSearchId.setUserId(userId);
    }


    @DynamoDBHashKey(attributeName = "UserId")
    public String getUserId() {

        return photoSearchId != null ? photoSearchId.getUserId():null;
    }

    public void setUserId(String userId) {
        if(photoSearchId == null){
            photoSearchId = new PhotoSearchId();
        }
        photoSearchId.setUserId(userId);
    }

    @DynamoDBRangeKey(attributeName = "TripId")
    @DynamoDBIndexHashKey(attributeName = "TripId",globalSecondaryIndexName="TripId_index")
    public String getTripId() {

        return photoSearchId != null ? photoSearchId.getTripId():null;

    }

    public void setTripId(String tripId) {
        if(photoSearchId == null){
            photoSearchId = new PhotoSearchId();
        }
        photoSearchId.setTripId(tripId);
    }

    @DynamoDBAttribute(attributeName = "PhotoId")
    public Set<String> getPhotoId() {
        return photosId;
    }

    public void setPhotoId(Collection<String> photoId) {
        if(this.photosId == null){
            this.photosId = new HashSet<String>();
        }
        this.photosId.addAll(photoId);
    }

    public void setPhotoId(String photoId){
        this.photosId.add(photoId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if(obj == null || obj.getClass()!= this.getClass())
            return false;

        PhotoSearch pS = (PhotoSearch) obj;
        return (
                pS.getUserId().equals(this.getUserId())
                        && pS.getTripId().equals(this.getTripId())
                        && pS.getPhotoId().equals(this.getPhotoId())
        );
    }

}
