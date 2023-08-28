package com.example.swp490_g25_sse.repository;

import java.util.List;

import com.example.swp490_g25_sse.model.Answer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    
    List<Answer> findByQuestionId(long id);
}
