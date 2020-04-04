package com.example.testj.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import com.example.testj.examples.TodoBusinessImpl;
import com.example.testj.examples.TodoService;
import com.example.testj.examples.TodoServiceImpl;

@ContextConfiguration(classes = { TodoBusinessImpTest.class, TodoServiceImpl.class })
public class TodoBusinessImpTest {

	/* Unit testing using mocks and spy */
	@Test
	public void getTodosTest() {

		// PROVIDED
		TodoService todoService = mock(TodoServiceImpl.class);
		TodoBusinessImpl todoBusinessImpl = spy(new TodoBusinessImpl(todoService));

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

		// PROVIDED
		TodoService todoService = new TodoServiceImpl();
		TodoBusinessImpl todoBusinessImpl = spy(new TodoBusinessImpl(todoService));

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
