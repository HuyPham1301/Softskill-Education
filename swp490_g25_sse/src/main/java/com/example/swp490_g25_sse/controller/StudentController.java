package com.example.swp490_g25_sse.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.swp490_g25_sse.dto.CourseDto;
import com.example.swp490_g25_sse.dto.CourseOverviewDto;
import com.example.swp490_g25_sse.dto.FeedbackDto;
import com.example.swp490_g25_sse.dto.MilestoneDto;
import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Student;
import com.example.swp490_g25_sse.model.StudentCourseEnrollment;
import com.example.swp490_g25_sse.repository.StudentCourseEnrollmentRepository;
import com.example.swp490_g25_sse.service.CourseService;
import com.example.swp490_g25_sse.service.StudentService;
import com.example.swp490_g25_sse.service.TestResultService;
import com.example.swp490_g25_sse.service.CustomUserDetailsService;
import com.example.swp490_g25_sse.service.FeedbackService;
import com.example.swp490_g25_sse.service.LectureResultService;
import com.example.swp490_g25_sse.service.StudentCourseEnrollmentService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author msi
 */
@Controller
@RequestMapping("/app/student")
public class StudentController {

    @Autowired
    private Environment env;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentCourseEnrollmentService studentCourseEnrollmentService;

    @Autowired
    private LectureResultService lectureResultService;

    @Autowired
    private TestResultService testResultService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentCourseEnrollmentRepository studentCourseEnrollmentRepository;

    @GetMapping("")
    private String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService userDetails = (CustomUserDetailsService) auth.getPrincipal();
        List<CourseDto> mostLearnCourses = courseService.getMostEnrolledCourses();
        List<CourseDto> bestFeedbackCourses = courseService.getBestFeedbackCourses();
        List<Course> allCourses = courseService.getCourses();
        List<CourseDto> allCoursesDtos = new ArrayList<>();
        for (Course course : allCourses) {
            CourseDto courseDto = modelMapper.map(course, CourseDto.class);
            if (course.getTitle().length() > 20) {
                courseDto.setCourseTitle(course.getTitle().substring(0, 20) + "...");
            }
            allCoursesDtos.add(courseDto);
        }
        String userImgUrl = userDetails.getUser().getImageURL();

        model.addAttribute("userImgUrl", userImgUrl);
        model.addAttribute("userName", userDetails.getUser().getFirstName());
        model.addAttribute("mostLearnCourses", mostLearnCourses);
        model.addAttribute("bestFeedbackCourses", bestFeedbackCourses);
        model.addAttribute("allCourses", allCoursesDtos);
        model.addAttribute("user", "student");

        // System.out.println(top4Course.getContent().get(0).getImageUrl());
        return "student/course-screen";
    }

    @GetMapping("/registeredCourses")
    private String registeredCourses(Model model, @RequestParam(name = "isFinished") Boolean isFinished) {
        if (isFinished == null) {
            isFinished = true;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService userDetails = (CustomUserDetailsService) auth.getPrincipal();

        Student student = studentService.getStudentInfo(userDetails.getUser());
        String contentStr = "";
        String[] contentPart;
        char lastChar;
        String rawContent = "";
        String content = "";
        List<Course> courses = null;
        List<CourseDto> courseDtos = new ArrayList<>();
        courses = courseService.getStudentCourses(student, isFinished);
        if (!courses.isEmpty()) {
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
        }

        courses.stream().forEach(course -> {

            CourseDto courseDto = modelMapper.map(course, CourseDto.class);

            courseDto.setTotalEnrolls(studentCourseEnrollmentRepository.countByCourse(course));

            courseDtos.add(courseDto);
        });

        String userImgUrl = userDetails.getUser().getImageURL();

        model.addAttribute("userImgUrl", userImgUrl);

        model.addAttribute("userName", userDetails.getUser().getFirstName());
        model.addAttribute("courses", courseDtos);
        model.addAttribute("user", "student");
        model.addAttribute("isFinished", isFinished);

        // System.out.println(top4Course.getContent().get(0).getImageUrl());
        return "student/registered-course";
    }

    @GetMapping("/course/{id}/feedback")
    private String feedback(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("user", "student");

        return "student/feedback";
    }

    @ModelAttribute("feedback")
    public FeedbackDto feedbackDto() {
        return new FeedbackDto();
    }

    @PostMapping("/course/{id}/feedback")
    private String giveFeedback(@PathVariable String id,
            @ModelAttribute("feedback") FeedbackDto feedbackDto, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService userDetails = (CustomUserDetailsService) auth.getPrincipal();

        model.addAttribute("feedback", new FeedbackDto());
        Course course = courseService.getCourseById(Long.parseLong(id)).get();
        Student student = studentService.getStudentInfo(userDetails.getUser());
        Boolean isEnrolled = courseService.isAlreadyEnrolled(course, student);
        model.addAttribute("userName", userDetails.getUser().getFirstName());
        model.addAttribute("course", course);
        model.addAttribute("isEnrolled", isEnrolled);
        model.addAttribute("user", "student");

        feedbackService.createNewFeedback(feedbackDto, student, course);

        // System.out.println(top4Course.getContent().get(0).getImageUrl());
        return "student/feedback-successful";
    }

    @GetMapping("/course/{id}")
    private String courseInformation(@PathVariable String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService userDetails = (CustomUserDetailsService) auth.getPrincipal();

        Course course = courseService.getCourseById(Long.parseLong(id)).get();
        Student student = studentService.getStudentInfo(userDetails.getUser());
        Boolean isEnrolled = courseService.isAlreadyEnrolled(course, student);
        String userImgUrl = userDetails.getUser().getImageURL();
        List<CourseDto> mostLearnCourses = courseService.getMostEnrolledCourses();

        model.addAttribute("userImgUrl", userImgUrl);
        model.addAttribute("userName", userDetails.getUser().getFirstName());
        model.addAttribute("course", course);
        model.addAttribute("isEnrolled", isEnrolled);
        model.addAttribute("mostLearnCourses", mostLearnCourses);
        model.addAttribute("user", "student");

        // System.out.println(top4Course.getContent().get(0).getImageUrl());
        return "student/course-information";
    }

    @PostMapping("/course/enroll/{id}")
    private String enroll(@PathVariable String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService userDetails = (CustomUserDetailsService) auth.getPrincipal();

        Course course = courseService.getCourseById(Long.parseLong(id)).get();
        Student student = studentService.getStudentInfo(userDetails.getUser());

        StudentCourseEnrollment enrollment = new StudentCourseEnrollment(student, course, false);
        studentCourseEnrollmentService.save(enrollment);

        course.getLectures().stream().forEach(lecture -> lectureResultService.createLectureReport(enrollment, lecture));
        course.getTests().stream().forEach(test -> testResultService.createTestReport(enrollment, test));

        return "redirect:/app/student/course/" + id;

        // System.out.println(top4Course.getContent().get(0).getImageUrl());
    }

    @GetMapping("/learn/{id}")
    private String learn(@PathVariable String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService userDetails = (CustomUserDetailsService) auth.getPrincipal();

        Course course = courseService.getCourseById(Long.parseLong(id)).get();
        Student student = studentService.getStudentInfo(userDetails.getUser());
        Boolean isEnrolled = courseService.isAlreadyEnrolled(course, student);
        String userImgUrl = userDetails.getUser().getImageURL();

        model.addAttribute("userImgUrl", userImgUrl);
        model.addAttribute("userName", userDetails.getUser().getFirstName());
        model.addAttribute("course", course);

        String prefix = env.getProperty("FIREBASE_PREFIX");
        String suffix = env.getProperty("FIREBASE_SUFFIX");

        model.addAttribute("firebasePrefix", prefix);
        model.addAttribute("firebaseSuffix", suffix);
        model.addAttribute("enrollmentId", studentCourseEnrollmentService.getEnrollmentInfo(student, course).getId());
        model.addAttribute("user", "student");

        if (!isEnrolled) {
            return "redirect:/app/student/course/" + id;
        }

        // System.out.println(top4Course.getContent().get(0).getImageUrl());
        return "student/learn";
    }

    @GetMapping("/learn/{id}/overview")
    private String courseOverview(@PathVariable String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService userDetails = (CustomUserDetailsService) auth.getPrincipal();

        Course course = courseService.getCourseById(Long.parseLong(id)).get();
        Student student = studentService.getStudentInfo(userDetails.getUser());
        Boolean isEnrolled = courseService.isAlreadyEnrolled(course, student);
        StudentCourseEnrollment enroll = studentCourseEnrollmentService.getEnrollmentInfo(student, course);
        List<CourseOverviewDto> courseOverview = courseService.overview(enroll);
        String userImgUrl = userDetails.getUser().getImageURL();

        model.addAttribute("userImgUrl", userImgUrl);
        model.addAttribute("userName", userDetails.getUser().getFirstName());
        model.addAttribute("course", course);
        model.addAttribute("isEnrolled", isEnrolled);
        model.addAttribute("user", "student");

        String prefix = env.getProperty("FIREBASE_PREFIX");
        String suffix = env.getProperty("FIREBASE_SUFFIX");

        model.addAttribute("firebasePrefix", prefix);
        model.addAttribute("firebaseSuffix", suffix);
        model.addAttribute("enrollmentId", enroll.getId());
        model.addAttribute("courseOverview", courseOverview);

        if (!isEnrolled) {
            return "redirect:/app/student/course/" + id;
        }

        // System.out.println(top4Course.getContent().get(0).getImageUrl());
        return "student/course-overview";
    }

    @GetMapping("/learn/{id}/milestone")
    private String courseMileStone(@PathVariable String id, @RequestParam(name = "week") Integer week,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService userDetails = (CustomUserDetailsService) auth.getPrincipal();

        Course course = courseService.getCourseById(Long.parseLong(id)).get();
        Student student = studentService.getStudentInfo(userDetails.getUser());
        Boolean isEnrolled = courseService.isAlreadyEnrolled(course, student);
        StudentCourseEnrollment enroll = studentCourseEnrollmentService.getEnrollmentInfo(student, course);
        List<MilestoneDto> milestone = courseService.milestone(enroll);
        String userImgUrl = userDetails.getUser().getImageURL();

        model.addAttribute("userImgUrl", userImgUrl);
        model.addAttribute("userName", userDetails.getUser().getFirstName());
        model.addAttribute("course", course);
        model.addAttribute("isEnrolled", isEnrolled);
        model.addAttribute("user", "student");

        String prefix = env.getProperty("FIREBASE_PREFIX");
        String suffix = env.getProperty("FIREBASE_SUFFIX");

        model.addAttribute("firebasePrefix", prefix);
        model.addAttribute("firebaseSuffix", suffix);
        model.addAttribute("enrollmentId", enroll.getId());
        model.addAttribute("milestone", milestone);
        model.addAttribute("currentWeek", week);

        if (!isEnrolled) {
            return "redirect:/app/student/course/" + id;
        }

        // System.out.println(top4Course.getContent().get(0).getImageUrl());
        return "student/milestone";
    }
}
