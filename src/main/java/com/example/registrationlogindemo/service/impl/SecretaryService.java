package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.entity.Secretary;
import com.example.registrationlogindemo.entity.Student;
import com.example.registrationlogindemo.repository.SecretaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecretaryService {

    private final SecretaryRepository secretaryRepository;

    @Autowired
    public SecretaryService(SecretaryRepository secretaryRepository) {
        this.secretaryRepository = secretaryRepository;
    }

    public List<Secretary> getSecretaries(){
        return secretaryRepository.findAll();
    }

    public Secretary getSecretaryByUserId(Long user_id){
        return secretaryRepository.findSecretaryByUser_Id(user_id);
    }
    public Secretary getSecretaryByUserEmail(String user_email){
        return secretaryRepository.findSecretaryByUserEmail(user_email);
    }

    public Secretary saveSecretary(Secretary secretary){
        secretaryRepository.save(secretary);
        return secretary;
    }

}
