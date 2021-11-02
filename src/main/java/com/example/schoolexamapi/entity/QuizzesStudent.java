package com.example.schoolexamapi.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class QuizzesStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sno;

    @Column(nullable = false)
    private long quizId,studentRoll; // multiple quiz-student relations might exist



    // -3 implies that quiz is unattempted
    // -2 implies that quiz is attempted but not successfully submitted(in case of switch off or app kill)
    // -1 implies that quiz is attempted successfully but not checked
    @ColumnDefault("-3")
    private int marksObtained; // to be updated only after a successful attempt of the test

}
