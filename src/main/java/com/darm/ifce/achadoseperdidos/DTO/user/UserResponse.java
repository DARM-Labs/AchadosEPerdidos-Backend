package com.darm.ifce.achadoseperdidos.DTO.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(

    @JsonProperty("user-id")
    Long id

) {
    
}
