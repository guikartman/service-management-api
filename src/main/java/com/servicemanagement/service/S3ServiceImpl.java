package com.servicemanagement.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.servicemanagement.service.exceptions.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URI;

@Service
public class S3ServiceImpl implements S3Service{

    private static final Logger logger = LoggerFactory.getLogger(S3ServiceImpl.class);

    private final AmazonS3 s3Client;
    private final String bucketName;

    public S3ServiceImpl(AmazonS3 s3Client, @Value("${s3.bucket}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    @Override
    public URI uploadFile(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            var inputStream = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadFile(inputStream,fileName, contentType);
        }catch (Exception ex) {
            throw new FileException("IOException error: "+ ex.getMessage(), ex);
        }
    }

    @Async
    @Override
    public void deleteFile(String keyName) {
        logger.info("Deleting file with name: {} ",keyName);
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, keyName));
        } catch (Exception e) {
            throw new FileException(String.format("Couldn't delete image %s, from the S3Bucket reason %s", keyName, e.getMessage()),e);
        }
        logger.info("File deleted successfully.");
    }

    private URI uploadFile(InputStream inputStream, String fileName, String contentType) {
            try {
                logger.info("Upload for the file {} started.", fileName);
                var metadata = new ObjectMetadata();
                metadata.setContentType(contentType);
                s3Client.putObject(bucketName,fileName,inputStream, metadata);
                logger.info("Upload completed.");
                return s3Client.getUrl(bucketName, fileName).toURI();
            } catch (Exception e) {
                throw new FileException("Error to convert URL into URI", e);
            }
    }
}
