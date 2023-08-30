package com.darm.ifce.achadoseperdidos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.darm.ifce.achadoseperdidos.DTO.comment.CommentRequestDTO;
import com.darm.ifce.achadoseperdidos.DTO.comment.CommentResponseDTO;
import com.darm.ifce.achadoseperdidos.DTO.mapper.comment.CommentResponseMapper;
import com.darm.ifce.achadoseperdidos.exceptions.ResourceNotFoundException;
import com.darm.ifce.achadoseperdidos.model.Comment;
import com.darm.ifce.achadoseperdidos.model.Thing;
import com.darm.ifce.achadoseperdidos.model.User;
import com.darm.ifce.achadoseperdidos.repository.CommentRepository;
import com.darm.ifce.achadoseperdidos.repository.UserRepository;

@Service
public class CommentService {
     
    private final CommentRepository commentRepository;
    private final CommentResponseMapper mapper;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, CommentResponseMapper mapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public List<CommentResponseDTO> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable)
                                .stream()
                                .map(mapper::map)
                                .toList();
    }

    public List<CommentResponseDTO> getCommentsByThingId(Long thingId) {
        return commentRepository.findByThingId(thingId)
                                .stream()
                                .map(mapper::map)
                                .toList();
    }

    public Comment save(Comment comment){
         return commentRepository.save(comment);
    }

    public CommentResponseDTO createComment(CommentRequestDTO commentDTO) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setDate(LocalDate.now());

        User user = userRepository.findById(commentDTO.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Thing thing = new Thing();
        thing.setId(commentDTO.thingId());

        comment.setUser(user);
        
        comment.setThing(thing);
        comment = this.save(comment);
        CommentResponseDTO responseDTO = new CommentResponseDTO(comment);
        return responseDTO;
    }

    public Comment updateComment(Long id, CommentRequestDTO updatedCommentDTO) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        BeanUtils.copyProperties(updatedCommentDTO, existingComment, "id", "user", "thing");
        
        return commentRepository.save(existingComment);
    }

    public void deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Comment not found");
        }
    }
}
