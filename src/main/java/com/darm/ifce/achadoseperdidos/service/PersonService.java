package com.darm.ifce.achadoseperdidos.service;



import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.darm.ifce.achadoseperdidos.DTO.AuthenticationResponse;
import com.darm.ifce.achadoseperdidos.DTO.mapper.person.PersonResponseMapper;
import com.darm.ifce.achadoseperdidos.DTO.person.PersonRequest;
import com.darm.ifce.achadoseperdidos.DTO.person.PersonResponse;
import com.darm.ifce.achadoseperdidos.DTO.person.PersonUpdateRequest;
import com.darm.ifce.achadoseperdidos.exceptions.ConflictException;
import com.darm.ifce.achadoseperdidos.exceptions.PersonNotFoundException;
import com.darm.ifce.achadoseperdidos.exceptions.ResourceNotFoundException;
import com.darm.ifce.achadoseperdidos.model.Person;
import com.darm.ifce.achadoseperdidos.repository.PersonRepository;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonResponseMapper mapper;
    private final UserService userService;

    public PersonService(PersonRepository personRepository,
            PersonResponseMapper mapper,UserService userService) {
        this.personRepository = personRepository;
        this.mapper = mapper;
        this.userService = userService;
    }

    public PersonResponse findById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);

        return mapper.map(person);

    }

    public Person searchById(Long id){
        return personRepository.findById(id)
                .orElseThrow(PersonNotFoundException::new);
    }

    public PersonResponse update(Long id, PersonUpdateRequest request) {
        Person person = personRepository.findById(id)
        .orElseThrow(PersonNotFoundException::new);

        if (!person.getEmail().equals(request.email())) {
            throw new ConflictException("The person email is already being used.");
        }

        person.setName(request.name());
        person.setEmail(request.email());
        person.setPhone(request.phone());
        person.setTypeClass(request.typeClass());
        person.setTypeUser(request.typeUser());
        
        personRepository.save(person);
        return mapper.map(person);
    }

    public List<PersonResponse> findAll(Pageable pageable) {
        return personRepository.findAll(pageable)
                .stream()
                .map(mapper::map)
                .toList();
    }
    public Person save(Person person){
        return personRepository.save(person);
    }

    public AuthenticationResponse createPerson(PersonRequest personRequest) {
        boolean personExists = personRepository.existsByEmail(personRequest.email());
        if (personExists) {
            throw new ConflictException("The person email is already being used.");
        }
        
        Person personSaved = new Person();
        BeanUtils.copyProperties(personRequest, personSaved);
        personSaved = personRepository.save(personSaved);

        return userService.createUser(personRequest, personSaved);
    }

    public boolean existsByEmail(String email) {
        return personRepository.existsByEmail(email);
    }

     @Transactional
    public void deleteById(Long id) {
        if (personRepository.existsById(id)) {
            userService.deleteByPersonId(id);
        }else{
            throw new ResourceNotFoundException("The user was not found in the database, " +
                        "please check the registered user.");
        }
    }
    public Person savePerson(Person person) {
        return this.personRepository.save(person);
    }
}
