package com.darm.ifce.achadoseperdidos.DTO.mapper.person;


import org.springframework.stereotype.Component;

import com.darm.ifce.achadoseperdidos.DTO.person.PersonResponse;
import com.darm.ifce.achadoseperdidos.model.Person;
import com.darm.ifce.achadoseperdidos.shared.Mapper;


@Component
public class PersonResponseMapper implements Mapper<Person, PersonResponse> {
    @Override
    public PersonResponse map(Person source) {
        return new PersonResponse(source.getId(),
                source.getFirtsName(),
                source.getLastName(),
                source.getEmail(),
                source.getTypeUser(),
                source.getTypeClass(),
                source.getPhone());
    }
}
