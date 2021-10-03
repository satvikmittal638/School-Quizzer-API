package com.example.schoolexamapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private long quizId; // single quiz will have multiple questions

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    private char correctOption; // ranges from A-B

}
