package com.example.testj.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import com.example.testj.examples.TodoBusinessImpl;
import com.example.testj.examples.TodoServiceImpl;

@ContextConfiguration(classes = { TodoBusinessImpTest.class, TodoServiceImpl.class })
@SpringBootTest
public class TodoBusinessImplTest2 {

	@MockBean
	TodoServiceImpl todoService;

	@InjectMocks
	@SpyBean
	TodoBusinessImpl todoBusinessImpl;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	/* Unit testing using mocks and spy */
	@Test
	public void getTodosTest() {

		// WHEN
		when(todoService.getTodosForUser("lav")).thenReturn(Arrays.asList("lavTask1", "lavTask2"));
		when(todoService.getTodos()).thenReturn(Arrays.asList("lavTask1", "lavTask2", "maniTask1", "maniTask2"));

		// can only be used for VOID return type
		// useful in cases where we need to call external service, example: auditing
		doNothing().when(todoBusinessImpl).logForAudit(any());

		// THEN
		List<String> actualUserTodos = todoBusinessImpl.retrieveTodos("lav");
		List<String> actualTodos = todoBusinessImpl.retrieveTodos();

		assertEquals(Arrays.asList("lavTask1", "lavTask2"), actualUserTodos, "user to do woked");
		assertEquals(2, actualUserTodos.size(), "Ok");
		verify(todoBusinessImpl, times(1)).retrieveTodos(any());

		assertEquals(Arrays.asList("lavTask1", "lavTask2", "maniTask1", "maniTask2"), actualTodos, "todos worked");
		assertEquals(4, actualTodos.size(), "Ok");
		verify(todoBusinessImpl, times(1)).retrieveTodos();
	}

	/* Integration testing using actual implementation */
	@Test
	public void getTodosTest2() {

		// WHEN
		// Assume this will make an external API call and we want to avoid that ;)
		doNothing().when(todoBusinessImpl).logForAudit(any());

		// THEN
		List<String> actualUserTodos = todoBusinessImpl.retrieveTodos("lav");
		List<String> actualTodos = todoBusinessImpl.retrieveTodos();

		assertEquals(Arrays.asList("lavTask1", "lavTask2"), actualUserTodos, "user to do woked");
		assertEquals(2, actualUserTodos.size(), "Ok");
		verify(todoBusinessImpl, times(1)).retrieveTodos(any());

		assertEquals(Arrays.asList("lavTask1", "lavTask2", "maniTask1", "maniTask2"), actualTodos, "todos worked");
		assertEquals(4, actualTodos.size(), "Ok");
		verify(todoBusinessImpl, times(1)).retrieveTodos();

	}

}
