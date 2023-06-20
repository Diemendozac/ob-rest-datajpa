package com.example.obrestdatajpa.services;

import static org.junit.jupiter.api.Assertions.*;

import com.example.obrestdatajpa.entities.Book;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class BookPriceCalculatorTest {

  @Test
  void calculatePrice() {
    Book book = new Book(12412L, "LoTR", "JRRT", 551, 30.55, LocalDate.of(1951, 5, 3), true);
    double price = BookPriceCalculator.calculatePrice(book);
    System.out.println(price);
    assertTrue(price > 0);
    assertTrue(price > book.getPrice());
    //assertEquals(39.3445, price);

  }
}
