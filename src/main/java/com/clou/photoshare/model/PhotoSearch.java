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

    @NotNull
    private String userId;

    @NotNull
    private String tripId;

    private Set<String> photosId;

    public PhotoSearch(){
    }

    public PhotoSearch(String userId, String tripId){
        this.photosId = new HashSet<>();
        this.tripId = tripId;
        this.userId = userId;
    }

    @Id
    private PhotoSearchId photoSearchId;

    @DynamoDBHashKey(attributeName = "Id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBRangeKey(attributeName = "TripId")
    @DynamoDBIndexHashKey(attributeName = "TripId",globalSecondaryIndexName="TripId_index")
    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    @DynamoDBAttribute(attributeName = "PhotoId")
    public Set<String> getPhotoId() {
        return photosId;
    }

    public void setPhotoId(Collection<String> photoId) {
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
