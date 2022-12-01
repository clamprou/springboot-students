package com.example.registrationlogindemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecretaryDto {
    @Email
    @NotEmpty
    private String user_email;
    @NotEmpty
    private String name;
}
