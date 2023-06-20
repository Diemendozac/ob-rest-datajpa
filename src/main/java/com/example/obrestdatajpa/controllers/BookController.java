package com.example.obrestdatajpa.controllers;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repositories.BookRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {
  private final Logger log = LoggerFactory.getLogger(BookController.class);
  BookRepository bookRepository;

  public BookController(BookRepository repository) {
    this.bookRepository = repository;
  }

  // CRUD sobre la entidad Book

  // Buscar todos los libros
  @GetMapping("/api/books")
  public ResponseEntity<List<Book>> findAll() {
    List<Book> result = bookRepository.findAll();
    if (!result.isEmpty()) {
      return ResponseEntity.ok(result);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  // Buscar libro en base de datos por ID
  @GetMapping("/api/books/{id}")
  public ResponseEntity<Book> searchById(@PathVariable Long id) {

    Optional<Book> bookOpt = bookRepository.findById(id);
    return bookOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  // Crear libro
  @PostMapping("/api/books")
  public ResponseEntity<Book> create(@RequestBody Book book, @RequestHeader HttpHeaders headers) {
    System.out.println(headers.get("user-agent"));
    // SI el libro ya existe
    if (book.getId() != null) {
      log.warn("El libro ya existe. Intente con otro");
      return ResponseEntity.badRequest().build();
    }
    Book result = bookRepository.save(book);
    return ResponseEntity.ok(result);
  }

  // Actualizar libro
  @PutMapping("/api/books")
  public ResponseEntity<Book> update(@RequestBody Book book) {
    if (book.getId() == null) {
      log.warn("Trying to update a non identified book");
      return ResponseEntity.badRequest().build();
    }
    if (!bookRepository.existsById(book.getId())) {
      log.warn("Trying to update a non existing book");
      return ResponseEntity.notFound().build();
    }
    Book result = bookRepository.save(book);
    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/api/books/{id}")
  public ResponseEntity<Book> delete(@PathVariable Long id) {
    if (!bookRepository.existsById(id)) {
      log.warn("The book doesn't exist");
      return ResponseEntity.notFound().build();
    }

    bookRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/api/books")
  public ResponseEntity<Book> deleteAll() {
    log.warn("REST Request for deleting all books");
    bookRepository.deleteAll();
    return ResponseEntity.noContent().build();
  }
}
