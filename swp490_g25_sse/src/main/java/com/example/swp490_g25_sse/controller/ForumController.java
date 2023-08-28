/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.controller;

import com.example.swp490_g25_sse.dto.AnswerDto;
import com.example.swp490_g25_sse.dto.QuestionDto;
import com.example.swp490_g25_sse.model.Answer;
import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Question;
import com.example.swp490_g25_sse.model.Student;
import com.example.swp490_g25_sse.model.Teacher;
import com.example.swp490_g25_sse.repository.StudentRepository;
import com.example.swp490_g25_sse.repository.TeacherRepository;
import com.example.swp490_g25_sse.service.AnswerService;
import com.example.swp490_g25_sse.service.CourseService;
import com.example.swp490_g25_sse.service.CustomUserDetailsService;
import com.example.swp490_g25_sse.service.QuestionService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping("/app/forum")
public class ForumController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private AnswerService answerService;

    @GetMapping("/{id}")
    private String index(Model model, @PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService userDetails = (CustomUserDetailsService) auth.getPrincipal();

        List<Question> questions = questionService.getQuestionsByCourseId(Long.parseLong(id));
        Course course = courseService.getCourseById(Long.parseLong(id)).get();
        String userImgUrl = userDetails.getUser().getImageURL();

        model.addAttribute("userImgUrl", userImgUrl);
        model.addAttribute("questions", questions);
        model.addAttribute("course", course);
        model.addAttribute("userName", userDetails.getUser().getFirstName());
        return "student/forum-questions";
    }

    @GetMapping("/question/{id}")
    private String getStudentQuestion(Model model, @PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService user = (CustomUserDetailsService) auth.getPrincipal();
        Student student = studentRepository.findFirstByUserId(user.getUser().getId());
        Long studentId = student.getId();
        Course course = courseService.getCourseById(Long.parseLong(id)).get();
        List<Question> questions = questionService.getQuestionsByCourseIdAndUserId(Long.parseLong(id), studentId);
        String userImgUrl = user.getUser().getImageURL();

        model.addAttribute("userImgUrl", userImgUrl);
        model.addAttribute("questions", questions);
        model.addAttribute("course", course);
        model.addAttribute("userName", user.getUser().getFirstName());
        return "student/student-questions";
    }

    @GetMapping("/creat/{id}")
    private String createQuestion(Model model, @PathVariable String id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService user = (CustomUserDetailsService) auth.getPrincipal();
        Student student = studentRepository.findFirstByUserId(user.getUser().getId());
        Long studentId = student.getId();
        Question question = new Question();
        Course course = courseService.getCourseById(Long.parseLong(id)).get();
        String userImgUrl = user.getUser().getImageURL();

        model.addAttribute("userImgUrl", userImgUrl);
        model.addAttribute("question", question);
        model.addAttribute("course", course);
        model.addAttribute("studentId", studentId);

        return "student/course-question";
    }

    @PostMapping("/creat/{id}")
    private String creatQuestion(@ModelAttribute("question") QuestionDto questionDto) {
        questionService.createQuestion(questionDto);
        return "redirect:/app/forum/{id}";
    }

    @GetMapping("/answer/course/{courseId}/question/{id}")
    private String getStudentAnswer(Model model, @PathVariable String id, @PathVariable String courseId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService user = (CustomUserDetailsService) auth.getPrincipal();
        String userRole = user.getRole();
        if (userRole.equals("ROLE_STUDENT")) {
            Student student = studentRepository.findFirstByUserId(user.getUser().getId());
            Long studentId = student.getId();
            Question question = questionService.getQuestionById(Long.parseLong(id)).get();
            Course course = courseService.getCourseById(Long.parseLong(courseId)).get();
            Answer answer = new Answer();
            List<Answer> answers = answerService.getAnswersByQuestionId(Long.parseLong(id));
            String userImgUrl = user.getUser().getImageURL();

            model.addAttribute("userImgUrl", userImgUrl);
            model.addAttribute("question", question);
            model.addAttribute("course", course);
            model.addAttribute("answer", answer);
            model.addAttribute("answers", answers);
            model.addAttribute("userName", user.getUser().getFirstName());
            model.addAttribute("studentId", studentId);
            return "student/forum-answers";
        } else {
            Teacher teacher = teacherRepository.findFirstByUserId(user.getUser().getId());
            Long teacherId = teacher.getId();
            Question question = questionService.getQuestionById(Long.parseLong(id)).get();
            Course course = courseService.getCourseById(Long.parseLong(courseId)).get();
            Answer answer = new Answer();
            List<Answer> answers = answerService.getAnswersByQuestionId(Long.parseLong(id));
            String userImgUrl = user.getUser().getImageURL();

            model.addAttribute("userImgUrl", userImgUrl);
            model.addAttribute("question", question);
            model.addAttribute("course", course);
            model.addAttribute("answer", answer);
            model.addAttribute("answers", answers);
            model.addAttribute("userName", user.getUser().getFirstName());
            model.addAttribute("teacherId", teacherId);
            return "teacher/forum-answers";
        }

    }

    @PostMapping("/answer/creat/{courseId}/{questionId}")
    private String creatAnswer(@ModelAttribute("answer") AnswerDto answerDto, @PathVariable String questionId) {
        answerService.createAnswer(answerDto, Long.parseLong(questionId));
        return "redirect:/app/forum/answer/course/{courseId}/question/{questionId}";
    }

    @GetMapping("/teacher/course/{id}")
    private String getCourseQuestions(Model model, @PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService userDetails = (CustomUserDetailsService) auth.getPrincipal();
        List<Question> questions = questionService.getQuestionsByCourseId(Long.parseLong(id));
        Course course = courseService.getCourseById(Long.parseLong(id)).get();
        String userImgUrl = userDetails.getUser().getImageURL();

        model.addAttribute("userImgUrl", userImgUrl);
        model.addAttribute("questions", questions);
        model.addAttribute("course", course);
        model.addAttribute("userName", userDetails.getUser().getFirstName());
        return "teacher/forum-questions";
    }
}
