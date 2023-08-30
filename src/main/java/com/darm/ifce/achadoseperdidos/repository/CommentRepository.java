package com.darm.ifce.achadoseperdidos.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.darm.ifce.achadoseperdidos.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByThingId(Long id);
    
}
