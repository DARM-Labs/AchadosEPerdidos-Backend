package com.example.course.resources;

import com.example.course.entities.FoundObject;
import com.example.course.resources.dtos.FoundObjectRequestDTO;
import com.example.course.services.FoundObjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/foundObjects")
public class FoundObjectsResource {

	@Autowired 
	private FoundObjectsService service;
	
	@GetMapping
	public ResponseEntity<Page<FoundObject>> findAll(Pageable pageable) {
		Page<FoundObject> foundObjects = service.findAll(pageable);
		return ResponseEntity.ok().body(foundObjects);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<FoundObject> findById(@PathVariable Long id){
		FoundObject obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping(value = "/createObject")
	public ResponseEntity<FoundObject> insert(@RequestBody FoundObject obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<FoundObject> update(@PathVariable Long id, @RequestBody FoundObjectRequestDTO obj) {
		return ResponseEntity.ok(service.update(id, obj));
	}
}