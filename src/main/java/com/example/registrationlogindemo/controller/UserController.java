package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.StudentDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Student;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
    public UserDto newUser(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email: "+ userDto.getEmail() +" is not Valid!");
        }
        if (userService.findByEmail(userDto.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User with email: "+ userDto.getEmail() +" already exists!");
        }
        return userService.saveUser(userDto);
    }
}
