package com.example.schoolexamapi.repository;

import com.example.schoolexamapi.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepo extends JpaRepository<Quiz,Long> {
}
