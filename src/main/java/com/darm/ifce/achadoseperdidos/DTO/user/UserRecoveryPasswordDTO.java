package com.darm.ifce.achadoseperdidos.DTO.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRecoveryPasswordDTO {
    Long userId;
    String password;
    String secondPassword;
}
