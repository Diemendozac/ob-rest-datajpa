package com.example.obrestdatajpa;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repositories.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class ObRestDataJpaApplication {

	public static void main(String[] args) {
		//Obtenemos un contenedor de beans
		ApplicationContext context = SpringApplication.run(ObRestDataJpaApplication.class, args);
		BookRepository repository = context.getBean(BookRepository.class);

		//CRUD
		//Crear un libro
		LocalDate fecha1 = LocalDate.of(2020, 06, 18);
		LocalDate fecha2 = LocalDate.of(1954, 02, 28);
		Book book1 = new Book(null, "La Náusea", "Sartre", 231, 29.99, fecha1, true);
		Book book2 = new Book(null, "La Caída", "Camus", 121, 39.99, fecha2, true);
		//Almacenar un Libro
		repository.save(book1);
		repository.save(book2);
		//Recuperar todos los libros
		System.out.println(repository.findAll());
		//Borrar un libro
		//repository.deleteById(1L);
		System.out.println(repository.findAll());
		System.out.println(repository.findAll());
	}

}
