package com.clou.photoshare.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName = "User")
public class User {
    @NotNull
    private String id;

    @NotNull
    private String nickName;

    private String firstName;
    private String lastName;

    @NotNull
    @Email
    private String email;

    private List<User> friends;

    @NotNull
    private S3Address profilePhotoAddress;

    // have a empty consturctor so we can constrcut object from DB
    public User () {}

    public User(String id, String nickName, String firstName, String lastName, String email, S3Address profilePhotoAddress) {
        this.id = id;
        this.nickName = nickName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.friends = new ArrayList<>();
        this.profilePhotoAddress = profilePhotoAddress;
    }

    @DynamoDBHashKey(attributeName = "Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "NickName")
    public String getNickName() { return nickName; }

    public void setNickName(String nickName) { this.nickName = nickName; }

    @DynamoDBAttribute(attributeName = "Email")
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @DynamoDBAttribute(attributeName = "LastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @DynamoDBAttribute(attributeName = "FirstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @DynamoDBAttribute(attributeName = "Friends")
    public List<User> getFriends() { return friends; }

    public void setFriends(List<User> firends) { this.friends = friends; }

    @DynamoDBAttribute(attributeName = "AddressKey")
    public String getAddressKey() {
        return this.profilePhotoAddress.getAddressKey();
    }

    @DynamoDBAttribute(attributeName = "AddressBucket")
    public String getAddressBucket () {
        return this.profilePhotoAddress.getAddressBucket();
    }

    public void setProfilePhotoAddress(S3Address newAddress) {
        this.profilePhotoAddress = newAddress;
    }

    @Override
    public String toString() {
        return String.format("User: id: %s, nickname: %s, firstname: %s, lastname: %s, email: %s",
                this.id, this.nickName, this.firstName, this.lastName, this.email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if(obj == null || obj.getClass()!= this.getClass())
            return false;

        User user = (User) obj;
        return (
                user.getId().equals(this.getId())
                        && user.getEmail().equals(this.getEmail())
                        && user.getFirstName().equals(this.getFirstName())
                        && user.getLastName().equals(this.getLastName())
                        && user.getNickName().equals(this.getNickName())
        );
    }
}