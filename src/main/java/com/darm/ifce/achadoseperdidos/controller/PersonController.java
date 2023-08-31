package com.darm.ifce.achadoseperdidos.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.darm.ifce.achadoseperdidos.DTO.AuthenticationResponse;
import com.darm.ifce.achadoseperdidos.DTO.person.PersonRequest;
import com.darm.ifce.achadoseperdidos.DTO.person.PersonResponse;
import com.darm.ifce.achadoseperdidos.DTO.person.PersonUpdateRequest;
import com.darm.ifce.achadoseperdidos.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/persons")
@Tag(name = "Persons", description = "This is api of persons.")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(summary = "Get all Persons by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Returns a list of Person",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Person not found by Id",
                    content = @Content)
             })
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> getPersonById(@PathVariable Long id) {
        return ResponseEntity.ok().body(personService.findById(id));
    }

    @Operation(summary = "Get all Persons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Returns a list of Person",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonResponse.class)) })})
    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAllPersons(Pageable pageable) {
       return ResponseEntity.ok().body(personService.findAll(pageable));
    }

    @Operation(summary = "Save a Person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Person saved.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonResponse.class)) }) }) 
    @PostMapping
    public ResponseEntity<AuthenticationResponse> createPerson(@RequestBody PersonRequest personRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.createPerson(personRequest));
    }

    @Operation(summary = "Update Person by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Returns a list of Person",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Person not found by Id",
                    content = @Content)
             })
    @PutMapping("/{id}")
    public ResponseEntity<PersonResponse> updatePerson(@PathVariable Long id, @RequestBody PersonUpdateRequest request) {
        return ResponseEntity.ok().body(personService.update(id, request));
    }

    @Operation(summary = "Delete Person by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Deleted Person",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Person not found by Id",
                    content = @Content)
             }) 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonById(@PathVariable Long id) {
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
