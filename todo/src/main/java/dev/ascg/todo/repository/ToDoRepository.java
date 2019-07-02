package dev.ascg.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.ascg.todo.model.ToDo;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Integer>, ToDoRepositoryCustom {
}
