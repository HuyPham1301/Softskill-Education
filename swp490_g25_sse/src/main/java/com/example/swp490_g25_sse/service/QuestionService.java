package com.example.swp490_g25_sse.service;

import java.util.List;
import java.util.Optional;

import com.example.swp490_g25_sse.dto.QuestionDto;
import com.example.swp490_g25_sse.model.Question;

public interface QuestionService {
    Question createQuestion(QuestionDto questionDto);

    List<Question> getQuestionsByCourseId(long id);

    List<Question> getQuestionsByCourseIdAndUserId(long courseId, long userId);

    Optional<Question> getQuestionById(long id);

}
