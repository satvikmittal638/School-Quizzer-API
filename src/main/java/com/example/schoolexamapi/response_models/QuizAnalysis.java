package com.example.schoolexamapi.response_models;

import com.example.schoolexamapi.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizAnalysis {
    private float classMarksAvg;
    private List<Student> leaderBoard;
    private long[][] marksFrequencyTable;
    private long attemptRateInPercent; // % of students who attempted the quiz out of all
}
