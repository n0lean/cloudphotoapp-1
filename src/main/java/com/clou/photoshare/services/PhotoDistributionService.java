package com.clou.photoshare.services;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.clou.photoshare.model.Photo;
import io.swagger.annotations.AuthorizationScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PhotoDistributionService {

    @Autowired
    private AWSCredentialsProvider awsCredentialsProvider;

    private AmazonRekognition rekoclient;

//    public PhotoDistributionService () {
//        DefaultAWSCredentialsProviderChain credProvider = new DefaultAWSCredentialsProviderChain();
//        this.rekoclient = AmazonRekognitionClientBuilder.standard().withCredentials(credProvider).build();
//    }


    public PhotoDistributionService() {
        this.rekoclient = AmazonRekognitionClientBuilder.standard().withCredentials(awsCredentialsProvider).build();
    }

    // For testing, not to be used in production
    public String getFaces(Photo photo) {

        AmazonRekognition rekoclient = AmazonRekognitionClientBuilder.defaultClient();


        String photoName = "testkey";
        String photoBucketName = "anda-bucket-cloudphoto-app";

        DetectFacesRequest request = new DetectFacesRequest()
                                        .withImage(new Image()
                                        .withS3Object(new S3Object()
                                        .withName(photoName).withBucket(photoBucketName)));

        DetectFacesResult result = rekoclient.detectFaces(request);


        return result.toString();
    }

}
