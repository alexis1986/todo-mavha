package dev.ascg.todo.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.ascg.todo.model.Status;
import dev.ascg.todo.model.ToDo;
import dev.ascg.todo.repository.ToDoRepository;
import dev.ascg.todo.service.ToDoService;

@Service
@Transactional(value = TxType.REQUIRED)
public class ToDoServiceImpl implements ToDoService {
	@Autowired
	private ToDoRepository repository;
	
	@Override
	@Transactional(value = TxType.SUPPORTS)
	public List<ToDo> findAll() {
		return repository.findAll();
	}
	
	@Override
	@Transactional(value = TxType.SUPPORTS)
	public List<ToDo> findBy(Integer id, String description, Status status) {
		return repository.findBy(id, description, status);
	}

	@Override
	public ToDo create(String description, String image) {
		ToDo todo = new ToDo();
		todo.setDescription(description);
		todo.setStatus(Status.PENDIENTE);
		todo.setImage(image);
		return repository.save(todo);
	}

	@Override
	public ToDo resolve(Integer id) {
		ToDo todo = repository.getOne(id);
		todo.setStatus(Status.RESUELTA);
		return repository.save(todo);
	}
}
