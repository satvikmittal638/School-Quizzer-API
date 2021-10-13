package com.example.schoolexamapi.repository;

import com.example.schoolexamapi.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepo extends JpaRepository<Quiz,Long> {
    List<Quiz> findByAssignedBy(long assignedBy);
}
