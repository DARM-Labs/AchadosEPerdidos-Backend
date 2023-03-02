package com.example.course.repositories;


import com.example.course.entities.FoundObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoundObjectsRepository extends JpaRepository<FoundObject, Long>{
}
