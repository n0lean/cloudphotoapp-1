package com.clou.photoshare.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DynamoDBTable(tableName = "Photo")
public class Photo {
    private String id;
    private String address;
    private String ownerId; // user-Id
    private List<String> viewersId;
    private List<String> tripsId;

    public Photo(String id){
        this.id = id;
        this.viewersId = new ArrayList<>();
        this.tripsId = new ArrayList<>();
    }

    public Photo(String id, String address){
        this.id = id;
        this.address = address;
        this.viewersId = new ArrayList<>();
        this.tripsId = new ArrayList<>();
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
    public List<String> getViewersId(){
        if(this.viewersId == null || this.viewersId.size() == 0){
            this.viewersId = new ArrayList<>();
            return this.viewersId;
        }

        return this.viewersId;
    }

    public void setViewersId( List<String> viewersId){
        this.viewersId = new ArrayList<>(viewersId);
    }

    @DynamoDBAttribute(attributeName = "TripsId")
    public List<String> getTripsId(){
        if(this.tripsId == null || this.tripsId.size() == 0){
            this.tripsId = new ArrayList<>();
            return this.tripsId;
        }

        return this.tripsId;
    }

    public void setTripsId(List<String> tripsId){
        this.tripsId = new ArrayList<>(tripsId);
    }


    @Override
    public String toString(){
        String joinedViewersId = this.viewersId.stream().collect(Collectors.joining(","));
        String joinedTripsId = this.tripsId.stream().collect(Collectors.joining(","));
        return String.format("Photo[id='%s', address='%s', OwnerId='%s',ViewersId='%s', tripsId='%s']",id, address, joinedViewersId, joinedTripsId);
    }







}
