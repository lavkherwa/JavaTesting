package com.example.testj.examples;

import java.util.List;

public interface TodoService {

	public List<String> getTodos();
	public List<String> getTodosForUser(String user);

}
