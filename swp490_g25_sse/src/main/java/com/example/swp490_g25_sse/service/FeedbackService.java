/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.swp490_g25_sse.service;

import com.example.swp490_g25_sse.dto.FeedbackDto;
import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Feedback;
import com.example.swp490_g25_sse.model.Student;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface FeedbackService {
    
    Feedback createNewFeedback(FeedbackDto feedbackDto, Student student, Course course);

    List<Feedback> getAllFeedBack(Course course);
}
