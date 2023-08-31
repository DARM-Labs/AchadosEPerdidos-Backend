package com.darm.ifce.achadoseperdidos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darm.ifce.achadoseperdidos.DTO.user.UserRecoveryLoginDTO;
import com.darm.ifce.achadoseperdidos.DTO.user.UserRecoveryPasswordDTO;
import com.darm.ifce.achadoseperdidos.DTO.user.UserResponse;
import com.darm.ifce.achadoseperdidos.DTO.user.VerificationCodeDTO;
import com.darm.ifce.achadoseperdidos.model.User;
import com.darm.ifce.achadoseperdidos.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "This is api of users.")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Update password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Request: Password change successfully;",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRecoveryPasswordDTO.class)) }),
            @ApiResponse(responseCode = "409", description = "Different passwords",
                    content = @Content) })
    @PostMapping("/updatePassword")
    public ResponseEntity<User> updatePassword(@RequestBody UserRecoveryPasswordDTO passwordsDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.updatePassword(passwordsDTO));
    }

    @Operation(summary = "Recovery password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Request: Email sent successfully;",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRecoveryLoginDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Email not sent",
                    content = @Content) })
    @PostMapping("/recoveryPassword")
    public ResponseEntity<Void> recoveryPassword(@RequestBody UserRecoveryLoginDTO loginDTO) throws MessagingException{
        this.userService.userRecoveryPassword(loginDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verification Code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Request: Correct code;",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VerificationCodeDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Incorrect code",
                    content = @Content) })
    @PostMapping("/verificationCode")
    public ResponseEntity<UserResponse> verficationCode(@RequestBody VerificationCodeDTO code){
        return ResponseEntity.ok().body(this.userService.verificationCode(code));
    }
}
