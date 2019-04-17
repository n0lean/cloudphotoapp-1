package com.clou.photoshare.errorHandler;

public class BucketNotFoundException extends IllegalArgumentException{
    public BucketNotFoundException(String bucketName){
            super("bucket " + bucketName +  " is not existed ");
        }
}
