package com.example.schoolexamapi.repository;

import com.example.schoolexamapi.entity.QuizzesStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizzesStudentRepo extends JpaRepository<QuizzesStudent, Long> {


    List<QuizzesStudent> findByStudentRoll(long roll);

//    QuizzesStudent findByStudentRoll(long roll);

    QuizzesStudent findByQuizIdAndStudentRoll(long quizId, long studentRoll);

    // considering 0 marks student for mean calculation purposes
    @Query("SELECT AVG(marksObtained) FROM QuizzesStudent WHERE quizId=?1 AND marksObtained >= 0")
    // to eliminate the students who haven't attempted the quiz
    float getQuizAvg(long quizId);

    @Query("SELECT studentRoll FROM QuizzesStudent WHERE quizId=?1 AND marksObtained>=0 ORDER BY marksObtained DESC")
    long[] getLeaderboardByQuiz(long quizId);


    int countAllByQuizId(long quizId);


    @Query("SELECT COUNT(studentRoll) FROM QuizzesStudent WHERE quizId=?1 AND marksObtained >= 0")
        // marks = -2 if quiz is unattempted
    int getAttemptedCountByQuizId(long quizId);


    @Query("SELECT marksObtained, COUNT(marksObtained)  FROM QuizzesStudent WHERE quizId=?1 AND marksObtained>=0 GROUP BY marksObtained")
    long[][] getMarksAndFrequency(long quizId);


}
