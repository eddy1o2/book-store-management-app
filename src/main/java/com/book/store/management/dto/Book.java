package com.book.store.management.dto;

import lombok.Data;
import org.joda.time.DateTime;

@Data
public class Book {
    private String name;
    private String publisher;
    private Author author;
    private int quantity;
    private DateTime dateOfPurchase;
    private DateTime dateOfSale;
}
