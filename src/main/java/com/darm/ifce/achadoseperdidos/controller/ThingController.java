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

import com.darm.ifce.achadoseperdidos.DTO.AuthenticationResponse;
import com.darm.ifce.achadoseperdidos.DTO.thing.ThingRequestDTO;
import com.darm.ifce.achadoseperdidos.DTO.thing.ThingResponseDTO;
import com.darm.ifce.achadoseperdidos.model.Thing;
import com.darm.ifce.achadoseperdidos.service.ThingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all things")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Returns a list of things",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ThingResponseDTO.class)) }),
             })
    @GetMapping
    public ResponseEntity<List<ThingResponseDTO>> getAllThings(Pageable pageable) {
        return ResponseEntity.ok().body(thingService.getAllThings(pageable));
    }

    @Operation(summary = "Get all things by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Returns a list of thing",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ThingResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Thing not found by Id",
                    content = @Content)
             })
    @GetMapping("/{id}")
    public ResponseEntity<ThingResponseDTO> getThingById(@PathVariable Long id) {
        return ResponseEntity.ok().body(thingService.searchById(id));
    }

    @Operation(summary = "Get all lost things")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Returns a list of thing",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ThingResponseDTO.class)) })
             }) 
    @GetMapping("/lost")
    public ResponseEntity<List<ThingResponseDTO>> getThingByLostObject() {
        return ResponseEntity.ok().body(thingService.findByTypeObjectLost());
    }

    @Operation(summary = "Get all found things")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Returns a list of thing",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ThingResponseDTO.class)) })
             }) 
    @GetMapping("/found")
    public ResponseEntity<List<ThingResponseDTO>> getThingByFoundObject() {
        return ResponseEntity.ok().body(thingService.findByTypeObjectFound());
    }

    @Operation(summary = "Get all things by User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Returns a list of thing",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ThingResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Thing not found by User",
                    content = @Content)
             })
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ThingResponseDTO>> getThingByUserId(@PathVariable Long id) {
        return ResponseEntity.ok().body(thingService.findByUserId(id));
    }

    @Operation(summary = "Save a thing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Thing saved.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ThingResponseDTO.class)) }) }) 
    @PostMapping
    public ResponseEntity<ThingResponseDTO> createThing(@RequestBody ThingRequestDTO thing) {
        return ResponseEntity.status(HttpStatus.CREATED).body(thingService.createThing(thing));
    }

    @Operation(summary = "Update thing by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Returns a list of thing",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ThingResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Thing not found by Id",
                    content = @Content)
             })
    @PutMapping("/{id}")
    public ResponseEntity<ThingResponseDTO> updateThing(@PathVariable Long id, @RequestBody ThingRequestDTO thing) {
        return ResponseEntity.ok().body(thingService.updateThing(id, thing));
    }

    @Operation(summary = "Delete thing by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Deleted thing",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ThingResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Thing not found by Id",
                    content = @Content)
             })    
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteThing(@PathVariable Long id) {
        thingService.deleteThing(id);
        return ResponseEntity.noContent().build();
    }
}
