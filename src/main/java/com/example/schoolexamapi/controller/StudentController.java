package com.example.schoolexamapi.controller;

import com.example.schoolexamapi.entity.Student;
import com.example.schoolexamapi.entity.StudentResponse;
import com.example.schoolexamapi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService service;

    @PostMapping("/login") // post mapping to hide any params and provide additional security
    public Student loginStudent(@RequestParam(name = "roll") long roll, @RequestParam(name = "password") String password) {
        return service.getStudentByRollAndPwd(roll, password);// password is excluded from the JSON response
    }

    @PostMapping("/upload") // TODO make insertion more secure
    public void uploadAnswers(@RequestBody List<StudentResponse> responseList) {
        service.uploadAnswers(responseList);
    }

    @GetMapping("/get")
    public Student getStudentByRollAndClass(@RequestParam(name = "roll") long roll) {
        return service.getStudentByRollNo(roll);
    }
}
