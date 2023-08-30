package com.darm.ifce.achadoseperdidos.DTO.comment;

public record CommentRequestDTO(
    Long userId,
    Long thingId,
    String subject
) {

}