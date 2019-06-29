package dev.ascg.todo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.ascg.todo.model.ToDo;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Integer> {

}
