package com.example.course.resources;

import java.net.URI;

import com.example.course.resources.dtos.ConfirmPasswordRequestDTO;
import com.example.course.resources.dtos.CreateUserRequestDTO;
import com.example.course.resources.dtos.UserLoginRequestDTO;
import com.example.course.resources.dtos.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.course.entities.User;
import com.example.course.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired 
	private UserService service;
	
	@GetMapping
	public ResponseEntity<Page<User>> findAll(Pageable pageable) {
		Page<User> users = service.findAll(pageable);
		return ResponseEntity.ok(users);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
		User userSaved = service.findById(id);
		return ResponseEntity.ok(UserResponseDTO.create(userSaved));
	}


	@PostMapping(value = "/login")
	public ResponseEntity<User> login(@RequestBody UserLoginRequestDTO login){
		// TODO: Implementar o login com o spring security

		User obj = service.loginUser(login.getEmail(), login.getPassword());

		return ResponseEntity.ok(obj);
	}
	@PostMapping(value = "/createUser/confirmPassword")
	public  ResponseEntity<Void> confirmPassword(@RequestBody ConfirmPasswordRequestDTO confirmPassword){
		service.confirmPassword(confirmPassword);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/createUser")
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody CreateUserRequestDTO userToSave){
		User userSaved = service.insert(userToSave);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(userSaved.getId()).toUri();

		return ResponseEntity.created(uri).body(userSaved);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody User obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok(UserResponseDTO.create(obj));
	}
}