package com.book.store.management.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.book.store.management.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamoDBServiceImpl implements DynamoDBService {

    @Autowired
    private AmazonDynamoDBAsync amazonDynamoDBAsync;

    @Override
    public Book save(Book book) {
        return null;
    }
}
