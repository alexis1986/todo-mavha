package dev.ascg.todo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.ascg.todo.model.Status;
import dev.ascg.todo.model.ToDo;
import dev.ascg.todo.service.FileStorageService;
import dev.ascg.todo.service.ToDoService;

@RestController
public class ToDoRestController {
	@Autowired
	private ToDoService service;
	@Autowired
	private FileStorageService fileService;
	
	@RequestMapping(value = "/todo", method = RequestMethod.GET)
	public List<ToDo> find(@RequestParam(name = "id", required = false) Integer id, @RequestParam(name = "description", required = false) String description, @RequestParam(name = "status", required = false) Status status) {
		List<ToDo> result = new ArrayList<>();
		Iterable<ToDo> todoList = service.findBy(id, description, status);
		todoList.iterator().forEachRemaining(current-> {
			completeImageUrl(current);
			result.add(current);
		});
		return result;
	}
	
	@RequestMapping(value = "/todo", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ToDo create(@RequestParam("description") String description, @RequestParam("image") MultipartFile image) {
		try {
			ToDo todo = service.create(description, fileService.storeFile(image));
			completeImageUrl(todo);
			return todo;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al procesar la imagen enviada.", e);
		}
	}

	@RequestMapping(value = "/todo/{id}", method = RequestMethod.PATCH)
	public ToDo resolve(@PathVariable("id") Integer id) {
		try {
			return service.resolve(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al cambiar el estado de la tarea.", e);
		}
	}
	
	@RequestMapping(value = "/todo/image/{imageName:.+}")
	public ResponseEntity<Resource> downloadImage(@PathVariable("imageName") String imageName, HttpServletRequest request) {
		Resource resource = fileService.loadFileAsResource(imageName);
		
		String contentType = null;
		
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al obtenido el tipo de imagen.", e);
		}
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	private void completeImageUrl(ToDo todo) {
		String imageDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
														        .path("/todo/image/")
														        .path(todo.getImage())
														        .toUriString();
		
		todo.setImage(imageDownloadUri);
	}
}
