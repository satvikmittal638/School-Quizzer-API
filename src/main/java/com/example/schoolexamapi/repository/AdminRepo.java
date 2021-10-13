package com.example.schoolexamapi.repository;

import com.example.schoolexamapi.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Administrator, Long> {
    Administrator findByEmailAndPassword(String email, String password);
}
