package com.example.course.repositories;


import com.example.course.entities.FoundObject;
import com.example.course.entities.LostObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostObjectsRepository extends JpaRepository<LostObject, Long>{
}
