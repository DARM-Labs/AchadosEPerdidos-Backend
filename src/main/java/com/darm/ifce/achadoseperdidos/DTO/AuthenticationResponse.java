package com.darm.ifce.achadoseperdidos.DTO;

import com.darm.ifce.achadoseperdidos.DTO.user.UserResponseDTO;

public record AuthenticationResponse(String token, UserResponseDTO user) {
}
