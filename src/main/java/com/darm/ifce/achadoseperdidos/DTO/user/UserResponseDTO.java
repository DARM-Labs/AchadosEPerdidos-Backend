package com.darm.ifce.achadoseperdidos.DTO.user;

import com.darm.ifce.achadoseperdidos.model.Person;
import com.darm.ifce.achadoseperdidos.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponseDTO {

    private Long id;
    private String login;

    @JsonProperty("person")
    private Person personDTO;


    public UserResponseDTO(){
    }

    public UserResponseDTO(User user){
        this.id = user.getId();
        this.login = user.getLogin();
    }

    public void setPersonDTO(Person savedPerson) {
        this.personDTO = savedPerson;
    }

}
