package com.darm.ifce.achadoseperdidos;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.darm.ifce.achadoseperdidos.model.Person;
import com.darm.ifce.achadoseperdidos.model.User;
import com.darm.ifce.achadoseperdidos.model.enums.PermissionTypeEnum;
import com.darm.ifce.achadoseperdidos.model.enums.TypeClass;
import com.darm.ifce.achadoseperdidos.model.enums.TypeUser;
import com.darm.ifce.achadoseperdidos.service.PersonService;
import com.darm.ifce.achadoseperdidos.service.UserService;

@SpringBootApplication
public class AchadoseperdidosApplication implements CommandLineRunner{

	private final PersonService personService;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;

	public AchadoseperdidosApplication(PersonService personService, PasswordEncoder passwordEncoder,UserService userService){
		this.personService = personService;
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(AchadoseperdidosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(userService.search().isEmpty()){
			Person person = Person.builder()
						.email("marcos@gmail.com")
						.name("Marcos Junior")
						.phone("85988406679")
						.typeClass(TypeClass.ADS)
						.typeUser(TypeUser.DISCENTE)
						.build();

			person  = personService.save(person);

			User user = User.builder()
						.login(person.getEmail())
						.password(passwordEncoder.encode("123456"))
						.person(person)
						.permission(List.of(PermissionTypeEnum.ROLE_USER))
						.build();

			user = userService.saveUser(user);

			Person personSecond = Person.builder()
						.email("admins@gmail.com")
						.name("Admin")
						.phone("85988406679")
						.typeClass(null)
						.typeUser(TypeUser.DOSCENTE)
						.build();

			personSecond  = personService.save(personSecond	);

			User userSecond = User.builder()
						.login(personSecond.getEmail())
						.password(passwordEncoder.encode("123456"))
						.person(personSecond)
						.permission(List.of(PermissionTypeEnum.ROLE_ADMIN))
						.build();

			userSecond = userService.saveUser(userSecond);
			
			System.out.println("Rodando");
		}
	}

}
