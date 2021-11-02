package com.example.schoolexamapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int schoolClass;
    private long questionId, quizId; // same q may have multiple correct answers

    private long studentRoll; // for identification of individual student
    private char optionSelected;
    private int timeTakenInSec;


}
