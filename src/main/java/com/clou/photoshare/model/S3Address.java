package com.clou.photoshare.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.clou.photoshare.errorHandler.InvalidArgumentException;
import org.springframework.stereotype.Component;


@Component

public class S3Address {
    private String addressKey;
    private String addressBucket;

    public S3Address () { }


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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if(obj == null || obj.getClass()!= this.getClass())
            return false;

        S3Address s3Address = (S3Address) obj;
        return this.addressKey.equals(s3Address.addressKey) && this.addressBucket.equals(s3Address.addressBucket);
    }

    @Override
    public String toString() {
        return this.getAddressBucket() + "@" + this.getAddressKey();
    }

    static public class S3AddressConverter implements DynamoDBTypeConverter<String, S3Address> {

        @Override
        public String convert(S3Address s3Address) {
            String addressStr;
            if (s3Address.addressBucket.isEmpty() || s3Address.addressKey.isEmpty())
                throw new InvalidArgumentException("wrong s3 address");
            addressStr = s3Address.toString();
            return addressStr;
        }

        @Override
        public S3Address unconvert(String s) {
            S3Address s3Address = new S3Address();
            if (s.length() >= 3 && s.contains("@") ) {
                String[] data = s.split("@");
                s3Address.setAddressBucket(data[0]);
                s3Address.setAddressKey(data[1]);
            }
            else {
                throw new InvalidArgumentException("wrong s3 address");
            }
            return s3Address;
        }

    }

}
