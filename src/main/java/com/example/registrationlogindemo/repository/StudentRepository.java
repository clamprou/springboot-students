package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findStudentByUser_Id(Long user_id);
    List<Student> findAllByStatus(String status);
}
