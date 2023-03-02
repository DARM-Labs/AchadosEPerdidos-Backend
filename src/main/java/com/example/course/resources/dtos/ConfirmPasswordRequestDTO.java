package com.example.course.resources.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmPasswordRequestDTO {
    private String password;
    private String confirm_password;
}
