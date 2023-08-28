package com.example.swp490_g25_sse.service;

import java.util.List;

import com.example.swp490_g25_sse.dto.AnswerDto;
import com.example.swp490_g25_sse.model.Answer;
import com.example.swp490_g25_sse.model.Question;
import com.example.swp490_g25_sse.model.Student;
import com.example.swp490_g25_sse.model.Teacher;
import com.example.swp490_g25_sse.repository.AnswerRepository;
import com.example.swp490_g25_sse.repository.QuestionRepository;
import com.example.swp490_g25_sse.repository.StudentRepository;
import com.example.swp490_g25_sse.repository.TeacherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public Answer createAnswer(AnswerDto answerDto, long questionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService currentUser = (CustomUserDetailsService) auth.getPrincipal();

        String userRole = currentUser.getRole();
        Answer newAnswer = null;
        if (userRole.equals("ROLE_STUDENT")) {
            Student student = studentRepository.findFirstByUserId(currentUser.getUser().getId());
            Question question = questionRepository.findFirstById(questionId).get();
            Answer answer = new Answer(answerDto.getContent(), student, question);
            newAnswer = answerRepository.save(answer);
        } else {
            Teacher teacher = teacherRepository.findFirstByUserId(currentUser.getUser().getId());
            Question question = questionRepository.findFirstById(questionId).get();
            System.out.println(question.getId());
            Answer answer = new Answer(answerDto.getContent(), teacher, question);
            newAnswer = answerRepository.save(answer);
        }

        return newAnswer;
    }

    @Override
    public List<Answer> getAnswersByQuestionId(long questionId) {
        List<Answer> answers = answerRepository.findByQuestionId(questionId);
        return answers;
    }
}
