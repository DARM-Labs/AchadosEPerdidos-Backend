package com.darm.ifce.achadoseperdidos.DTO.person;

import com.darm.ifce.achadoseperdidos.model.enums.TypeClass;
import com.darm.ifce.achadoseperdidos.model.enums.TypeUser;

public record PersonUpdateRequest(   
        String name,

        String email,

        TypeUser typeUser,

        TypeClass typeClass,
        
        String phone
) {
}
