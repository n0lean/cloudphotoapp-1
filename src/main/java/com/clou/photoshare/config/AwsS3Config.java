package com.clou.photoshare.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

@Configuration
public class AwsS3Config {

    @Value("${aws.access.key.id}")
    String awsAccessKey;

    @Value("${aws.access.key.secret}")
    String awsSecretKey;

    @Value("${aws.region}")
    String awsRegion;

    @Value("${aws.s3.photo.bucket}")
    String awsS3PhotoBucket;

    @Value("${aws.s3.photo.endpointUrl}")
    String endpointUrl;


    @Bean(name = "awsAccessKey")
    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    @Bean(name = "awsSecretKey")
    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    @Bean(name = "awsRegion")
    public Region getAwsRegion() {
        return Region.getRegion(Regions.fromName(awsRegion));
    }

    @Bean(name = "awsCredentialsProvider")
    public AWSCredentialsProvider getAWSCredentials() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(this.awsAccessKey, this.awsSecretKey);
        return new AWSStaticCredentialsProvider(awsCredentials);
    }

    @Bean(name = "awsS3PhotoBucket")
    public String getAWSS3AudioBucket() {
        return awsS3PhotoBucket;
    }

    @Bean(name = "awsS3Url")
    public String getEndpointUrl(){
        return endpointUrl;
    }
}
