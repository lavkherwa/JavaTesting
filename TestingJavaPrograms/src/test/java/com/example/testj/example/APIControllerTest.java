package com.example.testj.example;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.testj.examples.APIController;
import com.example.testj.examples.Book;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = { APIController.class })
@WebMvcTest(APIController.class)
public class APIControllerTest {

	@Autowired
	MockMvc mvc;

	@Test
	public void getBooksTest() throws Exception {

		mvc.perform(MockMvcRequestBuilders//
				.get("/api/books")//
				.accept(MediaType.APPLICATION_JSON))//
				.andDo(print())//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$", hasSize(2)))//
				.andExpect(jsonPath("$[0].name", is("book1")))//
				.andExpect(jsonPath("$[1].name", is("book2")));
	}

	@Test
	public void getBookTest() throws Exception {

		mvc.perform(MockMvcRequestBuilders//
				.get("/api/books/{id}", 1)//
				.accept(MediaType.APPLICATION_JSON))//
				.andDo(print())//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.name", is("book1")));
	}

	@Test
	public void updateBookTest() throws Exception {

		Book book = new Book(1, "book1");

		String bookRequestBody = new ObjectMapper().writeValueAsString(book);
		
		mvc.perform(MockMvcRequestBuilders//
				.put("/api/books")//
				.content(bookRequestBody)//
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))//
				.andDo(print())//
				.andExpect(status().isAccepted())//
				.andExpect(content().string("Updated book with id 1"));
	}

	@Test
	public void addBookTest() throws Exception {
		
		Book book = new Book(1, "book1");

		String bookRequestBody = new ObjectMapper().writeValueAsString(book);
		
		mvc.perform(MockMvcRequestBuilders//
				.post("/api/books")//
				.content(bookRequestBody)//
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))//
				.andDo(print())//
				.andExpect(jsonPath("$.name", is("book1")));
	}

}
