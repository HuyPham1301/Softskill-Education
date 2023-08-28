package com.example.swp490_g25_sse.controller;

import com.example.swp490_g25_sse.dto.CourseDto;
import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Feedback;
import com.example.swp490_g25_sse.model.Teacher;
import com.example.swp490_g25_sse.repository.StudentCourseEnrollmentRepository;
import com.example.swp490_g25_sse.repository.TeacherRepository;
import com.example.swp490_g25_sse.service.CourseService;
import com.example.swp490_g25_sse.service.CustomUserDetailsService;
import com.example.swp490_g25_sse.service.FeedbackService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author msi
 */
@Controller
@RequestMapping("/app/teacher")
public class TeacherController {

    @Autowired
    private Environment env;

    @Autowired
    private CourseService courseService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentCourseEnrollmentRepository studentCourseEnrollmentRepository;

    @GetMapping("")
    private String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService currentUser = (CustomUserDetailsService) auth.getPrincipal();

        Teacher teacher = teacherRepository.findFirstByUserId(currentUser.getUser().getId());

        List<Course> courses = teacher.getCourses();

        List<CourseDto> courseDtos = new ArrayList<>();

        String contentStr = "";
        String[] contentPart;
        char lastChar;
        String rawContent = "";
        String content = "";

        for (int i = 0; i < courses.size(); i++) {
            contentStr = courses.get(i).getContent();
            contentPart = contentStr.split("><");
            lastChar = contentPart[0].charAt(contentPart[0].length() - 1);

            if (String.valueOf(lastChar).equalsIgnoreCase("p")) {
                rawContent = contentPart[0] + ">";
                if (rawContent.length() >= 255) {
                    content = rawContent.substring(0, 255) + "...";
                } else {
                    content = rawContent;
                }
            } else {
                rawContent = contentPart[0];
                if (rawContent.length() >= 255) {
                    content = rawContent.substring(0, 255) + "...";
                } else {
                    content = rawContent;
                }
            }

            courses.get(i).setContent(content);

        }

        courses.stream().forEach(course -> {

            CourseDto courseDto = modelMapper.map(course, CourseDto.class);

            courseDto.setTotalEnrolls(studentCourseEnrollmentRepository.countByCourse(course));

            courseDtos.add(courseDto);
        });

        String userImgUrl = currentUser.getUser().getImageURL();

        model.addAttribute("userImgUrl", userImgUrl);
        model.addAttribute("userName", currentUser.getUser().getFirstName());        
        model.addAttribute("courses", courseDtos);
        model.addAttribute("user", "teacher");
        return "teacher/home-course";
    }

    @GetMapping("/create-course")
    private String createCourse(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService currentUser = (CustomUserDetailsService) auth.getPrincipal();

        String userImgUrl = currentUser.getUser().getImageURL();

        model.addAttribute("userImgUrl", userImgUrl);
        model.addAttribute("user", "teacher");
        model.addAttribute("userName", currentUser.getUser().getFirstName());

        String prefix = env.getProperty("FIREBASE_PREFIX");
        String suffix = env.getProperty("FIREBASE_SUFFIX");

        model.addAttribute("firebasePrefix", prefix);
        model.addAttribute("firebaseSuffix", suffix);

        return "teacher/create-course";
    }

    @GetMapping("/update-course/{id}")
    private String updateCourse(@PathVariable String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService currentUser = (CustomUserDetailsService) auth.getPrincipal();
        Long courseId = Long.parseLong(id);
        Optional<Course> course = courseService.getCourseById(courseId);
        System.out.println(course.get());
        String userImgUrl = currentUser.getUser().getImageURL();

        model.addAttribute("userImgUrl", userImgUrl);
        model.addAttribute("course", course.get());
        model.addAttribute("user", "teacher");
        model.addAttribute("userName", currentUser.getUser().getFirstName());

        String prefix = env.getProperty("FIREBASE_PREFIX");
        String suffix = env.getProperty("FIREBASE_SUFFIX");

        model.addAttribute("firebasePrefix", prefix);
        model.addAttribute("firebaseSuffix", suffix);

        return "teacher/update-course";
    }

    @GetMapping("/forum")
    private String forumQuestion(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService currentUser = (CustomUserDetailsService) auth.getPrincipal();

        Teacher teacher = teacherRepository.findFirstByUserId(currentUser.getUser().getId());

        List<Course> courses = teacher.getCourses();

        List<CourseDto> courseDtos = new ArrayList<>();

        String contentStr = "";
        String[] contentPart;
        char lastChar;
        String rawContent = "";
        String content = "";

        for (int i = 0; i < courses.size(); i++) {
            contentStr = courses.get(i).getContent();
            contentPart = contentStr.split("><");
            lastChar = contentPart[0].charAt(contentPart[0].length() - 1);

            if (String.valueOf(lastChar).equalsIgnoreCase("p")) {
                rawContent = contentPart[0] + ">";
                if (rawContent.length() >= 255) {
                    content = rawContent.substring(0, 255) + "...";
                } else {
                    content = rawContent;
                }
            } else {
                rawContent = contentPart[0];
                if (rawContent.length() >= 255) {
                    content = rawContent.substring(0, 255) + "...";
                } else {
                    content = rawContent;
                }
            }

            courses.get(i).setContent(content);

        }

        courses.stream().forEach(course -> {

            CourseDto courseDto = modelMapper.map(course, CourseDto.class);

            courseDto.setTotalEnrolls(studentCourseEnrollmentRepository.countByCourse(course));

            courseDtos.add(courseDto);
        });

        String userImgUrl = currentUser.getUser().getImageURL();

        model.addAttribute("userImgUrl", userImgUrl);

        model.addAttribute("userName", currentUser.getUser().getFirstName());        
        model.addAttribute("courses", courseDtos);
        model.addAttribute("user", "teacher");
        return "teacher/home-forum";
    }

    @GetMapping("/student-response")
    private String studentResponse(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService currentUser = (CustomUserDetailsService) auth.getPrincipal();

        Teacher teacher = teacherRepository.findFirstByUserId(currentUser.getUser().getId());

        String userImgUrl = currentUser.getUser().getImageURL();

        List<Course> courses = teacher.getCourses();

        List<Feedback> feedbacks = new ArrayList<>();

        courses.stream().forEach(course -> {
            feedbacks.addAll(feedbackService.getAllFeedBack(course));
        });

        model.addAttribute("userImgUrl", userImgUrl);
        model.addAttribute("userName", currentUser.getUser().getFirstName());
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("user", "teacher");
        return "teacher/students-response";
    }
}
