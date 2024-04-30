package com.revanhajiyev.quizapp.service;

import com.revanhajiyev.quizapp.entity.Question;
import com.revanhajiyev.quizapp.model.QuestionDAO;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionDAO questionDAO;
    public ResponseEntity<List<Question>> getAllQuestion() {
        try{
            return new ResponseEntity<>(questionDAO.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getAllQuestionByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDAO.findByCategory(category), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(questionDAO.findByCategory(category), HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDAO.save(question);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestionById(Integer id) {
        try {
            questionDAO.deleteById(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    public ResponseEntity<Question> updateQuestion(Integer id, Question qs) {
        Question foundedQuestion =questionDAO.findById(id).orElseThrow(()->
                new RuntimeException("task not found"));
        try {

            foundedQuestion.setQuestionTitle(qs.getQuestionTitle());
            foundedQuestion.setOption1(qs.getOption1());
            foundedQuestion.setOption2(qs.getOption2());
            foundedQuestion.setOption3(qs.getOption3());
            foundedQuestion.setOption4(qs.getOption4());
            foundedQuestion.setRightAnswer(qs.getRightAnswer());
            foundedQuestion.setDifficulty_level(qs.getDifficulty_level());
            foundedQuestion.setCategory(qs.getCategory());
            questionDAO.save(foundedQuestion);
            return new ResponseEntity<>(foundedQuestion, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        questionDAO.save(foundedQuestion);
        return new ResponseEntity<>(foundedQuestion, HttpStatus.OK);
    }
}
