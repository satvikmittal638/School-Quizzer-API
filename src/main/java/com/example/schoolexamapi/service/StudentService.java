package com.example.schoolexamapi.service;

import com.example.schoolexamapi.entity.QuizzesStudent;
import com.example.schoolexamapi.entity.Student;
import com.example.schoolexamapi.entity.StudentResponse;
import com.example.schoolexamapi.repository.QuizzesStudentRepo;
import com.example.schoolexamapi.repository.StudentRepo;
import com.example.schoolexamapi.repository.StudentResponseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private StudentResponseRepo studentResponseRepo; // for handling answers
    @Autowired
    private QuizzesStudentRepo quizzesStudentRepo;

    public Student getStudentByRollAndPwd(long roll, String password) {
        // in case of invalid details null is returned
        return studentRepo.findByRollNoAndPassword(roll, password);
    }

    public void uploadAnswers(List<StudentResponse> responseList) {
        studentResponseRepo.saveAll(responseList);
        // Updating the quiz status
        StudentResponse testResponse = responseList.get(0);
        long roll = testResponse.getStudentRoll(),
                quizId = testResponse.getQuizId();
        QuizzesStudent quizzesStudent = quizzesStudentRepo.findByQuizIdAndStudentRoll(quizId, roll);
        quizzesStudent.setMarksObtained(-1); // quiz is attempted but not checked
        quizzesStudentRepo.save(quizzesStudent);
    }

    // for use by teachers
    public Student getStudentByRollNo(long roll) {
        return studentRepo.findById(roll).orElse(null);
    }
}
