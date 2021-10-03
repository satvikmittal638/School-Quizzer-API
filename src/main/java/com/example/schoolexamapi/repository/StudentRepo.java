package com.example.schoolexamapi.repository;

import com.example.schoolexamapi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {
    Student findByRollNoAndPassword(long rollNo, String password);
}
