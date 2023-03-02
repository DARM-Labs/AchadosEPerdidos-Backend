package com.example.course.services;

import com.example.course.entities.User;
import com.example.course.resources.dtos.ConfirmPasswordRequestDTO;
import com.example.course.resources.dtos.CreateUserRequestDTO;
import com.example.course.repositories.UserRepository;
import com.example.course.services.exceptions.ConfirmPasswordException;
import com.example.course.services.exceptions.DatabaseException;
import com.example.course.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Transactional
    public User insert(CreateUserRequestDTO userToSave) {
        User user = new User(userToSave.getName(),
                userToSave.getEmail(),
                userToSave.getCurso(),
                userToSave.getSemestre(),
                userToSave.getMatricula(),
                userToSave.getSenha(),
                userToSave.getContactNumber(),
                userToSave.getUrlImageUser(),
                userToSave.getUserOffice());
        try {
           return repository.save(user);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error saving user: " + userToSave.getName() + " " + e.getMessage());
        }
    }

    public User loginUser(String email, String password) {
        if (findByEmail(email) != null) {
            Long idUser = findByEmail(email).getId();
            String userPassword = findById(idUser).getSenha();
            if (userPassword.equals(password)) {
                Optional<User> obj = repository.findByEmail(email);
                return obj.orElseThrow(() -> new ResourceNotFoundException(email));
            }
            throw new ResourceNotFoundException("Invalid password");
        }
        throw new ResourceNotFoundException("Not existing email registered");
    }

    public boolean recoverPassword(String nome, String email, Long id) {
        String userName = findById(id).getName();
        String userEmail = findById(id).getEmail();
        if (userName == nome && userEmail == email) {
            return true;
        }
        throw new IllegalArgumentException("Name or Email incorrect");
    }

    public boolean confirmPassword(ConfirmPasswordRequestDTO confirmPassword) {
        if (confirmPassword.getConfirm_password().equals(confirmPassword.getPassword())) {
            return true;
        }
        throw new ConfirmPasswordException("Password");
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional
    public User update(Long id, User obj) {

        User entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        User userToSave = updateData(entity, obj);
        return repository.save(userToSave);
    }

    private User updateData(User entity, User obj) {
        entity.setName(obj.getName());
        entity.setEmail(obj.getEmail());
        entity.setCurso(obj.getCurso());
        entity.setSemestre(obj.getSemestre());
        entity.setMatricula(obj.getMatricula());
        entity.setSenha(obj.getSenha());
        return entity;
    }
}
