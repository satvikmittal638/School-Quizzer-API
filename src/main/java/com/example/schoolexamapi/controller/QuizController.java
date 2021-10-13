package com.example.schoolexamapi.controller;

import com.example.schoolexamapi.entity.Questions;
import com.example.schoolexamapi.entity.Quiz;
import com.example.schoolexamapi.entity.QuizzesStudent;
import com.example.schoolexamapi.response_models.QuizAnalysis;
import com.example.schoolexamapi.service.QuizService;
import org.hibernate.mapping.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService service;

    // For teachers
    @PostMapping("/addQuiz")
    public Quiz addQuiz(@RequestBody Quiz quiz) {
        return service.addQuiz(quiz);
    }

    @PostMapping("/get")
    public List<Quiz> getQuizzesByAssignedBy(@RequestParam long assignedBy) {
        return service.getQuizzesByAssignedBy(assignedBy);
    }

    @PostMapping("/addQuestions")
    public List<Questions> addQuestionsForQuiz(@RequestBody List<Questions> questions, @RequestParam long quizId) {
        return service.addQuestionsForQuiz(questions, quizId);
    }

    @PostMapping("/addQuestion")
    public Questions addQuestionForQuiz(@RequestBody Questions question) {
        return service.addQuestionForQuiz(question);
    }


    // For Students
    @GetMapping("/details/{quizId}")
    public Quiz getQuizById(@PathVariable long quizId) {
        return service.getQuizById(quizId);
    }

    @GetMapping("/show/{roll}")
    public HashMap<String, List<Quiz>> getQuizzesForStudent(@PathVariable long roll) {
        return service.getQuizzesByRoll(roll);
    }

    @GetMapping("/{quizId}")
        public List<Questions> getQuestionsForQuiz(@PathVariable long quizId) {
        return service.getQuestionsByQuizId(quizId);
    }

    @GetMapping("/evaluate/{quizId}/{schoolClass}")
    public void evaluateAndUpdateResult(@PathVariable int schoolClass, @PathVariable long quizId) {
        service.evaluateResult(quizId, schoolClass);
    }

    @GetMapping("/result/{quizId}/{roll}")
    public QuizzesStudent getQuizStudent(@PathVariable long quizId, @PathVariable long roll) {
        return service.getQuizStudentByQuizIdAndRoll(quizId, roll);
    }

    @GetMapping("/result/{quizId}")
    public QuizAnalysis getQuizAnalysis(@PathVariable long quizId) {
        return service.getQuizAnalysis(quizId);
    }
}
