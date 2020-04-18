package com.example.testj.examples;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {

	@GetMapping("/books")
	public ResponseEntity<List<Book>> getBooks() {

		Book book1 = new Book(1, "book1");
		Book book2 = new Book(2, "book2");
		return new ResponseEntity<List<Book>>(Arrays.asList(book1, book2), HttpStatus.OK);
	}

	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable(value = "id") int id) {
		return new ResponseEntity<Book>(new Book(id, "book" + id), HttpStatus.OK);
	}

	@PutMapping("/books")
	public ResponseEntity<String> updateBook(@RequestBody Book book) {
		return new ResponseEntity<String>("Updated book with id " + book.getId(), HttpStatus.ACCEPTED);
	}

	@PostMapping("/books")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		return new ResponseEntity<Book>(book, HttpStatus.ACCEPTED);
	}
}
