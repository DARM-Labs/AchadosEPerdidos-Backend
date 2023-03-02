package com.example.course.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.course.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);

}
