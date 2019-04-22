package com.clou.photoshare.model;


import org.springframework.stereotype.Component;


// function signiture only, @Hu Xin
@Component
public class S3Address {
    private String addressKey;
    private String addressBucket;

    public String getAddressKey() { return this.addressKey; }
    public String getAddressBucket() { return this.addressBucket; }

}
