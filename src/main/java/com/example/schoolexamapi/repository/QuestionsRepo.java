package com.example.schoolexamapi.repository;

import com.example.schoolexamapi.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionsRepo extends JpaRepository<Questions, Long> {
    List<Questions> findByQuizId(long quizId);
}
