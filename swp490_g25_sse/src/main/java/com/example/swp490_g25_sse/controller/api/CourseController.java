/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.swp490_g25_sse.controller.api;

import com.example.swp490_g25_sse.dto.CourseDto;
import com.example.swp490_g25_sse.dto.LectureDto;
import com.example.swp490_g25_sse.dto.TestDto;
import com.example.swp490_g25_sse.exception.BaseRestException;
import com.example.swp490_g25_sse.model.Course;
import com.example.swp490_g25_sse.model.Lecture;
import com.example.swp490_g25_sse.model.LectureResult;
import com.example.swp490_g25_sse.model.Student;
import com.example.swp490_g25_sse.model.StudentCourseEnrollment;
import com.example.swp490_g25_sse.model.Teacher;
import com.example.swp490_g25_sse.model.Test;
import com.example.swp490_g25_sse.model.TestResult;
import com.example.swp490_g25_sse.repository.TeacherRepository;
import com.example.swp490_g25_sse.service.CourseService;
import com.example.swp490_g25_sse.service.CustomUserDetailsService;
import com.example.swp490_g25_sse.service.LectureResultService;
import com.example.swp490_g25_sse.service.StudentCourseEnrollmentService;
import com.example.swp490_g25_sse.service.StudentService;
import com.example.swp490_g25_sse.service.TestResultService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author msi
 */
@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentCourseEnrollmentService enrollService;

    @Autowired
    private LectureResultService lectureResultService;

    @Autowired
    private StudentCourseEnrollmentService studentCourseEnrollmentService;

    @Autowired
    private TestResultService testResultService;

    @Autowired
    private TeacherRepository teacherRepository;

    @GetMapping(value = "/{id}", produces = "application/json")
    public CourseDto getCourseById(@PathVariable long id) {
        Optional<Course> course = courseService.getCourseById(id);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
        System.out.println(course.get().getLectures());
        if (course.isEmpty()) {
            throw new BaseRestException(HttpStatus.NOT_FOUND, "Not Found");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService userDetails = (CustomUserDetailsService) auth.getPrincipal();

        Student student = studentService.getStudentInfo(userDetails.getUser());
        StudentCourseEnrollment enroll = studentCourseEnrollmentService.getEnrollmentInfo(student, course.get());

        CourseDto courseResult = new CourseDto();
        courseResult.setId(course.get().getId());
        courseResult.setContent(course.get().getContent());
        courseResult.setCourseImgUrl(course.get().getImageUrl());
        courseResult.setCourseTitle(course.get().getTitle());

        courseResult.setLectureDtos(
                course.get().getLectures().stream().map(item -> {
                    LectureDto lectureDto = new LectureDto();
                    lectureDto.setContent(item.getContent());
                    lectureDto.setId(item.getId());
                    lectureDto.setIndex(item.getIndexOrder());
                    lectureDto.setName(item.getName());
                    lectureDto.setResourceUrl(item.getResourceUrl());
                    lectureDto.setWeek(item.getWeek());

                    LectureResult lectureResult = lectureResultService.findFirstByEnrollmentAndLecture(enroll, item);

                    if (lectureResult == null) {
                        lectureDto.setIsFinished(false);
                    } else {

                        lectureDto.setIsFinished(lectureResult.getIsFinished());
                    }

                    return lectureDto;
                }).toList());

        courseResult.setTestDtos(
                course.get().getTests().stream().map(item -> {
                    TestDto testDto = new TestDto();
                    testDto.setContent(item.getContent());
                    testDto.setId(item.getId());
                    testDto.setIndex(item.getIndexOrder());
                    testDto.setName(item.getName());
                    testDto.setWeek(item.getWeek());
                    testDto.setTime(item.getTime());

                    TestResult testResult = testResultService.findFirstByEnrollmentAndTest(enroll, item);

                    if (testResult == null) {
                        testDto.setIsFinished(false);
                        testDto.setMark(0);
                    } else {
                        testDto.setIsFinished(testResult.getIsFinished());
                        testDto.setMark(testResult.getMark());
                    }
                    return testDto;
                }).toList());

        return courseResult;
    }

    @GetMapping(produces = "application/json")
    public List<Course> queryCourses() {
        return courseService.getCourses();
    }

    @GetMapping(value = "/search", produces = "application/json")
    public List<Course> searchCourse(@RequestParam("searchName") String searchName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService currentUser = (CustomUserDetailsService) auth.getPrincipal();

        Teacher teacher = null;
        if (currentUser.getRole().equals("ROLE_TEACHER")) {
            teacher = teacherRepository.findFirstByUserId(currentUser.getUser().getId());
        }

        return courseService.searchCourse(searchName, teacher);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Course createCourse(@RequestBody CourseDto dto) {        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService currentUser = (CustomUserDetailsService) auth.getPrincipal();

        Teacher teacher = teacherRepository.findFirstByUserId(currentUser.getUser().getId());
        Course course = new Course(teacher,
                dto.getCourseImgUrl(),
                dto.getCourseTitle(),
                dto.getContent());                                 

        return courseService.createCourse(dto, course);
    }

    //
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public Course deleteCourse(@PathVariable long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService currentUser = (CustomUserDetailsService) auth.getPrincipal();

        Optional<Course> course = courseService.getCourseById(id);

        if (course.isEmpty()) {
            throw new BaseRestException(HttpStatus.NOT_FOUND, "cant find this course to delete");
        }

        Teacher teacher = course.get().getTeacher();
        if (teacher.getUser().getId() != currentUser.getUser().getId()) {
            throw new BaseRestException(HttpStatus.FORBIDDEN, "you cant delete this course");
        }

        Optional<Course> result = courseService.deleteCourse(id);
        System.out.println("=======================================");
        return result.get();
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public Course updateCourse(@PathVariable long id, @RequestBody CourseDto dto) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        System.out.println(ow.writeValueAsString(dto));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService currentUser = (CustomUserDetailsService) auth.getPrincipal();

        Optional<Course> course = courseService.getCourseById(id);

        if (course.isEmpty()) {
            throw new BaseRestException(HttpStatus.NOT_FOUND, "cant find this course to edit");
        }

        Teacher teacher = course.get().getTeacher();

        if (teacher.getUser().getId() != currentUser.getUser().getId()) {
            throw new BaseRestException(HttpStatus.FORBIDDEN, "you cant edit this course");
        }

        Course result = courseService.updateCourse(dto, id);

        return result;
    }

    @PutMapping(value = "/{id}/lecture/{lectureId}", produces = "application/json")
    public void markLectureAsComplete(@PathVariable long id, @PathVariable long lectureId)
            throws JsonProcessingException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService currentUser = (CustomUserDetailsService) auth.getPrincipal();

        Optional<Course> course = courseService.getCourseById(id);

        if (course.isEmpty()) {
            throw new BaseRestException(HttpStatus.NOT_FOUND, "cant action on this course");
        }

        Lecture lecture = course.get().getLectures().stream().filter(item -> item.getId() == lectureId).findAny()
                .orElse(null);

        if (lecture == null) {
            throw new BaseRestException(HttpStatus.NOT_FOUND, "cant action on this lecture");
        }

        Student student = studentService.getStudentInfo(currentUser.getUser());
        StudentCourseEnrollment enrollment = enrollService.getEnrollmentInfo(student, course.get());
        lectureResultService.updateLectureResult(enrollment, lecture, true);
    }

    @PutMapping(value = "/{id}/test/{testId}", produces = "application/json")
    public void markTestAsCompleteAndUpdateMark(@PathVariable long id, @PathVariable long testId,
            @RequestBody Map<String, Integer> body)
            throws JsonProcessingException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService currentUser = (CustomUserDetailsService) auth.getPrincipal();

        Optional<Course> course = courseService.getCourseById(id);

        if (course.isEmpty()) {
            throw new BaseRestException(HttpStatus.NOT_FOUND, "cant action on this course");
        }

        Test test = course.get().getTests().stream().filter(item -> item.getId() == testId).findAny().orElse(null);

        if (test == null) {
            throw new BaseRestException(HttpStatus.NOT_FOUND, "cant action on this test");
        }

        Student student = studentService.getStudentInfo(currentUser.getUser());
        StudentCourseEnrollment enrollment = enrollService.getEnrollmentInfo(student, course.get());
        testResultService.updateTestResult(enrollment, test, true, body.get("mark"), body.get("finishTime"));
    }
}
