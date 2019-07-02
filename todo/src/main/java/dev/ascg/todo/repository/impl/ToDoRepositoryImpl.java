package dev.ascg.todo.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import dev.ascg.todo.model.Status;
import dev.ascg.todo.model.ToDo;
import dev.ascg.todo.repository.ToDoRepositoryCustom;

@Repository
public class ToDoRepositoryImpl implements ToDoRepositoryCustom{
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<ToDo> findBy(Integer id, String description, Status status) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ToDo> query = cb.createQuery(ToDo.class);
        Root<ToDo> todo = query.from(ToDo.class);
 
        List<Predicate> predicates = new ArrayList<>();
 
        if (id != null) {
        	Path<String> idPath = todo.get("id");
        	predicates.add(cb.equal(idPath, id));
        }
        
        if (description != null) {
        	Path<String> descriptionPath = todo.get("description");
        	predicates.add(cb.like(descriptionPath, description));
        }
        
        if (status != null) {
        	Path<String> statusPath = todo.get("status");
        	predicates.add(cb.equal(statusPath, status));
        }
        
        query.select(todo).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
 
        return em.createQuery(query).getResultList();
	}
}
