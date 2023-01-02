package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto saveUser(UserDto userDto);

    void deleteUser(User user);

    void saveUser(User user);

    User findByEmail(String email);

    List<UserDto> findAllUsers();

    List<UserDto> findAllUsersNotActivated();

    Optional<User> findById(Long id);
}
