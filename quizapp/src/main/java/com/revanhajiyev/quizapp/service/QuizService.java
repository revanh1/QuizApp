package com.revanhajiyev.quizapp.service;

import com.revanhajiyev.quizapp.entity.Question;
import com.revanhajiyev.quizapp.entity.QuestionWrapper;
import com.revanhajiyev.quizapp.entity.Quiz;
import com.revanhajiyev.quizapp.entity.Response;
import com.revanhajiyev.quizapp.model.QuestionDAO;
import com.revanhajiyev.quizapp.model.QuizDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizDAO quizDAO;
    private final QuestionDAO questionDAO;
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionDAO.findRandomQuestionByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDAO.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDAO.findById(id).orElseThrow(()->
                new RuntimeException("quiz not found!"));
        List<Question> questionsDB = quiz.getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();

        for(Question q: questionsDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(),
                    q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> submitQuiz(Integer id, List<Response> responses) {
        Quiz quiz = quizDAO.findById(id).get();
        List<Question> questions = quiz.getQuestions();

        int right = 0;
        int i = 0;

        for(Response response : responses){
           if(response.getResponse().equals(questions.get(i).getRightAnswer())) right++;
           i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
