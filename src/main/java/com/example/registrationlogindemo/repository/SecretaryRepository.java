package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Secretary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecretaryRepository extends JpaRepository<Secretary,Long> {
    Secretary findSecretaryByUser_Id(Long user_id);
    Secretary findSecretaryByUserEmail(String user_email);
}
