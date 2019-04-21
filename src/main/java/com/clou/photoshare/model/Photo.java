package com.clou.photoshare.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@DynamoDBTable(tableName = "Photo")
public class Photo {

    @NotNull
    private String id;
    private String ownerId;

    //TODO: Change type to S3Address
    private String address;

    private Set<String> viewersId;

    //TODO:  Change type to String
    private Set<String> tripsId;


    public Photo(){
        this.viewersId = new HashSet<>(Arrays.asList("NULL"));
        this.tripsId = new HashSet<>(Arrays.asList("NULL"));
    }

    public Photo(String id, String ownerId){
        this.id = id;
        this.ownerId = ownerId;
        this.viewersId = new HashSet<>(Arrays.asList("NULL"));
        this.tripsId = new HashSet<>(Arrays.asList("NULL"));
    }

    public Photo(String id, String ownerId, String address){
        this.id = id;
        this.ownerId = ownerId;
        this.address = address;
        this.viewersId = new HashSet<>(Arrays.asList("NULL"));
        this.tripsId = new HashSet<>(Arrays.asList("NULL"));
    }


    @DynamoDBHashKey(attributeName = "Id")
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }


    @DynamoDBAttribute(attributeName = "Address")
    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
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
        if(this.viewersId == null || this.viewersId.size() == 0){
            this.viewersId = new HashSet<>();
            return this.viewersId;
        }

        return this.viewersId;
    }

    public void setViewersId( Set<String> viewersId){
        if(this.viewersId.contains("NULL")){
            this.viewersId.remove("NULL");
        }
        this.viewersId.addAll(viewersId);
    }

    public void addOneViewerId( String viewerId ){
        if(this.viewersId.contains("NULL")){
            this.viewersId.remove("NULL");
        }
        this.viewersId.add(viewerId);
    }

    @DynamoDBAttribute(attributeName = "TripsId")
    public Set<String> getTripsId(){
        if(this.tripsId == null || this.tripsId.size() == 0){
            this.tripsId = new HashSet<>();
            return this.tripsId;
        }

        return this.tripsId;
    }

    public void setTripsId(Set<String> tripsId){
        if(this.tripsId.contains("NULL")){
            this.tripsId.remove("NULL");
        }
        this.tripsId.addAll(tripsId);
    }


    public void addOneTripId(String tripId){
        if(this.tripsId.contains("NULL")){
            this.tripsId.remove("NULL");
        }
        this.tripsId.add(tripId);
    }


    @Override
    public String toString(){
        String joinedViewersId = "NULL";
        String joinedTripsId = "NULL";
        if(this.viewersId.size() != 0){
            joinedViewersId = String.join(",",this.viewersId);
        }
        if(this.tripsId.size() != 0) {
            joinedTripsId = String.join(",", this.tripsId);
        }
        return String.format("Photo[id='%s', address='%s', OwnerId='%s',ViewersId='%s', tripsId='%s']",id, address, ownerId,joinedViewersId, joinedTripsId);
    }

}
