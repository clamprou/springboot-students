package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Role;
import com.example.registrationlogindemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findAllByRoles(Role role);

    List<User> findAllByActivated(Boolean activated);

    Optional<User> findById(Long id);
}
