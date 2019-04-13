package com.clou.photoshare.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@DynamoDBTable(tableName = "Photo")
public class Photo {

    @NotNull
    private String id;
    private String ownerId;

    private String address;

    private Set<String> viewersId;
    private Set<String> tripsId;


    public Photo(){
    }

    public Photo(String id, String ownerId){
        this.id = id;
        this.ownerId = ownerId;
        this.viewersId = new HashSet<>();
        this.tripsId = new HashSet<>();
    }

    public Photo(String id, String ownerId, String address){
        this.id = id;
        this.ownerId = ownerId;
        this.address = address;
        this.viewersId = new HashSet<>();
        this.tripsId = new HashSet<>();
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
        this.viewersId = viewersId;
    }

    public void addViewersId( String viewerId ){
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
        this.tripsId = tripsId;
    }

    public void addTripsId(String tripId){
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
