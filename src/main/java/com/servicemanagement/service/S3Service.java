package com.servicemanagement.service;

import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

public interface S3Service {

    /**
     * This method should upload a image to the S3 Bitbucket and return the url.
     *
     * @param multipartFile
     * @return
     */
    URI uploadFile(MultipartFile multipartFile);


    /**
     * This method should delete an image from the s3 Bitbucket.
     *
     * @param keyName
     */
    void deleteFile(final String keyName);
}
