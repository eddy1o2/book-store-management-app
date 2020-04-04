package com.book.store.management.service;

import com.book.store.management.dto.Book;

import java.io.IOException;

public interface S3Service {
    Book readFromS3(String bucket, String key) throws IOException;
    void writeToS3(String bucket, String key, String data) throws IOException;
}
