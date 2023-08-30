package com.darm.ifce.achadoseperdidos.DTO.thing;


import java.time.LocalDate;
import com.darm.ifce.achadoseperdidos.model.enums.TypeObject;

public record ThingRequestDTO(
        
     String name,

     String local,
    
     boolean isReceipt,
    
     boolean isOwner,
    
     String urlImage,
    
     LocalDate dateMeeting,  

     TypeObject typeObject,

     Long userId
) {

}
