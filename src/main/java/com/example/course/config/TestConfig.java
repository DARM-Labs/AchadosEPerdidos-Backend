package com.example.course.config;

import java.util.Arrays;

import com.example.course.entities.FoundObject;
import com.example.course.entities.LostObject;
import com.example.course.entities.enuns.Office;
import com.example.course.repositories.FoundObjectsRepository;
import com.example.course.repositories.LostObjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.course.entities.User;


import com.example.course.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FoundObjectsRepository foundObjectsRepository;

	@Autowired
	private LostObjectsRepository lostObjectsRepository;

	@Override
	public void run(String... args) throws Exception {
		User u1 = new User("Marcos Junior", "marcos@gmail.com", "ADS", "S4", "2022123","teste2", "image/garrafa15616.jpg", null, Office.TAES);
		User u2 = new User("Michael", "micha@gmail.com", "ADS", "S3", "2022145343","teste4", "image/garrafa15616.jpg","image/aluno23ad.jpg", Office.DISCENTE);

		FoundObject obj1 = new FoundObject(null, "garrafa", "quadra","image/garrafa15616.jpg",false, false,u1);
		FoundObject obj2 = new FoundObject(null, "tenis", "quadra","image/tenis12131.jpg",true, false, u2);

		LostObject obj3 = new LostObject(null, "livro","Lab1","image/tenis12131.jpg",false,null,true,u1);
		LostObject obj4 = new LostObject(null, "livro","Lab","image/tenis12131.jpg",false,null,true, u2);

		userRepository.saveAll(Arrays.asList(u1, u2));

		foundObjectsRepository.saveAll(Arrays.asList(obj1, obj2));

		lostObjectsRepository.saveAll(Arrays.asList(obj3, obj4));
		
		
		
	}
	
	
}
