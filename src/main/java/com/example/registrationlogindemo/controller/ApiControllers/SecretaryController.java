package com.example.registrationlogindemo.controller.ApiControllers;

import com.example.registrationlogindemo.dto.SecretaryDto;
import com.example.registrationlogindemo.entity.Secretary;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.UserService;
import com.example.registrationlogindemo.service.impl.SecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class SecretaryController {
    private final SecretaryService secretaryService;
    private final UserService userService;

    @Autowired
    public SecretaryController(SecretaryService secretaryService, UserService userService) {
        this.secretaryService = secretaryService;
        this.userService = userService;
    }

    @GetMapping(path = "myapi/secretaries")
    public List<Secretary> getSecretaries() {
        return secretaryService.getSecretaries();
    }

    @PostMapping(path = "myapi/secretary")
    public Secretary newSecretary(@RequestBody SecretaryDto secretaryDto) {
        if (userService.findByEmail(secretaryDto.getUser_email()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email: "+ secretaryDto.getUser_email() +" doesnt exists!");
        }
        if(secretaryService.getSecretaryByUserEmail(secretaryDto.getUser_email()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Secretary with email: "+ secretaryDto.getUser_email() +" already exists!");
        }
        Secretary secretary = new Secretary();
        secretary.setDetails(secretaryDto.getDetails());
        User user = userService.findByEmail(secretaryDto.getUser_email());
        secretary.setUser(user);
        return secretaryService.saveSecretary(secretary);
    }
}
