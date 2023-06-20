package com.example.obrestdatajpa.services;

import com.example.obrestdatajpa.entities.Book;

public class BookPriceCalculator {
    public static final double IVA = 0.19;
    public static final double SHIPPING_PRICE = 2.99;
    public static double calculatePrice(Book book) {
        return book.getPrice() + book.getPrice() * IVA + SHIPPING_PRICE;
    }
}
