package com.book.store.management.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.book.store.management.dto.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Service
public class S3ServiceImpl implements S3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Book readFromS3(String bucket, String key) throws IOException {
        try (S3Object s3Object = amazonS3.getObject(bucket, key)) {
            GZIPInputStream gzipInputStream = new GZIPInputStream(s3Object.getObjectContent());
            return objectMapper.readValue(gzipInputStream, Book.class);
        }
    }

    @Override
    public void writeToS3(String bucket, String key, String data) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
            gzipOutputStream.finish();
            byte[] zippedBytes = byteArrayOutputStream.toByteArray();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(zippedBytes.length);
            amazonS3.putObject(new PutObjectRequest(bucket, key, new ByteArrayInputStream(zippedBytes), metadata));
        }
    }
}
