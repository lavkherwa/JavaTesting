package com.example.testj.examples;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

	private Map<String, List<String>> todos = new HashMap<String, List<String>>();

	public TodoServiceImpl() {

		List<String> lav = Arrays.asList("lavTask1", "lavTask2");
		List<String> mani = Arrays.asList("maniTask1", "maniTask2");

		todos.put("lav", lav);
		todos.put("mani", mani);
	}

	public List<String> getTodos() {

		return todos//
				.values()//
				.stream()//
				.flatMap(Collection::stream)//
				.collect(Collectors.toList());
	}

	public List<String> getTodosForUser(String user) {

		return todos.get(user);
	}

}
