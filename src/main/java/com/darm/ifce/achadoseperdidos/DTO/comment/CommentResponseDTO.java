package com.darm.ifce.achadoseperdidos.DTO.comment;

import java.time.LocalDate;

import com.darm.ifce.achadoseperdidos.model.Comment;

public record CommentResponseDTO(
    Long id,
    String subject,
    Long userId,
    String userName,
    LocalDate date
) {

    public CommentResponseDTO(Comment comment) {
        this(comment.getId(), comment.getSubject(), comment.getUser().getId(),
            comment.getUser().getUsername(), comment.getDate());
    }

}
