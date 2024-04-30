package com.revanhajiyev.quizapp.model;

import com.revanhajiyev.quizapp.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDAO extends JpaRepository<Quiz, Integer> {

}
