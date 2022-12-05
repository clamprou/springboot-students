package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.entity.Role;
import com.example.registrationlogindemo.entity.Secretary;
import com.example.registrationlogindemo.repository.RoleRepository;
import com.example.registrationlogindemo.repository.SecretaryRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecretaryService {

    private final SecretaryRepository secretaryRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public SecretaryService(SecretaryRepository secretaryRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.secretaryRepository = secretaryRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
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
        Role role = roleRepository.findByName("ROLE_SECRETARY");
        if(role == null){
            role = checkRoleExist("ROLE_SECRETARY");
        }
        secretary.getUser().getRoles().add(role);
        userRepository.save(secretary.getUser());
        secretaryRepository.save(secretary);
        return secretary;
    }

    public Role checkRoleExist(String role) {
        Role rolee = new Role();
        rolee.setName(role);
        return roleRepository.save(rolee);
    }

}
