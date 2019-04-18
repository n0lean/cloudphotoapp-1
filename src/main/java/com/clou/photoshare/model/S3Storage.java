package com.clou.photoshare.model;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.*;
import com.clou.photoshare.errorHandler.BucketNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.util.StreamUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class S3Storage {

    @Autowired
    String awsS3PhotoBucket;    // default bucket

    private AmazonS3 amazonS3;

    public S3Storage(@Autowired Region awsRegion, @Autowired AWSCredentialsProvider awsCredentialsProvider) {
        this.amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
    }

    public String createBucket(String bucketName){
        try {
            if (!this.amazonS3.doesBucketExistV2(bucketName)) {
                // Because the CreateBucketRequest object doesn't specify a region, the
                // bucket is created in the region specified in the client.
                this.amazonS3.createBucket(new CreateBucketRequest(bucketName));

                // Verify that the bucket was created by retrieving it and checking its location.
                String bucketLocation = this.amazonS3.getBucketLocation(new GetBucketLocationRequest(bucketName));
                return bucketName;
            }else{
                return "bucket fail";
            }
        }catch (AmazonServiceException e) {
            e.printStackTrace();
            return e.getMessage();
        }catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public void uploadObject(String keyName, String content, String bucketName) {
        try {
            this.amazonS3.putObject(bucketName, keyName, content);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            e.printStackTrace();

        }catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }

    public List<String> listObjects(String bucketName){
        List<String> res = new ArrayList<>();

        try{
            if (!amazonS3.doesBucketExistV2(bucketName)) {
                // to do add exception(http response?)
                throw new BucketNotFoundException("no bucket" + bucketName);
            }


            ObjectListing objectListing = this.amazonS3.listObjects(bucketName);
            for(S3ObjectSummary os : objectListing.getObjectSummaries()){
                res.add(os.getKey());
            }
            return res;
        }catch (AmazonServiceException e){
            System.err.println(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    public String downloadObject(String bucketName, String objectKey){
        try {
            if (!amazonS3.doesBucketExistV2(bucketName)) {
                // to do add exception(http response?)
                throw new BucketNotFoundException("no bucket" + bucketName);
            }
            S3Object o = amazonS3.getObject(bucketName, objectKey);
            S3ObjectInputStream s3is = o.getObjectContent();
            return StreamUtils.copyToString(s3is, StandardCharsets.UTF_8);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            throw new IllegalStateException(e);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            throw new IllegalStateException(e);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    public void deleteObject(String bucketName, String objectKey){

        try {
            if (!amazonS3.doesBucketExistV2(bucketName)) {
                // to do add exception(http response?)
                throw new BucketNotFoundException("no bucket" + bucketName);
            }
            amazonS3.deleteObject(bucketName, objectKey);
        } catch (AmazonServiceException e){
            //add error message?
            System.err.println(e.getErrorMessage());
            throw new IllegalStateException(e);
        }
    }

    public void deleteMutiObjects(String bucketName, String objectKeys[]) {
        try {
            if (!amazonS3.doesBucketExistV2(bucketName)) {
                // to do add exception(http response?)
                throw new BucketNotFoundException("no bucket" + bucketName);
            }
            if(objectKeys.length < 1){
                // to do add exception(http response?)
                return;
            }
            DeleteObjectsRequest delObjReq = new DeleteObjectsRequest("baeldung-bucket")
                    .withKeys(objectKeys);
            amazonS3.deleteObjects(delObjReq);
        } catch (AmazonServiceException e){
            //add error message?
            System.err.println(e.getErrorMessage());
            throw new IllegalStateException(e);
        }
    }

}
