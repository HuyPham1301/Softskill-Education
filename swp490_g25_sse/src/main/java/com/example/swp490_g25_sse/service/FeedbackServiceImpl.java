/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.service;

import com.example.swp490_g25_sse.dto.FeedbackDto;
import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Feedback;
import com.example.swp490_g25_sse.model.Student;
import com.example.swp490_g25_sse.repository.FeedbackRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

  @Autowired
  private FeedbackRepository feedbackRepository;

  @Override
  public Feedback createNewFeedback(FeedbackDto feedbackDto, Student student, Course course) {
    Feedback feedback = new Feedback(course, student, feedbackDto.getRating(), feedbackDto.getContentKey(),
        feedbackDto.getContent());

    feedbackRepository.save(feedback);

    return feedback;
  }

  @Override
  public List<Feedback> getAllFeedBack(Course course) {
    return feedbackRepository.findByCourse(course);
  }
}
