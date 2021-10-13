package com.example.schoolexamapi.service;

import com.example.schoolexamapi.entity.*;
import com.example.schoolexamapi.repository.*;
import com.example.schoolexamapi.response_models.QuizAnalysis;
import org.hibernate.mapping.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuizService {
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private QuizzesStudentRepo quizzesStudentRepo;
    @Autowired
    private QuizRepo quizRepo;
    @Autowired
    private QuestionsRepo questionsRepo;
    @Autowired
    private StudentResponseRepo studentResponseRepo; // for handling answers

    // For Teachers
    public Quiz addQuiz(Quiz quiz) {
        return quizRepo.save(quiz);
    }

    public List<Quiz> getQuizzesByAssignedBy(long assignedBy) {
        return quizRepo.findByAssignedBy(assignedBy);
    }

    public List<Questions> addQuestionsForQuiz(List<Questions> questions, long quizId) {
        for(Questions question: questions){
            question.setQuizId(quizId);
        }
        return questionsRepo.saveAll(questions);
    }

    public Questions addQuestionForQuiz(Questions question) {
        return questionsRepo.save(question);
    }


    // For Students
    public Quiz getQuizById(long quizId) {
        return quizRepo.findById(quizId).orElse(null);
    }

    /**
     * Fetches the records by the roll from the quizStudent table, then gets the required quizzes by matching the respective quizId
     *
     * @param roll Roll no of the student
     */
    public HashMap<String, List<Quiz>> getQuizzesByRoll(long roll) {


        List<QuizzesStudent> quizStudentInfos = quizzesStudentRepo.findByStudentRoll(roll);
        List<Quiz> missedQuizzes = new ArrayList<>();
        List<Quiz> attemptedQuizzes = new ArrayList<>();
        List<Quiz> liveQuizzes = new ArrayList<>();
        List<Quiz> upcomingQuizzes = new ArrayList<>();
        // Finding the quizzes by matching the id from the info and adding it to the quizzes list
        for (QuizzesStudent info : quizStudentInfos) {
            Quiz quiz = quizRepo.findById(info.getQuizId()).orElse(null);
            int marksObtained = info.getMarksObtained();


            // classifying acc to their types
            if (marksObtained >= 0) {
                attemptedQuizzes.add(quiz);
            }
            // for upcoming, missed and live marks = -3
            else if (marksObtained == -3) {

                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(quiz.getDateTimeFrom()) && now.isBefore(quiz.getDateTimeTo())) {
                    liveQuizzes.add(quiz);
                } else if (now.isBefore(quiz.getDateTimeFrom())) {
                    upcomingQuizzes.add(quiz);
                } else if (now.isAfter(quiz.getDateTimeTo())) {
                    missedQuizzes.add(quiz);
                }

            }
        }

        HashMap<String, List<Quiz>> allSortedQuizzes = new HashMap<>();
        allSortedQuizzes.put("missed", missedQuizzes);
        allSortedQuizzes.put("attempted", attemptedQuizzes);
        allSortedQuizzes.put("live", liveQuizzes);
        allSortedQuizzes.put("upcoming", upcomingQuizzes);
        return allSortedQuizzes;
    }

    /**
     * Gets the question of the desired quiz
     *
     * @param quizId id of the quiz whose questions you want
     * @return List of questions
     */
    public List<Questions> getQuestionsByQuizId(long quizId) {
        return questionsRepo.findByQuizId(quizId);
    }


    // Additional functions

    /**
     * Evaluates the result of a particular class of the school
     *
     * @param schoolClass Class whose result is to be evaluated
     */
    public void evaluateResult(long quizId, int schoolClass) {
        // getting all the roll no of all students of a class
        List<Long> rollNos = studentResponseRepo.getAllRollNosByClass(schoolClass);
        List<Questions> questions = questionsRepo.findByQuizId(quizId);// questions remain same for all

        for (long rollNo : rollNos) {
            // Calculating score of each student
            List<StudentResponse> answers = studentResponseRepo.findByStudentRollAndQuizId(rollNo, quizId);
            int score = getIndividualScore(questions, answers);

            // Updating the score of each student in the quiz-student relation table for a particular quiz
            QuizzesStudent quizzesStudent = quizzesStudentRepo.findByQuizIdAndStudentRoll(quizId, rollNo);
            quizzesStudent.setMarksObtained(score);
            quizzesStudentRepo.save(quizzesStudent);
        }

    }

    private int getIndividualScore(List<Questions> questions, List<StudentResponse> answers) {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Questions question = questions.get(i);
            StudentResponse answer = answers.get(i);
            // evaluating
            if (answer.getOptionSelected() == question.getCorrectOption()) {
                score++;
            }
        }
        return score;
    }

    public QuizzesStudent getQuizStudentByQuizIdAndRoll(long quizId, long roll) {
        return quizzesStudentRepo.findByQuizIdAndStudentRoll(quizId, roll);
    }

    public QuizAnalysis getQuizAnalysis(long quizId) {
        QuizAnalysis analysis = new QuizAnalysis();
        List<Student> leaderBoard = new ArrayList<>();
        // Getting top scorers and their details
        for (long roll : quizzesStudentRepo.getLeaderboardByQuiz(quizId)) {
            leaderBoard.add(studentRepo.findById(roll).orElse(null));
        }

        // Calculating attemption rate
        long allQuizzesCount = quizzesStudentRepo.countAllByQuizId(quizId),
                attemptedQuizCount = quizzesStudentRepo.getAttemptedCountByQuizId(quizId);
        long attemptionRate = Math.floorDiv(attemptedQuizCount * 100, allQuizzesCount);

        // preparing frequency distribution table
        long[][] table = quizzesStudentRepo.getMarksAndFrequency(quizId);

        // Populating analysis object
        analysis.setClassMarksAvg(quizzesStudentRepo.getQuizAvg(quizId));
        analysis.setLeaderBoard(leaderBoard);
        analysis.setAttemptRateInPercent(attemptionRate);
        analysis.setMarksFrequencyTable(table);

        return analysis;
    }


}
