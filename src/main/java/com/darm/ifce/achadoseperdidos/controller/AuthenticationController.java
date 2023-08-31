package com.darm.ifce.achadoseperdidos.controller;



import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darm.ifce.achadoseperdidos.DTO.AuthenticationRequest;
import com.darm.ifce.achadoseperdidos.DTO.AuthenticationResponse;
import com.darm.ifce.achadoseperdidos.DTO.RegisterUserRequest;
import com.darm.ifce.achadoseperdidos.DTO.user.UserRecoveryPasswordDTO;
import com.darm.ifce.achadoseperdidos.model.enums.PermissionTypeEnum;
import com.darm.ifce.achadoseperdidos.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register-admin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(@RequestBody @Valid RegisterUserRequest request) {
        return ResponseEntity.ok(authService.register(request, PermissionTypeEnum.ROLE_ADMIN));
    }


    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Login;",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Wrong password",
                    content = @Content) })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {

        return ResponseEntity.ok(authService.authenticate(request));

    }

}
