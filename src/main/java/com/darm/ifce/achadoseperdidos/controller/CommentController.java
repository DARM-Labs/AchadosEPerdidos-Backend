package com.darm.ifce.achadoseperdidos.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darm.ifce.achadoseperdidos.DTO.comment.CommentRequestDTO;
import com.darm.ifce.achadoseperdidos.DTO.comment.CommentResponseDTO;
import com.darm.ifce.achadoseperdidos.model.Comment;
import com.darm.ifce.achadoseperdidos.service.CommentService;

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

    @GetMapping
    public List<CommentResponseDTO> getAllComments(Pageable pageable) {
        return commentService.getAllComments(pageable);
    }

    @GetMapping("/thing/{id}")
    public List<CommentResponseDTO> getCommentById(@PathVariable Long id) {
        return commentService.getCommentsByThingId(id);
    }

    @PostMapping
    public CommentResponseDTO createComment(@RequestBody CommentRequestDTO comment) {
        return commentService.createComment(comment);
    }

}
