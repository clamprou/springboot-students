package com.example.registrationlogindemo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name="student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "student_user",
//    joinColumns = {@JoinColumn(name = "student_id",referencedColumnName = "id")},
//    inverseJoinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")})
    private User user;

    private String status;

    private String studies;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "student_course",
//            joinColumns = { @JoinColumn(name = "student_id", referencedColumnName = "id") },
//            inverseJoinColumns = {@JoinColumn(name = "course_id",referencedColumnName = "id")})
    private Course course;

    private String degree;

}
