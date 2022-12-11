package com.example.registrationlogindemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="users")
public class User
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable=false)
    private Boolean activated = false;

    @Column(nullable=false)
    private String name;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(nullable=false, unique=true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable=false)
    private String password;

    @JsonBackReference
    @OneToOne(mappedBy = "user")
    private Student student;

    @JsonBackReference
    @OneToOne(mappedBy = "user")
    private Secretary secretary;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();


}
