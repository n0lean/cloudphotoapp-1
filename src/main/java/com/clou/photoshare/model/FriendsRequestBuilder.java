package com.clou.photoshare.model;

import java.util.Date;

public class FriendsRequestBuilder {
    private String _fromUserId;
    private String _toUserId;
    private String _status;
    private Date _timeStamp;

    public FriendsRequestBuilder () {
        this._status = "Pending";
        this._timeStamp = new Date();
    }

    public FriendsRequestBuilder fromUserId(String fromUserId) {
        this._fromUserId = fromUserId;
        return this;
    }

    public FriendsRequestBuilder toUserId(String toUserId) {
        this._toUserId = toUserId;
        return this;
    }

    public FriendsRequestBuilder status(String status) {
        this._status = status;
        return this;
    }

    public FriendsRequestBuilder timeStamp(Date timeStamp) {
        this._timeStamp = timeStamp;
        return this;
    }

    public FriendRequest buildFriendRequest() {
        return new FriendRequest(this._fromUserId, this._toUserId, this._status, this._timeStamp);
    }

}
