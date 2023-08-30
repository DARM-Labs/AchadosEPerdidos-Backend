package com.darm.ifce.achadoseperdidos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.darm.ifce.achadoseperdidos.model.Thing;

public interface ThingRepository extends JpaRepository<Thing,Long> {

    List<Thing> findAllByUserId(Long id);
}
