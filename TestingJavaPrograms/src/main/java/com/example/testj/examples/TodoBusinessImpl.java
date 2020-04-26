package com.example.testj.examples;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TodoBusinessImpl {

	final TodoService todoService;

	public TodoBusinessImpl(TodoService todoService) {
		this.todoService = todoService;
	}

	public List<String> retrieveTodos() {
		logForAudit("todos called");
		return todoService.getTodos();
	}

	public List<String> retrieveTodos(String user) {
		logForAudit("todos called for user" + user);
		return todoService.getTodosForUser(user);
	}

	public void logForAudit(String message) {
		// call network and notify
	}

}
