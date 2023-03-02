package com.example.course.resources.dtos;

import com.example.course.entities.User;
import com.example.course.entities.enuns.Office;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private String name;
    private String email;
    private Office userOffice;
    private String curso;
    private String semestre;
    private String matricula;
    private String contactNumber;
    private String urlImageUser;

    public static UserResponseDTO create(User user) {
        return UserResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .userOffice(user.getUserOffice())
                .curso(user.getCurso())
                .semestre(user.getSemestre())
                .matricula(user.getMatricula())
                .contactNumber(user.getContactNumber())
                .urlImageUser(user.getUrlImageUser())
                .build();
    }
}
