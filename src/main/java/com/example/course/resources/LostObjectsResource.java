package com.example.course.resources;

import com.example.course.entities.LostObject;
import com.example.course.resources.dtos.LostObjectRequestDTO;
import com.example.course.resources.dtos.UpdateDeliveredRequestDTO;
import com.example.course.services.LostObjectsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Log4j2
@RestController
@RequestMapping(value = "/lostObjects")
public class LostObjectsResource {

    @Autowired
    private LostObjectsService service;

    @GetMapping
    public ResponseEntity<Page<LostObject>> findAll(Pageable pageable) {
        Page<LostObject> lostObjects = service.findAll(pageable);
        return ResponseEntity.ok().body(lostObjects);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LostObject> findById(@PathVariable Long id) {
        LostObject obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping(value = "/createObject")
    public ResponseEntity<LostObject> insert(@RequestBody LostObject obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<LostObject> update(@PathVariable Long id, @RequestBody LostObjectRequestDTO lostObjectToUpdate) {
       log.info("Request lost object to updating: " + "Id: " + id + " Name: " + lostObjectToUpdate.getName());
        return ResponseEntity.ok(service.update(id, lostObjectToUpdate));
    }

    @PatchMapping(value = "/updateStatusDeliveredOwner/{id}")
    public ResponseEntity<LostObject> updateStatusDeliveredOwner(@PathVariable Long id, UpdateDeliveredRequestDTO statusDelivered) {
        log.info("Request update to status DeliveredOwner: " + statusDelivered.isDelivered() + " in LostObject with id: " + id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/updateStatusDeliveredReception/{id}")
    public ResponseEntity<LostObject>  updateStatusDeliveredReception(@PathVariable Long id, UpdateDeliveredRequestDTO statusDelivered) {
        log.info("Request update to status DeliveredReception: " + statusDelivered.isDelivered() + " in LostObject with id: " + id);
        return ResponseEntity.noContent().build();
    }

}