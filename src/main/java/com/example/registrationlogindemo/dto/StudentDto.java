package com.example.registrationlogindemo.dto;

import com.example.registrationlogindemo.entity.Course;
import com.example.registrationlogindemo.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private Long id;

    private String degree;

    private String studies;

    private String course;
}
