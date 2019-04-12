package com.clou.photoshare.model;

import java.util.ArrayList;
import java.util.List;


public class TripBuilder {
    private String _id;
    private String _tripName = "Untitled Trip";
    private List<String> _tripMember = new ArrayList<>();

    public TripBuilder() {  }

    public Trip buildTrip() {
        return new Trip(this._id, this._tripName, this._tripMember);
    }

    public TripBuilder id(String _id) {
        this._id = _id;
        return this;
    }

    public TripBuilder tripName(String _tripName) {
        this._tripName = _tripName;
        return this;
    }

    public TripBuilder addMember(String _member) {
        this._tripMember.add(_member);
        return this;
    }

    public TripBuilder addMembers(List<String> _members) {
        this._tripMember.addAll(_members);
        return this;
    }

}
