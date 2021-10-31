package com.servicemanagement.controller;

import com.servicemanagement.service.S3Service;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/bucket")
public class S3BucketController {

    private final S3Service s3Service;

    public S3BucketController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> uploadImage(@RequestPart("file") MultipartFile file) {
        URI uri = s3Service.uploadFile(file);
        return ResponseEntity.created(uri).build();
    }

}
