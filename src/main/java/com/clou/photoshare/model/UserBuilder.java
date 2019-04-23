package com.clou.photoshare.model;


import com.clou.photoshare.errorHandler.InvalidArgumentException;

import java.util.HashSet;
import java.util.Set;

public class UserBuilder {
    private String _id;
    private String _nickName;
    private String _firstName = "";
    private String _lastName = "";
    private String _email;
    private S3Address _address;
    private Set<String> _friends;

    public UserBuilder() {  }

    public User buildUser() {
        if (this._address == null || this._id == null || this._nickName == null || this._email == null) {
            throw new InvalidArgumentException(
                    "id, nickname, email and address cannot be null"
            );
        }
        if (this._friends == null) this._friends = new HashSet<>();
        this._friends.add(this._id);
        return new User(this._id, this._nickName, this._firstName, this._lastName, this._email, this._address, this._friends);
    }

    public UserBuilder id(String _id) {
        this._id = _id;
        return this;
    }

    public UserBuilder nickName(String _nickName) {
        this._nickName = _nickName;
        return this;
    }

    public UserBuilder firstName(String _firstName) {
        this._firstName = _firstName;
        return this;
    }

    public UserBuilder lastName(String _lastName) {
        this._lastName = _lastName;
        return this;
    }

    public UserBuilder email(String _email) {
        this._email = _email;
        return this;
    }

    public UserBuilder profilePhotoAddress(S3Address _address) {
        this._address = _address;
        return this;
    }

    public UserBuilder buildFriends(String _friend) {
        if (this._friends == null) this._friends = new HashSet<>();
        this._friends.add(_friend);
        return this;
    }
}