package dev.ascg.todo.service;

import dev.ascg.todo.model.Status;
import dev.ascg.todo.model.ToDo;

public interface ToDoService {
	public Iterable<ToDo> findAll();
	
	public Iterable<ToDo> findBy(Integer id, String description, Status status);
	
	public void create(String description, Status status, byte[] image);
	
	public void resolve(ToDo todo);
}
