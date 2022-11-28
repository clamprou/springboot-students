package com.example.registrationlogindemo.dto;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private Long course_id;

    private String user_email;

    private String degree;

    private String studies;


}
