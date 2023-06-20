package com.example.obrestdatajpa.controllers;

import static org.assertj.core.util.Arrays.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.obrestdatajpa.entities.Book;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
  private TestRestTemplate testRestTemplate;
  @Autowired private RestTemplateBuilder restTemplateBuilder;
  @LocalServerPort private int port;

  @BeforeEach
  void setUp() {
    restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
    this.testRestTemplate = new TestRestTemplate(restTemplateBuilder);
  }

  @Test
  void findAll() {
    HttpHeaders headers = createHeaders();
    createBooks(headers);
    ResponseEntity<Book[]> response = testRestTemplate.getForEntity("/api/books", Book[].class);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    List<Object> books = asList(response.getBody());
    System.out.println(books);
  }

  @Test
  void searchById() {
    HttpHeaders headers = createHeaders();
    createBooks(headers);
    ResponseEntity<Book> response = testRestTemplate.getForEntity("/api/books/1", Book.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    System.out.println(response.getBody());
  }

  @Test
  @DisplayName("Probando el método create de la clase BookController")
  void create() {

    HttpHeaders headers = createHeaders();
    String json =
        """
      {
          "title": "El Hombre Rebelde",
          "author": "Camus",
          "pages": 344,
          "price": 69.99,
          "relaseDate": "1953-06-21",
          "online": true
    }
    """;

    HttpEntity<String> request = new HttpEntity<>(json, headers);
    ResponseEntity<Book> response =
        testRestTemplate.exchange("/api/books", HttpMethod.POST, request, Book.class);
    assertEquals(1, response.getBody().getId());
    assertEquals("El Hombre Rebelde", response.getBody().getTitle());
    System.out.println(response.getBody());
  }

  @Test
  void update(){
    HttpHeaders headers = createHeaders();
    createBooks(headers);
    String json =
            """
          {
              "id": 5,
              "title": "El Hombre Rebelde",
              "author": "Albert Camus",
              "pages": 344,
              "price": 69.99,
              "relaseDate": "1953-06-21",
              "online": true
        }
        """;

    HttpEntity<String> request = new HttpEntity<>(json, headers);
    ResponseEntity result = testRestTemplate.exchange("/api/books", HttpMethod.PUT, request, Book.class);
    assertEquals(ResponseEntity.notFound().build().getStatusCode(), result.getStatusCode());
    System.out.println(result.getBody());
  }

  @Test
  void delete() {
    HttpHeaders headers = createHeaders();
    createBooks(headers);

    HttpEntity<String> request = new HttpEntity<>(headers);

    ResponseEntity result =
        testRestTemplate.exchange("/api/books/2", HttpMethod.DELETE, request, ResponseEntity.class);
    assertEquals(ResponseEntity.noContent().build().getStatusCode(), result.getStatusCode());
  }

  @Test
  void deleteAll() {
    HttpHeaders headers = createHeaders();
    createBooks(headers);

    HttpEntity<String> request = new HttpEntity<>(headers);
    ResponseEntity result = testRestTemplate.exchange("/api/books", HttpMethod.DELETE, request, ResponseEntity.class);
    assertEquals(ResponseEntity.noContent().build().getStatusCode(), result.getStatusCode());
  }

  HttpHeaders createHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    List<MediaType> media = new ArrayList<>();
    media.add(MediaType.APPLICATION_JSON);
    headers.setAccept(media);
    return headers;
  }

  void createBooks(HttpHeaders headers) {

    String jsonBook1 =
        """
              {
                  "title": "El Hombre Rebelde",
                  "author": "Camus",
                  "pages": 344,
                  "price": 69.99,
                  "relaseDate": "1953-06-21",
                  "online": true
            }
            """;
    String jsonBook2 =
        """
              {
                  "title": "EL Libro del Desasosiego",
                  "author": "Pessoa",
                  "pages": 541,
                  "price": 45.99,
                  "relaseDate": "1930-02-24",
                  "online": true
            }
            """;

    HttpEntity<String> createRequest1 = new HttpEntity<>(jsonBook1, headers);
    HttpEntity<String> createRequest2 = new HttpEntity<>(jsonBook2, headers);
    testRestTemplate.exchange("/api/books", HttpMethod.POST, createRequest1, Book.class);
    testRestTemplate.exchange("/api/books", HttpMethod.POST, createRequest2, Book.class);
  }
}
