package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserDto userDto);
    void saveUser(User user);

    User findByEmail(String email);

    List<UserDto> findAllUsers();

    Optional<User> findById(Long id);
}
