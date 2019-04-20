package com.clou.photoshare.services;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.clou.photoshare.model.Photo;
import io.swagger.annotations.AuthorizationScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.Compression;
import java.util.List;

@Component
public class PhotoDistributionService {

    @Autowired
    private AWSCredentialsProvider awsCredentialsProvider;

    private AmazonRekognition rekoclient;

    // this can be use for development
//    public PhotoDistributionService () {
//        DefaultAWSCredentialsProviderChain credProvider = new DefaultAWSCredentialsProviderChain();
//        this.rekoclient = AmazonRekognitionClientBuilder.standard().withCredentials(credProvider).build();
//    }


    public PhotoDistributionService() {
        this.rekoclient = AmazonRekognitionClientBuilder.standard().withCredentials(awsCredentialsProvider).build();
    }

    // For testing, not to be used in production
//    public String compareFacesInImages(Photo photo1, Photo photo2) {
//        String photoName1 = "testkey2";
//        String photoName2 = "testkey";
//        String photoBucketName = "anda-bucket-cloudphoto-app";
//
//        CompareFacesRequest request = new CompareFacesRequest()
//                                            .withSourceImage(new Image()
//                                            .withS3Object(new S3Object()
//                                            .withBucket(photoBucketName).withName(photoName1)))
//                                            .withTargetImage(new Image()
//                                            .withS3Object(new S3Object()
//                                            .withBucket(photoBucketName).withName(photoName2)));
//
//        CompareFacesResult result = this.rekoclient.compareFaces(request);
//
//        List<CompareFacesMatch> faceDetails = result.getFaceMatches();
//
//        for (CompareFacesMatch match : faceDetails) {
//            ComparedFace face = match.getFace();
//
//        }
//    }




    // For testing, not to be used in production
    public String getFaces(Photo photo) {

        // AmazonRekognition rekoclient = AmazonRekognitionClientBuilder.defaultClient();


        String photoName = "testkey";
        String photoBucketName = "anda-bucket-cloudphoto-app";

        DetectFacesRequest request = new DetectFacesRequest()
                                        .withImage(new Image()
                                        .withS3Object(new S3Object()
                                        .withName(photoName).withBucket(photoBucketName)));

        DetectFacesResult result = this.rekoclient.detectFaces(request);


        return result.toString();
    }

}
