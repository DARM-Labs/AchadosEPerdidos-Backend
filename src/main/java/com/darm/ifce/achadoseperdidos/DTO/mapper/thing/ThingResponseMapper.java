package com.darm.ifce.achadoseperdidos.DTO.mapper.thing;

import org.springframework.stereotype.Component;

import com.darm.ifce.achadoseperdidos.DTO.thing.ThingResponseDTO;
import com.darm.ifce.achadoseperdidos.model.Thing;
import com.darm.ifce.achadoseperdidos.shared.Mapper;

@Component
public class ThingResponseMapper implements Mapper<Thing, ThingResponseDTO> {
    @Override
    public ThingResponseDTO map(Thing source){
        return new ThingResponseDTO(source.getId(),
                source.getName(),
                source.getLocal(),
                source.isReceipt(),
                source.isOwner(),
                source.getUrlImage(),
                source.getDateMeeting(),
                source.getTypeObject());
    }
}
