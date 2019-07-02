package dev.ascg.todo.service;

import java.util.List;

import dev.ascg.todo.model.Status;
import dev.ascg.todo.model.ToDo;

public interface ToDoService {
	public List<ToDo> findAll();
	
	public List<ToDo> findBy(Integer id, String description, Status status);
	
	public ToDo create(String description, String image);
	
	public ToDo resolve(Integer id);
}
