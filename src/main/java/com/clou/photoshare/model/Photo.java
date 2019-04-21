package com.clou.photoshare.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;


import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@DynamoDBTable(tableName = "Photo")
@PropertySource("classpath:application.properties")
public class Photo {

    private String bucketName;

    @NotNull
    private String id;
    @NotNull
    private String ownerId;
    @NotNull
    private String photoKey;
    @NotNull
    private String tripId;

    private Set<String> viewersId;


    public Photo(){
        this.viewersId = new HashSet<>();
    }

    public Photo(String id, String ownerId, String photoKey, String tripId){
        this.id = id;
        this.ownerId = ownerId;
        this.photoKey = photoKey;
        this.tripId = tripId;
        this.viewersId = new HashSet<>();
    }

    public Photo(String id, String ownerId, String photoKey, String tripId, Set<String> viewersId){
        this.id = id;
        this.ownerId = ownerId;
        this.photoKey = photoKey;
        this.tripId = tripId;
        this.viewersId = viewersId;
    }


    @DynamoDBHashKey(attributeName = "Id")
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }


    @DynamoDBAttribute(attributeName = "PhotoKey")
    public String getPhotoKey(){
        return photoKey;
    }

    public void setPhotoKey(String photoKey){
        this.photoKey = photoKey;
    }

    @DynamoDBAttribute(attributeName = "OwnerId")
    public String getOwnerId(){
        return ownerId;
    }


    public void setOwnerId(String ownerId){
        this.ownerId = ownerId;
    }


    @DynamoDBAttribute(attributeName = "ViewersId")
    public Set<String> getViewersId(){

        return this.viewersId;
    }

    public void setViewersId( Set<String> viewersId){
        this.viewersId = viewersId;
    }

    public void addViewerId( String viewerId ){
        this.viewersId.add(viewerId);
    }
    public void addViewerId( Collection<String> viewerId ){
        this.viewersId.addAll(viewerId);
    }

    @DynamoDBAttribute(attributeName = "TripId")
    public String getTripId(){
        return this.tripId;
    }

    public void setTripId(String tripsId){
        this.tripId = tripId;
    }

    @DynamoDBAttribute(attributeName = "BucketName")
    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public boolean isViewerValid(String userId){
        if(this.viewersId.contains(userId)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString(){
        String joinedViewersId = "NULL";
        if(this.viewersId.size() != 0){
            joinedViewersId = String.join(",",this.viewersId);
        }
        return String.format("Photo[id='%s', address='%s', OwnerId='%s',ViewersId='%s', tripsId='%s']",id, photoKey, ownerId,joinedViewersId,tripId);
    }

}
