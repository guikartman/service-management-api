package com.servicemanagement.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    private final String awsId;
    private final String awsKey;
    private final String region;

    public S3Config(@Value("${aws.access_key_id}") String awsId,@Value("${aws.secret_access_key}") String awsKey,@Value("${s3.region}") String region) {
        this.awsId = awsId;
        this.awsKey = awsKey;
        this.region = region;
    }

    @Bean
    public AmazonS3 s3Client() {
        var awsCredentials = new BasicAWSCredentials(awsId, awsKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region)).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
    }
}
