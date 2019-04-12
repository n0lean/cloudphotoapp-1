package com.clou.photoshare.model;

import java.util.*;


public class TripBuilder {
    private String _id;
    private String _tripName = "Untitled Trip";
    private Set<String> _tripMember = new HashSet<>();

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

    public TripBuilder addMember(Collection<String> _members) {
        this._tripMember.addAll(_members);
        return this;
    }

}
