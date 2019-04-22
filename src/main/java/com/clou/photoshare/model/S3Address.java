package com.clou.photoshare.model;


import org.springframework.stereotype.Component;


public class S3Address {
    private String addressKey;
    private String addressBucket;

    public S3Address(){

    }

    public S3Address (String addressBucket, String addressKey) {
        this.addressKey = addressKey;
        this.addressBucket = addressBucket;
    }

    public String getAddressKey() { return this.addressKey; }
    public String getAddressBucket() { return this.addressBucket; }

    public void setAddressKey(String addressKey) {
        this.addressKey = addressKey;
    }

    public void setAddressBucket(String addressBucket) {
        this.addressBucket = addressBucket;
    }
}
