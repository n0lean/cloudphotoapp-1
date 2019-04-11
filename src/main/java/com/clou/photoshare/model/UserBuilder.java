package com.clou.photoshare.model;


public class UserBuilder {
    private String _id;
    private String _nickName;
    private String _firstName = "";
    private String _lastName = "";
    private String _email;

    public UserBuilder() {  }

    public User buildUser() {
        return new User(this._id, this._nickName, this._firstName, this._lastName, this._email);
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
}