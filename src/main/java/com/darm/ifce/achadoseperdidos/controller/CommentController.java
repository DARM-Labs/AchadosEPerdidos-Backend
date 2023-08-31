package com.darm.ifce.achadoseperdidos.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darm.ifce.achadoseperdidos.DTO.comment.CommentRequestDTO;
import com.darm.ifce.achadoseperdidos.DTO.comment.CommentResponseDTO;
import com.darm.ifce.achadoseperdidos.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping(value = "/api/v1/comment")
@Tag(name = "Comments", description = "This is api of comments.")
public class CommentController {
     private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Get all Comment by Thing Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Returns a list of Person",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Person not found by Id",
                    content = @Content)
             })
    @GetMapping("/thing/{id}")
    public List<CommentResponseDTO> getCommentById(@PathVariable Long id) {
        return commentService.getCommentsByThingId(id);
    }

    @Operation(summary = "Save a Comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Returns a list of Person",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponseDTO.class)) })})
    @PostMapping
    public CommentResponseDTO createComment(@RequestBody CommentRequestDTO comment) {
        return commentService.createComment(comment);
    }

    @Operation(summary = "Delete Comment by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Request: Deleted Person",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Person not found by Id",
                    content = @Content)
             }) 
    @DeleteMapping
    public ResponseEntity<Object> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
