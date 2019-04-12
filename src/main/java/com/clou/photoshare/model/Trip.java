package com.clou.photoshare.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.*;

import javax.validation.constraints.NotNull;


@DynamoDBTable(tableName = "Trip")
public class Trip {
    @NotNull
    private String id;

    @NotNull
    private String tripName;

    private Set<String> tripMember;

    public Trip() {  };

    public Trip(String id, String tripName, Set<String> tripMember) {
        this.id = id;
        this.tripName = tripName;
        this.tripMember = tripMember;
    }

    @DynamoDBHashKey(attributeName = "Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "TripName")
    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    @DynamoDBAttribute(attributeName = "TripMember")
    public Set<String> getTripMember() {
        return tripMember;
    }

    public void setTripMember(Set<String> tripMember) {
        this.tripMember = tripMember;
    }

    public void addTripMember(String memberId) {
        this.tripMember.add(memberId);
    }

    public void addTripMember(Collection<String> memberId) {
        this.tripMember.addAll(memberId);
    }

    public void deleteTripMember(String memberId) {
        if (this.tripMember.contains(memberId)) {
            this.tripMember.remove(memberId);
        }
        else {
            throw new NoSuchElementException("Cannot find a member with ID: " + memberId);
        }
    }

    public void deleteTripMember(Collection<String> memberId) {
        this.tripMember.removeAll(memberId);
    }

    @Override
    public String toString() {
        String joinedTripMemberId = String.join(",", this.tripMember);
        return String.format("Trip: id: %s, tripName: %s, tripMember: %s", id, tripName, joinedTripMemberId);
    }

}
