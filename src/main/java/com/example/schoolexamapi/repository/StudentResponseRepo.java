package com.example.schoolexamapi.repository;

import com.example.schoolexamapi.entity.StudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentResponseRepo extends JpaRepository<StudentResponse, Long> {
    @Query("SELECT DISTINCT studentRoll FROM StudentResponse WHERE schoolClass=?1")
    List<Long> getAllRollNosByClass(int schoolClass);

    List<StudentResponse> findByStudentRollAndQuizId(long roll, long quizId);
}
