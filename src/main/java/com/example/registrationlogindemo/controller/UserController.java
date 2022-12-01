package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.StudentDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Student;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(path = "myapi/users")
    public List<UserDto> getUsers(){
        return userService.findAllUsers();
    }
    @PostMapping(path = "myapi/user")
    public UserDto newUser(@RequestBody UserDto userDto){
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User with email already exists!");
        }
        return userService.saveUser(userDto);
    }
}
