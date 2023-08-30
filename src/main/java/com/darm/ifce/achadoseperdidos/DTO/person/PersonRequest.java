package com.darm.ifce.achadoseperdidos.DTO.person;

import com.darm.ifce.achadoseperdidos.model.enums.TypeClass;
import com.darm.ifce.achadoseperdidos.model.enums.TypeUser;

public record PersonRequest(
        String name,

        String email,

        String phone,

        TypeUser typeUser,

        TypeClass typeClass,

        String password
) {
    
}
