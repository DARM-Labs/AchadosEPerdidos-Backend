package com.darm.ifce.achadoseperdidos.DTO.user;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.darm.ifce.achadoseperdidos.model.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserRequestDTO {

    @NotNull @NotEmpty
    private String login;

    @NotNull @NotEmpty
    private String password;

    public User toUser(){
        password = new BCryptPasswordEncoder().encode(password);
        return new User(login,password);
    }
    
}
