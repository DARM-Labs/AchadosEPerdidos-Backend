package com.darm.ifce.achadoseperdidos.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.darm.ifce.achadoseperdidos.model.Person;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);
    boolean existsByEmail(String email);
}
