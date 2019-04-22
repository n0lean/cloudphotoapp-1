package com.clou.photoshare.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
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

    static public class S3AddressConverter implements DynamoDBTypeConverter<String, S3Address> {

        @Override
        public String convert(S3Address s3address) {
            String addressStr = null;
            S3Address itemS3Address = (S3Address) s3address;

            try {
                if (itemS3Address != null) {
                    addressStr = itemS3Address.getAddressBucket() + "@" + itemS3Address.getAddressKey();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return addressStr;
        }

        @Override
        public S3Address unconvert(String s) {
            S3Address s3Address = new S3Address();
            try {
                if (s != null && s.length() != 0) {
                    String[] data = s.split("@");
                    s3Address.setAddressBucket(data[0].trim());
                    s3Address.setAddressKey(data[1].trim());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return s3Address;
        }

    }

}
