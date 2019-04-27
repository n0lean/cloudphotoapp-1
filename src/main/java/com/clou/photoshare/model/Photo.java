package com.clou.photoshare.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.springframework.context.annotation.PropertySource;


import javax.validation.constraints.NotNull;

@DynamoDBTable(tableName = "Photo")
@PropertySource("classpath:application.properties")
public class Photo {


    @NotNull
    private String id;
    @NotNull
    private String ownerId;

    @NotNull
    private String tripId;

    @NotNull
    private S3Address photoAddress;

    public Photo(){

    }

//    public Photo(String id, String ownerId, String tripId){
//        this.id = id;
//        this.ownerId = ownerId;
//        this.tripId = tripId;
//    }

    public Photo(String id, String ownerId, String tripId, S3Address address){
        this.id = id;
        this.ownerId = ownerId;
        this.photoAddress = address;
        this.tripId = tripId;
    }


    @DynamoDBHashKey(attributeName = "Id")
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "OwnerId")
    public String getOwnerId(){
        return ownerId;
    }


    public void setOwnerId(String ownerId){
        this.ownerId = ownerId;
    }


    @DynamoDBAttribute(attributeName = "TripId")
    @DynamoDBIndexHashKey(attributeName = "TripId",globalSecondaryIndexName="TripId_index")
    public String getTripId(){
        return this.tripId;
    }

    public void setTripId(String tripId){
        this.tripId = tripId;
    }

//    @DynamoDBAttribute(attributeName = "BucketName")
    @DynamoDBIgnore
    public String getBucketName() {
        return photoAddress.getAddressBucket();
    }

    public void setBucketName(String bucketName) {
        this.photoAddress.setAddressBucket(bucketName);
    }

//    @DynamoDBAttribute(attributeName = "PhotoKey")
    @DynamoDBIgnore
    public String getPhotoKey() {
        return photoAddress.getAddressKey();
    }


    public void setPhotoKey(String PhotoKey) {
        this.photoAddress.setAddressKey(PhotoKey);
    }

    @DynamoDBAttribute(attributeName = "PhotoAddress")
    @DynamoDBTypeConverted(converter = S3Address.S3AddressConverter.class)
    public S3Address getPhotoAddress(){
        return this.photoAddress;
    }

    public void setPhotoAddress(S3Address s3Address) {
        this.photoAddress = s3Address;
    }

    @Override
    public String toString(){
        String joinedViewersId = "NULL";
        return String.format("Photo[id='%s', photoKey='%s', OwnerId='%s',ViewersId='%s', tripsId='%s']",id, photoAddress.getAddressKey(), ownerId,joinedViewersId,tripId);
    }

}
