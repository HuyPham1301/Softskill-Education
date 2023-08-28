package com.example.swp490_g25_sse.service;

import java.util.List;
import java.util.Optional;

import com.example.swp490_g25_sse.dto.QuestionDto;
import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Question;
import com.example.swp490_g25_sse.model.Student;
import com.example.swp490_g25_sse.repository.CourseRepository;
import com.example.swp490_g25_sse.repository.QuestionRepository;
import com.example.swp490_g25_sse.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Question createQuestion(QuestionDto questionDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService currentUser = (CustomUserDetailsService) auth.getPrincipal();
        Course course = courseRepository.findFirstById(questionDto.courseId);
        Student student = studentRepository.findFirstByUserId(currentUser.getUser().getId());

        Question question = new Question(student, course, questionDto.getTitle(), questionDto.getTag(),
                questionDto.getContent());

        Question newQuestion = questionRepository.save(question);

        return newQuestion;
    }

    @Override
    public List<Question> getQuestionsByCourseId(long id) {
        List<Question> questions = questionRepository.findByCourseId(id);
        return questions;
    }

    @Override
    public List<Question> getQuestionsByCourseIdAndUserId(long courseId, long studentId) {
        List<Question> questions = questionRepository.findByCourseIdAndStudentId(courseId, studentId);
        return questions;
    }

    @Override
    public Optional<Question> getQuestionById(long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question;
    }
}
