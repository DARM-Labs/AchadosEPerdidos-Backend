package com.darm.ifce.achadoseperdidos.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.darm.ifce.achadoseperdidos.DTO.mapper.thing.ThingResponseMapper;
import com.darm.ifce.achadoseperdidos.DTO.thing.ThingRequestDTO;
import com.darm.ifce.achadoseperdidos.DTO.thing.ThingResponseDTO;
import com.darm.ifce.achadoseperdidos.exceptions.ResourceNotFoundException;
import com.darm.ifce.achadoseperdidos.model.Thing;
import com.darm.ifce.achadoseperdidos.model.User;
import com.darm.ifce.achadoseperdidos.model.enums.TypeObject;
import com.darm.ifce.achadoseperdidos.repository.ThingRepository;

import jakarta.transaction.Transactional;

@Service
public class ThingService {
    
    private final ThingRepository thingRepository;
    private final ThingResponseMapper mapper;
    private final UserService userService;

    @Autowired
    public ThingService(ThingRepository thingRepository, UserService userService,ThingResponseMapper mapper) {
        this.thingRepository = thingRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    public Thing save(Thing thing) {
        return thingRepository.save(thing);
    }
    
    public List<ThingResponseDTO> getAllThings(Pageable pageable) {
        return thingRepository.findAll(pageable)
                              .stream()
                              .map(mapper::map)
                              .toList();
    }   

    public Thing findById(Long id) {
        return thingRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("The user was not found in the database, " +
                            "please check the registered user."));
    }

    public ThingResponseDTO searchById(Long id){
        Thing thing = this.findById(id);
        return new ThingResponseDTO(thing);
    }

    public List<ThingResponseDTO> findByTypeObjectFound(){
        List<Thing> things = thingRepository.findAll();
        List<ThingResponseDTO> listResponse = new ArrayList<>();
        for (Thing thing : things) {
            if(thing.getTypeObject().equals(TypeObject.FOUND)){
                listResponse.add(new ThingResponseDTO(thing));
            }
        }
        return listResponse;
    }

    public List<ThingResponseDTO> findByTypeObjectLost(){
        List<Thing> things = thingRepository.findAll();
        List<ThingResponseDTO> listResponse = new ArrayList<>();
        for (Thing thing : things) {
            if(thing.getTypeObject().equals(TypeObject.LOST)){
                listResponse.add(new ThingResponseDTO(thing));
            }
        }
        return listResponse;
    }

    @Transactional
    public ThingResponseDTO createThing(ThingRequestDTO thingDTO){
            User user = userService.searchByID(thingDTO.userId());
            Thing thing = new Thing();           
            BeanUtils.copyProperties(thingDTO, thing); 
            thing.setUser(user);
            thing = this.save(thing);
            ThingResponseDTO responseDTO = new ThingResponseDTO(thing);
            return responseDTO;
    }

    public ThingResponseDTO updateThing(Long id, ThingRequestDTO  updatedThing) {
        Thing thing = thingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Thing with id " + id + " not found"));
        thing.setName(updatedThing.name());
        thing.setLocal(updatedThing.local());
        thing.setReceipt(updatedThing.isReceipt());
        thing.setOwner(updatedThing.isOwner());
        thing.setUrlImage(updatedThing.urlImage());
        thing.setDateMeeting(updatedThing.dateMeeting());
        thing.setTypeObject(updatedThing.typeObject());

        thing = this.save(thing);
        ThingResponseDTO responseDTO = new ThingResponseDTO(thing);
        return responseDTO;
    }

    public void deleteThing(Long id) {
        if(thingRepository.existsById(id)){
            thingRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("The user was not found in the database");
        }
    }

    public List<ThingResponseDTO> findByUserId(Long id) {
        try{
            userService.searchByID(id);
            List<ThingResponseDTO> thingResponse = new ArrayList<>();
            List<Thing> things = this.thingRepository.findAllByUserId(id);
            if(things != null){
                for (Thing thing : things) {
                ThingResponseDTO responseDTO = new ThingResponseDTO(thing);
                thingResponse.add(responseDTO);
                }
            }else{
               throw new ResourceNotFoundException("The user has no objects registered"); 
            }
            return thingResponse;
        }catch(ResourceNotFoundException e){
            throw new ResourceNotFoundException("The user was not found in the database");
        }
    }
}
