package com.clou.photoshare.services;


import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.clou.photoshare.model.Photo;

public class PhotoDistributionService {

    public PhotoDistributionService () { }

    // For testing, may not be used in production
    public String getFaces(Photo photo) {

        AmazonRekognition rekoclient = AmazonRekognitionClientBuilder.defaultClient();

        // use this later
//        String photoName = Photo.getS3Address().getName();
//        String photoBucketName = Photo.getS3Address().getBucketName();

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
