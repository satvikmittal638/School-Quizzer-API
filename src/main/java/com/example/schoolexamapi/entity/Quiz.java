package com.example.schoolexamapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String subject;

    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;

    private int durationInMin;

    // TODO persist arrays
    private String instructions;

    private int maxMarks;
}
