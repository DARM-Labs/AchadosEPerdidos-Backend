package com.darm.ifce.achadoseperdidos.DTO.person;

import com.darm.ifce.achadoseperdidos.model.enums.TypeClass;
import com.darm.ifce.achadoseperdidos.model.enums.TypeUser;
import com.fasterxml.jackson.annotation.JsonProperty;

public record PersonResponse(
        @JsonProperty("person-id")
        Long id,

        @JsonProperty("first-name")
        String firstName,
        
        @JsonProperty("last-name")
        String lastName,

        String email,

        TypeUser typeUser,

        TypeClass typeClass,
        
        String phone
) {
}
