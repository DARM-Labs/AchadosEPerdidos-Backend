package com.darm.ifce.achadoseperdidos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.darm.ifce.achadoseperdidos.DTO.thing.ThingRequestDTO;
import com.darm.ifce.achadoseperdidos.DTO.thing.ThingResponseDTO;
import com.darm.ifce.achadoseperdidos.model.Thing;
import com.darm.ifce.achadoseperdidos.service.ThingService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/v1/thing")
@Tag(name = "Things", description = "This is api of things.")
public class ThingController {
    
    private final ThingService thingService;

    @Autowired
    public ThingController(ThingService thingService) {
        this.thingService = thingService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllThings(Pageable pageable) {
        return ResponseEntity.ok().body(thingService.getAllThings(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Thing> getThingById(@PathVariable Long id) {
        return ResponseEntity.ok().body(thingService.findById(id));
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<ThingResponseDTO>> getThingByUserId(@PathVariable Long id) {
        return ResponseEntity.ok().body(thingService.findByUserId(id));
    }

    @PostMapping
    public ResponseEntity<ThingResponseDTO> createThing(@RequestBody ThingRequestDTO thing) {
        return ResponseEntity.status(HttpStatus.CREATED).body(thingService.createThing(thing));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThingResponseDTO> updateThing(@PathVariable Long id, @RequestBody ThingRequestDTO thing) {
        return ResponseEntity.ok().body(thingService.updateThing(id, thing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteThing(@PathVariable Long id) {
        thingService.deleteThing(id);
        return ResponseEntity.noContent().build();
    }
}
