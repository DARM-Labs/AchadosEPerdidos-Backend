package com.darm.ifce.achadoseperdidos.DTO.thing;


import java.time.LocalDate;

import com.darm.ifce.achadoseperdidos.model.Thing;
import com.darm.ifce.achadoseperdidos.model.enums.TypeObject;

public record ThingResponseDTO(
    Long id,    

    String name,

    String local,
    
    boolean isReceipt,
    
    boolean isOwner,
    
    String urlImage,
    
    LocalDate dateMeeting,  

    TypeObject typeObject
) {

    public ThingResponseDTO(Thing thing) {
        this(thing.getId(), thing.getName(), thing.getLocal(), thing.isReceipt(), thing.isOwner(),
             thing.getUrlImage(), thing.getDateMeeting(), thing.getTypeObject());
    }
}
