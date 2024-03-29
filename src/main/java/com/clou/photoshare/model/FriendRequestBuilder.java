package com.clou.photoshare.model;

import java.util.Date;

public class FriendRequestBuilder {
    private String _fromUserId;
    private String _toUserId;
    private String _status;
    private String _timeStamp;

    public FriendRequestBuilder () {
    }

    public FriendRequestBuilder fromUserId(String fromUserId) {
        this._fromUserId = fromUserId;
        return this;
    }

    public FriendRequestBuilder toUserId(String toUserId) {
        this._toUserId = toUserId;
        return this;
    }

    public FriendRequestBuilder status(String status) {
        this._status = status;
        return this;
    }

    public FriendRequestBuilder timeStamp(String timeStamp) {
        this._timeStamp = timeStamp;
        return this;
    }

    public FriendRequest buildFriendRequest() {
        return new FriendRequest(this._fromUserId, this._toUserId, this._status, this._timeStamp);
    }
}

