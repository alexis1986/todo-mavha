package dev.ascg.todo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.ascg.todo.model.Status;
import dev.ascg.todo.model.ToDo;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Integer> {
	public Optional<ToDo> findByIdAndDescription(Integer id, String description);
	
	public Optional<ToDo> findByIdAndStatus(Integer id, Status status);
	
	public Optional<ToDo> findByIdAndDescriptionAndStatus(Integer id, String description, Status status);
	
	public Iterable<ToDo> findAllByDescriptionAndStatus(String description, Status status);	
}
