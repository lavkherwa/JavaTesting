package com.example.testj.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

import com.example.testj.examples.TodoBusinessImpl;
import com.example.testj.examples.TodoService;
import com.example.testj.examples.TodoServiceImpl;

@ContextConfiguration(classes = { TodoBusinessImpTest.class, TodoServiceImpl.class })
public class TodoBusinessImpTest {

	@Test
	public void getTodosTest() {

		// PROVIDED
		/* Unit testing using mocks and spy */
		TodoService todoService = spy(TodoServiceImpl.class);
		// TodoService todoService = mock(TodoService.class);
		TodoBusinessImpl todoBusinessImpl = spy(new TodoBusinessImpl(todoService));

		// WHEN
		when(todoService.getTodosForUser("lav")).thenReturn(Arrays.asList("lavTask1", "lavTask2"));

		// can only be used for VOID return type
		// useful in cases where we need to call external service, example: auditing
		doNothing().when(todoBusinessImpl).logForAudit(any());

		List<String> actualUserTodos = todoBusinessImpl.retrieveTodos("lav");
		List<String> actualTodos = todoBusinessImpl.retrieveTodos();

		// THEN
		assertEquals(Arrays.asList("lavTask1", "lavTask2"), actualUserTodos, "user to do woked");
		assertEquals(2, actualUserTodos.size(), "Ok");
		verify(todoBusinessImpl, times(1)).retrieveTodos(any());
		
		assertEquals(Arrays.asList("lavTask1", "lavTask2", "maniTask1", "maniTask2"), actualTodos, "todos worked");
		assertEquals(4, actualTodos.size(), "Ok");
		verify(todoBusinessImpl, times(1)).retrieveTodos();
		
		
	}

	@Test
	public void getTodosTest2() {

		// PROVIDED
		/* Integration testing using actual implementation */
		TodoService todoService = new TodoServiceImpl();
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoService);

		// WHEN
		List<String> actual = todoBusinessImpl.retrieveTodos("lav");

		// THEN
		assertEquals(Arrays.asList("lavTask1", "lavTask2"), actual, "Cool");

	}

}
