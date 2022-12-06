package com.example.registrationlogindemo.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    @NotEmpty
    private String course_title;
    @Email
    @NotEmpty
    private String user_email;
    @NotEmpty
    private String degree;
    @NotEmpty
    private String studies;


}
