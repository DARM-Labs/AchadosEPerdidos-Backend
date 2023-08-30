package com.darm.ifce.achadoseperdidos.DTO;


import com.darm.ifce.achadoseperdidos.model.enums.TypeClass;
import com.darm.ifce.achadoseperdidos.model.enums.TypeUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record RegisterPersonRequest(
        @JsonProperty("name") @NotBlank @NotEmpty String name,
        @JsonProperty("phone-number") @NotBlank @NotEmpty String phoneNumber,
        @JsonProperty("type-user") @NotBlank @NotEmpty TypeUser typeUser,
        @JsonProperty("type-class") @NotBlank @NotEmpty TypeClass typeClass,
        @JsonProperty("email") @NotBlank @NotEmpty @Email String email
) {
}
