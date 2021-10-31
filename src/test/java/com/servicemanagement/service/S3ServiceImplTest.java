package com.servicemanagement.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.servicemanagement.service.exceptions.FileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class S3ServiceImplTest {

    @Mock
    private AmazonS3 s3ClientMock;
    @Mock
    private InputStream inputStreamMock;
    @Mock
    private MultipartFile multipartFileMock;

    private S3ServiceImpl service;

    @BeforeEach
    void initTest() {
        openMocks(this);
        service = new S3ServiceImpl(s3ClientMock, "dummyBucketName");
    }

    @Test
    void shouldUploadFileTest() throws IOException, URISyntaxException {
        given(multipartFileMock.getInputStream()).willReturn(inputStreamMock);
        given(multipartFileMock.getOriginalFilename()).willReturn("dummy_file.jpg");
        given(multipartFileMock.getContentType()).willReturn("JPG");
        URL dummyUrl = new URL("https://dummyUrl.com");
        given(s3ClientMock.getUrl(anyString(), anyString())).willReturn(dummyUrl);
        service.uploadFile(multipartFileMock);
        verify(s3ClientMock, times(1)).putObject(anyString(), anyString(), any(InputStream.class), any(ObjectMetadata.class));
    }

    @Test
    void uploadFileShouldCatchExceptionTest() {
        assertThrows(FileException.class, () -> service.uploadFile(multipartFileMock)); }

    @Test
    void shouldValidateDeleteFileTest() {
        service.deleteFile("dummy_file.jpg");
        verify(s3ClientMock, times(1)).deleteObject(any(DeleteObjectRequest.class));
    }

    @Test
    void deleteFileShouldCatchExceptionTest() {
        doThrow(new RuntimeException("Mocked Exception")).when(s3ClientMock).deleteObject(any(DeleteObjectRequest.class));
        assertThrows(FileException.class, () -> service.deleteFile("dummy_file.jpg"));
    }

}