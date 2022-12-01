package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.service.impl.SecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecretaryController {
    private final SecretaryService secretaryService;

    @Autowired
    public SecretaryController(SecretaryService secretaryService) {
        this.secretaryService = secretaryService;
    }

}
