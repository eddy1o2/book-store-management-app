package com.book.store.management.service;

import com.book.store.management.dto.Book;

public interface DynamoDBService {
    Book save(Book book);
}
