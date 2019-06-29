package dev.ascg.todo.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.lang3.StringUtils;
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
	public Iterable<ToDo> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(value = TxType.SUPPORTS)
	public Iterable<ToDo> findBy(Integer id, String description, Status status) {
		List<ToDo> result = Collections.emptyList();
		Optional<ToDo> todo = Optional.empty();
		
		if (id != null && StringUtils.isNoneEmpty(description) && status != null) {
			todo = repository.findByIdAndDescriptionAndStatus(id, description, status);
		}  else if (id != null && StringUtils.isNoneEmpty(description)) {
			todo = repository.findByIdAndDescription(id, description);
		}  else if (id != null && status != null) {
			todo = repository.findByIdAndStatus(id, status);
		} else if (id != null) {
			todo = repository.findById(id);
		} else if (StringUtils.isNotEmpty(description) && status != null) {
			Iterable<ToDo> todoList = repository.findAllByDescriptionAndStatus(description, status);
			
			todoList.forEach(current -> { 
				result.add(current);
			});
		}
		
		if (todo.isPresent()) {
			result.add(todo.get());
		}
		
		return result;
	}
	
	@Override
	public void create(String description, Status status, byte[] image) {
		ToDo newTodo = new ToDo();
		
		newTodo.setDescription(description);
		newTodo.setStatus(status);
		newTodo.setImage(image);
		
		repository.save(newTodo);
	}

	@Override
	public void resolve(ToDo todo) {
		todo.setStatus(Status.RESUELTA);
		
		repository.save(todo);
	}

}
