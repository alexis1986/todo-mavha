package dev.ascg.todo.repository;

import java.util.List;

import dev.ascg.todo.model.Status;
import dev.ascg.todo.model.ToDo;

public interface ToDoRepositoryCustom {
	public List<ToDo> findBy(Integer id, String description, Status status);
}
