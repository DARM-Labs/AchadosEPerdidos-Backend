package com.darm.ifce.achadoseperdidos.DTO.mapper.comment;

import org.springframework.stereotype.Component;

import com.darm.ifce.achadoseperdidos.DTO.comment.CommentResponseDTO;
import com.darm.ifce.achadoseperdidos.model.Comment;
import com.darm.ifce.achadoseperdidos.shared.Mapper;

@Component
public class CommentResponseMapper implements Mapper<Comment, CommentResponseDTO> {

    @Override
    public CommentResponseDTO map(Comment source) {
        return new CommentResponseDTO(
                        source.getId(),
                        source.getSubject(),
                        source.getUser().getId(),
                        source.getUser().getPerson().getName(),
                        source.getDate());
    }
}
