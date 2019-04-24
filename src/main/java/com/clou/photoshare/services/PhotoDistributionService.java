package com.clou.photoshare.services;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.clou.photoshare.model.Photo;
import io.swagger.annotations.AuthorizationScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.Compression;
import java.util.List;

@Service
public class PhotoDistributionService {

    private AWSCredentialsProvider awsCredentialsProvider;
    private TripService tripService;
//    private PhotoService photoService;
    private AmazonRekognition rekoclient;
    private float similarityThreshold = 0.7f;


    // this can be use for development
//    public PhotoDistributionService () {
//        DefaultAWSCredentialsProviderChain credProvider = new DefaultAWSCredentialsProviderChain();
//        this.rekoclient = AmazonRekognitionClientBuilder.standard().withCredentials(credProvider).build();
//    }

    public PhotoDistributionService() {
        this.rekoclient = AmazonRekognitionClientBuilder.standard().withCredentials(awsCredentialsProvider).withRegion("us-east-1").build();
    }

    // main function
    public void assignViewer(Photo photo) {

        String tripId = photo.getTripId();

        // get list of Images that holds the profile photo of each people in that trip
        List<Photo> userProfilePhotos = tripService.getProfilePhotosByTripId(tripId);

        Image targetImage = awsImageConstructor(photo);

        // src photo is the profile photo, tar is the photo that need to assign viewer ids
        for (Photo srcPhoto : userProfilePhotos) {
            Image sourceImage = awsImageConstructor(srcPhoto);
            CompareFacesRequest request = new CompareFacesRequest()
                                            .withSourceImage(sourceImage)
                                            .withTargetImage(targetImage)
                                            .withSimilarityThreshold(similarityThreshold);

            try {
                CompareFacesResult result = this.rekoclient.compareFaces(request);
                List<CompareFacesMatch> facesMatches = result.getFaceMatches();
                if (facesMatches.size() == 0) {
                    continue;
                } else {
                    System.out.println("Face Matched");
//                    photoService.assignViewerOnPhotoById(photo, srcPhoto.getOwnerId());
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }


    }



    // return an AWS Image object
    public Image awsImageConstructor(Photo photo) {
        String targetPhotoBucket = photo.getBucketName();
        String targetPhotoKey = photo.getPhotoKey();

        Image img = new Image().withS3Object(new S3Object()
                                        .withBucket(targetPhotoBucket)
                                        .withName(targetPhotoKey));
        return img;
    }


    // For testing, not to be used in production
    public String compareFacesInImages(String photoName1, String photoName2) {

        String photoBucketName = "anda-bucket-cloudphoto-app";

        CompareFacesRequest request = new CompareFacesRequest()
                                            .withSourceImage(new Image()
                                            .withS3Object(new S3Object()
                                            .withBucket(photoBucketName).withName(photoName1)))
                                            .withTargetImage(new Image()
                                            .withS3Object(new S3Object()
                                            .withBucket(photoBucketName).withName(photoName2)));

        CompareFacesResult result = this.rekoclient.compareFaces(request);

        List<CompareFacesMatch> faceDetails = result.getFaceMatches();
        if (faceDetails.size() >0) {
            return "Matched";
        } else {
            return "Not Matched";
        }
    }




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
