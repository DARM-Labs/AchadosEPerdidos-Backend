package com.example.course.resources.dtos;

import com.example.course.entities.enuns.Office;
import lombok.Getter;

@Getter
public class CreateUserRequestDTO {
    private String name;
    private String email;
    private Office userOffice;
    private String curso;
    private String semestre;
    private String matricula;
    private String senha;
    private String contactNumber;
    private String urlImageUser;
}
